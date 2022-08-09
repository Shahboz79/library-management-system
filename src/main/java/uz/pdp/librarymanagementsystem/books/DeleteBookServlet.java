package uz.pdp.librarymanagementsystem.books;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/delete-book")
public class DeleteBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long bookId = Long.valueOf(req.getParameter("id"));
        System.out.println(bookId);
        PrintWriter writer = resp.getWriter();

        boolean result = BookDao.deleteBook(bookId);
        if (result == true) {
            writer.write("<h1 color='green'>succecfully deleted </h1");
        }else {
            writer.write("<h1 color='green'>error </h1");

        }

    }
}
