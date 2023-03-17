package controllers;

import application.Baloot;
import entities.Comment;
import entities.ExceptionHandler;
import entities.User;
import io.javalin.Javalin;

// todo: change all not found file to what default /404 does!!

public class VoteCommentController {
    private final Baloot baloot;

    public VoteCommentController(Baloot baloot) {
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

                ctx.redirect("/200");
            } catch (ExceptionHandler e) {
                ctx.redirect("/404");
            }

        });
    }

    // todo: refactor showVoteCommentPage

    public void showVoteCommentPage(Javalin app) {
        app.get("/voteComment/{user_id}/{comment_id}/{vote}", ctx -> {
            String commentId = ctx.pathParam("comment_id");
            String userId = ctx.pathParam("user_id");
            String vote = ctx.pathParam("vote");

            try {
                Comment comment = baloot.getCommentById(Integer.parseInt(commentId));
                User user = baloot.getUserById(userId);
                if (vote.equals("-1")) {
                    comment.addUserVote(userId, "dislike");
                    ctx.redirect("/200");
                } else if (vote.equals("1")) {
                    comment.addUserVote(userId, "like");
                    ctx.redirect("/200");
                } else if (vote.equals("0")) {
                    comment.addUserVote(userId, "neutral");
                    ctx.redirect("/200");
                } else
                    ctx.redirect("/403");

            } catch (ExceptionHandler e) {
                ctx.redirect("/404");
            }

        });
    }
}
