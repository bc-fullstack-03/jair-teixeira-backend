package com.sysmap.parrot.api;

import com.sysmap.parrot.services.friendRequest.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/friendRequest")
public class FriendRequestController {
    @Autowired
    private FriendRequestService _friendRequestService;
    @PostMapping
    public ResponseEntity<?> createFriendship(UUID friendId){
        try {
            _friendRequestService.requestFriend(friendId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> getFriendshipList(UUID id){
        try {
            return ResponseEntity.ok().body(_friendRequestService.getFriendRequestList(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
