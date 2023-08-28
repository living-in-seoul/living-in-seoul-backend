package com.gavoza.backend.domain.post.repository;

import com.gavoza.backend.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByContentContaining(String keyword, Pageable pageable);

    Page<Post> findAllByHashtagContaining(String keyword, Pageable pageable);

    Page<Post> findAllByHashtagContainingOrderByPostViewCountDesc(String hashtagName, Pageable pageable);

    Page<Post> findAllByHashtagContainingOrderByCreatedAtDesc(String hashtagName, Pageable pageable);

    Page<Post> findAllByCategoryAndHashtagContainingOrderByPostViewCountDesc(String category, String hashtagName, Pageable pageable);

    Page<Post> findAllByCategoryAndHashtagContainingOrderByCreatedAtDesc(String category, String hashtagName, Pageable pageable);

    List<Post> findAllBycategory(String category);

    Page<Post> findAllByUserId(Long id, Pageable pageable);
}
