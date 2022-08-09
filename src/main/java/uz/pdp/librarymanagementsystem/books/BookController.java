package uz.pdp.librarymanagementsystem.books;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.librarymanagementsystem.db.DbConnection.getConnection;

@WebServlet("/books")
public class BookController extends HttpServlet {

    public List getBookList(){
        List<Book> bookList = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
        ) {


            ResultSet resultSet
                    = statement.executeQuery("select*from book");
            while (resultSet.next()) {
                Book book = new Book();
                book.get(resultSet);
                bookList.add(book);
            }
            return bookList;

        } catch (Exception e) {
            return null;

        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List bookList = new BookController().getBookList();
        System.out.println(bookList);

        req.setAttribute("bookList", bookList);
        req.getRequestDispatcher("books.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String title= req.getParameter("bookName");
//        Long categoryId= Long.valueOf(req.getParameter("categoryId"));
//        String imgUrl= req.getParameter("imgUrl");
//        Long authorId= Long.valueOf(req.getParameter("authorId"));
//
//
//
//        PrintWriter printWriter=resp.getWriter();
//        Book book=new Book(title,categoryId,imgUrl,authorId);
//
//        boolean success =  new BookService().add(book);
//        if (success==true){
//            printWriter.write("<h1 color='green'>succesfully add book</h1");
//        }
//        else {
//            printWriter.write("<h1 color='red'>error</h1");
//
//        }


    }
}
