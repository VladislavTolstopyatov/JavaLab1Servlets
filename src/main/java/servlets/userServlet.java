package servlets;

import dto.PurchaseDto;
import dto.UserDto;
import entities.Purchase;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.PurchaseService;
import services.UserService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;

import static util.UrlPathUtil.USER;

@WebServlet(USER)
public class userServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final PurchaseService purchaseService = new PurchaseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = (UserDto) req.getSession().getAttribute("user");
        Integer id = userDto.getId();
        List<PurchaseDto> userPurchases = purchaseService.findByUserId(id);

        req.setAttribute("userPurchases", userPurchases);
        req.getRequestDispatcher(JspHelper.get("userCabinet")).forward(req, resp);
    }
}
