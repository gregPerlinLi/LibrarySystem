package com.gregperlinli.test;

import com.gregperlinli.utils.MD5Encrypt;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class MD5Test {

    @Test
    public void encryptPassword() {
        String vault;
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要加密的文本：");
        String input = sc.nextLine();
        vault = MD5Encrypt.stringMD5(input);
        System.out.println("MD5加密输出：" + vault);
    }
}
