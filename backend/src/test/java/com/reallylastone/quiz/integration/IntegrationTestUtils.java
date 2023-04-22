package com.reallylastone.quiz.integration;

import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

@Component
public class IntegrationTestUtils {
    /**
     * Extracts field value from JSON of given response of a request
     *
     * @param mvcResult response
     * @param field     field to get value of
     * @return value
     * @throws UnsupportedEncodingException if the character encoding is not supported
     */
    public String extract(MvcResult mvcResult, String field) throws UnsupportedEncodingException {
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), "$." + field);
    }
}
