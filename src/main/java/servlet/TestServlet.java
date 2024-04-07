package servlet;

import dao.GameDao;
import entity.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.JspHelper;
import util.UrlPathUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class TestServlet extends HttpServlet {
    private static final List<Game> GAMES = new ArrayList<>(List.of(
            new Game(1, "Цивилизация 6", "Стратегия", 500, LocalDate.now()),
            new Game(2, "Дота 2", "RPG", 0.0, LocalDate.of(2003, 1, 1))
    ));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("games", GAMES);
        req.getRequestDispatcher(JspHelper.get("games"))
                .forward(req, resp);
    }
}
