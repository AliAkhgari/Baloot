package controllers;

import application.Baloot;
import entities.User;
import exceptions.InvalidCreditRange;
import exceptions.NotExistentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        try {
            User user = Baloot.getInstance().getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotExistentUser e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/users/{id}/credit")
    public ResponseEntity<String> addCredit(@PathVariable String id, @RequestBody Map<String, String> input) {
        float credit = Float.parseFloat(input.get("credit"));
        try {
            Baloot.getInstance().getUserById(id).addCredit(credit);
            return new ResponseEntity<>("credit added successfully!", HttpStatus.OK);
        } catch (InvalidCreditRange e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotExistentUser e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }


    }
}

// users/{id}
// users/{id}/credit -> post