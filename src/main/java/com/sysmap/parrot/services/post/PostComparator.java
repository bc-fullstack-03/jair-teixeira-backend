package com.sysmap.parrot.services.post;

import com.sysmap.parrot.entities.Post;

import java.util.Comparator;

public class PostComparator implements Comparator<Post> {

    @Override
    public int compare(Post post, Post t1) {
        if (post.getTime().isBefore(t1.getTime())) {
            return 1;
        } else {
            return -1;
        }
    }
}
