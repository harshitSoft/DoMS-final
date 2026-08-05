package com.doms.doms.serviceImpl;
import org.springframework.core.io.Resource;
import com.doms.doms.dto.DocumentRequest;
import com.doms.doms.dto.DocumentResponseDTO;
import com.doms.doms.dto.UserResponseDTO;
import com.doms.doms.entity.Document;
import com.doms.doms.entity.Role;
import com.doms.doms.entity.User;
import com.doms.doms.repository.DocumentRepository;
import com.doms.doms.repository.UserRepository;
import com.doms.doms.service.DocumentService;
import com.doms.doms.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {


    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;



    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }



    private DocumentResponseDTO convertToDTO(Document document) {

        User user = document.getUploadedBy();


        UserResponseDTO userDTO =
                new UserResponseDTO(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRole()
                );


        return new DocumentResponseDTO(
                document.getId(),
                document.getFileName(),
                document.getFilePath(),
                document.getFileType(),
                document.getFileSize(),
                document.getDescription(),
                userDTO
        );
    }




    @Override
    public DocumentResponseDTO uploadDocument(MultipartFile file) {


        String filePath =
                fileStorageService.saveFile(file);


        User currentUser =
                getCurrentUser();



        Document document =
                Document.builder()
                        .fileName(file.getOriginalFilename())
                        .filePath(filePath)
                        .fileType(file.getContentType())
                        .fileSize(file.getSize())
                        .description("Uploaded Document")
                        .uploadedBy(currentUser)
                        .build();



        Document savedDocument =
                documentRepository.save(document);



        return convertToDTO(savedDocument);
    }





    @Override
    public List<DocumentResponseDTO> getAllDocuments() {


        User currentUser =
                getCurrentUser();



        List<Document> documents;


        if(currentUser.getRole() == Role.ROLE_ADMIN){

            documents =
                    documentRepository.findAll();

        }else{

            documents =
                    documentRepository.findByUploadedBy(currentUser);
        }



        return documents.stream()
                .map(this::convertToDTO)
                .toList();
    }





    @Override
    public DocumentResponseDTO getDocumentById(Long id) {


        User currentUser =
                getCurrentUser();


        Document document;



        if(currentUser.getRole() == Role.ROLE_ADMIN){


            document =
                    documentRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Document not found"));


        }else{


            document =
                    documentRepository.findByUploadedByAndId(currentUser,id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "You are not authorized to access this document"));
        }



        return convertToDTO(document);
    }





    @Override
    public void deleteDocument(Long id) {


        User currentUser =
                getCurrentUser();



        Document document;



        if(currentUser.getRole() == Role.ROLE_ADMIN){


            document =
                    documentRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Document not found"));


        }else{


            document =
                    documentRepository.findByUploadedByAndId(currentUser,id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "You are not authorized to delete this document"));
        }




        if(document.getFilePath()!=null){

            fileStorageService.deleteFile(
                    document.getFilePath()
            );
        }



        documentRepository.delete(document);
    }





    @Override
    public DocumentResponseDTO updateDocument(Long id,
                                              DocumentRequest request) {


        User currentUser =
                getCurrentUser();



        Document document;



        if(currentUser.getRole() == Role.ROLE_ADMIN){


            document =
                    documentRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Document not found"));


        }else{


            document =
                    documentRepository.findByUploadedByAndId(currentUser,id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "You are not authorized to update this document"));
        }




        document.setFileName(request.getFileName());
        document.setFileType(request.getFileType());
        document.setFileSize(request.getFileSize());



        Document updatedDocument =
                documentRepository.save(document);



        return convertToDTO(updatedDocument);
    }






    @Override
    public Resource downloadDocument(Long id) {


        User currentUser =
                getCurrentUser();



        Document document;



        if(currentUser.getRole() == Role.ROLE_ADMIN){


            document =
                    documentRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Document not found"));


        }else{


            document =
                    documentRepository.findByUploadedByAndId(currentUser,id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "You are not authorized to download this document"));
        }



        return fileStorageService.downloadFile(
                document.getFilePath()
        );
    }
@Override
public Resource viewDocument(Long id) {


    User currentUser = getCurrentUser();


    Document document;


    if(currentUser.getRole() == Role.ROLE_ADMIN){


        document =
                documentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Document not found"));


    }else{


        document =
                documentRepository.findByUploadedByAndId(currentUser,id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "You are not authorized to view this document"));
    }



    return fileStorageService.viewFile(
            document.getFilePath()
    );
}
@Override
public List<DocumentResponseDTO> searchDocuments(String keyword) {


    User currentUser = getCurrentUser();


    List<Document> documents;


    if(currentUser.getRole() == Role.ROLE_ADMIN){


        documents =
                documentRepository.findByFileNameContainingIgnoreCase(keyword);


    }else{


        documents =
                documentRepository
                .findByUploadedByAndFileNameContainingIgnoreCase(
                        currentUser,
                        keyword
                );
    }



    return documents.stream()
            .map(this::convertToDTO)
            .toList();
}
}