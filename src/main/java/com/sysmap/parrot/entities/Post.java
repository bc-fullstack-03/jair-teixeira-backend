package com.sysmap.parrot.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Post {
    private UUID id;
    private UUID userId;
    private LocalDateTime time;
    private String content;
    private List<String> photosUri;
    private List<Like> likes;
    private List<Comment> comments;

    public Post(UUID userId, String content) {
        setId();
        setTime();
        this.userId = userId;
        this.content = content;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.photosUri = new ArrayList<>();
    }

    public void setId() {
        this.id = UUID.randomUUID();
    }

    public void setTime() {
        this.time = LocalDateTime.now();
    }
}
