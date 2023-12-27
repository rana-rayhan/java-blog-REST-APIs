package com.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int categoryId;
    @NotEmpty
    @Size(min = 3, message = "Minimum length should be 3 char")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 5, message = "Minimum length should be 5 char")
    private String categoryDesc;
}
