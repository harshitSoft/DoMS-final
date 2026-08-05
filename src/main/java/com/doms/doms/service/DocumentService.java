package com.doms.doms.service;

import com.doms.doms.dto.DocumentRequest;
import com.doms.doms.dto.DocumentResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {


    // Upload document
    DocumentResponseDTO uploadDocument(MultipartFile file);



    // Get all documents
    List<DocumentResponseDTO> getAllDocuments();



    // Get document by id
    DocumentResponseDTO getDocumentById(Long id);



    // Update document
    DocumentResponseDTO updateDocument(Long id,
                                       DocumentRequest request);



    // Delete document
    void deleteDocument(Long id);



    // Download document
    Resource downloadDocument(Long id);



    // Preview document
    Resource viewDocument(Long id);



    // Search documents
    List<DocumentResponseDTO> searchDocuments(String keyword);

}