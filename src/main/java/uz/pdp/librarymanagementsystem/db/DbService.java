package uz.pdp.librarymanagementsystem.db;

import uz.pdp.librarymanagementsystem.user.User;

import java.sql.*;

import static uz.pdp.librarymanagementsystem.db.DbConnection.getConnection;

public class DbService {
    public User login(String username, String password) {
        try {


            Connection connection = getConnection();
            String query = "select * from users where username=? and password=? ";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong(1);
                username = resultSet.getString(2);
                password = resultSet.getString(3);
                String fullName=resultSet.getString(4);

                User user = new User(
                        id,
                        username,
                        password,
                        fullName

                );
                return user;


            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
}
