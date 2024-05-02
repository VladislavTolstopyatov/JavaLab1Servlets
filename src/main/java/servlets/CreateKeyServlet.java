package servlets;

import dao.GameDao;
import dao.KeyDao;
import dto.KeyDto;
import exceptions.DataBaseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CreateKeyMapper;
import mappers.KeyMapper;
import org.apache.log4j.Logger;
import services.KeyService;
import util.JspHelper;

import java.io.IOException;

import static util.UrlPathUtil.CREATE_KEY;
import static util.UrlPathUtil.GAMES;

@WebServlet(CREATE_KEY)

public class CreateKeyServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CreateKeyServlet.class);
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
        try {
            keyService.createKey(keyDto);
        } catch (DataBaseException e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
        resp.sendRedirect(GAMES);
    }
}
