package uz.pdp.librarymanagementsystem.issueReturnBook;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class IssueReturnedBook {
    private Long id;
    private Long studentId;
    private Long bookId;
    private Date dateTime;
    private Boolean isIssued;
}
