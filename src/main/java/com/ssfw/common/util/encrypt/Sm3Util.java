package com.ssfw.common.util.encrypt;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;

import java.util.Base64;

/**
 * 国密SM3工具
 * @date  4/26/2019 15:29 <br>
 * @author <a href="a@hotmail.com">a</a>
 */
public class Sm3Util {

    public static byte[] digest(String text){
        byte[] message = text.getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(message,0,message.length);
        byte[] digest = new byte[sm3.getDigestSize()];
        sm3.doFinal(digest, 0);
        return Hex.encode(digest);
    }

    public static String digestToString(String text){

        return new String(digest(text));
    }

    public static String digestToBase64(String text){

        return Base64.getEncoder().encodeToString(digest(text));
    }
}
