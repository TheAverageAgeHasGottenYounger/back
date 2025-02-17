package young.blaybus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.map.controller.request.SearchPoiRequest;
import young.blaybus.map.controller.response.geocoding.Coordinate;
import young.blaybus.map.service.MapService;

@SpringBootTest
public class BlaybusTest {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private MapService mapService;

  @Test
  void poiTest() {
    SearchPoiRequest request = new SearchPoiRequest("경기도 용인시 수지구 성복역");

    mapService.getPoiList(request);
  }

  @Test
  void geocodingTest() {
    Coordinate response1 = mapService.geocoding("서울시"+" 강남구"+" 역삼동");
    Coordinate response2 = mapService.geocoding("부산시"+" 해운대"+" 센텀");
//      Coordinate response3 = mapService.geocoding("대전시", "유성구", "어은동");
//      Coordinate response4 = mapService.geocoding("인천", "남동", "구월");
//      Coordinate response5 = mapService.geocoding("대구시", "동구", "신천동");
//      Coordinate response6 = mapService.geocoding("서울", "종로", "청운동");
//      Coordinate response7 = mapService.geocoding("경기도", "성남시", "분당구");
//      Coordinate response8 = mapService.geocoding("강원", "춘천", "교동");
//      Coordinate response9 = mapService.geocoding("전북", "전주", "객사");

    Double distance = mapService.getDistance(response1, response2);
    System.out.println("distance = " + distance);

  }

  @Test
  public void cryptoTest() {
    Member member = memberRepository.save(
      Member.builder()
        .id("id")
        .password("password")
        .profileUrl("profileUrl")
        .phoneNumber("010-1234-1234")
        .build()
    );

    System.out.println("member.getPhoneNumber() = " + member.getPhoneNumber());

  }

}
