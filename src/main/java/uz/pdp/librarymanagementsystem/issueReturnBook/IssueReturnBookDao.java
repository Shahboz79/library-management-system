package uz.pdp.librarymanagementsystem.issueReturnBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static uz.pdp.librarymanagementsystem.db.DbConnection.getConnection;

public class IssueReturnBookDao {
    public static boolean addIssueReturnedBookDao(IssueReturnedBook issueReturnedBook) {
        try {
        Connection connection = getConnection();
        String issueQuery="insert into issued_returned_books(student_id,book_id,is_issued)" +
                "values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(issueQuery);

            preparedStatement.setLong(1,issueReturnedBook.getStudent_id());
            preparedStatement.setLong(2,issueReturnedBook.getBook_id());
            preparedStatement.setBoolean(3,issueReturnedBook.getIsIssued());

            boolean execute = preparedStatement.execute();
            System.out.println(execute);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
