package com.springboot.mini.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "week")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Week {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "webtoon_id")
    private Integer webtoonId;

    @Column(name = "title")
    private String title;

    @Column(name = "publish_day")
    private String publishDay;

    public Week(Integer webtoonId, String title, String publishDay) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.title = title;
        this.publishDay = publishDay;
    }
    // public Week toEntity(){
    //     return new Week(webtoonId,title,publishDay);
    // }
}
