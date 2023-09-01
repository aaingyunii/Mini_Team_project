package com.springboot.mini.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebtoonDto {
    private Long numb;
    private Integer webtoonId;
    private String title;
    private String writer;
    private String painter;
    private Float starScore;
    private String age;
    private String publishDay;
    private Integer favorite;
    private String hashTag;
    private String thumbnailUrl;  
}
