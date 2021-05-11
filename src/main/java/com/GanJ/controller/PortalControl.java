package com.GanJ.controller;

import com.GanJ.constant.CodeType;
import com.GanJ.service.FriendLinkService;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GanJ
 * @Date: 2021/2/19 17:04
 * Describe: 链接页面
 */
@RestController
@Slf4j
public class PortalControl {

    @Autowired
    FriendLinkService friendLinkService;

    /**
     * 获得所有链接信息
     */
    @PostMapping(value = "/getFriendLinkInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getFriendLink(){
        try {
            DataMap data = friendLinkService.getFriendLink();
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get friend links exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
