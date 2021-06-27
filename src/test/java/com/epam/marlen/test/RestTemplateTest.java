package com.epam.marlen.test;

import com.epam.marlen.post.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class RestTemplateTest {

    RestTemplate restTemplate;
    ResponseEntity<Post[]> responseEntity;

    @BeforeClass
    public void SetUp() {
        restTemplate = new RestTemplate();
        responseEntity = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", Post[].class);
    }

    @Test
    public void checkStatusCode() {
        int statusCodeValue = responseEntity.getStatusCodeValue();
        Assert.assertEquals(statusCodeValue, 200);
    }

    @Test(dependsOnMethods = {"checkStatusCode"})
    public void checkResponseHeader() {
        List<String> valueOfContentTypeHeader = responseEntity.getHeaders().get("content-type");
        Assert.assertTrue(valueOfContentTypeHeader.get(0).contains("application/json; charset=utf-8"));
    }

    @Test(dependsOnMethods = {"checkResponseHeader"})
    public void checkResponseBody() {
        int responseBodyContent = responseEntity.getBody().length;
        Assert.assertEquals(responseBodyContent, 10);
    }
}
