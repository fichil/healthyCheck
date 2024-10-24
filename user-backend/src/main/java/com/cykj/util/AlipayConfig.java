package com.cykj.util;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenhua on 2024/8/14.
 * 配置文件读取
 */
@Configuration
@Component
public class AlipayConfig {

    // 商户appid
    public static String APPID = "9021000140601166";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCKX1tbKht4tYR+oN85hE43PtCIiLBLnkhHwUYq875zZCT7qHxDvSgiIRiHsl/8CtN3aTHsr/aRqG1ABT9acb0CromsyPDPk+XCI9PL7IKqt4oe+ZG+AFevuv6jhMXQ4pvgae4DqDh0nFjANRJqlHpgPisPpeRddga+bFvunHGwHJRDX/m1Nr1CgASuczSNhAFLiez9eOrvNGs7psLX6bI25323Ckbmcy0dH9yThG7jah4ZPXwlfRnPb7592CWWiu/FO6E7h13O8u7kQowttGqvXgbOvmFDeRLoyA3vC6maqrSeKIsBnnMHgqvIt3uhJsaZ9CfEFqkBfaKKf7Bvw0VzAgMBAAECggEAYKiD1Ibxcby6tQoDZNY7HPRQD1AMdoY2z4j/Tvm9TDXkb7ceNuNnqY+c5w1/ZzzYw01EhqXUg6M3zcTcoH1bHwh1O7GSJi6WDKuiQ40PKdnULhb/682pcyQYxHj3BheF85mLnhpgdXTRulJYbwMRCTHkOqYThAXyaAqhnWjDMdV6isKtN2nnwYCP3RLbZRlU9h8UUY6t5/I3tJEfCYRQZRMOcgGg4EXCQd3AOSHxqVADSVGsYXWTQabDH0Zb17H3qkRv3XveL5hWp6N+ZoCp4VxhibIXYqUtDrIVw/t5UUx260kUYGw+oPDYq/C9wKhxci3kQroP/F7iX+sHGoCCYQKBgQDdLL+XdA0kymYMViovSqZfNhRTh40I83LZ3wZbXRguCCHLnRykx0tGqp2JwZLcP6LR2/rjDF78JmaqJZXL8Qt4fJkaXfojE+Y/YTj1obDtvid6cIR8uIHzcsNQEDZRpKJUnmt70plRxs3LK6fMhw5+zbbQ3PpZ/3Icjvx9M8x6NQKBgQCgKPYY9z3dkRWlPTowI/564ES2SgqTDhMfeLdJ6sXrSmwGlCVQJ8C0AokclZVEd+IogqmGfi/fNq4EAT1vJyG/jYy0zFowFil7mKH9vbKmBUlpGs/AUz5u6Y2hXLwsKRaqtX/oUoOri+kCARrQT3PGiq7jKwdbbk/MC5ZOyGn2BwKBgDFBXO2ar66o0G8g5UYg5so/Xel4P4VlygcRLvY71j2yG+yfcZiUtYPoTSs3i8A2/8iip83WnHSlDKoJssF3TemoPeRsV19meBlXSnsRZjib52jSE/8+i9oujaaP5bjq5CfaG++4Z+KZVo1PjI/059p/b542CB7wz3XitgIf/gKdAoGAXCu2lV+URvK8FkBKQHI2fdibMp0hwMeU0cPTF8L47BQEXzTa1GpMSEw9RCoBoUms6ctobHIqcrcxJjTgeht3HJoFo/cG4C2g6fRJJZIKQelA+i/n57MAdywJyhdoY7GxzcRuO/FgvVTjX7frCn7vDg+53hphPYkaez8f3W8RK/kCgYA3+10UxSXpA2OBQ04RLVlDBrrIIqIsv2Ne65FBkFDvZR/m6BAbAx4FS64xuaFr163sVm7k18XynSNtTmWj14amexTAvjbKEe9tfFbc/G4selroSjs6bO1fJALlcpL0szY/czWGvnC98iuWHNyGBudHdGKg7qx8bDyoEXLv3TN5Zg==";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "https://2454-104-234-20-75.ngrok-free.app/api/user/notify";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://localhost:8083/#/profile";
    // 请求网关地址
    public static String URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvaTwK+xPYC6yBiAo3/tzSG+IotWr5NjYhDHAQHId13xuP1SdiVM684j+EbAVRQhK1Xwn4gVZFcGimwHGVBlk3TezetAdrJivlserqOgO9hNUGBcXUh7ahbxgQYRyZBHCAMARBE+d1nn5c8hhAJpO/gnZYaV/BSGKP1M0wJX2iluV0oWwLGrVw17j2hjsXxvjFW19QPmK5Z7EnTHqDQVXfZhPB2D3USmtlBL/9Wqoht4fLRWjMamtAJdw3cympxsvUyO/L+D0jUwSybkhc5pl4pM8enbWJLot6JWw1n/cmFByDFM+s2tMg0S7/n91Yw6XTPZ3gw2JU/9yFQzo29+2FwIDAQAB";
    // 日志记录目录定义在 logFile 中
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
