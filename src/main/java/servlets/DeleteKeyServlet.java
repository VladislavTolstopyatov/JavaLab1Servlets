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
import java.util.List;

import static util.UrlPathUtil.DELETE_KEY;

@WebServlet(DELETE_KEY)
public class DeleteKeyServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteKeyServlet.class);
    private final KeyService keyService = new KeyService(new KeyDao(), new KeyMapper(), new CreateKeyMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        Integer id = Integer.valueOf(req.getParameter("Id"));
        try {
            keyService.deleteById(id);
            List<KeyDto> keys = keyService.getAllByTitle(title);
            req.setAttribute("keys", keys);
            req.setAttribute("title", title);
            req.getRequestDispatcher(JspHelper.get("getAllKeys")).forward(req, resp);
        } catch (DataBaseException e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
