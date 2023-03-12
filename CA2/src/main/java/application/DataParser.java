package application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.Database;
import entities.User;
import entities.Provider;
import entities.Commodity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static defines.endpoints.HOST;
import static defines.endpoints.USERS_ENDPOINT;
import static defines.endpoints.PROVIDERS_ENDPOINT;
import static defines.endpoints.COMMODITIES_ENDPOINT;

//import enti

public class DataParser {
    private final ObjectMapper objectMapper = new ObjectMapper();
    Database database;

    public DataParser(Database database) {
        this.database = database;
    }

    private String makeGetRequest(String url_str) throws IOException {
        URL url = new URL(url_str);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return content.toString();
    }

    public void getUsersList() throws IOException {
        String usersString = makeGetRequest(HOST + USERS_ENDPOINT);
        List<User> userList = objectMapper.readValue(usersString, new TypeReference<List<User>>(){});
        database.setUsers((ArrayList<User>) userList);
    }

    public void getProvidersList() throws IOException {
        String providersString = makeGetRequest(HOST + PROVIDERS_ENDPOINT);
        List<Provider> providerList = objectMapper.readValue(providersString, new TypeReference<>(){});
        database.setProviders((ArrayList<Provider>) providerList);
    }

    public void getCommoditiesList() throws IOException {
        String commoditiesString = makeGetRequest(HOST + COMMODITIES_ENDPOINT);
        List<Commodity> commodityList = objectMapper.readValue(commoditiesString, new TypeReference<>(){});
        database.setCommodities((ArrayList<Commodity>) commodityList);
    }
}
