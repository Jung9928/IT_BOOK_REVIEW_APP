package com.jung0407.it_book_review_app.model.dto;

import com.jung0407.it_book_review_app.model.entity.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@AllArgsConstructor
public class BookSliceWithCount {

    private List<BookEntity> content;
    private long totalCount;
    private boolean hasNext;
}
