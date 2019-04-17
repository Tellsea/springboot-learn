package cn.tellsea.jwt;

import cn.tellsea.bean.UserInfo;
import cn.tellsea.utils.JwtUtils;
import cn.tellsea.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "D:/Temp/rsa.pub";

    private static final String priKeyPath = "D:/Temp/rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    // 测试生成公钥和私钥
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "123456");
    }

    @Before // 注释了生成公钥和私钥，然后放开验证
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    // 测试生成token
    @Test
    public void testGenerateToken() throws Exception {
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    // 测试解析token
    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU1NTUwOTQ1Mn0.PKYbPcKfSSEEVmVALpcMFdXQ4eed9fngZ5mxLpnPD6JLBRoL8L8AtlYBLJCClaKzM1JLpzefFnCeQtYUPwTAsgoa_u6fuBoKbbkjW76Hu_ocmIQiwY_Qyure6YWro_aZbRIv5rrUo6Hd2t1T95cQbCqIQdu5uMjQ83FYWoTFXQg";
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}

