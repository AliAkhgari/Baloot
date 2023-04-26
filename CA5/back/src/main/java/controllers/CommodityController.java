//package controllers;
//
//import application.Baloot;
//import entities.Comment;
//import entities.Commodity;
//import exceptions.*;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//@WebServlet(name = "Commodity Page", value = "/commodities/*")
//public class CommodityController extends HttpServlet {
//    private void loadPage(HttpServletRequest request, HttpServletResponse response, HttpSession session, int commodityId) throws ServletException, IOException {
//        try {
//            Commodity commodity = Baloot.getInstance().getCommodityById(commodityId);
//            String providerName = Baloot.getInstance().getProviderById(commodity.getProviderId()).getName();
//            ArrayList<Commodity> suggestedCommodities = Baloot.getInstance().suggestSimilarCommodities(commodity);
//
//            request.setAttribute("commodity", commodity);
//            request.setAttribute("provider_name", providerName);
//            request.setAttribute("comments", Baloot.getInstance().getCommentsForCommodity(commodityId));
//            request.setAttribute("suggestedCommodities", suggestedCommodities);
//
//
//            request.getRequestDispatcher("/commodity.jsp").forward(request, response);
//        } catch (NotExistentCommodity | NotExistentProvider e) {
//            session.setAttribute("errorMessage", e.getMessage());
//            response.sendRedirect(request.getContextPath() + "/error");
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        if (session.getAttribute("username") == null) response.sendRedirect(request.getContextPath() + "/login");
//        else {
//            String[] split_url = request.getRequestURI().split("/");
//            int commodityId = Integer.parseInt(split_url[split_url.length - 1]);
//            loadPage(request, response, session, commodityId);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        String[] split_url = request.getRequestURI().split("/");
//        int commodityId = Integer.parseInt(split_url[split_url.length - 1]);
//
//        if (request.getParameter("comment") != null) {
//            int commentId = Baloot.getInstance().generateCommentId();
//            String username = (String) session.getAttribute("username");
//            String commentText = request.getParameter("comment");
//
//            Date currentDate = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateString = dateFormat.format(currentDate);
//
//            Comment comment = new Comment(commentId, username, commodityId, commentText, dateString);
//            Baloot.getInstance().addComment(comment);
//        }
//
//        if (request.getParameter("quantity") != null) {
//            try {
//                int rate = Integer.parseInt(request.getParameter("quantity"));
//                String username = (String) session.getAttribute("username");
//                Commodity commodity = Baloot.getInstance().getCommodityById(commodityId);
//                commodity.addRate(username, rate);
//            } catch (NotExistentCommodity | NumberFormatException e) {
//                session.setAttribute("errorMessage", e.getMessage());
//                response.sendRedirect(request.getContextPath() + "/error");
//                return;
//            }
//        }
//
//        if (request.getParameter("like") != null) {
//            int commentId = Integer.parseInt(request.getParameter("comment_id"));
//            try {
//                Comment comment = Baloot.getInstance().getCommentById(commentId);
//                String username = (String) session.getAttribute("username");
//                comment.addUserVote(username, "like");
//            } catch (NotExistentComment e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if (request.getParameter("dislike") != null) {
//            int commentId = Integer.parseInt(request.getParameter("comment_id"));
//            try {
//                Comment comment = Baloot.getInstance().getCommentById(commentId);
//                String username = (String) session.getAttribute("username");
//                comment.addUserVote(username, "dislike");
//            } catch (NotExistentComment e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if (request.getParameter("add_to_buy_list") != null) {
//            try {
//                String username = (String) session.getAttribute("username");
//                Baloot.getInstance().addCommodityToUserBuyList(username, String.valueOf(commodityId));
//            } catch (NotExistentCommodity | NotExistentUser | AlreadyInBuyList e) {
//                session.setAttribute("errorMessage", e.getMessage());
//                response.sendRedirect(request.getContextPath() + "/error");
//                return;
//            }
//        }
//
//        loadPage(request, response, session, commodityId);
//    }
//}
