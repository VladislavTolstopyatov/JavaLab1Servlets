package servlets;

import dao.UserDao;
import dto.CreateUserDto;
import entities.Role;
import entities.User;
import exceptions.LoginAlreadyRegisteredException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CreateUserMapper;
import mappers.UserMapper;
import services.UserService;
import util.JspHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static util.UrlPathUtil.LOGIN;
import static util.UrlPathUtil.REGISTRATION;

@WebServlet(REGISTRATION)
public class registrationServlet extends HttpServlet {
    private static final UserService userService = new UserService(new UserDao(),new UserMapper(), new CreateUserMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String cardNumber = req.getParameter("cardNumber");

        try {
            User user = userService.createUser(new CreateUserDto(login,
                    password,
                    0,
                    cardNumber,
                    Role.USER));
            resp.sendRedirect(req.getContextPath() + LOGIN);
        } catch (LoginAlreadyRegisteredException e) {
            String message = e.getMessage();
            req.setAttribute("message", message);
            req.getRequestDispatcher(JspHelper.get("registration")).forward(req, resp);
        }
    }
}
