package Integration;

import application.Baloot;
import application.Server;
import entities.Commodity;
import entities.User;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static Integration.Utils.*;
import static defines.Endpoints.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class RateCommodityTest {
    private HttpClient client;
    private Baloot baloot;
    private Javalin app;

    @BeforeEach
    public void setup() {
        baloot = new Baloot();
        baloot.fetchAndStoreDataFromAPI();

        app = Javalin.create().start(SERVER_PORT);

        Server server = new Server(baloot, app);
        server.start();

        client = HttpClient.newHttpClient();
    }

    @AfterEach
    public void teardown() {
        baloot = null;
        client = null;
        app.stop();
    }

    @Test
    public void testSuccess() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" + getRandomUserId(baloot) + "/" + getRandomCommodityId(baloot) + "/1"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/200", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testInvalidUrl() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" + getRandomUserId(baloot) + "/1"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/404", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testNotExistentUser() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" + getNotExistentUserId(baloot) + "/" + getRandomCommodityId(baloot) + "/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/404", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testNotExistentCommodity() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" + getRandomUserId(baloot) + "/" + getNotExistentCommodityId(baloot) + "/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/404", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testInvalidRateFormat() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" + getRandomUserId(baloot) + "/" + getRandomCommodityId(baloot) + "/1a"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/403", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testInvalidRateRange() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" + getRandomUserId(baloot) + "/" + getRandomCommodityId(baloot) + "/-3"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/403", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testRateCorrectness() throws Exception {
        Commodity randomCommodity = baloot.getCommodityById(getRandomCommodityId(baloot));
        float commodityRate = randomCommodity.getRating();

        User user1 = baloot.getUsers().get(0);
        User user2 = baloot.getUsers().get(1);
        User user3 = baloot.getUsers().get(2);

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" +
                        user1.getUsername() + "/" + randomCommodity.getId() + "/6"))
                .GET()
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" +
                        user2.getUsername() + "/" + randomCommodity.getId() + "/4"))
                .GET()
                .build();

        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" +
                        user3.getUsername() + "/" + randomCommodity.getId() + "/9"))
                .GET()
                .build();

        client.send(request1, HttpResponse.BodyHandlers.ofString());
        client.send(request2, HttpResponse.BodyHandlers.ofString());
        client.send(request3, HttpResponse.BodyHandlers.ofString());

        assertEquals((commodityRate + 6 + 4 + 9) / 4, randomCommodity.getRating(), 0.001);
    }

    @Test
    public void testRateCorrectnessWithChangingRate() throws Exception {
        Commodity randomCommodity = baloot.getCommodityById(getRandomCommodityId(baloot));
        float commodityRate = randomCommodity.getRating();

        User user1 = baloot.getUsers().get(0);
        User user2 = baloot.getUsers().get(1);
        User user3 = baloot.getUsers().get(2);

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" +
                        user1.getUsername() + "/" + randomCommodity.getId() + "/6"))
                .GET()
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" +
                        user2.getUsername() + "/" + randomCommodity.getId() + "/4"))
                .GET()
                .build();

        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" +
                        user3.getUsername() + "/" + randomCommodity.getId() + "/9"))
                .GET()
                .build();

        client.send(request1, HttpResponse.BodyHandlers.ofString());
        client.send(request2, HttpResponse.BodyHandlers.ofString());
        client.send(request3, HttpResponse.BodyHandlers.ofString());

        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + RATE_COMMODITY_ENDPOINT + "/" +
                        user3.getUsername() + "/" + randomCommodity.getId() + "/1"))
                .GET()
                .build();

        client.send(request4, HttpResponse.BodyHandlers.ofString());

        assertEquals((commodityRate + 6 + 4 + 1) / 4, randomCommodity.getRating(), 0.001);
    }
}