package com.leetcode.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] ||
    (简单)
    NO.3069 将元素分配到两个数组中 I
    给你一个下标从 1 开始、包含[不同整数]的数组nums，数组长度为 n。
    你需要通过 n 次操作，将 nums 中的所有元素分配到两个数组arr1和arr2中。
      1）在第一次操作中，将 nums[1] 追加到 arr1 。
      2）在第二次操作中，将 nums[2] 追加到 arr2 。
      3）之后，在第 i 次操作中：
        如果 arr1 的最后一个元素 大于 arr2 的最后一个元素，就将 nums[i] 追加到 arr1。
        否则，将 nums[i] 追加到 arr2。
    通过【连接数组】arr1和arr2形成数组result。例如，如果 arr1 == [1,2,3] 且 arr2 == [4,5,6] ，那么 result = [1,2,3,4,5,6] 。
    返回数组 result 。
    示例 1：
        输入：nums = [2, 1, 3]
        输出：[2, 3, 1]
        解释：在前两次操作后，arr1 = [2] ，arr2 = [1] 。
             在第 3 次操作中，由于 arr1 的最后一个元素大于 arr2
             的最后一个元素（2 > 1），将 nums[3] 追加到 arr1。
             3 次操作后，arr1 = [2,3] ，arr2 = [1] 。
             因此，连接形成的数组 result 是 [2, 3, 1] 。
    示例 2：
        输入：nums = [5, 4, 3, 8]
        输出：[5, 3, 4, 8]
        解释：在前两次操作后，arr1 = [5] ，arr2 = [4] 。
             在第 3 次操作中，由于 arr1 的最后一个元素大于 arr2 的最后一个元素（5 > 4），
                将 nums[3] 追加到 arr1 ，因此 arr1 变为 [5,3] 。
             在第 4 次操作中，由于 arr2 的最后一个元素大于 arr1 的最后一个元素（4 > 3），
                将 nums[4] 追加到 arr2 ，因此 arr2 变为 [4,8] 。
             4 次操作后，arr1 = [5, 3] ，arr2 = [4, 8] 。
             因此，连接形成的数组 result 是 [5, 3, 4, 8] 。
    提示：
        3 <= n <= 50
        1 <= nums[i] <= 100
        nums中的所有元素都互不相同。
    Related Topics:数组,模拟
*/
public class NO3069_E_ResultArray {

    @Test
    public void test() {
        assertArrayEquals(getArrays(2, 3, 1),
                resultArray(getArrays(2, 1, 3)));
        assertArrayEquals(getArrays(5, 3, 4, 8),
                resultArray(getArrays(5, 4, 3, 8)));
    }

    public int[] resultArray(int[] nums) {
        // 2024/3/6  NO.1
        // 2024/3/8  NO.2
        // 2024/3/21 NO.3

        return null;
    }

}















/*
// 方法1：
public int[] resultArray(int[] nums) {
    List<Integer> arr1 = new ArrayList<>();
    List<Integer> arr2 = new ArrayList<>();
    arr1.add(nums[0]);
    arr2.add(nums[1]);
    int p = 2;
    while (p < nums.length) {
        if (arr1.get(arr1.size() - 1) > arr2.get(arr2.size() - 1))
            arr1.add(nums[p++]);
        else if (arr1.get(arr1.size() - 1) < arr2.get(arr2.size() - 1))
            arr2.add(nums[p++]);
        else
            break;
    }
    int[] res = new int[arr1.size() + arr2.size()];
    int cnt = 0;
    for (int num : arr1)
        res[cnt++] = num;

    for (int num : arr2)
        res[cnt++] = num;

    return res;
}
*/