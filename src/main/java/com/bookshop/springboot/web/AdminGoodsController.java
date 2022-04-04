package com.bookshop.springboot.web;

import com.bookshop.springboot.config.auth.LoginUser;
import com.bookshop.springboot.config.auth.dto.SessionUser;
import com.bookshop.springboot.service.AdminGoodsService;
import com.bookshop.springboot.service.GoodsService;
import com.bookshop.springboot.web.common.base.BaseController;
import com.bookshop.springboot.web.dto.GoodsResponseDto;
import com.bookshop.springboot.web.dto.GoodsSaveRequestDto;
import com.bookshop.springboot.web.dto.ImagesSaveRequestDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/admin/goods")
@Controller
public class AdminGoodsController extends BaseController {

    private final AdminGoodsService adminGoodsService;

    @Value("${resources.uri_path:}")
    private String resourcesUriPath;

    @GetMapping("/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "admin-goods-save";
    }

    @GetMapping("/update/{id}")
    public String goodsDetail(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        Map goodsMap= adminGoodsService.findById(id);
        model.addAttribute("goodsMap", goodsMap);
        model.addAttribute("resourcesUriPath", resourcesUriPath);
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "admin-goods-update";
    }

    @GetMapping("/main")
    public String goodsSave(HttpSession session, @RequestParam Map<String, String> dateMap,
                            Model model, @LoginUser SessionUser user) {
//        HttpSession session=request.getSession();
        session.setAttribute("side_menu", "admin_mode"); //마이페이지 사이드 메뉴로 설정한다.

        String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
        String section = dateMap.get("section");
        String pageNum = dateMap.get("pageNum");
        String beginDate=null,endDate=null;

        String [] tempDate=calcSearchPeriod(fixedSearchPeriod).split(",");
        beginDate=tempDate[0];
        endDate=tempDate[1];
        dateMap.put("beginDate", beginDate);
        dateMap.put("endDate", endDate);

        Map<String,Object> condMap=new HashMap<>();
        if(section== null) {
            section = "1";
        }
        condMap.put("section",section);
        if(pageNum== null) {
            pageNum = "1";
        }
        condMap.put("pageNum",pageNum);
        condMap.put("beginDate",beginDate);
        condMap.put("endDate", endDate);

        model.addAttribute("newGoodsList", adminGoodsService.listNewGoods(condMap));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "admin-goods-main";
    }
}
