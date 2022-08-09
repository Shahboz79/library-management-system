package uz.pdp.librarymanagementsystem.issueReturnBook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addIssue")
public class AddIssue extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long studentId = Long.valueOf(req.getParameter("userId"));
        Long bookId = Long.valueOf(req.getParameter("bookId"));
        Boolean isIssued = Boolean.valueOf(req.getParameter("issued"));
        IssueReturnedBook issueReturnedBook=IssueReturnedBook.builder()
                .student_id(studentId)
                .book_id(bookId)
                .isIssued(isIssued)
                .build();
    boolean result=IssueReturnBookDao.addIssueReturnedBookDao(issueReturnedBook);
        if (result) {
            resp.sendRedirect("/issue-return-book");
        }
    }
}
