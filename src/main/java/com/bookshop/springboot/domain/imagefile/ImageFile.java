package com.bookshop.springboot.domain.imagefile;

import com.bookshop.springboot.domain.BaseTimeEntity;
import com.bookshop.springboot.domain.goods.Goods;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ImageFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(length = 50)
    private String fileName;

    @Column(length = 40)
    private String fileType;

    @Column(length = 20)
    private String regId;

    @ManyToOne
    @JoinColumn(name="GOODS_ID", nullable = false)
    private Goods goods;

    @Builder
    public ImageFile(String fileName, String fileType, String regId){
        this.fileName = fileName;
        this.fileType = fileType;
        this.regId = regId;
    }

    public void update(String fileName, String fileType, String regId){
        this.fileName = fileName;
        this.fileType = fileType;
        this.regId = regId;
    }

    public void setGoods(Goods goods){
        this.goods = goods;
        goods.getImageList().add(this);
    }

//    @Builder
//    public ImageFile(String fileName, String fileType, String regId, Goods goods){
//        this.fileName = fileName;
//        this.fileType = fileType;
//        this.regId = regId;
//        this.goods = goods;
////        this.goods.getImageList().add(this);
//    }
}
