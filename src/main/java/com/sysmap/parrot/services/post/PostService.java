package com.sysmap.parrot.services.post;

import com.sysmap.parrot.data.IPostRepository;
import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.services.fileUpload.IFileUploadService;
import com.sysmap.parrot.services.friendship.IFriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PostService implements IPostService{
    @Autowired
    private IPostRepository _postRepository;
    @Autowired
    private IFileUploadService _fileUploadService;
    @Autowired
    private IFriendshipService _friendshipService;


    @Override
    public String createPost(CreatePostRequest request) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var post = new Post(user.getId(), request.getContent());

        _postRepository.save(post);
        return post.getId().toString();
    }

    @Override
    public void uploadPhoto(UUID postId, MultipartFile photo){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var post = getPost(postId);

        if (!user.getId().equals(post.getUserId())){
            throw new RuntimeException("Invalid credential!");
        }
        var photoUri = "";

        try {
            var fileName = UUID.randomUUID() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
            photoUri = _fileUploadService.upload(photo, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        post.getPhotosUri().add(photoUri);
        _postRepository.save(post);
    }
    public Post getPost(UUID id){
        return _postRepository.findById(id).get();
    }

    @Override
    public void removePhoto(UUID postId, String photoUri) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var post = getPost(postId);

        if (!user.getId().equals(post.getUserId())){
            throw new RuntimeException("Invalid credential!");
        }
        post.getPhotosUri().remove(photoUri);
        _postRepository.save(post);
    }

    @Override
    public List<Post> getPostsFromUser(UUID userId) {
        var posts = _postRepository.findPostByUserId(userId).get();

        Collections.sort(posts, new PostComparator());

        if (posts.isEmpty()){
            throw new RuntimeException("No registered posts!");
        }
        return posts;
    }

    @Override
    public List<Post> getPostsFromFriends(UUID userId) {
        List<Post> feed = new ArrayList<>();

        var friendsList = _friendshipService.getFriendshipList(userId);
        for(UUID friend : friendsList){
            for (Post post:getPostsFromUser(friend)){
                feed.add(post);
            }
        }
        if (feed.isEmpty()){
            throw new RuntimeException("No registered posts!");
        }

        Collections.sort(feed, new PostComparator());

        return feed;
    }
}
