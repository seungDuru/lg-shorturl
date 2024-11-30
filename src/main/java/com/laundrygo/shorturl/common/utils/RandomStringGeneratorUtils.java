package com.laundrygo.shorturl.common.utils;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomStringGeneratorUtils {

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, length)
                .mapToObj(i -> (char) (random.nextBoolean()
                        ? 'A' + random.nextInt(26)
                        : 'a' + random.nextInt(26)))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
