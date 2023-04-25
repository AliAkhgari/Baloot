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

@RestController
public class CommoditiesController {
    @GetMapping(value = "/commodities")
    public ResponseEntity<ArrayList<Commodity>> getCommodities() {
        return new ResponseEntity<>(Baloot.getInstance().getCommodities(), HttpStatus.OK);
    }

    @GetMapping(value = "/commodities/{id}")
    public ResponseEntity<Commodity> getCommodity(@PathVariable String id) {
        try {
            Commodity commodity = Baloot.getInstance().getCommodityById(Integer.parseInt(id));
            return new ResponseEntity<>(commodity, HttpStatus.OK);

        } catch (NotExistentCommodity e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/commodities/{id}/rate")
    public ResponseEntity<String> rateCommodity(@PathVariable String id,
                                                @RequestBody Map<String, String> input,
                                                HttpSession session) {
        try {
            int rate = Integer.parseInt(input.get("quantity"));
            String username = (String) session.getAttribute("username");
            Commodity commodity = Baloot.getInstance().getCommodityById(Integer.parseInt(id));
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

}

//@WebServlet(name = "Commodities Page", value = "/commodities")
//public class CommoditiesController extends HttpServlet {
//    private static void loadPage(HttpServletRequest request, HttpServletResponse response, ArrayList<Commodity> commodities) throws ServletException, IOException {
//        request.setAttribute("commodities", commodities);
//        request.getRequestDispatcher("commodities.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        if (session.getAttribute("username") == null) response.sendRedirect(request.getContextPath() + "/login");
//        else {
//            ArrayList<Commodity> commodities = Baloot.getInstance().getCommodities();
//            loadPage(request, response, commodities);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        String action = request.getParameter("action");
//        if (action == null) {
//            doGet(request, response);
//            return;
//        }
//
//        ArrayList<Commodity> commodities = null;
//
//        switch (action) {
//            case "search_by_category" -> {
//                String category = request.getParameter("search");
//                commodities = Baloot.getInstance().filterCommoditiesByCategory(category);
//                session.setAttribute("filteredCommodities", commodities);
//            }
//            case "search_by_name" -> {
//                String name = request.getParameter("search");
//                commodities = Baloot.getInstance().filterCommoditiesByName(name);
//                session.setAttribute("filteredCommodities", commodities);
//            }
//            case "clear" -> {
//                commodities = Baloot.getInstance().getCommodities();
//                session.removeAttribute("filteredCommodities");
//            }
//            case "sort_by_rate" -> {
//                if (session.getAttribute("filteredCommodities") != null)
//                    commodities = (ArrayList<Commodity>) session.getAttribute("filteredCommodities");
//                else commodities = Baloot.getInstance().getCommodities();
//                commodities = Baloot.getInstance().getSortedCommoditiesByRate(commodities);
//            }
//            case "sort_by_price" -> {
//                if (session.getAttribute("filteredCommodities") != null)
//                    commodities = (ArrayList<Commodity>) session.getAttribute("filteredCommodities");
//                else commodities = Baloot.getInstance().getCommodities();
//                commodities = Baloot.getInstance().getSortedCommoditiesByPrice(commodities);
//            }
//        }
//
//        loadPage(request, response, commodities);
//    }
//}
