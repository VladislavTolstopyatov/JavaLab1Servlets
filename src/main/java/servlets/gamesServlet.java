package servlets;

import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.*;
import services.GameService;
import services.KeyService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;

import static util.UrlPathUtil.GAMES;

@WebServlet(GAMES)
public class gamesServlet extends HttpServlet {
    private final GameService gameService = new GameService(new GameDao(),
            new KeyDao(),
            new GameMapper(),
            new CreateGameMapper(),
            new UpdateGameMapper());
    private final KeyService keyService = new KeyService(new KeyDao(),new KeyMapper(),new CreateKeyMapper());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GameDto> games = gameService.getAll();
        req.setAttribute("games", games);
        req.getRequestDispatcher(JspHelper.get("games")).forward(req, resp);
    }
}
