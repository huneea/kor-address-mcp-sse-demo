package com.example.koraddressmcpssedemo.client;

public interface JusoClient {

    JusoApiResponse find(int currentPage, int countPerPage, String keyword);
}
