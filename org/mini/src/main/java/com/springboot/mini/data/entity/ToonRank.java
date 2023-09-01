package com.springboot.mini.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "toonrank")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class ToonRank {
    @Id
    private Long numb;

    @ManyToOne
    @JoinColumn(name = "genre_code")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @Column(name = "genre_rank")
    private Integer genre_rank;

    public ToonRank toEntity(){
        return new ToonRank(numb, genre, webtoon, genre_rank);
    }
}
