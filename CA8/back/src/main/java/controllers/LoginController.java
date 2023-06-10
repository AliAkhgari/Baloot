package controllers;

import entities.User;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;
import exceptions.UsernameAlreadyTaken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import java.util.Map;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String, String> input) {
        try {
            String username = input.get("username");
            String password = String.valueOf(input.get("password").hashCode());

            String jwt_token = userService.login(username, password);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("token", jwt_token);
            map.add("username", input.get("username"));

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (NotExistentUser e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IncorrectPassword e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signup(@RequestBody Map<String, String> input) {
        String address = input.get("address");
        String birthDate = input.get("birthDate");
        String email = input.get("email");
        String username = input.get("username");
        String password = input.get("password");

        User newUser = new User(username, password, email, birthDate, address);
        try {
            userService.addUser(newUser);

            String jwt_token = userService.login(username, String.valueOf(password.hashCode()));
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("token", jwt_token);
            map.add("username", username);

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (UsernameAlreadyTaken | NotExistentUser | IncorrectPassword e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

