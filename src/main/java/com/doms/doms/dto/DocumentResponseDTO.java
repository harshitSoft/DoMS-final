package com.doms.doms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentResponseDTO {

    private Long id;

    private String fileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    private String description;

    private UserResponseDTO uploadedBy;
}