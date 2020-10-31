package com.wwj.crowd.test;

import com.wwj.crowd.util.CrowdUtil;
import org.junit.Test;

public class StringTest {

    @Test
    public void testMD5(){
        String source = "123456";
        String encoded = CrowdUtil.md5(source);
        System.out.println(encoded);
    }
}
