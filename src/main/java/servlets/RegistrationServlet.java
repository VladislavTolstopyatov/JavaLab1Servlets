package servlets;

import dao.GameDao;
import dao.UserDao;
import dto.CreateUserDto;
import entities.Role;
import entities.User;
import exceptions.DataBaseException;
import exceptions.LoginAlreadyRegisteredException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CreateUserMapper;
import mappers.UserDtoMapper;
import mappers.UserMapper;
import org.apache.log4j.Logger;
import services.UserService;
import util.JspHelper;
import util.PasswordHasher;

import java.io.IOException;

import static util.UrlPathUtil.LOGIN;
import static util.UrlPathUtil.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegistrationServlet.class);
    private final UserService userService = new UserService(new UserDao(),
            new UserMapper(),
            new CreateUserMapper(),
            new UserDtoMapper());

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
                    PasswordHasher.md5(password),
                    0,
                    cardNumber,
                    Role.USER));
            resp.sendRedirect(req.getContextPath() + LOGIN);
        } catch (LoginAlreadyRegisteredException | Exception e) {
            logger.error(e.getMessage());
            String message = e.getMessage();
            req.setAttribute("message", message);
            req.getRequestDispatcher(JspHelper.get("registration")).forward(req, resp);
        } catch (DataBaseException e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
