package servlets;

import dto.UserDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UserService;
import util.JspHelper;

import java.io.IOException;
import java.util.Objects;

import static util.UrlPathUtil.*;

@WebServlet(LOGIN)
public class loginServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Objects.isNull(req.getSession().getAttribute("user"))) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        req.getRequestDispatcher(JspHelper.get("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            UserDto user = userService.findByLoginAndPassword(login, password);
            req.getSession().setAttribute("user", userService.findByLogin(login));
            resp.sendRedirect(req.getContextPath() + GAMES);
        } catch (Exception e) {
            String message = "Неверный логин/пороль.";
            req.setAttribute("message", message);
            req.getRequestDispatcher(JspHelper.get("login")).forward(req, resp);
        }
    }
}
