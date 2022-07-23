package com.sparta.week01.domain.memo;

import com.sparta.week01.domain.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String contents;

    public Memo(MemoRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.contents = requestDto.getContents();
    }
}
