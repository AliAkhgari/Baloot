package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Discount;
import entities.User;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;
import exceptions.UsernameAlreadyTaken;
import org.springframework.stereotype.Service;
import repositories.UserRepository;
import utils.Request;

import java.io.IOException;
import java.util.List;

import static defines.Endpoints.HOST;
import static defines.Endpoints.USERS_ENDPOINT;

@Service
public class UserService {
    private final UserRepository userRepository;
    private Discount currentDiscount;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.fetchAndSaveProvidersFromApi();
    }

    public void fetchAndSaveProvidersFromApi() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String usersString = Request.makeGetRequest(HOST + USERS_ENDPOINT);
            List<User> userList = objectMapper.readValue(usersString, new TypeReference<>() {
            });
            userRepository.saveAll(userList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(String id) throws NotExistentUser {
        return userRepository.findById(id)
                .orElseThrow(NotExistentUser::new);
    }

    public void addUser(User user) throws UsernameAlreadyTaken {
        String username = user.getUsername();

        if (userRepository.existsById(username)) {
            throw new UsernameAlreadyTaken();
        }

        userRepository.save(user);
    }

    public void login(String username, String password) throws NotExistentUser, IncorrectPassword {
        if (!userRepository.existsById(username)) {
            throw new NotExistentUser();
        }

        User user = getUserById(username);
        if (!password.equals(user.getPassword())) {
            throw new IncorrectPassword();
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Discount getCurrentDiscount() {
        return currentDiscount;
    }

    public void setCurrentDiscount(Discount currentDiscount) {
        this.currentDiscount = currentDiscount;
    }
}
