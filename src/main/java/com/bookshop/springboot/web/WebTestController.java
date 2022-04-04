package com.bookshop.springboot.web;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.bookshop.springboot.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@RequiredArgsConstructor
@Controller
public class WebTestController {

    private final S3Uploader s3Uploader;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.credentials.access_key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret_key}")
    private String secretKey;

    @GetMapping("/test/file")
    public String index() {
        return "test-file";
    }

    @PostMapping("/test/upload")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "shopping/file_repo");
    }

    @GetMapping("/down")
    public void download(@RequestParam("imageFileName") String imageFileName,
                         HttpServletResponse response)throws Exception {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey,
                secretKey);

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
        try {
            String key_name = "shopping/file_repo/"+imageFileName;
            OutputStream out = response.getOutputStream();
            File file = new File(key_name);
            response.setHeader("Cache-Control", "no-cache");
            response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName);
//            S3Object o = s3Client.getObject(bucket, key_name);
            S3Object o = s3.getObject(bucket, key_name);
            S3ObjectInputStream s3is = o.getObjectContent();
            int read_len = 0;
            byte[] read_buf = new byte[1024];
            while ((read_len = s3is.read(read_buf)) > 0) {
                out.write(read_buf, 0, read_len);
            }
            s3is.close();
            out.close();

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}