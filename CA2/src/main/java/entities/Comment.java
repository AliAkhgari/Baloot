package entities;

import java.util.HashMap;
import java.util.Map;

public class Comment {

    int id;
    private String userEmail;
    private int commodityId;
    private String text;
    private String date;

    private int like;
    private int dislike;

    private Map<String, String> userVote = new HashMap<>();

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
        }
    }

}
