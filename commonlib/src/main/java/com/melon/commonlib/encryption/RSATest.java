package com.melon.commonlib.encryption;

import java.util.Map;

public class RSATest {
    private static String DEFAULT_PUBLIC_KEY;
    private static String DEFAULT_PRIVATE_KEY;

    public static void main(String[] args) throws Exception {
        Map<String, Object> keyPair = RSAUtils.genKeyPair();

        //获取私钥
        String privateKey = RSAUtils.getPrivateKey(keyPair);
        String publicKey = RSAUtils.getPublicKey(keyPair);

        System.out.print("privateKey: \n" + privateKey + "\n\npublicKey: \n" + publicKey + "\n");

        DEFAULT_PUBLIC_KEY = publicKey;
        DEFAULT_PRIVATE_KEY = privateKey;

        long start = System.currentTimeMillis();
        test();
        long end = System.currentTimeMillis();
        System.out.print("time: " + (end - start));
    }

    private static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看士大夫反反复复反反复复反反复复反反复复多发时段发生的发生的发生的发生的发生的发生的发生的发生的发生的发生的发生的发生的发生的发生的发生，不是吗？这是一行没有任何意义的文字，asdfsdafsdafsfasdfdfsa你看完了等于没看，不是吗？这是一行没有任何意义的文字，你看完了等于没看，不是吗？这是一行没有任何意义的文字，你看完了等于没看，不是吗？这是一行没有任何意义的文字，你看完了等于没看，不是吗？这是一行没有任何意义的文字，你看完了等于没看，不是吗？这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();

        //加密
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, DEFAULT_PUBLIC_KEY);
        System.out.println("加密后文字：\r\n"+encodedData.length+"\n" + new String(encodedData));

        //解密
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, DEFAULT_PRIVATE_KEY);
        System.out.println("解密后文字: \r\n" + new String(decodedData));
    }
}
	
	
	
