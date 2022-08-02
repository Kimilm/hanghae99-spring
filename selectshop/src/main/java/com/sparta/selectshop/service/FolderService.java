package com.sparta.selectshop.service;

import com.sparta.selectshop.models.folder.Folder;
import com.sparta.selectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public List<Folder> getUserFolders(Long userId) {
        return folderRepository.findByUserId(userId);
    }
}
