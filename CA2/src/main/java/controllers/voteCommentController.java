package controllers;

import application.Baloot;
import entities.Comment;
import entities.ExceptionHandler;
import entities.User;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

// todo: change all not found file to what default /404 does!!

public class voteCommentController {
    private final Baloot baloot;
    private static final String NOT_FOUND_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/404.html";

    public voteCommentController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void voteComment(Javalin app) {
        app.post("/voteComment", ctx -> {
            String commentId = ctx.formParam("comment_id");
            String userId = ctx.formParam("user_id");
            String vote = ctx.formParam("vote");

            if ((commentId == null) || (userId == null))
                ctx.redirect("/403");

            try {
                Comment comment = baloot.getCommentById(Integer.parseInt(commentId));
                User user = baloot.getUserById(userId);
                if (vote.equals("dislike"))
                    comment.addUserVote(userId, "dislike");
                else if (vote.equals("like"))
                    comment.addUserVote(userId, "like");

            } catch (ExceptionHandler e) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();
                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            }

        });
    }

    public void showVoteCommentPage(Javalin app) {
        app.get("/voteComment/{user_id}/{comment_id}/{vote}", ctx -> {
            String commentId = ctx.pathParam("comment_id");
            String userId = ctx.pathParam("user_id");
            String vote = ctx.pathParam("vote");

            try {
                Comment comment = baloot.getCommentById(Integer.parseInt(commentId));
                User user = baloot.getUserById(userId);
                if (vote.equals("-1"))
                    comment.addUserVote(userId, "dislike");
                else if (vote.equals("1"))
                    comment.addUserVote(userId, "like");
                else if (vote.equals("0"))
                    comment.addUserVote(userId, "neutral");
                else
                    ctx.redirect("/403");

            } catch (ExceptionHandler e) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();
                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            }

        });
    }
}
