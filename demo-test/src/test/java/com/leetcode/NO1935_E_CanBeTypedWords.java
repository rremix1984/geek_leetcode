/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
    (简单)
    1935. 可以输入的最大单词数
        键盘出现了一些故障，有些字母键无法正常工作。而键盘上所有其他键都能够正常工作。
        给你一个由若干单词组成的字符串 text ，单词间由单个空格组成（不含前导和尾随空格）；
        另有一个字符串 brokenLetters ，由所有已损坏的不同字母键组成，返回你可以使用此键盘完全输入的 text 中单词的数目。
    示例 1：
        输入：text = "hello world", brokenLetters = "ad"
        输出：1
        解释：无法输入 "world" ，因为字母键 'd' 已损坏。
    示例 2：
        输入：text = "leet code", brokenLetters = "lt"
        输出：1
        解释：无法输入 "leet" ，因为字母键 'l' 和 't' 已损坏。
    示例 3：
        输入：text = "leet code", brokenLetters = "e"
        输出：0
        解释：无法输入任何单词，因为字母键 'e' 已损坏。
*/
public class NO1935_E_CanBeTypedWords {

    @Test
    public void test() {
        assert 1 == canBeTypedWords("hello world", "ad");
        assert 1 == canBeTypedWords("leet code", "lt");
        assert 0 == canBeTypedWords("leet code", "e");
    }

    public int canBeTypedWords(String text, String brokenLetters) {
        int ans = 0;
        String[] words = text.split(" ");
        String[] brokenLetter = brokenLetters.split("");
        for (String word : words) {
            boolean canBeTyped = true;
            for (String letter : brokenLetter) {
                // 如果brokenLetters是空的，我们只需要跳过即可，因为所有的word都是可以被打出来的
                if ("".equals(letter))
                    continue;

                // 该方法能够知道word中是否有损坏的letter
                if (word.contains(letter)) {
                    canBeTyped = false;
                    break;
                }
            }
            if (canBeTyped)
                ans++;
        }
        return ans;
    }

}
