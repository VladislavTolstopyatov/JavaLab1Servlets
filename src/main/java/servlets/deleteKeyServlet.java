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
import java.util.List;

import static util.UrlPathUtil.DELETE_KEY;

@WebServlet(DELETE_KEY)
public class deleteKeyServlet extends HttpServlet {
    private final KeyService keyService = new KeyService(new KeyDao(), new KeyMapper(), new CreateKeyMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        Integer id = Integer.valueOf(req.getParameter("Id"));
        keyService.deleteById(id);
        List<KeyDto> keys = keyService.getAllByTitle(title);
        req.setAttribute("keys", keys);
        req.setAttribute("title", title);
        req.getRequestDispatcher(JspHelper.get("getAllKeys")).forward(req, resp);
    }
}
