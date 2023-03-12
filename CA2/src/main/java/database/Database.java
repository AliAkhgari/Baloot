package database;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Commodity;
import entities.Provider;
import entities.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Database {
    private final ArrayList<User> users = new ArrayList<User>();
    private final ArrayList<Provider> providers = new ArrayList<Provider>();
    private final ArrayList<Commodity> commodities = new ArrayList<Commodity>();

    public Database() {
    }

    public void getUsersList() throws IOException {
        URL url = new URL("http://5.253.25.110:5000" + "/api/users");

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

        System.out.println(content.toString());
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addProvider(Provider provider) {
        providers.add(provider);
    }

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Provider> getProviders() {
        return providers;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }


}
