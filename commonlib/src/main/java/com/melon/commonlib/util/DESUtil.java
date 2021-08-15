package com.melon.commonlib.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.util.Base64;

public class DESUtil {

    /**
     * 偏移变量，固定占8位字节
     */
    private final static String IV_PARAMETER = "12345678";
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";
    /**
     * 加密器
     */
    private static Cipher sEncryptCipher;
    /**
     * 解密器
     */
    private static Cipher sDecryptCipher;

    /**
     * 1、声明本地方法
     */
    public static native String getDESKey();

    static {
        //2、加载Native库
        System.loadLibrary("native-lib");

        //DES初始化
        try {
            //3、调用本地方法
            String desKey = getDESKey();
            DESKeySpec dks = new DESKeySpec(desKey.getBytes(CHARSET));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey sSecretKey = keyFactory.generateSecret(dks);

            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));

            sEncryptCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            sEncryptCipher.init(Cipher.ENCRYPT_MODE, sSecretKey, iv);

            sDecryptCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            sDecryptCipher.init(Cipher.DECRYPT_MODE, sSecretKey, iv);
        } catch (Exception e) {
            LogUtil.e("初始化key失败, " + e.getMessage());
        }
    }


    /**
     * DES加密字符串
     *
     * @param data 待加密字符串
     * @return 加密后内容
     */
    public static String encrypt(String data) {
        try {
            byte[] bytes = sEncryptCipher.doFinal(data.getBytes(CHARSET));
            //JDK1.8及以上可直接使用Base64（Android需要API26），JDK1.7及以下可以使用BASE64Encoder
            //Android平台也可以使用android.util.Base64
            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            LogUtil.e("encrypt error, " + e.getMessage());
            return data;
        }
    }

    /**
     * DES解密字符串
     *
     * @param data 待解密字符串
     * @return 解密后内容
     */
    public static String decrypt(String data) {
        try {
            return new String(sDecryptCipher.doFinal(Base64.getDecoder().decode(data.getBytes(CHARSET))), CHARSET);
        } catch (Exception e) {
            LogUtil.e("decrypt error, " + e.getMessage());
            return data;
        }
    }
}