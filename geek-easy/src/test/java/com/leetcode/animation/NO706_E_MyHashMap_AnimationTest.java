package com.leetcode.animation;

import com.leetcode.animation.datastructure.NO706_E_MyHashMap_Animation;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;

/**
 * NO.706 设计哈希映射动画测试类
 * 测试MyHashMap数据结构的核心逻辑和UI交互
 * 
 * 设计文档：
 * 1. 功能需求：验证MyHashMap数据结构的正确性和动画交互
 * 2. 测试范围：映射操作、边界条件、UI组件、性能验证
 * 3. 测试策略：put、get、remove、重复操作、边界值
 * 4. 边界条件：空映射、大量元素、重复键、边界值
 * 5. 性能验证：基本操作时间复杂度O(1)平均情况
 */
public class NO706_E_MyHashMap_AnimationTest extends BaseAnimationTest {
    
    private NO706_E_MyHashMap_Animation animation;
    private JTextField keyInput;
    private JTextField valueInput;
    private JButton putButton;
    private JButton getButton;
    private JButton removeButton;
    private JButton resetButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    @Override
    protected void setupSwingComponents() {
        animation = new NO706_E_MyHashMap_Animation();
        testFrame = animation;
        
        try {
            keyInput = (JTextField) getPrivateField(animation, "keyInput");
            valueInput = (JTextField) getPrivateField(animation, "valueInput");
            putButton = (JButton) getPrivateField(animation, "putButton");
            getButton = (JButton) getPrivateField(animation, "getButton");
            removeButton = (JButton) getPrivateField(animation, "removeButton");
            resetButton = (JButton) getPrivateField(animation, "resetButton");
            statusLabel = (JLabel) getPrivateField(animation, "statusLabel");
            resultLabel = (JLabel) getPrivateField(animation, "resultLabel");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get UI components", e);
        }
    }
    
    @Test
    public void testInitialState() throws Exception {
        assertTrue("窗口应该可见", isComponentVisible(animation));
        assertTrue("put按钮应该启用", isComponentEnabled(putButton));
        assertTrue("get按钮应该启用", isComponentEnabled(getButton));
        assertTrue("remove按钮应该启用", isComponentEnabled(removeButton));
        assertTrue("重置按钮应该启用", isComponentEnabled(resetButton));
        
        // 测试默认输入值
        assertEquals("默认键输入", "1", getTextFieldValue(keyInput));
        assertEquals("默认值输入", "1", getTextFieldValue(valueInput));
    }
    
    @Test
    public void testBasicPutOperation() throws Exception {
        setTextFieldValue(keyInput, "5");
        setTextFieldValue(valueInput, "10");
        clickButton(putButton);
        waitFor(200);
        
        String statusText = statusLabel.getText();
        assertTrue("应该显示put操作", statusText.contains("put") || statusText.contains("添加") || statusText.contains("设置"));
    }
    
