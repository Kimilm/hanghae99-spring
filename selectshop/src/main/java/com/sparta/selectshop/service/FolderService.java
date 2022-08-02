package com.sparta.selectshop.service;

import com.sparta.selectshop.models.folder.Folder;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    // 로그인한 회원이 등록한 모든 폴더 조회
    public List<Folder> getUserFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    // 유저 아이디로 폴더 생성
    public List<Folder> addFolders(List<String> folderNames, User user) {
        List<Folder> folderList = folderNames.stream()
                .map(name -> new Folder(name, user))
                .collect(Collectors.toList());

        return folderRepository.saveAll(folderList);
    }
}
