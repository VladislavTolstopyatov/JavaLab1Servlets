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

import static util.UrlPathUtil.GET_ALL_KEYS;

@WebServlet(GET_ALL_KEYS)
public class allKeysServlet extends HttpServlet {

    private final KeyService keyService = new KeyService(new KeyDao(), new KeyMapper(),new CreateKeyMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        List<KeyDto> keys = keyService.getAllByTitle(title);
        req.setAttribute("title", title);
        req.setAttribute("keys", keys);
        req.getRequestDispatcher(JspHelper.get("getAllKeys")).forward(req, resp);
    }
}
