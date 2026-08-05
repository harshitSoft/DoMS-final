package com.doms.doms.dto;

import lombok.Data;

@Data
public class DocumentRequest {

    private String fileName;
    private String fileType;
    private String filePath;
    private Long fileSize;
}