package com.wiredbrain.friends.controller;

import com.wiredbrain.friends.model.Friend;
import com.wiredbrain.friends.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.Optional;

@RestController
public class FriendController {

    @Autowired
    FriendService friendService;

    @PostMapping("/friend")
    Friend create(@RequestBody Friend friend) throws ValidationException {
        System.out.println("Creating Friend: " + friend.getFirstName());
        if (friend.getId() == 0 && friend.getFirstName() != null
                && friend.getLastName() != null) {
            return friendService.save(friend);
        } else {
            throw new ValidationException("Friend cannot be created");
        }
    }

    @GetMapping("/friend")
    Iterable<Friend> read() {
        System.out.println("Getting all friend");
        return friendService.findAll();
    }

    @PutMapping("/friend")
    ResponseEntity<Friend> update(@RequestBody Friend friend) {
        System.out.println("Updating Friend: " + friend.getFirstName());
        if (friendService.findById(friend.getId()).isPresent()) {
            return new ResponseEntity(friendService.save(friend), HttpStatus.OK);
        } else {
            return new ResponseEntity(friend, HttpStatus.BAD_REQUEST);
        }
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
