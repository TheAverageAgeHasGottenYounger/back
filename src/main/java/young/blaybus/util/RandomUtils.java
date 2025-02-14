package young.blaybus.util;

import java.util.UUID;

public class RandomUtils {

  public static String getRandomString() {
    return UUID.randomUUID().toString();
  }
}
