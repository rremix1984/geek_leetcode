/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/**
    [STRING]
    (简单)
    1805. 字符串中不同整数的数目
        给你一个字符串 word ，该字符串由数字和小写英文字母组成。
        请你用空格替换每个不是数字的字符。例如，"a123bc34d8ef34" 将会变成 " 123  34 8  34" 。注意，剩下的这些整数为（相邻彼此至少有一个空格隔开）："123"、"34"、"8" 和 "34" 。
        返回对 word 完成替换后形成的 不同 整数的数目。
        只有当两个整数的 不含前导零 的十进制表示不同， 才认为这两个整数也不同。
    示例 1：
        输入：word = "a123bc34d8ef34"
        输出：3
        解释：不同的整数有 "123"、"34" 和 "8" 。注意，"34" 只计数一次。
    示例 2：
        输入：word = "leet1234code234"
        输出：2
    示例 3：
        输入：word = "a1b01c001"
        输出：1
        解释："1"、"01" 和 "001" 视为同一个整数的十进制表示，因为在比较十进制值时会忽略前导零的存在。
*/
public class NO1805_E_NumDifferentIntegers_x2 {

    @Test
    public void test() {
        assert 3 == numDifferentIntegers("a123bc34d8ef34");
        assert 2 == numDifferentIntegers("leet1234code234");
        assert 1 == numDifferentIntegers("a1b01c001");
    }

    public int numDifferentIntegers(String word) {
        Set<String> set = new HashSet<>();
        return set.size();
    }

}















/**
public int numDifferentIntegers(String word) {
    String[] words = word.split("[a-z]+");
    Set<String> set = new HashSet<>();
    for (String s : words)
        if (s.length() != 0) {
            int i = 0; //对前导零的处理，因为考虑‘00000’的情况，所以到最后一位的前一位即可
            while (s.charAt(i) == '0' && i < s.length() - 1)
                i++;
            set.add(s.substring(i));
        }
    return set.size();
}
*/