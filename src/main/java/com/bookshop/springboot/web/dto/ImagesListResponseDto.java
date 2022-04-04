package com.bookshop.springboot.web.dto;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
public class ImagesListResponseDto {
    private Long imageId;
    private String fileName;
    private String fileType;
    private String regId;
    private Goods goods;

    public ImagesListResponseDto(ImageFile entity) {
        this.imageId = entity.getImageId();
        this.fileName = entity.getFileName();
        this.fileType = entity.getFileType();
        this.regId = entity.getRegId();
        this.goods = entity.getGoods();
    }
}
