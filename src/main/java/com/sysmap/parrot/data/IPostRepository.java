package com.sysmap.parrot.data;

import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPostRepository extends MongoRepository<Post, UUID> {
    Optional<List<Post>> findPostByUserId(UUID userId);
}
