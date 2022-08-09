package uz.pdp.librarymanagementsystem.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.librarymanagementsystem.db.DbService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet({"", "/login"})
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        DbService dbService = new DbService();
        User user = dbService.login(username, password);

        PrintWriter printWriter = resp.getWriter();
        if (user != null) {
            if (user.getUsername().equals("Shahboz") && user.getPassword().equals("02")) {
                resp.sendRedirect("/admin");
            }
        } else {
            if (user!=null){
                resp.sendRedirect("/user");
            }else {
                resp.sendRedirect("/login");
            }
        }
    }
}
