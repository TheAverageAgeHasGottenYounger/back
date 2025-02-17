package young.blaybus.map.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.map.City;
import young.blaybus.map.Dong;
import young.blaybus.map.GuGun;
import young.blaybus.map.SkClient;
import young.blaybus.map.controller.request.SearchPoiRequest;
import young.blaybus.map.controller.response.geocoding.Coordinate;
import young.blaybus.map.controller.response.geocoding.GeocodingResponse;
import young.blaybus.map.controller.response.poi.ListPoiResponse;
import young.blaybus.map.controller.response.matrix.MatrixResponse;
import young.blaybus.map.controller.response.poi.PoiDto;
import young.blaybus.map.repository.CityRepository;
import young.blaybus.map.repository.DongRepository;
import young.blaybus.map.repository.GuGunRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MapService {

  private final SkClient skClient;
  private final AmazonS3 amazonS3;

  private final static int version = 1;
  private final static int count = 1;
  private final static String addressFlag = "F00";
  private final static String coordType = "WGS84GEO";
  private final CityRepository cityRepository;
  private final GuGunRepository guGunRepository;
  private final DongRepository dongRepository;

  @Value("${sk.app-key}")
  private String appKey;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  private final static String mapFileUrl = "전국행정동리스트.xlsx";

  /**
   * 주소 -> 위도/경도 변환
   */
  public Coordinate geocoding(String cityDo, String guGun, String dong) {

    // 상세주소 받게되면 dong 뒤에 맨 앞 공백과 함께 추가한 뒤 붙여서 요청
    GeocodingResponse response = skClient.geocoding(
      appKey, version, cityDo + " " + guGun + " " + dong, addressFlag, coordType, count
    );

    return response.getCoordinateInfo().getCoordinate().get(0);
  }

  /**
   * 좌표 간 거리 계산
   */
  public Double getDistance(Coordinate start, Coordinate end) {
    String requestBody = String.format(
      "{\"origins\":[{\"lon\":\"%f\",\"lat\":\"%f\"}],\"destinations\":[{\"lon\":\"%f\",\"lat\":\"%f\"}]}",
      start.getLon(), start.getLat(), end.getLon(), end.getLat()
    );

    MatrixResponse matrix = skClient.matrix(appKey, version, requestBody);
    return matrix.getMatrix().get(0).getDistance();

  }

  public ListPoiResponse getPoiList(SearchPoiRequest request) {

    ListPoiResponse response = skClient.poi(appKey, version, request.address());
    for (PoiDto poi : response.getSearchPoiInfo().getPoiResponse().getPoiList()) {
      System.out.println("poi = " + poi);
    }
    return response;
  }

  public void readMapExcel() {
    // S3에서 파일 가져오기
    S3Object s3Object = amazonS3.getObject(bucketName, mapFileUrl);
    InputStream inputStream = s3Object.getObjectContent();

    XSSFWorkbook workbook;
    try {
      workbook = new XSSFWorkbook(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Sheet sheet = workbook.getSheetAt(0);  // 첫 번째 시트 가져오기

    for (Row row : sheet) {
      String root = row.getCell(0).getStringCellValue();  // 대분류
      String city = row.getCell(1).getStringCellValue();  // 시/군
      String guGun = row.getCell(2).getStringCellValue();   // 구
      String dong = row.getCell(3).getStringCellValue(); // 동/면/리
      if ((!StringUtils.hasText(root) && !StringUtils.hasText(city)) || !StringUtils.hasText(guGun) || !StringUtils.hasText(dong))
        continue;
      if (Objects.equals(city, "시 / 군")) continue;

      City savedCity;
      if (StringUtils.hasText(root) && StringUtils.hasText(city)) {
        savedCity = cityRepository.save(City.builder().id(city).build());
      }
      else if (StringUtils.hasText(root))
        savedCity = cityRepository.save(City.builder().id(root).build());
      else savedCity = cityRepository.save(City.builder().id(city).build());

      GuGun savedGuGun = guGunRepository.save(GuGun.builder().id(guGun).city(savedCity).build());
      dongRepository.save(Dong.builder().id(dong).guGun(savedGuGun).build());
    }

    try {
      workbook.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      inputStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<String> getCityList() {
    return cityRepository.findAll().stream().map(City::getId).toList();
  }

  public List<String> getGuGunList(String city) {
    City cityEntity = cityRepository.findById(city)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST));
    return guGunRepository.findAllByCity(cityEntity).stream().map(GuGun::getId).toList();
  }

  public List<String> getDongList(String guGun) {
    GuGun guGunEntity = guGunRepository.findById(guGun)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST));
    return dongRepository.findAllByGuGun(guGunEntity).stream().map(Dong::getId).toList();
  }
}
