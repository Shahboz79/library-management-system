package uz.pdp.librarymanagementsystem.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.librarymanagementsystem.user.role.Role;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.librarymanagementsystem.db.DbConnection.getConnection;
import static uz.pdp.librarymanagementsystem.utils.Util.PAGE_SIZE;

@WebServlet("/students")
public class ViewUserServlet extends HttpServlet {
    public List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
        ) {


            ResultSet resultSet
                    = statement.executeQuery("select*from users");
            while (resultSet.next()) {
                User user = new User();
                user.get(resultSet);
                userList.add(user);
            }
            return userList;

        } catch (Exception e) {
            return null;

        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page");
        int page = 0;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) - 1;
        }
        List<User> users = UserService.getAllUsers(PAGE_SIZE, page);
        req.setAttribute("userList", users);
        req.setAttribute("pageSize", PAGE_SIZE);
        int total = (int) Math.round((double) UserService.getTotalSize() / (double) PAGE_SIZE);
        req.setAttribute("total", total);
        List<Role> roles = UserService.getRoles();
        req.setAttribute("roles",roles);
//        System.out.println(roles);
        Boolean added = Boolean.valueOf(req.getParameter("added"));
        if (added){
            req.setAttribute("message","Successfuly added");
        }

        req.getRequestDispatcher("/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
