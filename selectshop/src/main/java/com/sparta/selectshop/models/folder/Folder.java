package com.sparta.selectshop.models.folder;

import com.sparta.selectshop.models.Timestamped;
import com.sparta.selectshop.models.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Folder extends Timestamped {
    @Id
    @Column(name = "FOLDER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    public User user;
}
