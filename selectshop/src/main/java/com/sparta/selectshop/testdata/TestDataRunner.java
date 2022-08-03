package com.sparta.selectshop.testdata;

import com.sparta.selectshop.controller.SearchRequestController;
import com.sparta.selectshop.models.folder.Folder;
import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.models.product.Product;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.models.user.UserRoleEnum;
import com.sparta.selectshop.repository.FolderRepository;
import com.sparta.selectshop.repository.ProductRepository;
import com.sparta.selectshop.repository.UserRepository;
import com.sparta.selectshop.security.model.UserDetailsImpl;
import com.sparta.selectshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.selectshop.service.ProductService.MIN_MY_PRICE;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {

    private final UserService userService;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final FolderRepository folderRepository;

    private final SearchRequestController searchRequestController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        // 테스트 User 생성
//        User testUser1 = new User("정국", passwordEncoder.encode("123"), "jg@sparta.com", UserRoleEnum.USER);
//        User testUser2 = new User("제이홉", passwordEncoder.encode("123"), "hope@sparta.com", UserRoleEnum.USER);
//        User testAdminUser1 = new User("아미", passwordEncoder.encode("123"), "army@sparta.com", UserRoleEnum.ADMIN);
//        testUser1 = userRepository.save(testUser1);
//        testUser2 = userRepository.save(testUser2);
//        testAdminUser1 = userRepository.save(testAdminUser1);
//
//        // testUser1 강제 로그인 처리
//        UserDetails userDetails = new UserDetailsImpl(testUser1);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        List<User> testUserList = new ArrayList<>();
//
//        testUserList.add(new User("kimilm", passwordEncoder.encode("kimilm"), "kimilm@kimilm.com", UserRoleEnum.USER));
//        testUserList.add(new User("kimilmKakao", passwordEncoder.encode("kimilmKakao"), "gnlfla7416@daum.net", UserRoleEnum.USER));
//        testUserList.add(new User("kimilmAdmin", passwordEncoder.encode("kimilmAdmin"), "kimilmAdmin@kimilm.com", UserRoleEnum.ADMIN));
//
//        testUserList = userRepository.saveAll(testUserList);
//
//        // 테스트 User 의 관심상품 등록
//        // 검색어 당 관심상품 10개 등록
//        createTestData(testUser1, "신발");
//        createTestData(testUser1, "과자");
//        createTestData(testUser1, "키보드");
//        createTestData(testUser1, "휴지");
//        createTestData(testUser1, "휴대폰");
//        createTestData(testUser1, "앨범");
//        createTestData(testUser1, "헤드폰");
//        createTestData(testUser1, "이어폰");
//        createTestData(testUser1, "노트북");
//        createTestData(testUser1, "무선 이어폰");
//        createTestData(testUser1, "모니터");
    }

    private void createTestData(User user, String searchWord) {
        // 네이버 쇼핑 API 통해 상품 검색
        List<ItemDto> itemDtoList = searchRequestController.execSearch(searchWord, new UserDetailsImpl(user));

        List<Product> productList = new ArrayList<>();

        for (ItemDto itemDto : itemDtoList) {
            Product product = new Product();
            // 관심상품 저장 사용자
            product.setUserId(user.getId());
            // 관심상품 정보
            product.setTitle(itemDto.getTitle());
            product.setLink(itemDto.getLink());
            product.setImage(itemDto.getImage());
            product.setLprice(itemDto.getLprice());

            // 희망 최저가 랜덤값 생성
            // 최저 (100원) ~ 최대 (상품의 현재 최저가 + 10000원)
            int myPrice = getRandomNumber(MIN_MY_PRICE, itemDto.getLprice() + 10000);
            product.setMyprice(myPrice);

            productList.add(product);
        }

        productRepository.saveAll(productList);

        Folder folder = new Folder(searchWord, user);
        folderRepository.save(folder);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}