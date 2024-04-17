package servlets;

import dto.GameDto;
import dto.KeyDto;
import entities.Key;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.KeyMapper;
import services.GameService;
import services.KeyService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;

import static util.UrlPathUtil.FIND_GAME;
import static util.UrlPathUtil.GAMES;

@WebServlet(FIND_GAME)
public class findGameByTitleServlet extends HttpServlet {
    private final GameService gameService = new GameService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("findGame")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gameTitle = req.getParameter("gameName");
        GameDto gameDto = null;
        try {
            gameDto = gameService.findByTitle(gameTitle);
            req.setAttribute("game", gameDto);
            req.getRequestDispatcher(JspHelper.get("findGame")).forward(req, resp);
        } catch (GameWithSuchTitleAlreadyExistsException e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(JspHelper.get("findGame")).forward(req, resp);
        }
    }
}
