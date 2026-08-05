package com.doms.doms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    private String description;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
}