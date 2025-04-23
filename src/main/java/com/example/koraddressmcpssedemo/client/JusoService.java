package com.example.koraddressmcpssedemo.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JusoService {
    private final JusoClient jusoClient;


    @Tool(name = "kor-address-find-address", description = "주소를 검색합니다.")
    public JusoApiResponse find(
        @ToolParam(description = "현재 페이지 번호")
        int currentPage,
        @ToolParam(description = "페이지당 출력할 결과 Row 수")
        int countPerPage,
        @ToolParam(description = "검색 키워드")
        String searchKeyword
                               ) {
        return jusoClient.find(currentPage, countPerPage, searchKeyword);
    }

}
