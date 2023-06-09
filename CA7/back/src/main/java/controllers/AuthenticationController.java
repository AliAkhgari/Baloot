package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.User;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static javax.xml.bind.DatatypeConverter.parseDateTime;

@RestController
public class AuthenticationController {

    public static String CLIENT_ID = "192c5d086cf97b3a245b";
    public static String CLIENT_SECRET = "8144f1030cbbf056dbc0ea97f02171cc71888ede";
    public String ACCESS_TOKEN = "";
    public final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    protected ResponseEntity<String> callbackLogin(@RequestParam(value = "code") String code) throws IOException, InterruptedException, NotExistentUser, IncorrectPassword {
        String url = "https://github.com/login/oauth/access_token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + code;
        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .build();

        HttpRequest.Builder access_token_builder = HttpRequest.newBuilder().uri(URI.create(url));
        HttpRequest access_token_request = access_token_builder
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(access_token_request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        HashMap result_body = mapper.readValue(response.body(), HashMap.class);
        ACCESS_TOKEN = (String) result_body.get("access_token");

        User user = getUser();

        try {
            userService.addOrUpdateUser(user);

            String jwt_token = userService.login(user.getUsername(), user.getPassword());

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("token", jwt_token);
            map.add("username", user.getUsername());
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (NotExistentUser | IncorrectPassword e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public User getUser() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("https://api.github.com/user");
        HttpRequest.Builder uri_builder = HttpRequest.newBuilder()
                .uri(uri);
        HttpRequest request2 = uri_builder.GET().header("Authorization", "token " + ACCESS_TOKEN).build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        String body = response2.body();
        System.out.println("ffffff : " + body);

        ObjectMapper mapper = new ObjectMapper();

        HashMap data_body = mapper.readValue(body, HashMap.class);
        String username = (String) data_body.get("login");
        String email = (String) data_body.get("email");
        String name = (String) data_body.get("name");
        String created_at = (String) data_body.get("created_at");
        Calendar cal = parseDateTime(created_at);
        cal.add(Calendar.YEAR, -18);
        Date date = cal.getTime();

        String birthdate = new SimpleDateFormat("yyyy/MM/dd").format(date).toString();

        return new User(username, String.valueOf("NULL".hashCode()), email, birthdate, "");
    }

}