package uz.pdp.librarymanagementsystem.user;

import uz.pdp.librarymanagementsystem.db.DbConnection;
import uz.pdp.librarymanagementsystem.user.role.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public  List<User> getUserList(){

        try (Connection connection = DbConnection.getConnection();) {
            List<User> userList = new ArrayList<>();

            String sql = "select * from users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("fullname");

                User user = User.builder()
                        .id(id)
                        .username(username)
                        .password(password)
                        .fullName(fullName)
                        .build();

                userList.add(user);
            }

            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> getAllUsers(int size, int page) {


        try (Connection connection = DbConnection.getConnection();) {
            List<User> userList = new ArrayList<>();

            String sql = "select * from users\n" +
                    "limit ? offset ? * ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size);
            preparedStatement.setInt(3, page);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("fullname");
                User user = User.builder()
                        .id(id)
                        .username(username)
                        .password(password)
                        .fullName(fullName)
                        .build();

                userList.add(user);
            }

            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static boolean addNewUser(User user,Long roleId) {
        try {
            Connection connection = DbConnection.getConnection();
            String insertStudent = "insert into users ( username, password, fullname) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertStudent);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3, user.getFullName());

            String insertUsersRoles = "insert into users_roles VALUES ((select currval('users_id_seq')), ?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(insertUsersRoles);

            int executeUpdate1 = preparedStatement.executeUpdate();

            int executeUpdate2 = 0;

                preparedStatement2.setLong(1, roleId);
                executeUpdate2 = preparedStatement2.executeUpdate();


            return executeUpdate1 == 1 && executeUpdate2 == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getTotalSize() {
        try (Connection connection = DbConnection.getConnection();) {
            String sql = "select count(*) from users";
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
    public static List<Role> getRoles() {


        try (Connection connection = DbConnection.getConnection();) {
            List<Role> roles = new ArrayList<>();

            String sql = "select * from roles";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                System.out.println(name);

                Role role = Role.builder()
                        .id(id)
                        .name(name)
                        .build();

                roles.add(role);
            }

            return roles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
