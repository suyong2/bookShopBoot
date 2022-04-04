package com.bookshop.springboot.service;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.goods.GoodsRepository;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import com.bookshop.springboot.domain.imagefile.ImageFileRepository;
import com.bookshop.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;
//    private final ImageFileRepository imagesRepository;

    public GoodsResponseDto goodsDetail(Long id) {
        Goods entity = goodsRepository.selectGoodsDetail(id);
        if (entity == null) {
            throw new IllegalArgumentException("해당 상품이 없습니다. id=" + id);
        }
        return new GoodsResponseDto(entity);
    }

    public GoodsResponseDto findById(Long id) {
        Goods entity = goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));

        return new GoodsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public Map<String,List<GoodsListResponseDto>> listGoods() {

        Map<String,List<GoodsListResponseDto>> goodsMap= new HashMap<>();
        String[] kinds = {"bestseller", "newbook", "steadyseller"};
        List<GoodsListResponseDto> goodsList=null;
        Pageable paging = PageRequest.of(0, 15);
        for (int i=0; i<kinds.length; i++){
            goodsList=goodsRepository.selectGoodsList(kinds[i], paging).stream()
                    .map(GoodsListResponseDto::new)
                    .collect(Collectors.toList());
            goodsMap.put(kinds[i], goodsList);
        }

        return goodsMap;
    }

}
