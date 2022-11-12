package com.leetcode;

import org.junit.Test;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
    (中等)
    264. 丑数 II
        给你一个整数 n ，请你找出并返回第 n 个 丑数 。
        丑数 就是只包含质因数 2、3 和/或 5 的正整数。
    示例 1：
        输入：n = 10
        输出：12
        解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
    示例 2：
        输入：n = 1
        输出：1
        解释：1 通常被视为丑数。
    提示：
        1 <= n <= 1690

    丑数生成法(dp+三指针):详细见收藏回答评论区
    观察丑数:x=2*2*...3*3*...5*5*...可见丑数生成必定是某个丑数通过*2/*3/*5生成
    因此可以设法每次生成三个有可能的待选丑数!
    定义dp[i]为第i个丑数的值,dp[1]=1(初始化所有的dp[i]=0)
    ** 三个待选丑数必定是紧贴在之前的丑数dp[i-1]后面的!
    因此定义三个指针ptr2,ptr3,ptr5分别指向还没参与生成丑数的丑数处(初始化为1)
        ptr2代表*2生成丑数的列表中,下一个即将要生成的位置
        ptr3代表*3生成丑数的列表中,下一个即将要生成的位置
        ptr5代表*5生成丑数的列表中,下一个即将要生成的位置
    分别计算2*dp[ptr2],3*dp[ptr3],5*dp[ptr5]得到的就是三个候选的丑数
    选择最小的添加到dp[i]即可
    最后返回dp[n]就是答案
*/
public class NO264_N_NthUglyNumber{

    @Test
    public void test() {
        assert 12 == nthUglyNumber(10);
        assert 1 == nthUglyNumber(1);
    }

    public int nthUglyNumber(int n) {
        // 定义dp数组:dp[i]表示为第i个丑数(默认dp[i]为0)
        int[] dp = new int[n + 1];
        // 初始化dp[1]=1
        dp[1] = 1;
        // 定义三个指针,初始化都指向1
        int ptr2 = 1, ptr3 = 1, ptr5 = 1;
        // 要推导的dp[i]索引为i∈[2,n]
        for(int i = 2; i <= n; i++) {
            // 分别计算出2*dp[ptr2],3*dp[ptr3],5*dp[ptr5]
            int num2 = 2 * dp[ptr2], num3 = 3 * dp[ptr3], num5 = 5 * dp[ptr5];
            // num2,num3,num5中选择最小的作为第i个丑数,即dp[i]
            dp[i] = Math.min(Math.min(num2, num3), num5);
            // 三个指针移动
            // 这里注意:只要ptr(i)负责生成的丑数与dp[i]相等,即取到了dp[ptr(i)]*i就要移动对应指针
            // 例如:2*5=5*2所以相同的丑数可能会被生成多次读取多次,因此要跳过
            // 本质上说就是我们不管哪个参与生成的作为dp[i],有可能num2=num5=10的情况出现
            // 这时我们直接取一次10加入dp[i],然后两个涉及的指针统统移动,那么下一次只会出现3*5/6*2>10
            // 细细琢磨一下!
            if(dp[i] == num2)
                ptr2++;

            if(dp[i] == num3)
                ptr3++;

            if(dp[i] == num5)
                ptr5++;
        }
        return dp[n];
    }

}




















/**
// 方法1：
public int nthUglyNumber(int n) {
    int[] factors = {2, 3, 5};
    Set<Long> seen = new HashSet<>();
    PriorityQueue<Long> heap = new PriorityQueue<>();
    seen.add(1L);
    heap.offer(1L);
    int ugly = 0;
    for (int i = 0; i < n; i++) {
        long curr = heap.poll();
        ugly = (int) curr;
        for (int factor : factors) {
            long next = curr * factor;
            if (seen.add(next)) {
                heap.offer(next);
            }
        }
    }
    return ugly;
}
*/