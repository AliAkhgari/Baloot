package entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Comment {

    int id;
    private String userEmail;
    private String username;
    private int commodityId;
    private String text;
    private String date;
    private int like;
    private int dislike;
    private Map<String, String> userVote = new HashMap<>();

    public Comment() {
    }

    public Comment(int id, String userEmail, String username, int commodityId, String text) {
        this.id = id;
        this.userEmail = userEmail;
        this.username = username;
        this.commodityId = commodityId;
        this.text = text;
        this.date = getCurrentDate();
    }

    public String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(currentDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public Map<String, String> getUserVote() {
        return userVote;
    }

    // todo: make like and dislike counting efficient

    public void addUserVote(String userName, String vote) {
        userVote.put(userName, vote);

        this.like = 0;
        this.dislike = 0;

        for (String key : userVote.keySet()) {
            if (userVote.get(key).equals("like"))
                this.like += 1;
            else if (userVote.get(key).equals("dislike"))
                this.dislike += 1;
            else if (userVote.get(key).equals("neutral"))
                ;
        }
    }

}
