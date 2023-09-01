package com.springboot.mini.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenreDto {
    private Long numb;
    private String genre_code;
    private String label_korea;
    private String genre_kind;
    private String label_english;
}
