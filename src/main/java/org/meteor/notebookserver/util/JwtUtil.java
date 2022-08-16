package org.meteor.notebookserver.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.meteor.notebookserver.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.*;
import java.security.Key;
import java.util.*;


/**
 * Token工具类
 */

public class JwtUtil {

    //JWT接收对象
    private static final String clientId = "098f6bcd4621d373cade4e832627b4f6";

    //私钥
    private static final String base64Secret = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";

    //Token过期时间（毫秒）
    private static final int expiresSecond =  24 * 3600 * 1000 * 30;//30天

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 解析jwt
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 构建jwt，将参数信息加密
     */
    public static String createJWT(String id,String username) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Token头部信息
        Map<String, Object> map = new HashMap<>();
        map.put("typ", "JWT");
        map.put("alg", "HS256");

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeader(map)

                .claim("id", id)//用户ID
                .claim("username", username)//用户名
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (expiresSecond >= 0) {
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            long expMillis = nowMillis + expiresSecond;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        //生成JWT
        return builder.compact();
    }

    /**
     * 获取头文件中的Token，根据Token取得用户信息,返回model
     *
     * @param Token
     * @return
     */
    public static UserInfo geTokenInfo(String Token) {
        Claims claims = JwtUtil.parseJWT(Token);
        UserInfo userInfo = null;
        try {
            if (claims != null) {
                userInfo = new UserInfo();
                userInfo.setId(Long.valueOf(String.valueOf(claims.get("id"))));
                userInfo.setUsername((String)claims.get("username"));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return userInfo;
    }

}