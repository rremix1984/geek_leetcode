/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
    [STRING]
    (简单)
    1108. IP 地址无效化
        给你一个有效的 IPv4 地址 address，返回这个 IP 地址的无效化版本。
        所谓无效化 IP 地址，其实就是用 "[.]" 代替了每个 "."。
    示例 1：
        输入：address = "1.1.1.1"
        输出："1[.]1[.]1[.]1"
    示例 2：
        输入：address = "255.100.50.0"
        输出："255[.]100[.]50[.]0"
*/
public class NO1108_E_DefangIpAddr_x2 {

    @Test
    public void test() {
        assertEquals("1[.]1[.]1[.]1", defangIPaddr("1.1.1.1"));
        assertEquals("255[.]100[.]50[.]0", defangIPaddr("255.100.50.0"));
    }

    public String defangIPaddr(String address) {
        return null;
    }

}


















/**
public String defangIPaddr(String address) {
    StringBuilder ans = new StringBuilder();
    for (char c : address.toCharArray()) {
        if (c == '.')
            ans.append("[.]");
        else
            ans.append(c);
    }
    return ans.toString();
}
*/