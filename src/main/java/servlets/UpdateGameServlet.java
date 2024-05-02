package servlets;

import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
import exceptions.DataBaseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CreateGameMapper;
import mappers.GameMapper;
import mappers.UpdateGameMapper;
import org.apache.log4j.Logger;
import services.GameService;
import util.JspHelper;

import java.io.IOException;

import static util.UrlPathUtil.GAMES;
import static util.UrlPathUtil.UPDATE_GAME;

@WebServlet(UPDATE_GAME)
public class UpdateGameServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateGameServlet.class);
    private final GameService gameService = new GameService(new GameDao(),
            new KeyDao(),
            new GameMapper(),
            new CreateGameMapper(),
            new UpdateGameMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        GameDto gameDto = null;
        try {
            gameDto = gameService.findByTitle(title);
        } catch (DataBaseException e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
        req.setAttribute("gameDto", gameDto);
        req.setAttribute("title", title);
        req.getRequestDispatcher(JspHelper.get("updateGame")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Double price = Double.valueOf(req.getParameter("newPrice"));
        String title = req.getParameter("title");
        try {
            GameDto gameDto = gameService.findByTitle(title);
            gameDto.setPrice(price);
            gameService.update(gameDto);
            resp.sendRedirect(GAMES);
        } catch (DataBaseException e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }

    }
}
