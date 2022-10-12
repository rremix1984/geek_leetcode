/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    599. 两个列表的最小索引总和
        假设 Andy 和 Doris 想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。
        你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设答案总是存在。
    示例 1:
        输入: list1 = {"Shogun", "Tapioca Express", "Burger King", "KFC"}，
             list2 = {"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"}
        输出: {"Shogun"}
        解释: 他们唯一共同喜爱的餐厅是“Shogun”。
    示例 2:
        输入:list1 = {"Shogun", "Tapioca Express", "Burger King", "KFC"}，
             list2 = {"KFC", "Shogun", "Burger King"}
        输出: {"Shogun"}
        解释: 他们共同喜爱且具有最小索引和的餐厅是“Shogun”，它有最小的索引和1(0+1)。
*/
public class NO599_E_FindRestaurant {

    @Test
    public void test() {
        assertArrayEquals(new String[]{"Shogun"}, findRestaurant(
                new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"},
                new String[]{"KFC", "Shogun", "Burger King"}));
        assertArrayEquals(new String[]{"Shogun"}, findRestaurant(
                new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"},
                new String[]{"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"}));
    }

    public String[] findRestaurant(String[] list1, String[] list2) {
        List<String> list = new ArrayList<>();
        int min = MAX_VALUE;
        for (int i = 0; i < list1.length; i++)
            for (int j = 0; j < list2.length; j++)
                if (list1[i].equals(list2[j]))
                    if (i + j == min) {
                        list.add(list1[i]);
                    } else if (i + j < min) {
                        min = i + j;
                        list.clear();
                        list.add(list1[i]);
                    }
        return list.toArray(new String[list.size()]);
    }

}
















/**
public String[] findRestaurant(String[] list1, String[] list2) {
    List<String> list = new ArrayList<>();
    int min = MAX_VALUE;
    for (int i = 0; i < list1.length; i++)
        for (int j = 0; j < list2.length; j++)
            if (list1[i].equals(list2[j]))
                if (i + j == min) {
                    list.add(list1[i]);
                } else if (i + j < min) {
                    min = i + j;
                    list.clear();
                    list.add(list1[i]);
                }

    return list.toArray(new String[list.size()]);
}
*/