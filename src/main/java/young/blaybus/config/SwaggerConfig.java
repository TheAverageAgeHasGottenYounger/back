package young.blaybus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

  public SwaggerConfig(MappingJackson2HttpMessageConverter converter) {
    List<MediaType> supportMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
    supportMediaTypes.add(new MediaType("application", "octet-stream"));
    converter.setSupportedMediaTypes(supportMediaTypes);
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
      .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .build();
    converters.add(new ByteArrayHttpMessageConverter());
    converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
  }

  @Bean
  public OpenAPI KoalaServerAPI() {
    Info info = new Info()
            .title("요양보호사-노인 매칭 서비스 API 명세서")
            .description("Blaybus API")
            .version("v3");

    String jwtSchemeName = "JWT TOKEN";
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

    Components components = new Components()
                  .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                  .name("Authorization")
                  .type(SecurityScheme.Type.HTTP)
                  .scheme("bearer")
                  .bearerFormat("JWT")
                  .in(SecurityScheme.In.HEADER));

    return new OpenAPI()
            .info(info)
            .addServersItem(new Server().url("/"))
            .addSecurityItem(securityRequirement)
            .components(components);
  }
}
