package com.sysmap.parrot.api;

import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.services.post.CreatePostRequest;
import com.sysmap.parrot.services.post.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    @Autowired
    private IPostService _postService;
    @GetMapping
    public ResponseEntity<?> getPost(UUID postId){
        try {
            return ResponseEntity.ok().body(_postService.getPost(postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getPostsFromUser(UUID userId){
        try {
            return ResponseEntity.ok().body(_postService.getPostsFromUser(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/feed")
    public ResponseEntity<?> getPostsFromFriends(UUID userId){
        try {
            return ResponseEntity.ok().body(_postService.getPostsFromFriends(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request){
        try {
            _postService.createPost(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/photo/upload")
    public ResponseEntity<?> uploadPhotoProfile(@RequestParam("id") UUID postId,
                                             @RequestParam("photo") MultipartFile photo) {
        try {
            _postService.uploadPhoto(postId, photo);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/photo/remove")
    public ResponseEntity<?> uploadPhotoProfile(@RequestParam("id") UUID postId,
                                             @RequestParam("photoUri") String photoUri) {
        try {
            _postService.removePhoto(postId, photoUri);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
