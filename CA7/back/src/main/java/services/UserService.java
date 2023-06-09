package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Discount;
import entities.User;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;
import exceptions.UsernameAlreadyTaken;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import repositories.UserRepository;
import utils.Request;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

            hashUserPasswords(userList);

            userRepository.saveAll(userList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void hashUserPasswords(List<User> userList) {
        for (User user : userList)
            user.setPassword(String.valueOf(user.getPassword().hashCode()));
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

        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        userRepository.save(user);
    }

    public void addOrUpdateUser(User newUser) {
        Optional<User> user = userRepository.findById(newUser.getUsername());
        if (user.isPresent()) {
            user.get().setEmail(newUser.getEmail());
            user.get().setBirthDate(newUser.getBirthDate());
            userRepository.save(user.get());
        } else {
            userRepository.save(newUser);
        }
    }

    public String createJwtToken(String username) {
        String sign_key = "Baloot2023Baloot2023Baloot2023Baloot2023Baloot2023Baloot2023";

        SecretKey signature_type = new SecretKeySpec(sign_key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        return Jwts.builder()
                .claim("username", username)
                .setId(UUID.randomUUID().toString())
                .setIssuer("IEMDB_SYSTEM")                                                      // iss claim
                .setIssuedAt(Date.from(Instant.now()))                                          // iat claim
                .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS))) // exp claim
                .signWith(signature_type)
                .compact();
    }

    public String login(String username, String password) throws NotExistentUser, IncorrectPassword {
        if (!userRepository.existsById(username)) {
            throw new NotExistentUser();
        }

        User user = getUserById(username);
        if (!password.equals(user.getPassword())) {
            throw new IncorrectPassword();
        }

        return createJwtToken(username);
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
