package young.blaybus.config.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.StringUtils;
import young.blaybus.util.CryptoUtils;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

  @Override
  public String convertToDatabaseColumn(String str) {
    if (!StringUtils.hasText(str)) {
      return null;
    }
    return CryptoUtils.encode(str);
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if (!StringUtils.hasText(dbData)) {
      return null;
    }
    return CryptoUtils.decode(dbData);
  }
}
