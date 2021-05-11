package com.GanJ.controller;

import com.aliyun.oss.ClientException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.GanJ.component.PhoneRandomBuilder;
import com.GanJ.redis.StringRedisServiceImpl;
import com.GanJ.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author: GanJ
 * @Date: 2021/2/4 15:03
 * Describe: 注册获得手机验证码
 */
@RestController
@Slf4j
public class GetPhoneCodeControl {

    @Autowired
    StringRedisServiceImpl stringRedisService;

    private static final String REGISTER = "register";

    /**
     * 腾讯云 appid
     */
    @Value("${tencent.cloud.appid}")
    private int appid;

    /**
     * 腾讯云 appkey
     */
    @Value("${tencent.cloud.appkey}")
    private String appkey;

    /**
     * 腾讯云短信发送模板
     */
    private static final String smsSign = "枫林纪阿林";

    @PostMapping(value = "/getCode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAuthCode(@RequestParam("phone") String phone,
                              @RequestParam("sign") String sign) {

        String trueMsgCode = PhoneRandomBuilder.randomBuilder();

        //在redis中保存手机号验证码并设置过期时间
        stringRedisService.set(phone, trueMsgCode);
        stringRedisService.expire(phone, 300);

        int templateId;
        //注册的短信模板
        if (REGISTER.equals(sign)) {
            templateId = 896828;
        }
        //改密码的短信模板
        else {
            templateId = 896832;
        }
        try {
            String[] params = {trueMsgCode};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phone,
                    templateId, params, smsSign, "", "");
            System.out.println(result);
        } catch (ClientException | HTTPException | IOException e) {
            log.error("[{}] send phone message exception", phone, e);
            return JsonResult.fail().toJSON();
        }

        return JsonResult.success().toJSON();
    }

}
