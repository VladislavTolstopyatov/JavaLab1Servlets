package servlets;

import dto.GameDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.GameService;
import services.KeyService;
import util.JspHelper;
import util.UrlPathUtil;

import java.io.IOException;
import java.util.List;

import static util.UrlPathUtil.GAMES;

@WebServlet(GAMES)
public class gamesServlet extends HttpServlet {
    private final GameService gameService = new GameService();
    private final KeyService keyService = new KeyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GameDto> games = gameService.getAll();
        req.setAttribute("games", games);
        req.getRequestDispatcher(JspHelper.get("games")).forward(req, resp);
    }
}
