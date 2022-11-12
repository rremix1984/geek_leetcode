/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    139. 单词拆分
        给你一个字符串s和一个字符串列表wordDict作为字典。
        请你判断是否可以利用字典中出现的单词拼接出s。
        注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
    示例 1：
        输入: s = "leetcode", wordDict = ["leet", "code"]
        输出: true
        解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
    示例 2：
        输入: s = "applepenapple", wordDict = ["apple", "pen"]
        输出: true
        解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
        注意，你可以重复使用字典中的单词。
    示例 3：
        输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
        输出: false
    提示：
        1 <= s.length <= 300
        1 <= wordDict.length <= 1000
        1 <= wordDict[i].length <= 20
        s 和 wordDict[i] 仅有小写英文字母组成
        wordDict 中的所有字符串 互不相同
    完全背包问题:
        转化为是否可以用wordDict中的词组合成s，完全背包问题，
        并且为“考虑排列顺序的完全背包问题”，外层循环为target，内层循环为选择池wordDict。
        dp[i]表示以i结尾的字符串是否可以被wordDict中组合而成。
        外层遍历s中每一个与word同长度的字串s.substr(i-sz, sz)；
        内层遍历wordDict每个word。
        判断 s.substr(i - sz, sz) == word：
        （1）若不相等，说明与该 word 不匹配，继续遍历；
        （2）若相等，说明从[i - sz]到i的字符与word匹配。
            dp[i] = dp[i] || d[[i - sz]]
        对于边界条件，我们定义 dp[0] = true 表示空串且合法。
        最后返回 dp[s.size()]
*/
public class NO139_N_WordBreak_x2 {

    @Test
    public void test() {
        assert wordBreak("leetcode",
                getArray("leet", "code"));
        assert wordBreak("applepenapple",
                getArray("apple", "pen"));
        assert !wordBreak("catsandog",
                getArray("cats", "dog", "sand", "and", "cat"));
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++)
            for (int j = 0; j < i; j++)
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
        return dp[s.length()];
    }

}















/**
// 方法1：动态规划：
public boolean wordBreak(String s, List<String> wordDict) {
    Set<String> wordDictSet = new HashSet<>(wordDict);
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    for (int i = 1; i <= s.length(); i++)
        // j去划分成两部分
        for (int j = 0; j < i; j++)
            // 后缀部分是单词，且左侧子串[0,j-1]的dp[j]为真
            if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                dp[i] = true;
                // dp[i] = true了，i长度的子串已经可以拆成单词了，不需要j继续划分子串了
                break;
            }

    return dp[s.length()];
}
*/