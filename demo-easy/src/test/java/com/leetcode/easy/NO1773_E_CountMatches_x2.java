/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    1773. 统计匹配检索规则的物品数量
        给你一个数组 items ，其中 items[i] = [typei, colori, namei] ，描述第 i 件物品的类型、颜色以及名称。
        另给你一条由两个字符串 ruleKey 和 ruleValue 表示的检索规则。
        如果第 i 件物品能满足下述条件之一，则认为该物品与给定的检索规则 匹配 ：
        ruleKey == "type" 且 ruleValue == typei 。
        ruleKey == "color" 且 ruleValue == colori 。
        ruleKey == "name" 且 ruleValue == namei 。
        统计并返回 匹配检索规则的物品数量 。
    示例 1：
        输入：items = {{"phone", "blue", "pixel"}, 
                      {"computer", "silver", "lenovo"}, 
                      {"phone", "gold", "iphone"}}, 
            ruleKey = "color", 
            ruleValue = "silver"
        输出：1
        解释：只有一件物品匹配检索规则，这件物品是 {"computer", "silver", "lenovo"} 。
    示例 2：
        输入：items = {{"phone", "blue", "pixel"}, 
                      {"computer", "silver", "phone"}, 
                      {"phone", "gold", "iphone"}}, 
             ruleKey = "type", 
             ruleValue = "phone"
        输出：2
        解释：只有两件物品匹配检索规则，这两件物品分别是
             {"phone", "blue", "pixel"} 和 {"phone", "gold", "iphone"} 。
             注意，{"computer",  "silver",  "phone"} 未匹配检索规则。
*/
public class NO1773_E_CountMatches_x2 {

    @Test
    public void test() {
        assert 1 == countMatches(
                getArray(new String[][]
                       {{"phone", "blue", "pixel"},
                        {"computer", "silver", "lenovo"},
                        {"phone", "gold", "iphone"}}),"color","silver");
        assert 2 == countMatches(
                getArray(new String[][]
                       {{"phone", "blue", "pixel"},
                        {"computer", "silver", "phone"},
                        {"phone", "gold", "iphone"}}),"type","phone");
    }
    
    public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
        int result = 0;
        for (List<String> item : items)
            switch (ruleKey) {
                case "type":
                    if (item.get(0).equals(ruleValue))
                        result++;
                    break;
                case "color":
                    if (item.get(1).equals(ruleValue))
                        result++;
                    break;
                case "name":
                    if (item.get(2).equals(ruleValue))
                        result++;
                    break;
            }
        return result;
    }

}
















/**
public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
    int result = 0;
    for (List<String> item : items)
        switch (ruleKey) {
            case "type":
                if (item.get(0).equals(ruleValue))
                    result++;
                break;
            case "color":
                if (item.get(1).equals(ruleValue))
                    result++;
                break;
            case "name":
                if (item.get(2).equals(ruleValue))
                    result++;
                break;
        }
    return result;
}
*/