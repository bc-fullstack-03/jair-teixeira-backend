package com.sysmap.parrot.services.post;

import com.sysmap.parrot.entities.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    String createPost(CreatePostRequest post);

    void uploadPhoto(UUID postId, MultipartFile photo);
    Post getPost(UUID id);

    void removePhoto(UUID postId, String photoUri);

    List<Post> getPostsFromUser(UUID userId);

    List<Post> getPostsFromFriends(UUID userId);
}
