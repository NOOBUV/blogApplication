package com.app.blog.payloads.Dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CategoryDto {
    private Integer categoryId;
    @Size(min = 4, max = 30, message = "Category Title should be proper")
    private String categoryTitle;
    @Size(min = 4, message = "Describe Category Properly")
    private String categoryDescription;
}
