package com.CloneShopee.controllers.test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.ProductImage;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.jsonwebtoken.io.IOException;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Console;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
public class TestController {

    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/upload-multiple-files")
    public BaseRespone uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws java.io.IOException {
        List<ProductImage> productImages = new ArrayList<ProductImage>();
        for (MultipartFile file : files) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                productImages.add(new ProductImage(uploadResult.get("url").toString()));
                // String url = uploadResult.get("url").toString();
            } catch (IOException e) {
                return new BaseRespone(null, "failes");
            }
        }

        return new BaseRespone(productImages, "success");
    }

    @PostMapping("/upload-single-files")
    public BaseRespone uploadSingleFile(@RequestParam("files") MultipartFile file) throws java.io.IOException {
        String url;
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            // Lấy URL từ kết quả trả về
            url = uploadResult.get("url").toString();
            new BaseRespone(url, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseRespone(null, "failes");
        }
        return new BaseRespone(url, "success");
    }

    @PostMapping("/upload-single-files-video")
    public BaseRespone uploadSingleFileVideo(@RequestParam("files") MultipartFile file) throws java.io.IOException {
        String url;
        try {
            // Kiểm tra nếu tệp rỗng
            if (file.isEmpty()) {
                return new BaseRespone(null, "File is empty");
            }

            // Upload file lên Cloudinary với resource_type là video
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "video")); // Chỉ định resource_type là video

            // Lấy URL từ kết quả trả về
            url = uploadResult.get("url").toString();
            return new BaseRespone(url, "success"); // Trả về URL
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseRespone("failed", "error");
        }
    }

}