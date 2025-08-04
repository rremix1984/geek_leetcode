package com.leetcode.animation;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 动画测试基类
 * 提供通用的测试工具方法和Swing测试支持
 * 
 * 设计文档：
 * 1. 功能需求：为所有动画类提供统一的测试基础设施
 * 2. 技术架构：基于JUnit的测试框架，支持Swing组件测试
 * 3. 核心功能：窗口创建/销毁、事件分发、反射调用等
 * 4. 边界条件：超时处理、异常捕获、资源清理
 * 5. 性能考虑：避免测试间相互影响，确保测试隔离性
 */
public class BaseAnimationTest {
    
    protected JFrame testFrame;
    protected CountDownLatch latch;
    protected Exception testException;
    
    @Before
    public void setUp() {
        // 确保在EDT线程中执行Swing操作
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    setupSwingComponents();
                });
            } catch (Exception e) {
                throw new RuntimeException("Failed to setup Swing components", e);
            }
        } else {
            setupSwingComponents();
        }
    }
    
    @After
    public void tearDown() {
        if (testFrame != null) {
            SwingUtilities.invokeLater(() -> {
                testFrame.dispose();
                testFrame = null;
            });
        }
        
        // 等待EDT处理完所有事件
        try {
            SwingUtilities.invokeAndWait(() -> {
                // 空操作，只是为了确保EDT处理完所有事件
            });
        } catch (Exception e) {
            // 忽略异常
        }
    }
    
    /**
     * 子类实现此方法来设置特定的Swing组件
     * 基类提供默认实现
     */
    protected void setupSwingComponents() {
        // 默认创建一个简单的测试窗口
        testFrame = new JFrame("Test Frame");
        testFrame.setSize(400, 300);
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    /**
     * 在EDT线程中执行操作并等待完成
     */
    protected void runOnEDT(Runnable runnable) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeAndWait(runnable);
        }
    }
    
    /**
     * 在EDT线程中执行操作，不等待完成
     */
    protected void runOnEDTLater(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }
    
    /**
     * 等待指定时间，用于动画测试
     */
    protected void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 使用反射调用私有方法
     */
    protected Object invokePrivateMethod(Object obj, String methodName, Class<?>[] paramTypes, Object... args) 
            throws Exception {
        Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        return method.invoke(obj, args);
    }
    
    /**
     * 使用反射获取私有字段值
     */
    protected Object getPrivateField(Object obj, String fieldName) throws Exception {
        java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
    
    /**
     * 使用反射设置私有字段值
     */
    protected void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
    
    /**
     * 模拟按钮点击
     */
    protected void clickButton(JButton button) throws Exception {
        runOnEDT(() -> {
            button.doClick();
        });
        waitFor(100); // 等待事件处理
    }
    
    /**
     * 设置文本框内容
     */
    protected void setTextFieldValue(JTextField textField, String value) throws Exception {
        runOnEDT(() -> {
            textField.setText(value);
        });
    }
    
    /**
     * 获取文本框内容
     */
    protected String getTextFieldValue(JTextField textField) throws Exception {
        final String[] result = new String[1];
        runOnEDT(() -> {
            result[0] = textField.getText();
        });
        return result[0];
    }
    
    /**
     * 检查组件是否可见
     */
    protected boolean isComponentVisible(Component component) throws Exception {
        final boolean[] result = new boolean[1];
        runOnEDT(() -> {
            result[0] = component.isVisible();
        });
        return result[0];
    }
    
    /**
     * 检查组件是否启用
     */
    protected boolean isComponentEnabled(Component component) throws Exception {
        final boolean[] result = new boolean[1];
        runOnEDT(() -> {
            result[0] = component.isEnabled();
        });
        return result[0];
    }
    
    /**
     * 等待条件满足或超时
     */
    protected boolean waitForCondition(java.util.function.BooleanSupplier condition, long timeoutMs) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            if (condition.getAsBoolean()) {
                return true;
            }
            waitFor(50);
        }
        return false;
    }
    
    /**
     * 创建测试用的CountDownLatch
     */
    protected CountDownLatch createLatch(int count) {
        return new CountDownLatch(count);
    }
    
    /**
     * 等待latch或超时
     */
    protected boolean awaitLatch(CountDownLatch latch, long timeout, TimeUnit unit) {
        try {
            return latch.await(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * 基础测试方法 - 验证测试框架本身是否正常工作
     */
    @Test
    public void testBaseFramework() throws Exception {
        assertNotNull("测试框架应该存在", testFrame);
        assertEquals("测试窗口标题应该正确", "Test Frame", testFrame.getTitle());
        assertEquals("测试窗口宽度应该正确", 400, testFrame.getWidth());
        assertEquals("测试窗口高度应该正确", 300, testFrame.getHeight());
        assertEquals("窗口关闭操作应该正确", JFrame.DISPOSE_ON_CLOSE, testFrame.getDefaultCloseOperation());
    }
    
    /**
     * 测试EDT线程操作
     */
    @Test
    public void testEDTOperations() throws Exception {
        final boolean[] testResult = {false};
        
        runOnEDT(() -> {
            testResult[0] = SwingUtilities.isEventDispatchThread();
        });
        
        assertTrue("应该在EDT线程中执行", testResult[0]);
    }
    
    /**
     * 测试等待机制
     */
    @Test
    public void testWaitMechanism() {
        long startTime = System.currentTimeMillis();
        waitFor(100);
        long endTime = System.currentTimeMillis();
        
        long duration = endTime - startTime;
        assertTrue("等待时间应该大于等于100ms", duration >= 100);
        assertTrue("等待时间应该小于200ms", duration < 200);
    }
    
    /**
     * 测试反射工具方法
     */
    @Test
    public void testReflectionUtils() throws Exception {
        // 创建一个测试对象
        TestObject testObj = new TestObject();
        
        // 测试设置私有字段
        setPrivateField(testObj, "privateField", "test value");
        
        // 测试获取私有字段
        Object fieldValue = getPrivateField(testObj, "privateField");
        assertEquals("私有字段值应该正确", "test value", fieldValue);
        
        // 测试调用私有方法
        Object result = invokePrivateMethod(testObj, "privateMethod", 
                                          new Class<?>[]{String.class}, "hello");
        assertEquals("私有方法返回值应该正确", "hello world", result);
    }
    
    /**
     * 测试用的内部类
     */
    private static class TestObject {
        private String privateField = "initial";
        
        private String privateMethod(String input) {
            return input + " world";
        }
    }
}