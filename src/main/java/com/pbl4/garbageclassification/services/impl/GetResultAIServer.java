package com.pbl4.garbageclassification.services.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class GetResultAIServer {
    //private final String targetUrl = "http://localhost:5000/pred";
    private final String targetUrl = "http://172.20.10.7:5000/pred";
    public String callExternalApiWithFormData(byte[] binary) throws IOException {

        // Create MultiValueMap to represent form-data

        String base64String = Base64.getEncoder().encodeToString(binary);
        System.out.println("base64"+ base64String);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("img", new HttpEntity<>(base64String, getHeaders()));

        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make the HTTP POST request with form-data
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                targetUrl,
                HttpMethod.POST,
                new HttpEntity<>(formData, getHeaders()),
                String.class
        );

        // Handle the response
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            // Process the response body
            System.out.println("Response from API: " + responseBody);
            return responseBody;
        } else {
            // Handle error cases
            System.out.println("Error calling external API. Status code: " + responseEntity.getStatusCodeValue());
            return null;
        }
    }

    // Helper method to set headers for form-data
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }
}
