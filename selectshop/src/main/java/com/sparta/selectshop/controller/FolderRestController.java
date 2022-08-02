package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.folder.Folder;
import com.sparta.selectshop.models.folder.FolderRequestDto;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.security.model.UserDetailsImpl;
import com.sparta.selectshop.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FolderRestController {

    private final FolderService folderService;

    // 폴더 생성
    @PostMapping("/api/folders")
    public List<Folder> addFolders(
            @RequestBody FolderRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<String> folderNames = requestDto.getFolderNames();
        User user = userDetails.getUser();

        List<Folder> folders = folderService.addFolders(folderNames, user);
        return folders;
    }
}
