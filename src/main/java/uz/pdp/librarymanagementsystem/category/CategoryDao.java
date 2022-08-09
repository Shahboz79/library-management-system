package uz.pdp.librarymanagementsystem.category;

import uz.pdp.librarymanagementsystem.db.DbConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.librarymanagementsystem.db.DbConnection.getConnection;

public class CategoryDao {


    public static List<Category> getAllCategories() {


        try (Connection connection = DbConnection.getConnection();) {
            List<Category> categoryList = new ArrayList<>();

            String sql = "select * from category";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");

                Category category = Category.builder()
                        .id(id)
                        .name(name)
                        .build();

                categoryList.add(category);
            }

            return categoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static List<Category> getAllCategory(int size, int page) {
        try (Connection connection = DbConnection.getConnection();) {
            List<Category> categoryList = new ArrayList<>();

            String sql = "select * from category\n" +
                    "limit ? offset ? * ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size);
            preparedStatement.setInt(3, page);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");

                Category category = Category.builder()
                        .id(id)
                        .name(name)

                        .build();

                categoryList.add(category);
            }

            return categoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getTotalSize() {
        try (Connection connection = DbConnection.getConnection();) {
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

    public static boolean addNewCategory(Category category) {
        try {
            Connection connection = DbConnection.getConnection();
            String insertStudent = "insert into category ( name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertStudent);
            preparedStatement.setString(1, category.getName());


            int executeUpdate = preparedStatement.executeUpdate();

            return executeUpdate == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteCategory(Long categoryId) {

        int execute=0;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = getConnection();
            String query = "delete from category where id=? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, categoryId);

            execute = preparedStatement.executeUpdate();
            System.out.println(execute);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return execute>0;
    }
}
