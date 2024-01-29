package com.blog.app.apis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CategoryDto {


    private  Integer categoryId;

    @NotBlank
    @Size(min = 5,message = "min size of categories title is 4 ")
    private  String categoryTitle;

    @NotBlank
    @Size(min = 10,message = "min size of categories description is 10")
    private  String categoryDescription;
}
