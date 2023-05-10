package com.sysmap.parrot.data;

import com.sysmap.parrot.entities.FriendRequest;
import com.sysmap.parrot.entities.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IFriendRequestRepository extends MongoRepository<FriendRequest, UUID> {
}
