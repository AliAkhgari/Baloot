package controllers;

import application.Baloot;
import entities.Comment;
import entities.Commodity;
import entities.User;
import exceptions.NotExistentCommodity;
import exceptions.NotExistentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CommentService;
import services.CommodityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CommoditiesController {
    private final CommentService commentService;
    private final CommodityService commodityService;

    public CommoditiesController(CommentService commentService, CommodityService commodityService) {
        this.commentService = commentService;
        this.commodityService = commodityService;
    }

    @GetMapping(value = "/commodities")
    public ResponseEntity<List<Commodity>> getCommodities() {
        return new ResponseEntity<>(commodityService.getAllCommodities(), HttpStatus.OK);
    }

    @GetMapping(value = "/commodities/{id}")
    public ResponseEntity<Commodity> getCommodity(@PathVariable String id) {
        try {
            Commodity commodity = commodityService.getCommodityById(id);
            return new ResponseEntity<>(commodity, HttpStatus.OK);

        } catch (NotExistentCommodity e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/commodities/{id}/rate")
    public ResponseEntity<String> rateCommodity(@PathVariable String id,
                                                @RequestBody Map<String, String> input) {
        try {
            int rate = Integer.parseInt(input.get("rate"));
            String username = input.get("username");
            Commodity commodity = commodityService.getCommodityById(id);

            commodity.addRate(username, rate);
            return new ResponseEntity<>("rate added successfully!", HttpStatus.OK);
        } catch (NotExistentCommodity e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/commodities/{id}/comment")
    public ResponseEntity<String> addCommodityComment(@PathVariable String id,
                                                      @RequestBody Map<String, String> input) {
        String username = input.get("username");
        String commentText = input.get("comment");

        User user = null;
        try {
            user = Baloot.getInstance().getUserById(username);
        } catch (NotExistentUser ignored) {
        }

        Comment comment = new Comment(user.getEmail(), Integer.parseInt(id), commentText);
        comment.setUsername(username);
        commentService.addComment(comment);

        return new ResponseEntity<>("comment added successfully!", HttpStatus.OK);
    }

    @GetMapping(value = "/commodities/{id}/comment")
    public ResponseEntity<ArrayList<Comment>> getCommodityComment(@PathVariable String id) {
        ArrayList<Comment> comments = Baloot.getInstance().getCommentsForCommodity(Integer.parseInt(id));

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping(value = "/commodities/search")
    public ResponseEntity<ArrayList<Commodity>> searchCommodities(@RequestBody Map<String, String> input) {
        String searchOption = input.get("searchOption");
        String searchValue = input.get("searchValue");

        ArrayList<Commodity> commodities = switch (searchOption) {
            case "name" -> Baloot.getInstance().filterCommoditiesByName(searchValue);
            case "category" -> Baloot.getInstance().filterCommoditiesByCategory(searchValue);
            case "provider" -> Baloot.getInstance().filterCommoditiesByProviderName(searchValue);
            default -> new ArrayList<>();
        };

        return new ResponseEntity<>(commodities, HttpStatus.OK);
    }

    @GetMapping(value = "/commodities/{id}/suggested")
    public ResponseEntity<ArrayList<Commodity>> getSuggestedCommodities(@PathVariable String id) {
        try {
            Commodity commodity = commodityService.getCommodityById(id);

            ArrayList<Commodity> suggestedCommodities = Baloot.getInstance().suggestSimilarCommodities(commodity);
            return new ResponseEntity<>(suggestedCommodities, HttpStatus.OK);
        } catch (NotExistentCommodity ignored) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

}