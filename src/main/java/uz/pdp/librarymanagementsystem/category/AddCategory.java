package uz.pdp.librarymanagementsystem.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addCategory")
public class AddCategory extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        Category category = new Category();
        category.setName(name);

        boolean result = CategoryDao.addNewCategory(category);

        if (result) {
            resp.sendRedirect("/category?added=true");
        }
    }
}
