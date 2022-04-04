package com.bookshop.springboot.domain.goods;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g, ImageFile d " +
            "        where g.goodsId=d.goods.goodsId " +
            "        and d.fileType='main_image' " +
            "        and g.goodsStatus=:goodsStatus " +
            "            order by g.goodsId desc ")
    List<Goods> selectGoodsList(@Param("goodsStatus") String goodsStatus, Pageable paging);

    @Query("select g from Goods g, ImageFile d " +
            "        where g.goodsId=d.goods.goodsId " +
            "        and d.fileType='main_image' " +
            "        and g.goodsId=:goodsId ")
    Goods selectGoodsDetail(@Param("goodsId") Long id);

    @Query("select g from Goods g " +
            "where g.createdDate between :beginDate and :endDate " +
            "order by g.goodsId desc")
    List<Goods> selectNewGoodsList(@Param("beginDate") LocalDate beginDate,
                                   @Param("endDate") LocalDate endDate);

}
