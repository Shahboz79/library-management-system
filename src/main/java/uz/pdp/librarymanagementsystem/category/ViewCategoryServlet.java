package uz.pdp.librarymanagementsystem.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static uz.pdp.librarymanagementsystem.utils.Util.PAGE_SIZE;

@WebServlet("/category")
public class ViewCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page");
        int page = 0;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) - 1;
        }
        List<Category> categoryList = CategoryDao.getAllCategory(PAGE_SIZE, page);

        req.setAttribute("categoryList", categoryList);
        req.setAttribute("pageSize", PAGE_SIZE);
        int total = (int) Math.round((double) CategoryDao.getTotalSize() / (double) PAGE_SIZE);
        req.setAttribute("total", total);

        Boolean added = Boolean.valueOf(req.getParameter("added"));
        if (added){
            req.setAttribute("message","Successfuly added");
        }
        Boolean deleted = Boolean.valueOf(req.getParameter("deleted"));
        if (deleted){
            req.setAttribute("message","Successfuly deleted");
        }

        req.getRequestDispatcher("/category.jsp").forward(req, resp);
    }
}
