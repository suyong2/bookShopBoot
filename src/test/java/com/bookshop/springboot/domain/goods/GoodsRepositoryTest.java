package com.bookshop.springboot.domain.goods;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.goods.GoodsRepository;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import com.bookshop.springboot.domain.imagefile.ImageFileRepository;
import com.bookshop.springboot.web.dto.GoodsSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private  ImageFileRepository imagesRepository;

    @Before
    public void prepare() {
        goodsRepository.deleteAll();
    }

    @After
    public void cleanup() {
        goodsRepository.deleteAll();
    }

    @Test
    public void 상품등록_불러오기() {
        //given
        String goodsTitle = "Hello";
        String goodsWriter = "world";
        String fileName = "testFile";

        Goods goods = Goods.builder()
                .goodsTitle(goodsTitle)
                .goodsWriter(goodsWriter)
                .build();

        for (int i=0; i<3; i++){
            ImageFile img = ImageFile.builder()
                    .fileName(fileName+i)
                    .build();
            img.setGoods(goods);
        }
        goodsRepository.save(goods);

        //when
        List<Goods> goodsList = goodsRepository.findAll();
        List<ImageFile> imageList = imagesRepository.findAll();

        //then
        goods = goodsList.get(0);
        assertThat(goods.getGoodsTitle()).isEqualTo(goodsTitle);
        assertThat(goods.getGoodsWriter()).isEqualTo(goodsWriter);

        for (int i=0;i<imageList.size();i++){
            ImageFile image = imageList.get(i);
            assertThat(image.getFileName()).isEqualTo(fileName+i);
        }
    }

    @Test
    @Transactional
    public void 상품등록_수정하기() {
        //given
        Goods goods = Goods.builder()
                .goodsTitle("Hello")
                .goodsWriter("world")
                .build();
        for (int i=0; i<3; i++){
            ImageFile img = ImageFile.builder()
                    .fileName("testFile"+i)
                    .build();
            img.setGoods(goods);
        }
        Goods savedGoods= goodsRepository.save(goods);

        Long id = savedGoods.getGoodsId();

        String expectedTitle = "Hello2";
        String expectedWriter = "world2";
        String expectedFileName = "testFile2";

        goods = goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));
        List<ImageFile> imgList = goods.getImageList();
        for (int i=0; i<imgList.size(); i++){
            ImageFile img = imgList.get(i);
            img.update(expectedFileName+i, null, null);
        }
        goods.update(expectedTitle, expectedWriter, null, null, null,
                null, null, null
//                , null, null
//                , null, null, null, null
//                , null, null
        );

        List<Goods> all = goodsRepository.findAll();
        assertThat(all.get(0).getGoodsTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getGoodsWriter()).isEqualTo(expectedWriter);
        imgList = all.get(0).getImageList();
        for (int i=0; i<imgList.size(); i++){
            assertThat(imgList.get(i).getFileName()).isEqualTo(expectedFileName+i);
        }
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDate now = LocalDate.of(2019, 6, 4);
        Goods goods = Goods.builder()
                .goodsTitle("Hello")
                .goodsWriter("world")
                .build();
        for (int i=0; i<3; i++){
            ImageFile img = ImageFile.builder()
                    .fileName("testFile"+i)
                    .build();
            img.setGoods(goods);
        }
        goodsRepository.save(goods);

        //when
        List<Goods> goodsList = goodsRepository.findAll();

        //then
        goods = goodsList.get(0);

        System.out.println("Goods>>>>>>>>> createDate=" + goods.getCreatedDate() + ", modifiedDate=" + goods.getModifiedDate());

        assertThat(goods.getCreatedDate()).isAfter(now);
        assertThat(goods.getModifiedDate()).isAfter(now);

        List<ImageFile> imgList = goods.getImageList();
        for (int i=0; i<imgList.size(); i++){
            ImageFile img = imgList.get(i);
            System.out.println("ImageFile>>>>>>>>> createDate=" + img.getCreatedDate() + ", modifiedDate=" + img.getModifiedDate());
            assertThat(img.getCreatedDate()).isAfter(now);
            assertThat(img.getModifiedDate()).isAfter(now);
        }

    }
}
