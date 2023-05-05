package controllers;

import application.Baloot;
import entities.Provider;
import entities.User;
import exceptions.NotExistentProvider;
import exceptions.NotExistentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProviderController {
    @GetMapping(value = "/providers/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable String id) {
        try {
            Provider provider = Baloot.getInstance().getProviderById(id);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        } catch (NotExistentProvider e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
