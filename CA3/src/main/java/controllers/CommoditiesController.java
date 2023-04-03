package controllers;

import application.Baloot;
import entities.Commodity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Commodities Page", value = "/commodities")
public class CommoditiesController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("username") == null) response.sendRedirect(request.getContextPath() + "/login");
        else {
            ArrayList<Commodity> commodities = Baloot.getInstance().getCommodities();
            request.setAttribute("commodities", commodities);
            request.getRequestDispatcher("commodities.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("username") == null)
            response.sendRedirect(request.getContextPath() + "/login");
        else {
            String action = request.getParameter("action");
            if (action == null) {
                doGet(request, response);
                return;
            }

            ArrayList<Commodity> commodities = null;

            if (action.equals("search_by_category")) {
                String category = request.getParameter("search");
                commodities = Baloot.getInstance().filterCommoditiesByCategory(category);
                session.setAttribute("filteredCommodities", commodities);
            } else if (action.equals("search_by_name")) {
                String name = request.getParameter("search");
                commodities = Baloot.getInstance().filterCommoditiesByName(name);
                session.setAttribute("filteredCommodities", commodities);
            } else if (action.equals("clear")) {
                commodities = Baloot.getInstance().getCommodities();
                session.removeAttribute("filteredCommodities");
            }

            if (action.equals("sort_by_rate")) {
                if (session.getAttribute("filteredCommodities") != null)
                    commodities = (ArrayList<Commodity>) session.getAttribute("filteredCommodities");
                else commodities = Baloot.getInstance().getCommodities();

                commodities = Baloot.getInstance().getSortedCommoditiesByRate(commodities);
            } else if (action.equals("sort_by_price")) {
                if (session.getAttribute("filteredCommodities") != null)
                    commodities = (ArrayList<Commodity>) session.getAttribute("filteredCommodities");
                else commodities = Baloot.getInstance().getCommodities();

                commodities = Baloot.getInstance().getSortedCommoditiesByPrice(commodities);
            }

            request.setAttribute("commodities", commodities);
            request.getRequestDispatcher("commodities.jsp").forward(request, response);
        }
    }
}
