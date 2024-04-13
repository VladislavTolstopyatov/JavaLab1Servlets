package servlets;

import dto.KeyDto;
import dto.PurchaseDto;
import dto.UserDto;
import entities.Key;
import entities.Purchase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.GameService;
import services.KeyService;
import services.PurchaseService;
import services.UserService;
import util.JspHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static util.UrlPathUtil.*;

@WebServlet(BUYGAME)
public class buyGameServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final PurchaseService purchaseService = new PurchaseService();
    private final KeyService keyService = new KeyService();
    private final GameService gameService = new GameService();

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
//                req.getParameter("gameTitle");

        // Сохраняем параметры игры в сессии
//        req.getSession().setAttribute("gamePrice", gamePrice);
//        req.getSession().setAttribute("gameTitle", gameTitle);

        String message;

        // положительный исход покупки
        if (userDto.getBalance() - gamePrice > 0) {

            // получение ключа игры
            KeyDto key = keyService.findByGameId(gameService.findIdByTitle(gameTitle)).get(0);
            // TODO надо удалить ключ или оставить, посмотрим

            // фиксация покупки пользователя и добавление в бд
            PurchaseDto purchaseDto = purchaseService.createPurchase(new PurchaseDto(LocalDate.now(),
                    null, gameTitle, userDto.getId(), key.getKeyStr()));

            userDto.setPurchases(purchaseService.findByUserId(userDto.getId()));
            req.setAttribute("userPurchases", userDto.getPurchases());
            req.getRequestDispatcher(JspHelper.get("userCabinet")).forward(req, resp);
//            resp.sendRedirect(req.getContextPath() + USER);
        } else {
            // отрицательный исход покупки
            message = "Недостаточно средств на счете!";
            req.setAttribute("message", message);
            req.setAttribute("gameTitle", gameTitle);
            req.getRequestDispatcher(JspHelper.get("buyGame")).forward(req, resp);
        }
    }
}
