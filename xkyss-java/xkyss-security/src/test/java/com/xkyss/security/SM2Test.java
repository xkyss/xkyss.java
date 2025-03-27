package com.xkyss.security;

import cn.hutool.core.codec.BCD;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.params.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.*;


public class SM2Test {

    @Test
    public void test() throws Exception {
        String text = "我是一段测试aaaa";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        ECPublicKeyParameters ecPublicKeyParameters = ECKeyUtil.decodePublicKeyParams(publicKey);

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);

        // 公钥加密，私钥解密
        {
            String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
            String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));

            Assertions.assertEquals(text, decryptStr);
        }

        // // 公钥加密，公钥解密 (擦, 不支持)
        // {
        //     String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        //     byte[] dataBytes = BCD.ascToBcd(StrUtil.bytes(encryptStr, CharsetUtil.CHARSET_UTF_8));
        //     String decryptStr = StrUtil.utf8Str(sm2.decrypt(dataBytes, ecPublicKeyParameters));
        //
        //     Assertions.assertEquals(text, decryptStr);
        // }
    }
}
