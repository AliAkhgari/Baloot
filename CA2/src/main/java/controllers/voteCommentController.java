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

    // todo: user that already voted, should retract its vote!

    public void voteComment(Javalin app) throws IOException {
        app.post("/voteComment", ctx -> {
            String commentId = ctx.formParam("comment_id");
            String userId = ctx.formParam("user_id");
            String vote = ctx.formParam("vote");

            System.out.println("voteeeeee  : " + vote);

            if ((commentId == null) || (userId == null))
                ctx.redirect("/403");

            try {
                Comment comment = baloot.getCommentById(Integer.parseInt(commentId));
                User user = baloot.getUserById(userId);
                if (vote.equals("dislike"))
                    comment.addDislike();
                else if (vote.equals("like"))
                    comment.addLike();

            } catch (ExceptionHandler e) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();
                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            }

        });
    }
}
