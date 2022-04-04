package com.bookshop.springboot.service;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.goods.GoodsRepository;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import com.bookshop.springboot.domain.imagefile.ImageFileRepository;
import com.bookshop.springboot.web.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminGoodsService {
    private final GoodsRepository goodsRepository;
    private final ImageFileRepository imagesRepository;

    @Transactional(readOnly = true)
    public List<GoodsListResponseDto> listNewGoods(Map condMap){
        String beginDate=(String)condMap.get("beginDate");
        String endDate=(String)condMap.get("endDate");
        LocalDate begin_date = LocalDate.parse(beginDate, DateTimeFormatter.ISO_DATE);
        LocalDate end_date = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        return goodsRepository.selectNewGoodsList(begin_date, end_date).stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Goods entity = goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));

        goodsRepository.delete(entity);
    }

    public Map findById(Long id) {
//        Goods entity = goodsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));
        Map goodsMap = new HashMap();
        Goods entity = goodsRepository.selectGoodsDetail(id);
        if (entity == null) {
            throw new IllegalArgumentException("해당 상품이 없습니다. id=" + id);
        }
        List<ImageFile> imageFileList = imagesRepository.getImages(id);
        List <ImagesListResponseDto> imgDtoList = new ArrayList<>();
        for (ImageFile image:imageFileList){
            imgDtoList.add(new ImagesListResponseDto(image));
        }
        goodsMap.put("goods",  new GoodsResponseDto(entity));
        goodsMap.put("imageFileList", imgDtoList);
        return goodsMap;
    }

    @Transactional
    public Long save(GoodsSaveRequestDto requestDto) {
        Goods goods= requestDto.toEntity();
        ArrayList<ImagesSaveRequestDto> imageFileList =
                (ArrayList<ImagesSaveRequestDto>) requestDto.getImageList();
        if (imageFileList!= null) {
            for (ImagesSaveRequestDto imageDto : imageFileList) {
                ImageFile image = imageDto.toEntity();
                image.setGoods(goods);
            }
        }
        return goodsRepository.save(goods).getGoodsId();
    }

    @Transactional
    public Long update(Long id, GoodsUpdateRequestDto requestDto) {
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));
        List<ImagesSaveRequestDto> newImageList = requestDto.getImageList();
        if (newImageList!= null) {
            List<ImageFile> orgImgList = imagesRepository.getImages(id);
            System.out.println("이미지갯수:"+newImageList.size());
            for (int i=0; i<newImageList.size(); i++){
                ImagesSaveRequestDto dto = newImageList.get(i);
                System.out.println("이미지이름:"+dto.getFileName());
                if (dto.getFileName()==null || dto.getFileName().equals("")){
                    ImageFile image= orgImgList.get(i);
                    dto.setFileName(image.getFileName());
                }
//                if (i<orgImgList.size()){
//                    ImageFile image= orgImgList.get(i);
//                    if (dto.getFileType().equals(image.getFileType())){
//                        if (dto.getFileName()==null || dto.getFileName().equals("")){
//                            dto.setFileName(image.getFileName());
//                        }
//                    }
//                }
            }
            for (ImageFile image: orgImgList){
                imagesRepository.delete(image);
                goods.getImageList().remove(image);
            }
            System.out.println("삭제후 : "+goods);

            for(ImagesSaveRequestDto imageDto : newImageList) {
                ImageFile image = imageDto.toEntity();
                image.setGoods(goods);
            }
        }

        goods.update(requestDto.getGoodsTitle(), requestDto.getGoodsWriter(),
                requestDto.getGoodsPrice(), requestDto.getGoodsPublisher(),
                requestDto.getGoodsStatus(), requestDto.getGoodsIsbn(),
                requestDto.getGoodsPublishedDate(), requestDto.getGoodsSalesPrice());

        return id;
    }
}
