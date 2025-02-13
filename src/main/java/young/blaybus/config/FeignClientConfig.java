package young.blaybus.config;

import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import java.lang.System.Logger.Level;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

  @Bean
  Request.Options requestOptions() {
    return new Request.Options(15, TimeUnit.SECONDS, 15, TimeUnit.SECONDS, false);
  }

  @Bean
  Retryer retry() {
    return new Retryer.Default(500, 1000, 2);
  }

  @Bean
  Level feignLoggerLevel() {
    return Level.ALL;
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("Content-Type", "application/json");
      requestTemplate.header("Accept", "application/json");
    };
  }
}
