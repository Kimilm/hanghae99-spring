package com.sparta.selectshop.models.folder;

import com.sparta.selectshop.models.Timestamped;
import com.sparta.selectshop.models.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Folder extends Timestamped {
    @Id
    @Column(name = "FOLDER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }
}


