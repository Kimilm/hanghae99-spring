package com.sparta.selectshop.repository;

import com.sparta.selectshop.models.folder.Folder;
import com.sparta.selectshop.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);

    List<Folder> findAllByUserAndNameIn(User user, List<String> folderNames);
}
