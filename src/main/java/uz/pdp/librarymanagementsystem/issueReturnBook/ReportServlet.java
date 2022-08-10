package uz.pdp.librarymanagementsystem.issueReturnBook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static uz.pdp.librarymanagementsystem.utils.Util.PAGE_SIZE;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page");
        int page = 0;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) - 1;
        }
        List<IssueReturnedBook> reports = IssueReturnBookDao.getAllReports(PAGE_SIZE, page);

        req.setAttribute("reports", reports);
        req.setAttribute("pageSize", PAGE_SIZE);
        int total = (int) (Math.round((double) IssueReturnBookDao.getTotalSize() / (double) PAGE_SIZE)+2);
        req.setAttribute("total", total);

        Boolean added = Boolean.valueOf(req.getParameter("added"));
        if (added){
            req.setAttribute("message","Successfuly added");
        }
        Boolean deleted = Boolean.valueOf(req.getParameter("deleted"));
        if (deleted){
            req.setAttribute("message","Successfuly deleted");
        }

        req.getRequestDispatcher("/reports.jsp").forward(req, resp);
    }
}
