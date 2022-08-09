package uz.pdp.librarymanagementsystem.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.librarymanagementsystem.authors.Author;
import uz.pdp.librarymanagementsystem.category.Category;

import java.sql.ResultSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
    @Builder
public class Book {
    private Long id;
    private String title;
    private String description;
    private Set<Author> authors; // for get/edit

    private Set<Long> authorsIds; // for insert/update
    private Category category; // for get/edit

    private Long categoryId; // for insert/update
    private String isbn;
    private Integer year;
    private String imgUrl;



    public Book(Long id, String title, String description, Set<Author> authors, Category category, String isbn,
                Integer year, String imgUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.category = category;
        this.isbn = isbn;
        this.year = year;
        this.imgUrl = imgUrl;
    }
    public void get(ResultSet resultSet) {
        try {
            this.id = resultSet.getLong("id");
            this.title = resultSet.getString("title");
            this.isbn=resultSet.getString("isbn");
            this.year=resultSet.getInt("year");
            this.imgUrl = resultSet.getString("imgUrl");
            this.description = resultSet.getString("description");
            this.categoryId=resultSet.getLong("category_id");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
