package com.cykj.controller;

import com.cykj.util.CaptchaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 验证码Controller
 */
@Controller
public class CaptchaController {

    @RequestMapping("/captcha")
    @ResponseBody
    // 获取验证码
    public void captcha(HttpServletResponse response, HttpSession session) {
        CaptchaUtils captchaUtils = new CaptchaUtils();
        session.setAttribute("captcha", captchaUtils.getCode());
        try {
            captchaUtils.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
