package uz.pdp.librarymanagementsystem.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/userAdd")
public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Long roleId = Long.valueOf(req.getParameter("roleId"));
        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(password);
        boolean result = UserService.addNewUser(user,roleId);

        if (result) {
            resp.sendRedirect("/students?added=true");
        }
    }
}
