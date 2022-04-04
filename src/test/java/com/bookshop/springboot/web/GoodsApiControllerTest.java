package com.bookshop.springboot.web;

import com.bookshop.springboot.domain.goods.Goods;
import com.bookshop.springboot.domain.goods.GoodsRepository;
import com.bookshop.springboot.domain.imagefile.ImageFile;
import com.bookshop.springboot.domain.imagefile.ImageFileRepository;
import com.bookshop.springboot.web.dto.GoodsSaveRequestDto;
import com.bookshop.springboot.web.dto.GoodsUpdateRequestDto;
import com.bookshop.springboot.web.dto.ImagesSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoodsApiControllerTest {

    @LocalServerPort
    private int port;

    // @SpringBootTest
    // 전반적인 웹 어플리케이션의 테스트를 하려면 @SpringBootTest
    // @SpringBootTest는 모든 빈들이 등록은 되지만 MockMvc를 만들어주지 않는다.
    // 그래서 @AutoConfigureMockMvc 가 필요
//    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ImageFileRepository imagesRepository;

    @Autowired
    private WebApplicationContext ctx;

    @Before
    public void prepare() {
        goodsRepository.deleteAll();
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        goodsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void Goods_삭제된다() throws Exception {
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
        String url = "http://localhost:" + port + "/api/v1/goods/" + id;
        //when
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        List<ImageFile> imageList = imagesRepository.findAll();
        List<Goods> goodsList = goodsRepository.findAll();
        //then

        assertThat(imageList.size()).isEqualTo(0);
        assertThat(goodsList.size()).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Goods_등록된다() throws Exception {
        //given
        String goodsTitle = "Hello";
        String goodsWriter = "world";
//        String[] fileNames = {"dummy.jpeg", "dummy2.jpeg"};

//        MockMultipartFile file = new MockMultipartFile("main_image", fileNames[0],
//                "image/jpeg", "Some dataset...".getBytes());
//        MockMultipartFile file2 = new MockMultipartFile("main_image", fileNames[0],
//                "image/jpeg", "Some dataset...".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/v1/goods")
//                        .file(file)
//                        .file(file2)
                ;
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("POST");
                return request;
            }
        });
        //when
        mvc.perform(builder
                .param("goodsTitle", goodsTitle)
                .param("goodsWriter", goodsWriter)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(content().string("데일의 블로그입니다. dale"))
                .andDo(print());
        List<Goods> all = goodsRepository.findAll();
//        List<ImageFile> imageList = imagesRepository.findAllOrdered();
        assertThat(all.get(0).getGoodsTitle()).isEqualTo(goodsTitle);
        assertThat(all.get(0).getGoodsWriter()).isEqualTo(goodsWriter);
//        for (int i=0;i<imageList.size();i++){
//            ImageFile image = imageList.get(i);
//            assertThat(image.getFileName()).isEqualTo(fileNames[i]);
//        }
    }

    @Test
    @WithMockUser(roles="USER")
    public void Goods_수정된다() throws Exception {
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

        Long updateId = savedGoods.getGoodsId();
        String expectedTitle = "Hello2";
        String expectedWriter = "world2";
        System.out.println("/api/v1/goods/"+updateId);

//        String[] fileNames = {"dummy.jpeg", "dummy2.jpeg"};
//
//        MockMultipartFile file = new MockMultipartFile("main_image", fileNames[0],
//                "image/jpeg", "Some dataset...".getBytes());
//        MockMultipartFile file2 = new MockMultipartFile("main_image", fileNames[0],
//                "image/jpeg", "Some dataset...".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/v1/goods/"+updateId)
//                        .file(file).file(file2)
                ;
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("POST");
                return request;
            }
        });

        //when
        mvc.perform(builder
                .param("goodsTitle", expectedTitle)
                .param("goodsWriter", expectedWriter)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(content().string("데일의 블로그입니다. dale"))
                .andDo(print());

//
        List<Goods> all = goodsRepository.findAll();
//        List<ImageFile> imageList = imagesRepository.findAll();
        assertThat(all.get(0).getGoodsTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getGoodsWriter()).isEqualTo(expectedWriter);

//        for (int i=0;i<imageList.size();i++){
//            ImageFile image = imageList.get(i);
//            assertThat(image.getFileName()).isEqualTo(fileNames[i]);
//        }
    }
}
