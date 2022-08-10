package uz.pdp.librarymanagementsystem.books;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.librarymanagementsystem.authors.Author;
import uz.pdp.librarymanagementsystem.category.Category;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static uz.pdp.librarymanagementsystem.db.DbConnection.getConnection;

public class BookDao {


    public static List<Book> getAllBooks(int size, int page) {
        try {
            ArrayList<Book> bookList = new ArrayList<>();

//          1. CONNECTION OCHAMIZ
            Connection connection = getConnection();

//        2. GET PREPARED STATEMENT

            String sql = "select b.id,\n" +
                    "       b.title,\n" +
                    "       b.imgurl,\n" +
                    "       json_agg(\n" +
                    "               json_build_object(\n" +
                    "                       'id', a.id,\n" +
                    "                       'fullName', a.fullname)) as authors" +
                    ",\n" +
                    "    json_build_object('id', c.id, 'name', c.name) as category\n" +
                    "--        c.id                                     as categoryId,\n" +
                    "--        c.name                                   as categoryName\n" +
                    "from book b\n" +
                    "         join book_authors ba on b.id = ba.book_id\n" +
                    "         join author a on a.id = ba.author_id\n" +
                    "         join category c on c.id = b.category_id\n" +
                    "group by b.id, c.id, c.name, b.title\n" +
                    "limit ? offset ? * (? - 1)";


            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, page);
            preparedStatement.setInt(3, page);


//            3. GET RESULTSET

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                long bookId = resultSet.getLong("id");
                String title = resultSet.getString("title");
                Array array = resultSet.getArray("authors");
                Object categoryObj = resultSet.getObject("category");
                String imgUrl = resultSet.getString("imgUrl");
                Type listType = new TypeToken<Set<Author>>() {
                }.getType();
                Set<Author> list = new Gson().fromJson(array.toString(), listType);

                Category category = new Gson().fromJson(categoryObj.toString(), Category.class);


                Book book = Book.builder()
                        .id(bookId)
                        .title(title)
                        .authors(list)
                        .category(category)
                        .imgUrl(imgUrl)
                        .build();

                bookList.add(book);


            }
            return bookList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean deleteBook(Long bookId) {
        boolean execute = false;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = getConnection();
            String query = "delete from book where id=? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, bookId);

            execute = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return execute;
    }
//    update book set title=?,description=?,category_id=?,imgurl=?,year=?,isbn=? where id=4
    public static boolean editBook(Book book, Long bookId) {
        try {

            Connection connection = getConnection();

            String insertBook = "  update book set title=?,description=?,category_id=?,imgurl=?,year=?,isbn=? where id=? ";
            // TODO: 03/08/22 add isbn, year

            PreparedStatement preparedStatement = connection.prepareStatement(insertBook);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setLong(3, book.getCategoryId());
            preparedStatement.setString(4, book.getImgUrl());
            preparedStatement.setInt(5, book.getYear());
            preparedStatement.setString(6, book.getIsbn());
            preparedStatement.setLong(7,bookId);


            String insertBooksAuthors = "update book_authors set author_id=? where book_id=?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(insertBooksAuthors);

            int executeUpdate1 = preparedStatement.executeUpdate();

            int executeUpdate2 = 0;
            for (Long authorId : book.getAuthorsIds()) {
                preparedStatement2.setLong(1, authorId);
                executeUpdate2 = preparedStatement2.executeUpdate();
            }
            preparedStatement2.setLong(2,bookId);

            return executeUpdate1 == 1 && executeUpdate2 == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List getFindAuthorsBookId(Long findAuthorBookId) {
        ArrayList books = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
        ) {
            String findAuthorsBookId = "select b.*,a.id as author_id, a.fullname as author_name from book b\n" +
                    "    join book_authors ba on b.id = ba.book_id\n" +
                    "    join author a on a.id = ba.author_id where b.id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(findAuthorsBookId);

            preparedStatement.setLong(1, findAuthorBookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Object o = new BookDao().get(resultSet);
                books.add(o);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static boolean addNewBook(Book book) {

        try {

            Connection connection = getConnection();

            String insertBook = "insert into book (title, description, category_id, imgurl,year,isbn) VALUES " +
                    "(?, ?, ?, ?,?,?)";
            // TODO: 03/08/22 add isbn, year

            PreparedStatement preparedStatement = connection.prepareStatement(insertBook);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setLong(3, book.getCategoryId());
            preparedStatement.setString(4, book.getImgUrl());
            preparedStatement.setInt(5, book.getYear());
            preparedStatement.setString(6, book.getIsbn());


            String insertBooksAuthors = "insert into book_authors VALUES ((select currval('book_id_seq')), ?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(insertBooksAuthors);

            int executeUpdate1 = preparedStatement.executeUpdate();

            int executeUpdate2 = 0;
            for (Long authorId : book.getAuthorsIds()) {
                preparedStatement2.setLong(1, authorId);
                executeUpdate2 = preparedStatement2.executeUpdate();
            }

            return executeUpdate1 == 1 && executeUpdate2 == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public Object get(ResultSet resultSet) {
        try {
            String author = resultSet.getString("author_name");
            Long booId = resultSet.getLong("id");
            String isbn = resultSet.getString("isbn");
            String title = resultSet.getString("title");
            String imgUrl = resultSet.getString("imgurl");
            String description = resultSet.getString("description");
            Long categoryId = resultSet.getLong("category_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
