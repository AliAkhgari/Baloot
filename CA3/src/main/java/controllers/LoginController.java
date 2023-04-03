package controllers;

import application.Baloot;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login Page", value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("username");
        String password = request.getParameter("Password");
        try {
            HttpSession session = request.getSession();
            Baloot.getInstance().login(userId, password);

            session.setAttribute("username", userId);
            request.setAttribute("username", userId);

            response.sendRedirect(request.getContextPath() + "/");
        } catch (NotExistentUser | IncorrectPassword e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
