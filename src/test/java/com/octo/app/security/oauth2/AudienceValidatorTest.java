package com.octo.app.security.oauth2;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Test class for the {@link AudienceValidator} utility class.
 */
public class AudienceValidatorTest {

    private AudienceValidator validator = new AudienceValidator();
    
    @Test
    public void testInit() {
    	Assert.assertNotNull(ReflectionTestUtils.getField(validator, "error"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testInvalidAudience() {
    	testValidate(Lists.newArrayList("bar"),false);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testValidAudience() {
    	testValidate(Lists.newArrayList("api://default"),true);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testValidAudience2() {
    	testValidate(Lists.newArrayList("account"),true);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testValidAudience3() {
    	testValidate(Lists.newArrayList("account","api://default"),true);
    }
    
    private void testValidate(List<String> claims,boolean valid) {
    	Map<String, Object> claimsMap = new HashMap<>();
    	claims.forEach(claim->claimsMap.put("aud", claim));
        Jwt jwt = mock(Jwt.class);
        when(jwt.getAudience()).thenReturn(new ArrayList(claimsMap.values()));
        OAuth2TokenValidatorResult result = validator.validate(jwt);
        if(valid) {
        	Assert.assertEquals(OAuth2TokenValidatorResult.success(), result);
        }
        else {
        	Assert.assertEquals(1,result.getErrors().size());
        	Assert.assertEquals("The required audience is missing",result.getErrors().iterator().next().getDescription());
        	Assert.assertEquals("invalid_token",result.getErrors().iterator().next().getErrorCode());

        }
    }
}