    @Test
    public void testBasicGetOperation() throws Exception {
        // 先put一个键值对
        setTextFieldValue(keyInput, "5");
        setTextFieldValue(valueInput, "10");
        clickButton(putButton);
        waitFor(200);
        
        // 然后get
        setTextFieldValue(keyInput, "5");
        clickButton(getButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("应该获取到正确的值", resultText.contains("10"));
    }
    
    @Test
    public void testGetNonExistentKey() throws Exception {
        // 获取不存在的键
        setTextFieldValue(keyInput, "999");
        clickButton(getButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("应该显示键不存在", resultText.contains("-1") || resultText.contains("不存在"));
    }
    
    @Test
    public void testBasicRemoveOperation() throws Exception {
        // 先put一个键值对
        setTextFieldValue(keyInput, "5");
        setTextFieldValue(valueInput, "10");
        clickButton(putButton);
        waitFor(200);
        
        // 然后remove
        setTextFieldValue(keyInput, "5");
        clickButton(removeButton);
        waitFor(200);
        
        String statusText = statusLabel.getText();
        assertTrue("应该显示remove操作", statusText.contains("remove") || statusText.contains("删除"));
        
        // 验证删除后不存在
        clickButton(getButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("删除后应该不存在", resultText.contains("-1") || resultText.contains("不存在"));
    }
    
    @Test
    public void testRemoveNonExistentKey() throws Exception {
        // 删除不存在的键
        setTextFieldValue(keyInput, "999");
        clickButton(removeButton);
        waitFor(200);
        
        // 操作应该成功完成，不会报错
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testUpdateExistingKey() throws Exception {
        // 添加键值对
        setTextFieldValue(keyInput, "5");
        setTextFieldValue(valueInput, "10");
        clickButton(putButton);
        waitFor(200);
        
        // 更新相同键的值
        setTextFieldValue(keyInput, "5");
        setTextFieldValue(valueInput, "20");
        clickButton(putButton);
        waitFor(200);
        
        // 验证值被更新
        clickButton(getButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("值应该被更新为20", resultText.contains("20"));
    }
    
    @Test
    public void testMultipleKeyValuePairs() throws Exception {
        // 测试多个键值对
        int[][] pairs = {{1, 10}, {2, 20}, {3, 30}, {4, 40}, {5, 50}};
        
        // 添加多个键值对
        for (int[] pair : pairs) {
            setTextFieldValue(keyInput, String.valueOf(pair[0]));
            setTextFieldValue(valueInput, String.valueOf(pair[1]));
            clickButton(putButton);
            waitFor(100);
        }
        
        // 验证所有键值对都存在
        for (int[] pair : pairs) {
            setTextFieldValue(keyInput, String.valueOf(pair[0]));
            clickButton(getButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("键 " + pair[0] + " 应该对应值 " + pair[1], 
                      resultText.contains(String.valueOf(pair[1])));
        }
        
        // 删除部分键值对
        for (int i = 0; i < pairs.length; i += 2) {
            setTextFieldValue(keyInput, String.valueOf(pairs[i][0]));
            clickButton(removeButton);
            waitFor(100);
        }
        
        // 验证删除的键不存在，未删除的键存在
        for (int i = 0; i < pairs.length; i++) {
            setTextFieldValue(keyInput, String.valueOf(pairs[i][0]));
            clickButton(getButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            if (i % 2 == 0) {
                assertTrue("删除的键 " + pairs[i][0] + " 应该不存在", 
                          resultText.contains("-1") || resultText.contains("不存在"));
            } else {
                assertTrue("未删除的键 " + pairs[i][0] + " 应该存在", 
                          resultText.contains(String.valueOf(pairs[i][1])));
            }
        }
    }
    
    @Test
    public void testBoundaryValues() throws Exception {
        // 测试边界值
        int[][] boundaryPairs = {
            {0, 0},
            {1, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, 1},
            {Integer.MIN_VALUE, Integer.MIN_VALUE}
        };
        
        for (int[] pair : boundaryPairs) {
            setTextFieldValue(keyInput, String.valueOf(pair[0]));
            setTextFieldValue(valueInput, String.valueOf(pair[1]));
            clickButton(putButton);
            waitFor(100);
            
            clickButton(getButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("边界值键 " + pair[0] + " 应该正确处理", 
                      resultText.contains(String.valueOf(pair[1])));
        }
    }
    
    @Test
    public void testNegativeNumbers() throws Exception {
        // 测试负数
        int[][] negativePairs = {{-1, -10}, {-100, -200}, {-999, -1000}};
        
        for (int[] pair : negativePairs) {
            setTextFieldValue(keyInput, String.valueOf(pair[0]));
            setTextFieldValue(valueInput, String.valueOf(pair[1]));
            clickButton(putButton);
            waitFor(100);
            
            clickButton(getButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("负数键 " + pair[0] + " 应该正确处理", 
                      resultText.contains(String.valueOf(pair[1])));
        }
    }
    
    @Test
    public void testLargeNumbers() throws Exception {
        // 测试大数
        int[][] largePairs = {
            {1000000, 2000000},
            {999999, 1999999},
            {1000001, 2000001}
        };
        
        for (int[] pair : largePairs) {
            setTextFieldValue(keyInput, String.valueOf(pair[0]));
            setTextFieldValue(valueInput, String.valueOf(pair[1]));
            clickButton(putButton);
            waitFor(100);
            
            clickButton(getButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("大数键 " + pair[0] + " 应该正确处理", 
                      resultText.contains(String.valueOf(pair[1])));
        }
    }
    
    @Test
    public void testZeroValues() throws Exception {
        // 测试零值
        setTextFieldValue(keyInput, "0");
        setTextFieldValue(valueInput, "0");
        clickButton(putButton);
        waitFor(200);
        
        clickButton(getButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("零值应该正确处理", resultText.contains("0"));
        
        // 测试键为0，值为非0
        setTextFieldValue(keyInput, "0");
        setTextFieldValue(valueInput, "100");
        clickButton(putButton);
        waitFor(200);
        
        clickButton(getButton);
        waitFor(200);
        
        resultText = resultLabel.getText();
        assertTrue("键为0的值应该被更新", resultText.contains("100"));
    }
    
    @Test
    public void testResetFunctionality() throws Exception {
        // 添加一些键值对
        for (int i = 1; i <= 5; i++) {
            setTextFieldValue(keyInput, String.valueOf(i));
            setTextFieldValue(valueInput, String.valueOf(i * 10));
            clickButton(putButton);
            waitFor(100);
        }
        
        // 重置
        clickButton(resetButton);
        waitFor(200);
        
        // 验证重置后键值对不存在
        setTextFieldValue(keyInput, "1");
        clickButton(getButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("重置后键值对应该不存在", resultText.contains("-1") || resultText.contains("不存在"));
        
        // 验证UI状态
        assertTrue("重置后所有按钮应该可用", isComponentEnabled(putButton));
        assertEquals("重置后状态标签应该清空", "", statusLabel.getText());
    }
    
    @Test
    public void testInvalidKeyInput() throws Exception {
        // 测试无效键输入
        setTextFieldValue(keyInput, "abc");
        setTextFieldValue(valueInput, "10");
        
        try {
            clickButton(putButton);
            waitFor(200);
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testInvalidValueInput() throws Exception {
        // 测试无效值输入
        setTextFieldValue(keyInput, "5");
        setTextFieldValue(valueInput, "xyz");
        
        try {
            clickButton(putButton);
            waitFor(200);
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testEmptyInput() throws Exception {
        // 测试空输入
        setTextFieldValue(keyInput, "");
        setTextFieldValue(valueInput, "");
        
        try {
            clickButton(putButton);
            waitFor(200);
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testDataStructureState() throws Exception {
        setTextFieldValue(keyInput, "5");
        setTextFieldValue(valueInput, "10");
        clickButton(putButton);
        waitFor(200);
        
        // 获取内部数据结构
        Object myHashMap = getPrivateField(animation, "myHashMap");
        assertNotNull("MyHashMap对象应该存在", myHashMap);
    }
    
    @Test
    public void testUIComponentsExist() throws Exception {
        assertNotNull("键输入框应该存在", keyInput);
        assertNotNull("值输入框应该存在", valueInput);
        assertNotNull("put按钮应该存在", putButton);
        assertNotNull("get按钮应该存在", getButton);
        assertNotNull("remove按钮应该存在", removeButton);
        assertNotNull("重置按钮应该存在", resetButton);
        assertNotNull("状态标签应该存在", statusLabel);
        assertNotNull("结果标签应该存在", resultLabel);
    }
    
    @Test
    public void testSequentialOperations() throws Exception {
        // 测试一系列操作的正确性
        
        // 1. put(1, 10)
        setTextFieldValue(keyInput, "1");
        setTextFieldValue(valueInput, "10");
        clickButton(putButton);
        waitFor(100);
        
        // 2. get(1) -> 10
        clickButton(getButton);
        waitFor(100);
        String result1 = resultLabel.getText();
        assertTrue("get(1)应该返回10", result1.contains("10"));
        
        // 3. put(2, 20)
        setTextFieldValue(keyInput, "2");
        setTextFieldValue(valueInput, "20");
        clickButton(putButton);
        waitFor(100);
        
        // 4. get(1) -> 10 (仍然存在)
        setTextFieldValue(keyInput, "1");
        clickButton(getButton);
        waitFor(100);
        String result2 = resultLabel.getText();
        assertTrue("get(1)应该仍然返回10", result2.contains("10"));
        
        // 5. get(2) -> 20
        setTextFieldValue(keyInput, "2");
        clickButton(getButton);
        waitFor(100);
        String result3 = resultLabel.getText();
        assertTrue("get(2)应该返回20", result3.contains("20"));
        
        // 6. put(1, 30) (更新)
        setTextFieldValue(keyInput, "1");
        setTextFieldValue(valueInput, "30");
        clickButton(putButton);
        waitFor(100);
        
        // 7. get(1) -> 30
        clickButton(getButton);
        waitFor(100);
        String result4 = resultLabel.getText();
        assertTrue("get(1)应该返回更新后的值30", result4.contains("30"));
        
        // 8. remove(1)
        clickButton(removeButton);
        waitFor(100);
        
        // 9. get(1) -> -1
        clickButton(getButton);
        waitFor(100);
        String result5 = resultLabel.getText();
        assertTrue("remove后get(1)应该返回-1", result5.contains("-1") || result5.contains("不存在"));
        
        // 10. get(2) -> 20 (仍然存在)
        setTextFieldValue(keyInput, "2");
        clickButton(getButton);
        waitFor(100);
        String result6 = resultLabel.getText();
        assertTrue("get(2)应该仍然返回20", result6.contains("20"));
    }
    
    @Test
    public void testPerformanceWithManyElements() throws Exception {
        // 测试大量元素的性能
        int numElements = 100;
        
        long startTime = System.currentTimeMillis();
        
        // 添加大量键值对
        for (int i = 0; i < numElements; i++) {
            setTextFieldValue(keyInput, String.valueOf(i));
            setTextFieldValue(valueInput, String.valueOf(i * 10));
            clickButton(putButton);
            waitFor(10); // 减少等待时间以加快测试
        }
        
        long putTime = System.currentTimeMillis() - startTime;
        
        // 获取所有键值对
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numElements; i++) {
            setTextFieldValue(keyInput, String.valueOf(i));
            clickButton(getButton);
            waitFor(10);
            
            String resultText = resultLabel.getText();
            assertTrue("键 " + i + " 应该对应值 " + (i * 10), 
                      resultText.contains(String.valueOf(i * 10)));
        }
        long getTime = System.currentTimeMillis() - startTime;
        
        // 删除所有键值对
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numElements; i++) {
            setTextFieldValue(keyInput, String.valueOf(i));
            clickButton(removeButton);
            waitFor(10);
        }
        long removeTime = System.currentTimeMillis() - startTime;
        
        // 验证性能合理（这里只是基本检查）
        assertTrue("put操作时间应该合理", putTime < 30000);
        assertTrue("get操作时间应该合理", getTime < 30000);
        assertTrue("remove操作时间应该合理", removeTime < 30000);
        
        // 验证所有键值对都被删除
        setTextFieldValue(keyInput, "0");
        clickButton(getButton);
        waitFor(100);
        String resultText = resultLabel.getText();
        assertTrue("删除后键应该不存在", resultText.contains("-1") || resultText.contains("不存在"));
    }
    
    @Test
    public void testHashCollisionHandling() throws Exception {
        // 测试可能产生哈希冲突的键
        // 这些键在简单的哈希函数下可能产生冲突
        int[][] collisionPairs = {
            {1, 100},
            {1001, 101},
            {2001, 102},
            {3001, 103}
        }; // 假设使用模1000的哈希函数
        
        // 添加所有键值对
        for (int[] pair : collisionPairs) {
            setTextFieldValue(keyInput, String.valueOf(pair[0]));
            setTextFieldValue(valueInput, String.valueOf(pair[1]));
            clickButton(putButton);
            waitFor(100);
        }
        
        // 验证所有键值对都能正确获取
        for (int[] pair : collisionPairs) {
            setTextFieldValue(keyInput, String.valueOf(pair[0]));
            clickButton(getButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("冲突键 " + pair[0] + " 应该正确处理", 
                      resultText.contains(String.valueOf(pair[1])));
        }
        
        // 更新部分键的值
        setTextFieldValue(keyInput, "1");
        setTextFieldValue(valueInput, "999");
        clickButton(putButton);
        waitFor(100);
        
        setTextFieldValue(keyInput, "2001");
        setTextFieldValue(valueInput, "888");
        clickButton(putButton);
        waitFor(100);
        
        // 验证更新后的值
        setTextFieldValue(keyInput, "1");
        clickButton(getButton);
        waitFor(100);
        String result1 = resultLabel.getText();
        assertTrue("更新后键1的值应该是999", result1.contains("999"));
        
        setTextFieldValue(keyInput, "1001");
        clickButton(getButton);
        waitFor(100);
        String result2 = resultLabel.getText();
        assertTrue("键1001的值应该保持不变", result2.contains("101"));
        
        setTextFieldValue(keyInput, "2001");
        clickButton(getButton);
        waitFor(100);
        String result3 = resultLabel.getText();
        assertTrue("更新后键2001的值应该是888", result3.contains("888"));
    }
}