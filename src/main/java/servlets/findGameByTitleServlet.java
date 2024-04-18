package servlets;

<<<<<<< HEAD
import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
=======
import dto.GameDto;
import dto.KeyDto;
import entities.Key;
>>>>>>> origin/master
import exceptions.GameWithSuchTitleAlreadyExistsException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import mappers.CreateGameMapper;
import mappers.GameMapper;
import mappers.UpdateGameMapper;
import services.GameService;
import util.JspHelper;

import java.io.IOException;

import static util.UrlPathUtil.FIND_GAME;

@WebServlet(FIND_GAME)
public class findGameByTitleServlet extends HttpServlet {
    private final GameService gameService = new GameService(new GameDao(),
            new KeyDao(),
            new GameMapper(),
            new CreateGameMapper(),
            new UpdateGameMapper());
=======
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
>>>>>>> origin/master
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
