package com.sparta.selectshop.service;

import com.sparta.selectshop.models.folder.Folder;
import com.sparta.selectshop.models.product.Product;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.repository.FolderRepository;
import com.sparta.selectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    // 로그인한 회원이 등록한 모든 폴더 조회
    public List<Folder> getUserFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    // 유저 아이디로 폴더 생성
    public List<Folder> addFolders(List<String> folderNames, User user) {
        // 유저의 입력값에서 중복 제거
        folderNames = folderNames.stream().distinct().collect(Collectors.toList());
        // 회원이 기존에 생성한 폴더 리스트
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNames) {
            if (!isExistFolderName(folderName, existFolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            }
        }

        return folderRepository.saveAll(folderList);
    }

    // 회원ID가 소유한 폴더에 저장되어있는 상품들 조회
    public Page<Product> getProductsInFolder(
            Long folderId,
            int page,
            int size,
            String sortBy,
            boolean isAsc,
            User user
    ) {
        Pageable pageable = PageUtils.createPageRequest(page, size, sortBy, isAsc);
        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }
    
    private boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        for (Folder folder : existFolderList) {
            if (folder.getName().equals(folderName)) {
                return true;
            }
        }

        return false;
    }
}
