package controllers;


import application.Baloot;
import entities.Commodity;
import entities.User;
import exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

// /buyList -> get
// /buyList/add -> post
// /buyList/remove -> post
// /buyList/purchase -> post
@RestController
public class BuyListController {
    @GetMapping(value = "/buy-list")
    public ResponseEntity<ArrayList<Commodity>> getBuyList(HttpSession session) {
        String username = (String) session.getAttribute("username");
        try {
            ArrayList<Commodity> buyList = Baloot.getInstance().getUserBuyList(username);
            return new ResponseEntity<>(buyList, HttpStatus.OK);
            // TODO: delete exception
        } catch (NotExistentUser e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/buy-list/add")
    public ResponseEntity<String> addToBuyList(@RequestBody Map<String, String> input, HttpSession session) {
        String username = (String) session.getAttribute("username");
        try {
            Baloot.getInstance().addCommodityToUserBuyList(username, input.get("id"));
            return new ResponseEntity<>("commodity added to buy list successfully!", HttpStatus.OK);
            // TODO: delete exception
        } catch (NotExistentUser | NotExistentCommodity e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyInBuyList e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/buy-list/remove")
    public ResponseEntity<String> removeFromBuyList(@RequestBody Map<String, String> input, HttpSession session) {
        String username = (String) session.getAttribute("username");
        try {
            Baloot.getInstance().removeCommodityFromUserBuyList(username, input.get("id"));
            return new ResponseEntity<>("commodity added to buy list successfully!", HttpStatus.OK);
            // TODO: delete exceptions
        } catch (MissingUserId | MissingCommodityId | NotExistentUser | NotExistentCommodity |
                 CommodityIsNotInBuyList e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/buy-list/purchase")
    public ResponseEntity<String> purchaseBuyList(@RequestBody Map<String, String> input, HttpSession session) {
        String username = (String) session.getAttribute("username");
        try {
            User user = Baloot.getInstance().getUserById(username);
            // TODO: make these two functions one in baloot
            user.withdrawPayableAmount();
            Baloot.getInstance().moveCommoditiesFromBuyListToPurchasedList((String) session.getAttribute("username"));
            return new ResponseEntity<>("buy list purchased successfully!", HttpStatus.OK);
            //TODO: remove not necessary exceptions
        } catch (InsufficientCredit | MissingUserId | NotExistentUser e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

//@WebServlet(name = "Buy List Page", value = "/buyList")
//public class BuyListController extends HttpServlet {
//    private static void loadPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
//        User user;
//        try {
//            user = Baloot.getInstance().getUserById(String.valueOf(session.getAttribute("username")));
//        } catch (NotExistentUser e) {
//            throw new RuntimeException(e);
//        }
//
//        request.setAttribute("user", user);
//        request.getRequestDispatcher("buyList.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        if (session.getAttribute("username") == null)
//            response.sendRedirect(request.getContextPath() + "/login");
//        else {
//            loadPage(request, response, session);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        HttpSession session = request.getSession();
//
//        User user;
//        Commodity commodity;
//        try {
//            user = Baloot.getInstance().getUserById(String.valueOf(session.getAttribute("username")));
//        } catch (NotExistentUser e) {
//            throw new RuntimeException(e);
//        }
//
//        if (request.getParameter("userId") != null) {
//            try {
//                user.withdrawPayableAmount();
//                Baloot.getInstance().moveCommoditiesFromBuyListToPurchasedList((String) session.getAttribute("username"));
//            } catch (InsufficientCredit | MissingUserId | NotExistentUser e) {
//                session.setAttribute("errorMessage", e.getMessage());
//                response.sendRedirect(request.getContextPath() + "/error");
//                return;
//            }
//        }
//
//        if (request.getParameter("commodityId") != null) {
//            try {
//                int commodityId = Integer.parseInt(request.getParameter("commodityId"));
//                commodity = Baloot.getInstance().getCommodityById(commodityId);
//                user.removeItemFromBuyList(commodity);
//            } catch (CommodityIsNotInBuyList | NotExistentCommodity e) {
//                session.setAttribute("errorMessage", e.getMessage());
//                response.sendRedirect(request.getContextPath() + "/error");
//                return;
//            }
//        }
//
//        if (request.getParameter("discountId") != null) {
//            String discountCode = request.getParameter("discountId");
//            try {
//                Discount discount = Baloot.getInstance().getDiscountByCode(discountCode);
//                Baloot.getInstance().checkDiscountExpiration(user, discount);
//                user.setCurrentDiscount(discount);
//            } catch (NotExistentDiscount | ExpiredDiscount e) {
//                session.setAttribute("errorMessage", e.getMessage());
//                response.sendRedirect(request.getContextPath() + "/error");
//                return;
//            }
//        }
//
//        request.setAttribute("user", user);
//        request.getRequestDispatcher("buyList.jsp").forward(request, response);
//    }
//}
