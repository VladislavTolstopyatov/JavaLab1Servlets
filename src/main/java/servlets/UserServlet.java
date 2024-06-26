package servlets;

import dao.GameDao;
import dao.KeyDao;
import dao.PurchaseDao;
import dao.UserDao;
import dto.PurchaseDto;
import dto.UserDto;
import exceptions.DataBaseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.*;
import org.apache.log4j.Logger;
import services.GameService;
import services.PurchaseService;
import services.UserService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;

import static util.UrlPathUtil.USER;

@WebServlet(USER)
public class UserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserServlet.class);
    private final UserService userService = new UserService(new UserDao(),
            new UserMapper(),
            new CreateUserMapper(), new UserDtoMapper());
    private final PurchaseService purchaseService = new PurchaseService(new PurchaseDao(),
            new PurchaseMapper(),
            new GameService(new GameDao(),
                    new KeyDao(),
                    new GameMapper(),
                    new CreateGameMapper(),
                    new UpdateGameMapper()), new CreatePurchaseMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        Integer id = user.getId();
        List<PurchaseDto> userPurchases = null;
        try {
            userPurchases = purchaseService.findByUserId(id);
        } catch (DataBaseException e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
        req.setAttribute("user", user);
        req.setAttribute("userPurchases", userPurchases);
        req.getRequestDispatcher(JspHelper.get("userCabinet")).forward(req, resp);
    }
}
