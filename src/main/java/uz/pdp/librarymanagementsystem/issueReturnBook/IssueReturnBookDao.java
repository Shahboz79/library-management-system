package uz.pdp.librarymanagementsystem.issueReturnBook;

import uz.pdp.librarymanagementsystem.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static uz.pdp.librarymanagementsystem.db.DbConnection.getConnection;

public class IssueReturnBookDao {
    public static boolean addIssueReturnedBookDao(IssueReturnedBook issueReturnedBook) {
        try {
        Connection connection = getConnection();
        String issueQuery="insert into issued_returned_books(student_id,book_id,is_issued)" +
                "values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(issueQuery);

            preparedStatement.setLong(1,issueReturnedBook.getStudentId());
            preparedStatement.setLong(2,issueReturnedBook.getBookId());
            preparedStatement.setBoolean(3,issueReturnedBook.getIsIssued());

            boolean execute = preparedStatement.execute();
            System.out.println(execute);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<IssueReturnedBook> getAllReports(int size, int page) {
        try (Connection connection = DbConnection.getConnection()) {
            List<IssueReturnedBook> reports = new ArrayList<>();

            String sql = "select * from issued_returned_books\n" +
                    "limit ? offset ? * ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size);
            preparedStatement.setInt(3, page);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long userId = resultSet.getLong("student_id");
                long bookId = resultSet.getLong("book_id");
                Date dateTime=resultSet.getDate("datetime");
                boolean isIssued = resultSet.getBoolean("is_issued");


                IssueReturnedBook issuedReturnedBook = IssueReturnedBook.builder()
                        .id(id)
                        .studentId(userId)
                        .bookId(bookId)
                        .dateTime(dateTime)
                        .isIssued(isIssued)

                        .build();

                reports.add(issuedReturnedBook);
            }

            return reports;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getTotalSize() {
        try (Connection connection = DbConnection.getConnection()) {
            String sql = "select count(*) from category";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteIssuedReturnedBook(Long issuedReturnedId) {
        int execute=0;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = getConnection();
            String query = "delete from issued_returned_books where id=? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, issuedReturnedId);

            execute = preparedStatement.executeUpdate();
            System.out.println(execute);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return execute>0;
    }
}
