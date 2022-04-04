package com.bookshop.springboot.web.dto;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
public class GoodsResponseDto {
    private Long goodsId;
    private String goodsTitle;
    private String goodsWriter;
    private Integer goodsPrice;
    private String goodsPublisher;
    private String goodsStatus;
    private String goodsIsbn;
//    private Integer    goodsPoint;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate goodsPublishedDate;
//    private Integer    goodsTotalPage;
    private Integer    goodsSalesPrice;
//    private String goodsIntro;
//    private String goodsWriterIntro;
//    private String goodsContentsOrder;
//    private String goodsPublisherComment;
//    private String goodsRecommendation;
//    private String fileName;
    private List<ImageFile> imageList;

    public GoodsResponseDto(Goods entity){
        this.goodsId = entity.getGoodsId();
        this.goodsTitle = entity.getGoodsTitle();
        this.goodsWriter = entity.getGoodsWriter();
        this.goodsPrice = entity.getGoodsPrice();
        this.goodsPublisher= entity.getGoodsPublisher();
        this.goodsStatus = entity.getGoodsStatus();
        this.goodsIsbn = entity.getGoodsIsbn();
//        this.goodsPoint = entity.getGoodsPoint();
        this.goodsPublishedDate = entity.getGoodsPublishedDate();
//        this.goodsTotalPage= entity.getGoodsTotalPage();
        this.goodsSalesPrice=entity.getGoodsSalesPrice();
//        this.goodsIntro=entity.getGoodsIntro();
//        this.goodsWriterIntro=entity.getGoodsWriterIntro();
//        this.goodsContentsOrder=entity.getGoodsContentsOrder();
//        this.goodsPublisherComment=entity.getGoodsPublisherComment();
//        this.goodsRecommendation=entity.getGoodsRecommendation();
//        this.fileName=entity.getFileName();
        this.imageList = entity.getImageList();
    }
}
