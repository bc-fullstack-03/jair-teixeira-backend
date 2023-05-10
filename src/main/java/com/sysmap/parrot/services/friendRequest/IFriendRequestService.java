package com.sysmap.parrot.services.friendRequest;

import java.util.List;
import java.util.UUID;

public interface IFriendRequestService {
    List<UUID> getFriendRequestList(UUID id);
    void requestFriend(UUID id);

}
