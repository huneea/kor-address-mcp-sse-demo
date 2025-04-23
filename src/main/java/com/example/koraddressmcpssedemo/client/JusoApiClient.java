package com.example.koraddressmcpssedemo.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class JusoApiClient implements JusoClient {
    public static final String BASE_URL = "https://business.juso.go.kr";
    public static final String SEARCH_JUSO_PATH = "/addrlink/addrLinkApi.do";
    public static final String RESULT_TYPE_JSON = "json";
    private final RestClient restClient;

    @Value("${juso.api.key}")
    private String apiKey;


    @Override
    public JusoApiResponse find(int currentPage, int countPerPage, String keyword) {

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("confmKey", apiKey); // 신청시 발급받은 승인키
        formData.add("currentPage", currentPage);
        formData.add("countPerPage", countPerPage);
        formData.add("resultType", RESULT_TYPE_JSON);
        formData.add("keyword", keyword);

        return restClient.post()
                         .uri(BASE_URL + SEARCH_JUSO_PATH)
                         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                         .body(formData)
                         .retrieve()
                         .toEntity(JusoApiResponse.class)
                         .getBody();
    }

}
