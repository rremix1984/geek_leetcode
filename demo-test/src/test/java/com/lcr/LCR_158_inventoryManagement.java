package com.lcr;

import org.junit.Test;
import java.util.Map;
import java.util.HashMap;

/*
    [ARRAY]
    （简单）
    LCR.158 库存管理 II
    仓库管理员以数组 stock 形式记录商品库存表。stock[i] 表示商品 id，可能存在重复。请返回库存表中数量大于 stock.length / 2 的商品 id。
    示例 1:
        输入: stock = [6, 1, 3, 1, 1, 1]
        输出: 1
    限制：
        1 <= stock.length <= 50000
        给定数组为非空数组，且存在结果数字
    Related Topics:数组,哈希表,分治,计数,排序
*/
public class LCR_158_inventoryManagement {

    @Test
    public void test() {
        assert 1 == majorityElement(new int[]{6, 1, 3, 1, 1, 1});
    }

    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length / 2;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) > n)
                return num;
        }
        return 0;
    }

}
