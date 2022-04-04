package com.bookshop.springboot.web.dto;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.RandomAccess;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImagesSaveRequestDto {

    private String fileName;
    private String fileType;
    private String regId;

    @Builder
    public ImagesSaveRequestDto(String fileName, String fileType, String regId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.regId = regId;
    }

    public ImageFile toEntity() {
        return ImageFile.builder()
                .fileName(fileName)
                .fileType(fileType)
                .regId(regId)
                .build();
    }
}
