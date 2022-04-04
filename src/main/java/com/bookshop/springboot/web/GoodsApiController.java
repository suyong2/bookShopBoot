package com.bookshop.springboot.web;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.service.AdminGoodsService;
import com.bookshop.springboot.service.GoodsService;
import com.bookshop.springboot.util.S3Uploader;
import com.bookshop.springboot.web.common.base.BaseController;
import com.bookshop.springboot.web.dto.GoodsResponseDto;
import com.bookshop.springboot.web.dto.GoodsSaveRequestDto;
import com.bookshop.springboot.web.dto.GoodsUpdateRequestDto;
import com.bookshop.springboot.web.dto.ImagesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class GoodsApiController extends BaseController {
    private final GoodsService goodsService;
    private final AdminGoodsService adminGoodsService;
    private final String div = System.getProperty("file.separator");
    private final S3Uploader s3Uploader;

    @DeleteMapping("/api/v1/goods/{id}")
    public Long delete(@PathVariable Long id) {
        adminGoodsService.delete(id);
        return id;
    }

    @PostMapping("/api/v1/goods")
    public Long save(MultipartHttpServletRequest request, GoodsSaveRequestDto requestDto) throws Exception{

        System.out.println("save시 : "+requestDto);
        request.setCharacterEncoding("utf-8");
        List<ImagesSaveRequestDto> imageFileList =upload(request);
        System.out.println("save시 : "+imageFileList);
        System.out.println("이미지count : "+imageFileList.size());
        if(imageFileList!= null && imageFileList.size()!=0) {
            requestDto.setImageList(imageFileList);
        }
        String imageFileName=null;
        Long goodsId = adminGoodsService.save(requestDto);

        Iterator<String> fileNames = request.getFileNames();// 파일이름들 목록을 가져온다..
        while(fileNames.hasNext()) {
            String fileName = fileNames.next();
            MultipartFile mFile = request.getFile(fileName);
            if(mFile.getSize()!=0) { //File Null Check
                s3Uploader.upload(mFile, "shopping/file_repo/" + goodsId);
            }
        }

//        try {
//            if(imageFileList!=null && imageFileList.size()!=0) {
//                for(ImagesSaveRequestDto imageFileVO:imageFileList) {
//                    imageFileName = imageFileVO.getFileName();
//                    File srcFile =
//                            new File(CURR_IMAGE_REPO_PATH+div+"temp"+div+imageFileName);
//                    File destDir =
//                            new File(CURR_IMAGE_REPO_PATH+div+goodsId);
//                    FileUtils.moveFileToDirectory(srcFile, destDir,true);
//                }
//            }
//        }catch(Exception e) {
//            if(imageFileList!=null && imageFileList.size()!=0) {
//                for(ImagesSaveRequestDto imageFileVO:imageFileList) {
//                    imageFileName = imageFileVO.getFileName();
//                    File srcFile =
//                            new File(CURR_IMAGE_REPO_PATH+div+"temp"+div+imageFileName);
//                    srcFile.delete();
//                }
//            }
//            e.printStackTrace();
//        }
        return goodsId;
    }

    @PostMapping("/api/v1/goods/{id}")
    public Long update(MultipartHttpServletRequest request, @PathVariable Long id,
                       GoodsUpdateRequestDto requestDto) throws Exception {
        System.out.println("update시 : "+requestDto);
        request.setCharacterEncoding("utf-8");

        List<ImagesSaveRequestDto> imageFileList =upload(request);
        System.out.println("update시 : "+imageFileList);
        System.out.println("이미지count : "+imageFileList.size());
        if(imageFileList!= null && imageFileList.size()!=0) {
            requestDto.setImageList(imageFileList);
        }
        String imageFileName=null;
        Iterator<String> fileNames = request.getFileNames();// 파일이름들 목록을 가져온다..
        while(fileNames.hasNext()) {
            String fileName = fileNames.next();
            MultipartFile mFile = request.getFile(fileName);
            if(mFile.getSize()!=0) { //File Null Check
                s3Uploader.upload(mFile, "shopping/file_repo/" + id);
            }
        }

//        try {
//            if(imageFileList!=null && imageFileList.size()!=0) {
//                for(ImagesSaveRequestDto imageFileVO : imageFileList) {
//                    imageFileName = imageFileVO.getFileName();
//                    if (imageFileName!= null && !imageFileName.equals("")){
//                        File srcFile =
//                                new File(CURR_IMAGE_REPO_PATH+div+"temp"+div+imageFileName);
//                        File destDir =
//                                new File(CURR_IMAGE_REPO_PATH+div+id);
//                        FileUtils.moveFileToDirectory(srcFile, destDir,true);
//                    }
//                }
//            }
//        }catch(Exception e) {
//            if(imageFileList!=null && imageFileList.size()!=0) {
//                for(ImagesSaveRequestDto imageFileVO:imageFileList) {
//                    imageFileName = imageFileVO.getFileName();
//                    File srcFile =
//                            new File(CURR_IMAGE_REPO_PATH+div+"temp"+div+imageFileName);
//                    srcFile.delete();
//                }
//            }
//            e.printStackTrace();
//        }
        return adminGoodsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/goods/{id}")
    public GoodsResponseDto findById(@PathVariable Long id) {
        return goodsService.findById(id);
    }
}
