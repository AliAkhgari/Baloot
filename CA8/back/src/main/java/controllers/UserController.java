package controllers;

import entities.User;
import exceptions.InvalidCreditRange;
import exceptions.NotExistentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotExistentUser e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/users/{id}/credit")
    public ResponseEntity<String> addCredit(@PathVariable String id, @RequestBody Map<String, String> input) {
        try {
            float credit = Float.parseFloat(input.get("credit"));
            User user = userService.getUserById(id);
            user.addCredit(credit);
            userService.saveUser(user);
            return new ResponseEntity<>("credit added successfully!", HttpStatus.OK);
        } catch (InvalidCreditRange e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotExistentUser e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid number for the credit amount."
                    , HttpStatus.BAD_REQUEST);
        }
    }
}