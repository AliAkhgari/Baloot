package controllers;

import application.Baloot;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String, String> input, HttpSession session) {
        try {
            String username = input.get("username");
            String password = input.get("password");
            Baloot.getInstance().login(username, password);
            session.setAttribute("username", username);
            return new ResponseEntity<>("login successfully!", HttpStatus.OK);
        } catch (NotExistentUser | IncorrectPassword e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("logout successfully!", HttpStatus.OK);
    }
}

