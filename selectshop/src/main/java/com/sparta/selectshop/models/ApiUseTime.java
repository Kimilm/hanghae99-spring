package com.sparta.selectshop.models;

import com.sparta.selectshop.models.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ApiUseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long totalTime;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long totalCount;

    public ApiUseTime(User user, Long totalTime) {
        this.user = user;
        this.totalTime = totalTime;
        this.totalCount = 1L;
    }

    public void addUseTime(Long useTime) {
        this.totalTime += useTime;
        this.totalCount += 1L;
    }
}
