package com.CloneShopee.controllers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.UserBean;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.ShopFollow;
import com.CloneShopee.services.User.FollowService;

@RestController
public class FollowController {

    @Autowired
    FollowService followService;

    @Autowired
    UserBean userBean;

    @GetMapping("/user/follow/addfollow")
    public ResponseEntity<Object> followShop(@RequestBody Integer shopId) {
        if (followService.checkFollowedShop(shopId, userBean.getId()) == null) {
            try {
                followService.followShop(new ShopFollow(shopId, userBean.getId()));
                return new ResponseEntity<>(new BaseRespone(null, "Đã follow shop thành công"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new BaseRespone(null, "Bạn đã follow đối với shop này"),
                        HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new BaseRespone(null, "Bạn đã follow đối với shop này"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/follow/unfollow")
    public ResponseEntity<Object> unFollow(@RequestBody Integer shopId) {
        if (followService.unFollow(shopId, userBean.getId()) > 0) {
            return new ResponseEntity<>(new BaseRespone(null, "Đã hủy follow shop"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Bạn chưa từng follow shop này."), HttpStatus.BAD_REQUEST);
    }

}
