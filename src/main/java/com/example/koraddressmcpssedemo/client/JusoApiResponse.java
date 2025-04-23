package com.example.koraddressmcpssedemo.client;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("주소 API 응답")
public class JusoApiResponse {
    private JusoApiResult results;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JusoApiResult {
        private JusoApiCommon common;
        @JsonProperty("juso")
        private List<Juso> jusos;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonClassDescription("주소 API 응답 중 공통 정보")
    public static class JusoApiCommon {
        @JsonPropertyDescription("에러 메시지")
        private String errorMessage;
        @JsonPropertyDescription("상세 건물명")
        private int countPerPage;
        @JsonPropertyDescription("페이지당 출력할 결과 Row 수")
        private int totalCount;
        @JsonPropertyDescription("에러 코드")
        private String errorCode;
        @JsonPropertyDescription("페이지 번호")
        private int currentPage;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonClassDescription("주소 API 응답 중 주소 정보")
    public static class Juso {
        @JsonPropertyDescription("상세 건물명")
        private String detBdNmList;
        @JsonPropertyDescription("도로명 주소 (영문)")
        private String engAddr;
        @JsonPropertyDescription("도로 명")
        private String rn;
        @JsonPropertyDescription("읍면동 명")
        private String emdNm;
        @JsonPropertyDescription("우편번호")
        private String zipNo;
        @JsonPropertyDescription("도로명 주소 참고항목")
        private String roadAddrPart2;
        @JsonPropertyDescription("읍면동 일련번호")
        private String emdNo;
        @JsonPropertyDescription("시군구 명")
        private String sggNm;
        @JsonPropertyDescription("지번 주소")
        private String jibunAddr;
        @JsonPropertyDescription("시도 명")
        private String siNm;
        @JsonPropertyDescription("도로명 주소 (참고항목 제외)")
        private String roadAddrPart1;
        @JsonPropertyDescription("건물 명")
        private String bdNm;
        @JsonPropertyDescription("행정구역 코드")
        private String admCd;
        @JsonPropertyDescription("지하여부 (0:지상, 1:지하)")
        private String udrtYn;
        @JsonPropertyDescription("지번 본번 (번지)")
        private String lnbrMnnm;
        @JsonPropertyDescription("전체 도로명주소")
        private String roadAddr;
        @JsonPropertyDescription("지번 부번 (호)")
        private String lnbrSlno;
        @JsonPropertyDescription("건물 본번")
        private String buldMnnm;
        @JsonPropertyDescription("공동주택여부(1:공동주택, 0:비공동주택)")
        private String bdKdcd;
        @JsonPropertyDescription("법정리 명")
        private String liNm;
        @JsonPropertyDescription("도로명 코드")
        private String rnMgtSn;
        @JsonPropertyDescription("산여부(0 : 대지, 1 : 산)")
        private String mtYn;
        @JsonPropertyDescription("건물 관리번호")
        private String bdMgtSn;
        @JsonPropertyDescription("건물 부번")
        private String buldSlno;
    }
}


