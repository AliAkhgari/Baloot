package controllers;

import application.Baloot;
import exceptions.IncorrectPassword;
import exceptions.NotExistentUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Logout Page", value = "/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("username");
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
