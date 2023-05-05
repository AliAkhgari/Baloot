package controllers;

import application.Baloot;
import entities.Comment;
import entities.Commodity;
import exceptions.NotExistentCommodity;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CommoditiesController {
    @GetMapping(value = "/commodities")
    public ResponseEntity<ArrayList<Commodity>> getCommodities() {
        return new ResponseEntity<>(Baloot.getInstance().getCommodities(), HttpStatus.OK);
    }

    @GetMapping(value = "/commodities/{id}")
    public ResponseEntity<Commodity> getCommodity(@PathVariable String id) {
        try {
            Commodity commodity = Baloot.getInstance().getCommodityById(id);
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
            Commodity commodity = Baloot.getInstance().getCommodityById(id);
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
                                                      @RequestBody Map<String, String> input,
                                                      HttpSession session) {
        int commentId = Baloot.getInstance().generateCommentId();
        String username = (String) session.getAttribute("username");
        String commentText = input.get("comment");

        Comment comment = new Comment(commentId, username, Integer.parseInt(id), commentText);
        Baloot.getInstance().addComment(comment);

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

}