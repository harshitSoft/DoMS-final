package com.doms.doms.controller;

import com.doms.doms.dto.DocumentRequest;
import com.doms.doms.dto.DocumentResponseDTO;
import com.doms.doms.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin
public class DocumentController {


    private final DocumentService documentService;



    // Upload Document
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponseDTO> uploadDocument(
            @RequestParam("file") MultipartFile file) {


        return ResponseEntity.ok(
                documentService.uploadDocument(file)
        );
    }





    // Get All Documents
    @GetMapping
    public ResponseEntity<List<DocumentResponseDTO>> getAllDocuments() {


        return ResponseEntity.ok(
                documentService.getAllDocuments()
        );
    }





    // Get Document By ID
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDTO> getDocumentById(
            @PathVariable Long id) {


        return ResponseEntity.ok(
                documentService.getDocumentById(id)
        );
    }





    // Download Document
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Long id) {


        Resource resource =
                documentService.downloadDocument(id);



        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" 
                                + resource.getFilename()
                                + "\""
                )
                .body(resource);
    }





    // Preview / View Document
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewDocument(
            @PathVariable Long id) {


        Resource resource =
                documentService.viewDocument(id);



        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }





    // Update Document
    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponseDTO> updateDocument(
            @PathVariable Long id,
            @RequestBody DocumentRequest request) {


        return ResponseEntity.ok(
                documentService.updateDocument(id, request)
        );
    }





    // Delete Document
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(
            @PathVariable Long id) {


        documentService.deleteDocument(id);


        return ResponseEntity.ok(
                "Document deleted successfully"
        );
    }
@GetMapping("/search")
public ResponseEntity<List<DocumentResponseDTO>> searchDocuments(
        @RequestParam String keyword
) {

    return ResponseEntity.ok(
            documentService.searchDocuments(keyword)
    );
}
}