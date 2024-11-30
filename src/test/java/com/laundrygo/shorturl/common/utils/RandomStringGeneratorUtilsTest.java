package com.laundrygo.shorturl.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class RandomStringGeneratorUtilsTest {

    @Test
    @DisplayName("주어진 길이의 문자열을 생성한다")
    void shouldGenerateStringWithSpecifiedLength() {
        // given
        int length = 8;

        // when
        String result = RandomStringGeneratorUtils.generateRandomString(length);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(length);
    }

    @Test
    @DisplayName("생성된 문자열이 알파벳 대소문자로만 구성되어 있는지 확인한다")
    void shouldGenerateOnlyAlphabeticCharacters() {
        // given
        int length = 10;

        // when
        String result = RandomStringGeneratorUtils.generateRandomString(length);

        // then
        assertThat(result).matches("^[a-zA-Z]+$");
    }

    @Test
    @DisplayName("매번 호출 시 서로 다른 문자열을 생성한다")
    void shouldGenerateDifferentStringsEachTime() {
        // given
        int length = 12;

        // when
        String result1 = RandomStringGeneratorUtils.generateRandomString(length);
        String result2 = RandomStringGeneratorUtils.generateRandomString(length);

        // then
        assertThat(result1).isNotEqualTo(result2);
    }
}