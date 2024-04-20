package servlets;

import dao.GameDao;
import dao.KeyDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CreateGameMapper;
import mappers.GameMapper;
import mappers.UpdateGameMapper;
import services.GameService;

import java.io.IOException;

import static util.UrlPathUtil.DELETE_GAME;
import static util.UrlPathUtil.GAMES;

@WebServlet(DELETE_GAME)
public class deleteGameServlet extends HttpServlet {
    private final GameService gameService = new GameService(new GameDao(),
            new KeyDao(),
            new GameMapper(),
            new CreateGameMapper(),
            new UpdateGameMapper());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        gameService.deleteById(gameService.findIdByTitle(title));
        resp.sendRedirect(req.getContextPath() + GAMES);
    }
}
