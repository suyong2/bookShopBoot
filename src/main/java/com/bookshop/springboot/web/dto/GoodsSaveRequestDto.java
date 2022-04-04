package com.bookshop.springboot.web.dto;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GoodsSaveRequestDto {
    private String goodsTitle;
    private String goodsWriter;
    private Integer goodsPrice;
    private String goodsPublisher;
    private String goodsStatus;
    private String goodsIsbn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate goodsPublishedDate;
    private Integer goodsSalesPrice;
    private List<ImagesSaveRequestDto> imageList;

//    public void setImageList(List<ImagesSaveRequestDto> imageList){
//        this.imageList = imageList;
//    }

    @Builder
    public GoodsSaveRequestDto(String goodsTitle, String goodsWriter, Integer goodsPrice,
                               String goodsPublisher, String goodsStatus, String goodsIsbn,
                               LocalDate goodsPublishedDate, Integer goodsSalesPrice) {
        this.goodsTitle = goodsTitle;
        this.goodsWriter = goodsWriter;
        this.goodsPrice = goodsPrice;
        this.goodsPublisher = goodsPublisher;
        this.goodsStatus = goodsStatus;
        this.goodsIsbn = goodsIsbn;
        this.goodsPublishedDate = goodsPublishedDate;
        this.goodsSalesPrice = goodsSalesPrice;
    }

    public Goods toEntity() {
        return Goods.builder()
                .goodsTitle(goodsTitle)
                .goodsWriter(goodsWriter)
                .goodsPrice(goodsPrice)
                .goodsPublisher(goodsPublisher)
                .goodsStatus(goodsStatus)
                .goodsIsbn(goodsIsbn)
                .goodsPublishedDate(goodsPublishedDate)
                .goodsSalesPrice(goodsSalesPrice)
                .build();
    }
}
