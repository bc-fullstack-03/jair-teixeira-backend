package com.sysmap.parrot.api;

import com.sysmap.parrot.data.IFriendshipRepository;
import com.sysmap.parrot.services.friendship.IFriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/friendship")
public class FriendshipController {
    @Autowired
    private IFriendshipService _friendshipService;

    @PostMapping
    public ResponseEntity<?> createFriendship(UUID friendId){
        try {
            _friendshipService.createFriendship(friendId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> getFriendshipList(@RequestParam(required = false) UUID userId){
        try {
            return ResponseEntity.ok().body(_friendshipService.getFriendshipList(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
