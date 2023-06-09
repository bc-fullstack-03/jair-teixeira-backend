package com.sysmap.parrot.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Friendship {
    private UUID id;
    private List<UUID> friendsList;

    public Friendship(UUID id) {
        this.id = id;
        this.friendsList = new ArrayList<>();
    }
}
