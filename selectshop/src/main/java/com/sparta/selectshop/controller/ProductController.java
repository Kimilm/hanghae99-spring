package com.sparta.selectshop.controller;

        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/selectshop")
    public String selectShop() {
        return "selectshop.html";
    }
}
