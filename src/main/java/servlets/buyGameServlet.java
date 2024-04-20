package servlets;

import dao.GameDao;
import dao.KeyDao;
import dao.PurchaseDao;
import dao.UserDao;
import dto.KeyDto;
import dto.PurchaseDto;
import dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mappers.*;
import services.GameService;
import services.KeyService;
import services.PurchaseService;
import services.UserService;
import util.JspHelper;

import java.io.IOException;
import java.time.LocalDate;

import static util.UrlPathUtil.*;

@WebServlet(BUY_GAME)
public class buyGameServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserDao(), new UserMapper(), new CreateUserMapper(), new UserDtoMapper());
    private final PurchaseService purchaseService = new PurchaseService(new PurchaseDao(),
            new PurchaseMapper(),
            new GameService(new GameDao(), new KeyDao(), new GameMapper(), new CreateGameMapper(), new UpdateGameMapper()), new CreatePurchaseMapper());
    private final KeyService keyService = new KeyService(new KeyDao(), new KeyMapper(), new CreateKeyMapper());
    private final GameService gameService = new GameService(new GameDao(), new KeyDao(), new GameMapper(),
            new CreateGameMapper(),
            new UpdateGameMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gameTitle = req.getParameter("gameTitle");
        Double gamePrice = Double.valueOf(req.getParameter("gamePrice"));

        HttpSession session = req.getSession();

        // Сохраняем параметры в сессии
        session.setAttribute("gameTitle", gameTitle);
        session.setAttribute("gamePrice", gamePrice);

        req.getRequestDispatcher(JspHelper.get("buyGame")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = (UserDto) req.getSession().getAttribute("user");
        double gamePrice = (double) req.getSession().getAttribute("gamePrice");
        String gameTitle = (String) req.getSession().getAttribute("gameTitle");

        String message;

        // положительный исход покупки
        if (userDto.getBalance() - gamePrice > 0) {

            // получение ключа игры
            KeyDto key = keyService.findByGameId(gameService.findIdByTitle(gameTitle)).get(0);

            // удаление ключа из базы
            keyService.deleteById(key.getId());

            // фиксация покупки пользователя и добавление в бд
            PurchaseDto purchaseDto = new PurchaseDto(null, LocalDate.now(), null, gameTitle, userDto.getId(), key.getKeyStr());
            purchaseService.createPurchase(purchaseDto);

            // списание денег
            userDto.setBalance(userDto.getBalance() - gamePrice);
            userService.updateUser(userDto);

            userDto.setPurchases(purchaseService.findByUserId(userDto.getId()));
            req.setAttribute("userPurchases", userDto.getPurchases());
            req.setAttribute("user", userDto);
            req.getRequestDispatcher(JspHelper.get("userCabinet")).forward(req, resp);
        } else {
            // отрицательный исход покупки
            message = "Недостаточно средств на счете!";
            req.setAttribute("message", message);
            req.setAttribute("gameTitle", gameTitle);
            req.getRequestDispatcher(JspHelper.get("buyGame")).forward(req, resp);
        }
    }
}
