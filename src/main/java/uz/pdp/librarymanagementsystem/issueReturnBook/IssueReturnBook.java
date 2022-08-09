package uz.pdp.librarymanagementsystem.issueReturnBook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.librarymanagementsystem.books.Book;
import uz.pdp.librarymanagementsystem.books.BookController;
import uz.pdp.librarymanagementsystem.user.User;
import uz.pdp.librarymanagementsystem.user.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/issue-return-book")
public class IssueReturnBook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        List<User> userList = userService.getUserList();
        BookController bookController = new BookController();
        List<Book> bookList = bookController.getBookList();
        System.out.println(bookList);
        System.out.println(userList);


        req.setAttribute("userList",userList);
        req.setAttribute("bookList",bookList);

        req.getRequestDispatcher("issue-return-book.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
