package servlets;

import dao.GameDao;
import dao.UserDao;
import dto.UserDto;
import exceptions.DataBaseException;
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
import java.util.Objects;

import static util.UrlPathUtil.*;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class);
    private final UserService userService = new UserService(new UserDao(), new UserMapper(), new CreateUserMapper(), new UserDtoMapper());

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
            UserDto user = userService.findByLoginAndPassword(login, PasswordHasher.md5(password));
            req.getSession().setAttribute("user", userService.findByLogin(login));
            resp.sendRedirect(req.getContextPath() + GAMES);
        } catch (Exception e) {
            logger.error(e.getMessage());
            String message = "Неверный логин/пороль.";
            req.setAttribute("message", message);
            req.getRequestDispatcher(JspHelper.get("login")).forward(req, resp);
        } catch (DataBaseException e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
