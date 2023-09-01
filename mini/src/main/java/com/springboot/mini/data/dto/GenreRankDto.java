package com.springboot.mini.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GenreRankDto {
    private Integer genreRank;
    private String title;
    private String thumbnailUrl;
}
