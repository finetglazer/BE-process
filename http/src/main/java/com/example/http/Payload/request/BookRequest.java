package com.example.http.Payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class BookRequest {
    @NotBlank
    @Size(min=1, max = 100)
    private String title;

    private String author;

}
