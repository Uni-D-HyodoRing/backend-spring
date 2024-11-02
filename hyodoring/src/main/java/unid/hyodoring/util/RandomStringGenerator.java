package unid.hyodoring.util;

import java.util.Random;

public class RandomStringGenerator {

  public static String generateRandomString(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    StringBuilder result = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      result.append(characters.charAt(index));
    }

    return result.toString();
  }
}
