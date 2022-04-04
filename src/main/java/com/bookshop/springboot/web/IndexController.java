package com.bookshop.springboot.web;

import com.bookshop.springboot.config.auth.LoginUser;
import com.bookshop.springboot.config.auth.dto.SessionUser;
import com.bookshop.springboot.service.GoodsService;
import com.bookshop.springboot.web.dto.GoodsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final GoodsService goodsService;

    @Value("${resources.uri_path:}")
    private String resourcesUriPath;

    @GetMapping("/")
    public String index(HttpSession session, Model model, @LoginUser SessionUser user) {
        session.setAttribute("side_menu", "user");
        model.addAttribute("goodsMap", goodsService.listGoods());
        model.addAttribute("resourcesUriPath", resourcesUriPath);
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "main";
    }

    @GetMapping("/member/login")
    public String login(){
        System.out.println("login");
        return "member-login";
    }

    @GetMapping("/goods/detail/{id}")
    public String goodsDetail(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        GoodsResponseDto dto= goodsService.goodsDetail(id);
        model.addAttribute("goods", dto);
        model.addAttribute("resourcesUriPath", resourcesUriPath);
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "goods-detail";
    }


}
