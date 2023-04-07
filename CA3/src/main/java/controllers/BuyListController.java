package controllers;

import application.Baloot;
import entities.Commodity;
import entities.Discount;
import entities.User;
import exceptions.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Buy List Page", value = "/buyList")
public class BuyListController extends HttpServlet {
    private static void loadPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        User user;
        try {
            user = Baloot.getInstance().getUserById(String.valueOf(session.getAttribute("username")));
        } catch (NotExistentUser e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("buyList.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null)
            response.sendRedirect(request.getContextPath() + "/login");
        else {
            loadPage(request, response, session);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        User user;
        Commodity commodity;
        try {
            user = Baloot.getInstance().getUserById(String.valueOf(session.getAttribute("username")));
        } catch (NotExistentUser e) {
            throw new RuntimeException(e);
        }

        if (request.getParameter("userId") != null) {
            try {
                user.withdrawPayableAmount();
                Baloot.getInstance().moveCommoditiesFromBuyListToPurchasedList((String) session.getAttribute("username"));
            } catch (InsufficientCredit | MissingUserId | NotExistentUser e) {
                session.setAttribute("errorMessage", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }
        }

        if (request.getParameter("commodityId") != null) {
            try {
                int commodityId = Integer.parseInt(request.getParameter("commodityId"));
                commodity = Baloot.getInstance().getCommodityById(commodityId);
                user.removeItemFromBuyList(commodity);
            } catch (CommodityIsNotInBuyList | NotExistentCommodity e) {
                session.setAttribute("errorMessage", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }
        }

        if (request.getParameter("discountId") != null) {
            String discountCode = request.getParameter("discountId");
            try {
                Discount discount = Baloot.getInstance().getDiscountByCode(discountCode);
                Baloot.getInstance().checkDiscountExpiration(user, discount);
                user.setCurrentDiscount(discount);
            } catch (NotExistentDiscount | ExpiredDiscount e) {
                session.setAttribute("errorMessage", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("buyList.jsp").forward(request, response);
    }
}
