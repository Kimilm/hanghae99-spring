package com.sparta.selectshop.controller;

import com.sparta.selectshop.exception.RestApiException;
import com.sparta.selectshop.models.folder.Folder;
import com.sparta.selectshop.models.folder.FolderRequestDto;
import com.sparta.selectshop.models.folder.FolderResponseDto;
import com.sparta.selectshop.models.product.Product;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.security.model.UserDetailsImpl;
import com.sparta.selectshop.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FolderRestController {

    private final FolderService folderService;

    // 폴더 생성
    @PostMapping("/api/folders")
    public ResponseEntity<?> addFolders(
            @RequestBody FolderRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<String> folderNames = requestDto.getFolderNames();
        User user = userDetails.getUser();

        List<FolderResponseDto> folders = folderService.addFolders(folderNames, user).stream()
                .map(FolderResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(folders);
    }

    // 유저가 생성한 모든 폴더 조회
    @GetMapping("/api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getUserFolders(userDetails.getUser());
    }

    // 회원이 등록한 폴더 내 모든 상품 조회
    @GetMapping("/api/folders/{folderId}/products")
    public Page<Product> getProductsInFolder(
            @PathVariable Long folderId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        page = page - 1;
        return folderService.getProductsInFolder(
                folderId,
                page,
                size,
                sortBy,
                isAsc,
                userDetails.getUser()
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> handleException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessge(ex.getMessage());

        return ResponseEntity.badRequest().body(restApiException);
    }
}
