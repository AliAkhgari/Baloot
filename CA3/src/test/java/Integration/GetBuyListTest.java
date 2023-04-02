package Integration;

import application.Baloot;
import application.Server;
import entities.Commodity;
import entities.User;
import io.javalin.Javalin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;

import static Integration.Utils.*;
import static defines.Endpoints.*;
import static org.junit.Assert.assertEquals;


public class GetBuyListTest {
    private HttpClient client;
    private Baloot baloot;
    private Javalin app;

    @Before
    public void setup() {
        baloot = new Baloot();
        baloot.fetchAndStoreDataFromAPI();

        app = Javalin.create().start(SERVER_PORT);

        Server server = new Server(baloot, app);
        server.start();

        client = HttpClient.newHttpClient();
    }

    @After
    public void teardown() {
        baloot = null;
        client = null;
        app.stop();
    }

    @Test
    public void testSuccess() throws Exception {
        Commodity commodity1 = baloot.getCommodities().get(0);
        User user1 = baloot.getUsers().get(0);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" + user1.getUsername() + "/" + commodity1.getId()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/200", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testInvalidUrl() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" + getRandomUserId(baloot)))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/404", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testNotExistentUser() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" + getNotExistentUserId(baloot) + "/" + getRandomCommodityId(baloot)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/404", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testNotExistentCommodity() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" + getRandomUserId(baloot) + "/" + getNotExistentCommodityId(baloot)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("/404", response.headers().map().get("Location").get(0));
    }

    @Test
    public void testBuyListCorrectness() throws Exception {
        Commodity commodity1 = baloot.getCommodities().get(0);
        Commodity commodity2 = baloot.getCommodities().get(1);
        Commodity commodity3 = baloot.getCommodities().get(2);

        User user1 = baloot.getUsers().get(0);

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" +
                        user1.getUsername() + "/" + commodity1.getId()))
                .GET()
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" +
                        user1.getUsername() + "/" + commodity2.getId()))
                .GET()
                .build();

        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" +
                        user1.getUsername() + "/" + commodity3.getId()))
                .GET()
                .build();

        client.send(request1, HttpResponse.BodyHandlers.ofString());
        client.send(request2, HttpResponse.BodyHandlers.ofString());
        client.send(request3, HttpResponse.BodyHandlers.ofString());

        ArrayList<Commodity> expected = new ArrayList<>(Arrays.asList(commodity1, commodity2, commodity3));

        assertEquals(expected, user1.getBuyList());
    }

    @Test
    public void testRateCorrectnessWithRemovingItem() throws Exception {
        Commodity commodity1 = baloot.getCommodities().get(0);
        Commodity commodity2 = baloot.getCommodities().get(1);
        Commodity commodity3 = baloot.getCommodities().get(2);

        User user1 = baloot.getUsers().get(0);

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" +
                        user1.getUsername() + "/" + commodity1.getId()))
                .GET()
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" +
                        user1.getUsername() + "/" + commodity2.getId()))
                .GET()
                .build();

        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + ADD_TO_BUY_LIST_ENDPOINT + "/" +
                        user1.getUsername() + "/" + commodity3.getId()))
                .GET()
                .build();

        client.send(request1, HttpResponse.BodyHandlers.ofString());
        client.send(request2, HttpResponse.BodyHandlers.ofString());
        client.send(request3, HttpResponse.BodyHandlers.ofString());

        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(new URI(LOCALHOST_URL + REMOVE_FROM_BUY_LIST_ENDPOINT + "/" +
                        user1.getUsername() + "/" + commodity2.getId()))
                .GET()
                .build();
        client.send(request4, HttpResponse.BodyHandlers.ofString());

        ArrayList<Commodity> expected = new ArrayList<>(Arrays.asList(commodity1, commodity3));

        assertEquals(expected, user1.getBuyList());
    }
}