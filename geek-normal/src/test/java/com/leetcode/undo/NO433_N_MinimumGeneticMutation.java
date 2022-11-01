/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
   （中等）
    433. 最小基因变化
        基因序列可以表示为一条由 8 个字符组成的字符串，其中每个字符都是
        'A'、'C'、'G' 和 'T' 之一。假设我们需要调查从基因序列 start
        变为 end 所发生的基因变化。一次基因变化就意味着这个基因序列中的
        一个字符发生了变化。例如，"AACCGGTT" --> "AACCGGTA" 就是一次
        基因变化。另有一个基因库 bank 记录了所有有效的基因变化，只有基因
        库中的基因才是有效的基因序列。（变化后的基因必须位于基因库 bank 中）
        给你两个基因序列 start 和 end ，以及一个基因库 bank ，请你找出
        并返回能够使 start 变化为 end 所需的最少变化次数。如果无法完成此
        基因变化，返回 -1 。注意：起始基因序列 start 默认是有效的，但是它
        并不一定会出现在基因库中。
    示例 1：
        输入：start = "AACCGGTT", end = "AACCGGTA",
            bank = ["AACCGGTA"]
        输出：1
    示例 2：
        输入：start = "AACCGGTT", end = "AAACGGTA",
            bank = ["AACCGGTA", "AACCGCTA", "AAACGGTA"]
        输出：2
    示例 3：
        输入：start = "AAAAACCC", end = "AACCCCCC",
            bank = ["AAAACCCC", "AAACCCCC", "AACCCCCC"]
        输出：3
*/
@SuppressWarnings("all")
public class NO433_N_MinimumGeneticMutation {

    @Test
    public void test() {
        // 需1步
//        assert 1 == minMutation("AACCGGTT", "AACCGGTA",
//            new String[]{"AACCGGTA"});// 1
//        // 需2步
//        assert 2 == minMutation("AACCGGTT", "AAACGGTA",
//            new String[]{"AACCGGTA", "AACCGCTA", "AAACGGTA"}); // 2
        // 需3步
        assert 3 == minMutation("AAAAACCC", "AACCCCCC",
            new String[]{"AAAACCCC", "AAACCCCC", "AACCCCCC"});// 3
    }

    int ans = Integer.MAX_VALUE;
    public int minMutation(String start, String end, String[] bank) {
        return ans;
    }

}

















/*
// 方法1：回溯法
int ans = Integer.MAX_VALUE;
public int minMutation(String start, String end, String[] bank) {
    call(start, end, bank, new boolean[bank.length], 0);
    if (ans == Integer.MAX_VALUE)
        return -1;
    return ans;
}

public void call(String start, String end, String[] bank, boolean[] used, int t) {
    if (t >= ans)
        return;
    if (start.equals(end)) {
        ans = t;
    } else {
        int diff = 0;
        for (int i = 0; i < bank.length; i++) {
            if (used[i])
                continue;
            for (int j = 0; j < start.length(); j++) {
                if (start.charAt(j) != bank[i].charAt(j))
                    diff++;
            }
            if (diff == 1) {
                used[i] = true;
                call(bank[i], end, bank, used, t + 1);
                used[i] = false;
            }
            diff = 0;
        }
    }
}
*/