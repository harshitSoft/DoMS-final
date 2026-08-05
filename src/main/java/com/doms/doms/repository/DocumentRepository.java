package com.doms.doms.repository;

import com.doms.doms.entity.Document;
import com.doms.doms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {


    List<Document> findByUploadedBy(User user);


    Optional<Document> findByUploadedByAndId(User user, Long id);


    List<Document> findByFileNameContainingIgnoreCase(String keyword);


    List<Document> findByUploadedByAndFileNameContainingIgnoreCase(
            User user,
            String keyword
    );

}