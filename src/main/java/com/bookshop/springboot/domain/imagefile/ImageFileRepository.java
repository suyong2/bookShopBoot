package com.bookshop.springboot.domain.imagefile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

    @Query("select i from ImageFile i " +
            "        where i.fileType!='main_image' " +
            "        and i.goods.goodsId=:goodsId ")
    List<ImageFile> selectGoodsDetailImage(@Param("goodsId") Long id);

    @Query("select i from ImageFile i where i.goods.goodsId=:goodsId order by i.imageId asc")
    List<ImageFile> getImages(@Param("goodsId") Long id);

    @Query("select i from ImageFile i order by i.imageId asc")
    List<ImageFile> findAllOrdered();
}
