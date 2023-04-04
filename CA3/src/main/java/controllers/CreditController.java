package controllers;

import application.Baloot;
import exceptions.InvalidCreditRange;
import exceptions.NotExistentUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Credit Page", value = "/credit")
public class CreditController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("username") == null)
            response.sendRedirect(request.getContextPath() + "/login");
        else
            request.getRequestDispatcher("credit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null)
            response.sendRedirect(request.getContextPath() + "/login");
        else {
            String userId = (String) session.getAttribute("username");
            float credit = Float.parseFloat(request.getParameter("credit"));

            try {
                Baloot.getInstance().getUserById(userId).addCredit(credit);
                response.sendRedirect(request.getContextPath() + "/200");
            } catch (InvalidCreditRange | NotExistentUser e) {
                session.setAttribute("errorMessage", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/error");
            }
        }
    }
}
