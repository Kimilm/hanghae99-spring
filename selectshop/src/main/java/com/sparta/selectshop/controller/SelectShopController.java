package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.folder.Folder;
import com.sparta.selectshop.models.user.UserRoleEnum;
import com.sparta.selectshop.security.model.UserDetailsImpl;
import com.sparta.selectshop.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SelectShopController {

    private final FolderService folderService;

    @GetMapping("/selectshop")
    public String selectShop(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());

        // 관리자 로그인시 관리자 권한 추가
        if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN) {
            model.addAttribute("admin_role", true);
        }

        // 유저가 생성한 모든 폴더 불러오기
        List<Folder> folderList = folderService.getUserFolders(userDetails.getUser());
        model.addAttribute("folders", folderList);

        return "selectshop";
    }
}
