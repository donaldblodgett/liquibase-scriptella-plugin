package com.donaldblodgett.scriptella.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class TestUtils {
  public static String randomString() {
    return RandomStringUtils.randomAlphanumeric(randomInt());
  }
  
  public static int randomInt() {
    return RandomUtils.nextInt(5, 10);
  }
}
