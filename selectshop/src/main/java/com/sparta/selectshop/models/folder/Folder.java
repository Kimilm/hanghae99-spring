package com.sparta.selectshop.models.folder;

import com.sparta.selectshop.models.Timestamped;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Folder extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public Long userId;
}
