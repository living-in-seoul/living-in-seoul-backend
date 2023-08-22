package com.gavoza.backend.domain.post.controller;

import com.gavoza.backend.domain.post.dto.PostRequestDto;
import com.gavoza.backend.domain.post.response.PostListResponse;
import com.gavoza.backend.domain.post.response.PostResponse;
import com.gavoza.backend.domain.post.service.PostService;
import com.gavoza.backend.domain.user.entity.User;
import com.gavoza.backend.global.exception.MessageResponseDto;
import com.gavoza.backend.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    //게시글 생성
    @PostMapping
    public MessageResponseDto uploadFile(
            @RequestPart("post") PostRequestDto requestDto,
            @RequestPart("photos") List<MultipartFile> photos,
            @AuthenticationPrincipal UserDetailsImpl userDetails
          ) throws IOException {
        User user = userDetails.getUser();
        return postService.upload(requestDto,user,photos);
    }

    //게시글 수정
    @PutMapping("/{postId}")
    public MessageResponseDto updatePost(@PathVariable("postId") Long postId,
                                         @RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.updatePost(postId, requestDto, user);

        return new MessageResponseDto("게시글 수정 성공");
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public MessageResponseDto deletePost(@PathVariable("postId") Long postId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();

        postService.deletePost(postId, user);

        return new MessageResponseDto("게시글 삭제 성공");
    }

    //유저 게시글 상세 조회
    @GetMapping("/like/get/{postId}")
    public PostResponse getLikeOnePost(@PathVariable("postId") Long postId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return postService.getLikeOnePost(postId, user);
    }

    //게시글 상세 조회
    @GetMapping("/get/{postId}")
    public PostResponse getOnePost(@PathVariable("postId") Long postId){
        return postService.getOnePost(postId);
    }


    //유저 게시글 전체 조회(커뮤티니)
    @GetMapping("/like/get")
    public PostListResponse getLikePost(@RequestParam int page,
                                    @RequestParam int size,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return postService.getLikePost(page-1,size, user);
    }

    //게시글 전체 조회(커뮤티니)
    @GetMapping("/get")
    public PostListResponse getPost(@RequestParam int page,
                                        @RequestParam int size){
        return postService.getPost(page-1,size);
    }

    //유저 게시글 검색
    @GetMapping("/like/get/search")
    public PostListResponse searchLikePosts(@RequestParam int page,
                                        @RequestParam int size,
                                        @RequestParam String keyword,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return postService.searchLikePosts(page, size, keyword, user);
    }

    //게시글 검색
    @GetMapping("/get/search")
    public PostListResponse searchPosts(@RequestParam int page,
                                            @RequestParam int size,
                                            @RequestParam String keyword){
        return postService.searchPosts(page, size, keyword);
    }
}
