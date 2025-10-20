package com.CloneShopee.controllers.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.services.sale.LiveService;

@RestController
public class LiveUserController {

    @Autowired
    LiveService liveService;

    

    


    @PostMapping("user/live/comment/add")
    public ResponseEntity<Object> checkAlive(@RequestParam("liveId") Integer liveId,
            @RequestParam("comment") String comment) {
        if (liveService.checkAlive(liveId) != null) {
            if (comment != null && comment.trim().length() > 0) {
                return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseRespone(null, "Comment phải ít nhất 1 ký tự."), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Phiên live hiện không tìm thấy."), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("user/live/comment/getall")
    public ResponseEntity<Object> getCommentByLiveId(Integer liveId) {
        if (liveService.checkAlive(liveId) != null) {
            return new ResponseEntity<>(new BaseRespone(liveService.getAllComments(liveId), "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Phiên live hiện không hoạt động"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("user/live/product/react")
    public ResponseEntity<Object> addReact(@RequestParam("liveId") Integer liveId,
            @RequestParam("liveId") Integer productId) {
        if (liveService.checkAlive(liveId) != null) {
            if (liveService.plusCounReact(liveId, productId)) {
                return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseRespone(null, "Yêu cầu phản ứng sản phẩm ko hợp lệ"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Phiên live hiện không tìm thấy."), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("user/live/product/addToCart")
    public ResponseEntity<Object> addCounAddToCart(@RequestParam("liveId") Integer liveId,
            @RequestParam("liveId") Integer productId) {
        if (liveService.checkAlive(liveId) != null) {
            if (liveService.plusCounCart(liveId, productId)) {
                return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseRespone(null, "Yêu cầu phản ứng sản phẩm ko hợp lệ"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Phiên live hiện không tìm thấy."), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("user/live/info")
    public ResponseEntity<Object> getInfoLive(@RequestParam(name = "id", defaultValue = "-1") Integer liveId) {
        return new ResponseEntity<>(new BaseRespone(liveService.getLiveById(liveId), "success"), HttpStatus.OK);
    }

}