package young.blaybus.map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import young.blaybus.config.FeignClientConfig;
import young.blaybus.map.controller.response.geocoding.GeocodingResponse;
import young.blaybus.map.controller.response.poi.ListPoiResponse;
import young.blaybus.map.controller.response.matrix.MatrixResponse;

@FeignClient(name = "sk", url = "${sk.url}", configuration = FeignClientConfig.class)
public interface SkClient {

  @GetMapping("/geo/fullAddrGeo")
  GeocodingResponse geocoding(
    @RequestHeader("appKey") String appKey,
    @RequestParam("version") int version,
    @RequestParam("fullAddr") String address,
    @RequestParam("addressFlag") String addressFlag,
    @RequestParam("coordType") String coordType,
    @RequestParam("count") int count
  );

  @PostMapping("/matrix")
  MatrixResponse matrix(
    @RequestHeader("appKey") String appKey,
    @RequestParam("version") int version,
    @RequestBody String requestBody
  );

  @GetMapping("/pois")
  ListPoiResponse poi(
    @RequestHeader("appKey") String appKey,
    @RequestParam("version") int version,
    @RequestParam("searchKeyword") String searchKeyword
  );

}
