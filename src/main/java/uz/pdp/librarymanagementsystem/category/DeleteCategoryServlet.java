package uz.pdp.librarymanagementsystem.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/delete-category")
public class DeleteCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long categoryId = Long.valueOf(req.getParameter("id"));
        System.out.println(categoryId);
        PrintWriter writer = resp.getWriter();

        boolean result = CategoryDao.deleteCategory(categoryId);
        if (result == true) {
            resp.sendRedirect("/category?deleted=true");
        }

    }
}
