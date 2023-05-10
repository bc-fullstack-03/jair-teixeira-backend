package com.sysmap.parrot.services.friendRequest;

import com.sysmap.parrot.data.IFriendRequestRepository;
import com.sysmap.parrot.entities.FriendRequest;
import com.sysmap.parrot.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FriendRequestService implements IFriendRequestService{
    @Autowired
    private IFriendRequestRepository _friendRequestRepository;

    @Override
    public List<UUID> getFriendRequestList(UUID id) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if(_friendRequestRepository.existsById(id)){
            return _friendRequestRepository.findById(id).get().getFriendRequestsList();
        }

        throw new RuntimeException("No friend requests exists!");

    }

    @Override
    public void requestFriend(UUID id) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var friend = _friendRequestRepository.findById(id).get();

        if (friend.getFriendRequestsList().contains(user.getId())){
            throw new RuntimeException("Already done!");
        }

        if(_friendRequestRepository.existsById(id)){
            var request = _friendRequestRepository.findById(id).get();

            request.getFriendRequestsList().add(user.getId());

            _friendRequestRepository.save(request);

        }else {
            var request = new FriendRequest(id);
            request.getFriendRequestsList().add(user.getId());

            _friendRequestRepository.save(request);
        }
    }
}
