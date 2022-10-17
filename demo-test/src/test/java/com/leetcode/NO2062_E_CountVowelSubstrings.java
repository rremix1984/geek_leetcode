/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
    (简单)
    2062. 统计字符串中的元音子字符串
        子字符串 是字符串中的一个连续（非空）的字符序列。
        元音子字符串 是 仅 由元音（'a'、'e'、'i'、'o' 和 'u'）组成的一个子字符串，且必须包含 全部五种 元音。
        给你一个字符串 word ，统计并返回 word 中 元音子字符串的数目 。
    示例 1：
        输入：word = "aeiouu"
        输出：2
        解释：下面列出 word 中的元音子字符串（斜体加粗部分）：
             - "aeiouu"
             - "aeiouu"
    示例 2：
        输入：word = "unicornarihan"
        输出：0
        解释：word 中不含 5 种元音，所以也不会存在元音子字符串。
    示例 3：
        输入：word = "cuaieuouac"
        输出：7
        解释：下面列出 word 中的元音子字符串（斜体加粗部分）：
             - "cuaieuouac"
             - "cuaieuouac"
             - "cuaieuouac"
             - "cuaieuouac"
             - "cuaieuouac"
             - "cuaieuouac"
             - "cuaieuouac"
    示例 4：
        输入：word = "bbaeixoubb"
        输出：0
        解释：所有包含全部五种元音的子字符串都含有辅音，所以不存在元音子字符串。
*/
public class NO2062_E_CountVowelSubstrings {

    @Test
    public void test() {
        assert 2 == countVowelSubstrings("aeiouu");
        assert 0 == countVowelSubstrings("unicornarihan");
        assert 7 == countVowelSubstrings("cuaieuouac");
    }


    public int countVowelSubstrings(String word) {
        int cnt = 0;
        if (word.length() < 5)
            return 0;

        for (int i = 0; i < word.length(); i++){
            Set<Character> set = new HashSet<>();
            for (int j = i; j < word.length(); j++){
                if(!vowel(word.charAt(j)))
                    break;

                set.add(word.charAt(j));
                if (set.size() == 5)
                    cnt++;
            }
        }
        return cnt;
    }

    public boolean vowel(char ch){
        return ch == 'a' || ch == 'e' || ch == 'i'
                || ch == 'o' || ch == 'u';
    }

}















/**
// 方法1：
public int countVowelSubstrings(String word) {
    int cnt = 0;
    if (word.length() < 5)
        return 0;

    for (int i = 0; i < word.length(); i++){
        Set<Character> set = new HashSet<>();
        for (int j = i; j < word.length(); j++){
            if(!vowel(word.charAt(j)))
                break;

            set.add(word.charAt(j));
            if (set.size() == 5)
                cnt++;
        }
    }
    return cnt;
}

public boolean vowel(char ch){
    return ch == 'a' || ch == 'e' || ch == 'i'
            || ch == 'o' || ch == 'u';
}


// 方法2：
public int countVowelSubstrings(String word) {
    int ans = 0;
    int vowel = 1 | 1 << 4 | 1 << 8 | 1 << 14 | 1 << 20;// 五个元音的状态
    for (int i = 0; i < word.length(); i++) {
        int mask = 0;
        for (int j = i; j < word.length(); j++) {
            char ch = word.charAt(j);
            if ((vowel & 1 << ch - 'a') == 0)
                break;// 碰到非元音

            mask |= 1 << ch - 'a';
            if (mask == vowel)
                ans++;
        }
    }
    return ans;
}
*/