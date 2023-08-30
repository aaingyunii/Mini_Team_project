package com.springboot.mini.data.dto;
// DTO: 각 클래스 및 인터페이스 호출 및 전달 매개변수로 사용되는 데이터 객체
// 데이터 교환에만 사용되는 객체이므로 DTO에는 별도의 로직이 포함되지 않습니다.

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.springboot.mini.data.entity.Webtoon;

@Data // 롬복 어노테이션의 모두를 포괄하는 어노테이션
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

    public Webtoon toEntity(){
        return new Webtoon(numb, webtoonId, title, writer, painter, age, favorite, starScore, thumbnailUrl, publishDay, hashTag);
    }
}
