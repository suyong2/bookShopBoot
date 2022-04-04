package com.bookshop.springboot.web.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GoodsUpdateRequestDto {
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

    public void setImageList(List<ImagesSaveRequestDto> imageList){
        this.imageList = imageList;
    }

    @Builder
    public GoodsUpdateRequestDto(String goodsTitle, String goodsWriter, Integer goodsPrice,
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
}
