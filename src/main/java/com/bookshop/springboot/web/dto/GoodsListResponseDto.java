package com.bookshop.springboot.web.dto;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GoodsListResponseDto {
    private Long goodsId;
    private String goodsTitle;
    private String goodsWriter;
    private Integer goodsPrice;
    private String goodsPublisher;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate goodsPublishedDate;
    private Integer    goodsSalesPrice;
    private LocalDate createdDate;
    private List<ImageFile> imageList;

    public GoodsListResponseDto(Goods entity) {
        this.goodsId = entity.getGoodsId();
        this.goodsTitle = entity.getGoodsTitle();
        this.goodsWriter = entity.getGoodsWriter();
        this.goodsPrice = entity.getGoodsPrice();
        this.goodsPublisher = entity.getGoodsPublisher();
        this.goodsPublishedDate = entity.getGoodsPublishedDate();
        this.goodsSalesPrice = entity.getGoodsSalesPrice();
        this.createdDate = entity.getCreatedDate();
        this.imageList = entity.getImageList();
    }
}
