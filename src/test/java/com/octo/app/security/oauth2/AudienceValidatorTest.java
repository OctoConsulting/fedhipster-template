package com.octo.app.security.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Test class for the {@link AudienceValidator} utility class.
 */
public class AudienceValidatorTest {

    private AudienceValidator validator = new AudienceValidator();

    @Test
    public void testInvalidAudience() {
        Map<String, String> claims = new HashMap<>();
        claims.put("aud", "bar");
        Jwt badJwt = mock(Jwt.class);
        when(badJwt.getAudience()).thenReturn(claims.values().stream().collect(Collectors.toList()));
        assertThat(validator.validate(badJwt).hasErrors()).isTrue();
    }

    @Test
    public void testValidAudience() {
        Map<String, String> claims = new HashMap<>();
        claims.put("aud", "api://default");
        Jwt badJwt = mock(Jwt.class);
        when(badJwt.getAudience()).thenReturn(claims.values().stream().collect(Collectors.toList()));
        assertThat(validator.validate(badJwt).hasErrors()).isFalse();
    }
}
