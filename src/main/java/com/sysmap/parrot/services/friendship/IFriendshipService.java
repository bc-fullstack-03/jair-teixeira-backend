package com.sysmap.parrot.services.friendship;

import java.util.List;
import java.util.UUID;

public interface IFriendshipService {
    public void createFriendship(UUID friendId);

    List<UUID> getFriendshipList(UUID userId);
}
