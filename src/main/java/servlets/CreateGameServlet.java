package servlets;

import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
import exceptions.DataBaseException;
import exceptions.GameWithSuchTitleAlreadyExistsException;
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
import java.time.LocalDate;

import static util.UrlPathUtil.CREATE_GAME;
import static util.UrlPathUtil.GAMES;

@WebServlet(CREATE_GAME)
public class CreateGameServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CreateGameServlet.class);
    private final GameService gameService = new GameService(new GameDao(),
            new KeyDao(),
            new GameMapper(),
            new CreateGameMapper(),
            new UpdateGameMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("createGame")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("gameTitle");
        String description = req.getParameter("gameDescription");
        Double price = Double.valueOf(req.getParameter("gamePrice"));
        String dateOfRelease = req.getParameter("releaseDate");

        if (dateOfRelease.length() > 10) {
            String message = "Некорректный год релиза!";
            req.setAttribute("message", message);
            doGet(req, resp);
        } else {
            LocalDate localDateR = LocalDate.parse(req.getParameter("releaseDate"));
            GameDto gameDto = new GameDto(null, title, description, price, localDateR, null, 0);
            try {
                gameService.createGame(gameDto);
            } catch (GameWithSuchTitleAlreadyExistsException e) {
                logger.error(e.getMessage());
                req.setAttribute("notUniqueTitle", e.getMessage());
                doGet(req, resp);
            } catch (DataBaseException e) {
                logger.error(e.getMessage());
                req.setAttribute("error", e.getMessage());
                doGet(req, resp);
            }
            resp.sendRedirect(GAMES);
        }
    }
}
