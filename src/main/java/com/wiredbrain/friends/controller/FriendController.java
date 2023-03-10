package com.wiredbrain.friends.controller;

import com.wiredbrain.friends.model.Friend;
import com.wiredbrain.friends.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FriendController {

    @Autowired
    FriendService friendService;

    @PostMapping("/friend")
    Friend create(@RequestBody Friend friend) {
        System.out.println("Creating Friend: " + friend.getFirstName());
        return friendService.save(friend);
    }

    @GetMapping("/friend")
    Iterable<Friend> read() {
        System.out.println("Getting all friend");
        return friendService.findAll();
    }

    @PutMapping("/friend")
    Friend update(@RequestBody Friend friend) {
        System.out.println("Updating Friend: " + friend.getFirstName());
        return friendService.save(friend);
    }

    @DeleteMapping("/friend/{id}")
    void delete(@PathVariable Integer id) {
        System.out.println("Deleting Friend with id: " + id);
        friendService.deleteById(id);
        System.out.println("Friend deleted");
    }

    @GetMapping("friend/{id}")
    Optional<Friend> findById(@PathVariable Integer id) {
        return friendService.findById(id);
    }

    @GetMapping("friend/search")
    Iterable<Friend> findAll(@RequestParam(value = "first", required = false) String firstName,
                             @RequestParam(value = "last", required = false) String lastName) {
        if (firstName != null && lastName != null) {
            return friendService.findByFirstNameAndLastName(firstName, lastName);
        } else {
            if (firstName != null) {
                return friendService.findByFirstName(firstName);
            } else if (lastName != null) {
                return friendService.findByLastName(lastName);
            }
        }
        return friendService.findAll();
    }
}
