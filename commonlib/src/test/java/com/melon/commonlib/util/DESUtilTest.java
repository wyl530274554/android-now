package com.melon.commonlib.util;

import org.junit.Assert;
import org.junit.Test;

public class DESUtilTest {
    @Test
    public void testEncrypt() {
        String text = "我是谁";
        String encrypt = DESUtil.encrypt(text);
        System.out.println("encrypt：" + encrypt);
        String decrypt = DESUtil.decrypt(encrypt);
        System.out.println("decrypt：" + decrypt);
        Assert.assertEquals(text, decrypt);
    }
}