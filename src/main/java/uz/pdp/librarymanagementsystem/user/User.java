package uz.pdp.librarymanagementsystem.user;

import lombok.*;

import java.sql.ResultSet;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String fullName;

    public void get(ResultSet resultSet) {
        try {
            this.id = resultSet.getLong("id");
            this.username = resultSet.getString("username");
            this.password = resultSet.getString("password");
            this.fullName = resultSet.getString("fullname");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
