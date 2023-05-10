package com.sysmap.parrot.services.friendship;

import com.sysmap.parrot.data.IFriendshipRepository;
import com.sysmap.parrot.entities.Friendship;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FrienshipService implements IFriendshipService{
    @Autowired
    private IFriendshipRepository _friendshipRepository;
    @Autowired
    private IUserService _userService;
    @Override
    public void createFriendship(UUID friendId) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var friend = _userService.getUserById(friendId);

        if(_friendshipRepository.existsById(friendId)){
            var friendship = _friendshipRepository.findById(friendId).get();

            friendship.getFriendsList().add(user.getId());

            _friendshipRepository.save(friendship);

        } else {
            var friendship = new Friendship(friendId);
            friendship.getFriendsList().add(user.getId());
            _friendshipRepository.save(friendship);
        }

        if(_friendshipRepository.existsById(user.getId())){
            var friendship = _friendshipRepository.findById(user.getId()).get();

            if (friendship.getFriendsList().contains(friendId)){
                throw new RuntimeException("Friendship is already registered!");
            }

            friendship.getFriendsList().add(friendId);

            _friendshipRepository.save(friendship);

        }else {
            var friendship = new Friendship(user.getId());
            friendship.getFriendsList().add(friendId);

            _friendshipRepository.save(friendship);
        }
    }
    @Override
    public List<UUID> getFriendshipList(UUID userId) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if(userId==null){ userId=user.getId(); }

        return _friendshipRepository.findById(userId).get().getFriendsList();
    }
}
