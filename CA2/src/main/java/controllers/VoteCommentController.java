package controllers;

import exceptions.*;
import application.Baloot;
import io.javalin.Javalin;

public class VoteCommentController {
    private final Baloot baloot;

    public VoteCommentController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void voteComment(Javalin app) {
        app.post("/voteComment", ctx -> {
            String userId = ctx.formParam("user_id");
            String commentId = ctx.formParam("comment_id");
            String vote = ctx.formParam("vote");

            try {
                baloot.userVoteComment(userId, commentId, vote);
                ctx.redirect("/200");
            } catch (MissingUserId | MissingCommentId | MissingVoteValue | InvalidVoteFormat e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentComment e2) {
                ctx.redirect("/404");
            }
        });
    }

    public void showVoteCommentPage(Javalin app) {
        app.get("/voteComment/{user_id}/{comment_id}/{vote}", ctx -> {
            String userId = ctx.pathParam("user_id");
            String commentId = ctx.pathParam("comment_id");
            String vote = ctx.pathParam("vote");

            try {
                baloot.userVoteComment(userId, commentId, vote);
                ctx.redirect("/200");
            } catch (MissingUserId | MissingCommentId | MissingVoteValue | InvalidVoteFormat e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentComment e2) {
                ctx.redirect("/404");
            }
        });
    }
}
