package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String login() {
        return "Hello, world!";
    }
}


//@WebServlet(name = "Login Page", value = "/login")
//public class LoginController extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("login.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String userId = request.getParameter("username");
//        String password = request.getParameter("Password");
//        try {
//            HttpSession session = request.getSession();
//            Baloot.getInstance().login(userId, password);
//
//            session.setAttribute("username", userId);
//
//            response.sendRedirect(request.getContextPath() + "/200");
//        } catch (NotExistentUser | IncorrectPassword e) {
//            HttpSession session = request.getSession(false);
//            session.setAttribute("errorMessage", e.getMessage());
//            response.sendRedirect(request.getContextPath() + "/error");
//        }
//    }
//}
//
//
//
