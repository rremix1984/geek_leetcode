/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;

import static com.leetcode.util.LogUtil.info;

/**
    [STRING]
    (简单)
    1189. “气球” 的最大数量
        给你一个字符串 text，你需要使用 text 中的字母来拼凑尽可能多的单词 "balloon"（气球）。
        字符串 text 中的每个字母最多只能被使用一次。请你返回最多可以拼凑出多少个单词 "balloon"。
    示例 1：
        输入：text = "nlaebolko"
        输出：1
    示例 2：
        输入：text = "loonbalxballpoon"
        输出：2
    示例 3：
        输入：text = "leetcode"
        输出：0
*/
public class NO1189_E_MaxNumberOfBalloons_x2 {

    @Test
    public void test() {
        assert 1 == maxNumberOfBalloons("nlaebolko");
        assert 2 == maxNumberOfBalloons("loonbalxballpoon");
        assert 0 == maxNumberOfBalloons("leetcode");
        assert 0 == maxNumberOfBalloons("lloo");
    }

    public int maxNumberOfBalloons(String text) {
        int[] cnt = new int[5];
        for (char c : text.toCharArray())
            if (c == 'b')
                cnt[0] += 2;
            else if (c == 'a')
                cnt[1] += 2;
            else if (c == 'n')
                cnt[4] += 2;
            else if (c == 'l')
                cnt[2]++;
            else if (c == 'o')
                cnt[3]++;
        return Arrays.stream(cnt).min().getAsInt() / 2;
    }

}




















/**
public int maxNumberOfBalloons(String text) {
    int[] cnt = new int[5];
    for (char c : text.toCharArray()) {
        switch (c) {
            case 'b':
                cnt[0]++;
                break;
            case 'a':
                cnt[1]++;
                break;
            case 'l':
                cnt[2]++;
                break;
            case 'o':
                cnt[3]++;
                break;
            case 'n':
                cnt[4]++;
        }
    }
    cnt[2] /= 2;
    cnt[3] /= 2;
    return Arrays.stream(cnt).min().getAsInt();
}
*/