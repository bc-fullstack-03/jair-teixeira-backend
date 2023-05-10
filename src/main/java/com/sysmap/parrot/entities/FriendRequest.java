package com.sysmap.parrot.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class FriendRequest {
    private UUID id;
    private List<UUID> friendRequestsList;

    public FriendRequest(UUID id) {
        this.id = id;
        this.friendRequestsList = new ArrayList<>();
    }
}
