package controllers;

import application.Baloot;
import entities.Comment;
import entities.Commodity;
import exceptions.NotExistentCommodity;
import exceptions.NotExistentProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Commodity Page", value = "/commodities/*")
public class CommodityController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("username") == null) response.sendRedirect(request.getContextPath() + "/login");
        else {
            String[] split_url = request.getRequestURI().split("/");
            int commodityId = Integer.parseInt(split_url[split_url.length - 1]);
            try {
                Commodity commodity = Baloot.getInstance().getCommodityById(commodityId);
                String providerName = Baloot.getInstance().getProviderById(commodity.getProviderId()).getName();

                request.setAttribute("commodity", commodity);
                request.setAttribute("provider_name", providerName);
                request.setAttribute("comments", Baloot.getInstance().getCommentsForCommodity(commodityId));

                request.getRequestDispatcher("/commodity.jsp").forward(request, response);
            } catch (NotExistentCommodity | NotExistentProvider e) {
                session.setAttribute("errorMessage", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/error");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("username") == null)
            response.sendRedirect(request.getContextPath() + "/login");
        else {
            String[] split_url = request.getRequestURI().split("/");
            int commodityId = Integer.parseInt(split_url[split_url.length - 1]);

            request.setAttribute("comments", Baloot.getInstance().getCommentsForCommodity(commodityId));

            if (request.getParameter("comment") != null) {
                int commentId = Baloot.getInstance().generateCommentId();
                String username = (String) session.getAttribute("username");
                String commentText = request.getParameter("comment");

                Comment comment = new Comment(commentId, username, commodityId, commentText);
                Baloot.getInstance().addComment(comment);
            }

            if (request.getParameter("quantity") != null) {
                int rate = Integer.parseInt(request.getParameter("quantity"));
                String username = (String) session.getAttribute("username");

                try {
                    Commodity commodity = Baloot.getInstance().getCommodityById(commodityId);
                    commodity.addRate(username, rate);
                } catch (NotExistentCommodity e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
