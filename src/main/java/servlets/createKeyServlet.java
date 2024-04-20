package servlets;

import dao.KeyDao;
import dto.KeyDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CreateKeyMapper;
import mappers.KeyMapper;
import services.KeyService;
import util.JspHelper;

import java.io.IOException;

import static util.UrlPathUtil.CREATE_KEY;
import static util.UrlPathUtil.GAMES;

@WebServlet(CREATE_KEY)

public class createKeyServlet extends HttpServlet {
    private final KeyService keyService = new KeyService(new KeyDao(), new KeyMapper(), new CreateKeyMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        req.setAttribute("title", title);
        req.getRequestDispatcher(JspHelper.get("createKey")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        req.setAttribute("title", title);
        String keyStr = req.getParameter("keyStr");
        KeyDto keyDto = new KeyDto(null, keyStr, title);
        keyService.createKey(keyDto);
        resp.sendRedirect(GAMES);
    }
}
