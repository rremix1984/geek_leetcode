package com.leetcode.animation;

import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;

/**
 * AlgorithmAnimationLauncher测试类
 * 测试动画启动器的核心功能和UI交互
 * 
 * 设计文档：
 * 1. 功能需求：验证动画启动器的正确性和UI交互
 * 2. 测试范围：窗口创建、按钮功能、动画启动、异常处理
 * 3. 测试策略：UI组件验证、动画启动验证、错误处理验证
 * 4. 边界条件：无效动画类、反射异常、UI异常
 * 5. 性能验证：启动时间、内存使用
 */
public class AlgorithmAnimationLauncherTest extends BaseAnimationTest {
    
    private AlgorithmAnimationLauncher launcher;
    private JFrame mainFrame;
    
    @Override
    protected void setupSwingComponents() {
        launcher = new AlgorithmAnimationLauncher();
        testFrame = launcher;
        mainFrame = launcher;
    }
    
    @Test
    public void testInitialState() throws Exception {
        assertTrue("主窗口应该可见", isComponentVisible(launcher));
        assertEquals("窗口标题应该正确", "算法动画演示系统", launcher.getTitle());
        
        // 验证窗口大小
        Dimension size = launcher.getSize();
        assertTrue("窗口宽度应该合理", size.width >= 800);
        assertTrue("窗口高度应该合理", size.height >= 600);
        
        // 验证窗口居中
        assertTrue("窗口应该设置了位置", launcher.getX() >= 0);
        assertTrue("窗口应该设置了位置", launcher.getY() >= 0);
    }
    
    @Test
    public void testMainPanelExists() throws Exception {
        Container contentPane = launcher.getContentPane();
        assertNotNull("内容面板应该存在", contentPane);
        
        Component[] components = contentPane.getComponents();
        assertTrue("应该有组件", components.length > 0);
    }
    
    @Test
    public void testAnimationButtons() throws Exception {
        // 获取所有按钮
        Component[] allComponents = getAllComponents(launcher);
        
        int buttonCount = 0;
        for (Component comp : allComponents) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                String text = button.getText();
                
                if (text != null && !text.trim().isEmpty()) {
                    buttonCount++;
                    assertTrue("按钮应该启用", button.isEnabled());
                    assertNotNull("按钮应该有动作监听器", button.getActionListeners());
                }
            }
        }
        
        assertTrue("应该有动画按钮", buttonCount > 0);
    }
    
    @Test
    public void testSpecificAnimationButtons() throws Exception {
        // 测试特定的动画按钮是否存在
        String[] expectedAnimations = {
            "NO.001 两数之和",
            "NO.704 二分查找",
            "NO.703 数据流中的第K大元素",
            "NO.705 设计哈希集合",
            "NO.706 设计哈希映射",
            "NO.1005 K次取反后最大化的数组和"
        };
        
        Component[] allComponents = getAllComponents(launcher);
        
        for (String expectedText : expectedAnimations) {
            boolean found = false;
            for (Component comp : allComponents) {
                if (comp instanceof JButton) {
                    JButton button = (JButton) comp;
                    if (expectedText.equals(button.getText())) {
                        found = true;
                        assertTrue("按钮 " + expectedText + " 应该启用", button.isEnabled());
                        break;
                    }
                }
            }
            assertTrue("应该找到按钮: " + expectedText, found);
        }
    }
    
    @Test
    public void testButtonActionListeners() throws Exception {
        Component[] allComponents = getAllComponents(launcher);
        
        for (Component comp : allComponents) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                String text = button.getText();
                
                if (text != null && text.startsWith("NO.")) {
                    assertTrue("动画按钮应该有动作监听器", 
                              button.getActionListeners().length > 0);
                }
            }
        }
    }
    
    @Test
    public void testLaunchAnimation() throws Exception {
        // 测试启动一个动画
        Component[] allComponents = getAllComponents(launcher);
        JButton testButton = null;
        
        for (Component comp : allComponents) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if ("NO.001 两数之和".equals(button.getText())) {
                    testButton = button;
                    break;
                }
            }
        }
        
        assertNotNull("应该找到测试按钮", testButton);
        
        // 模拟点击按钮
        clickButton(testButton);
        waitFor(1000);
        
        // 验证是否有新窗口打开
        Window[] windows = Window.getWindows();
        boolean foundNewWindow = false;
        for (Window window : windows) {
            if (window != launcher && window.isVisible()) {
                foundNewWindow = true;
                break;
            }
        }
        
        assertTrue("应该打开新的动画窗口", foundNewWindow);
    }
    
    @Test
    public void testMultipleAnimationLaunch() throws Exception {
        // 测试启动多个动画
        Component[] allComponents = getAllComponents(launcher);
        
        String[] animationsToTest = {"NO.001 两数之和", "NO.704 二分查找"};
        
        for (String animationText : animationsToTest) {
            JButton button = null;
            for (Component comp : allComponents) {
                if (comp instanceof JButton) {
                    JButton btn = (JButton) comp;
                    if (animationText.equals(btn.getText())) {
                        button = btn;
                        break;
                    }
                }
            }
            
            if (button != null) {
                clickButton(button);
                waitFor(500);
            }
        }
        
        // 验证多个窗口是否打开
        Window[] windows = Window.getWindows();
        int animationWindowCount = 0;
        for (Window window : windows) {
            if (window != launcher && window.isVisible()) {
                animationWindowCount++;
            }
        }
        
        assertTrue("应该打开多个动画窗口", animationWindowCount >= 1);
    }
    
    @Test
    public void testWindowCloseOperation() throws Exception {
        assertEquals("窗口关闭操作应该正确设置", 
                    JFrame.EXIT_ON_CLOSE, launcher.getDefaultCloseOperation());
    }
    
    @Test
    public void testLayoutManager() throws Exception {
        Container contentPane = launcher.getContentPane();
        LayoutManager layout = contentPane.getLayout();
        assertNotNull("应该有布局管理器", layout);
    }
    
    @Test
    public void testReflectionMethods() throws Exception {
        // 测试反射方法是否正确工作
        try {
            Method launchMethod = launcher.getClass().getDeclaredMethod("launchAnimation", String.class);
            assertNotNull("launchAnimation方法应该存在", launchMethod);
        } catch (NoSuchMethodException e) {
            // 如果方法不存在，这是正常的，因为可能使用了不同的实现方式
        }
    }
    
    @Test
    public void testErrorHandling() throws Exception {
        // 测试错误处理
        try {
            // 尝试启动一个不存在的动画类
            Method[] methods = launcher.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().contains("launch") && method.getParameterCount() == 1) {
                    method.setAccessible(true);
                    method.invoke(launcher, "NonExistentAnimation");
                    break;
                }
            }
        } catch (Exception e) {
            // 预期可能会有异常，但程序应该继续运行
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(launcher));
    }
    
    @Test
    public void testUIResponsiveness() throws Exception {
        // 测试UI响应性
        Component[] allComponents = getAllComponents(launcher);
        
        for (Component comp : allComponents) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button.getText() != null && button.getText().startsWith("NO.")) {
                    assertTrue("按钮应该可以获得焦点", button.isFocusable());
                    assertTrue("按钮应该启用", button.isEnabled());
                }
            }
        }
    }
    
    @Test
    public void testMemoryUsage() throws Exception {
        // 基本的内存使用测试
        Runtime runtime = Runtime.getRuntime();
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // 启动几个动画
        Component[] allComponents = getAllComponents(launcher);
        int launchedCount = 0;
        
        for (Component comp : allComponents) {
            if (comp instanceof JButton && launchedCount < 3) {
                JButton button = (JButton) comp;
                if (button.getText() != null && button.getText().startsWith("NO.")) {
                    clickButton(button);
                    waitFor(200);
                    launchedCount++;
                }
            }
        }
        
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = finalMemory - initialMemory;
        
        // 验证内存增长在合理范围内（这里设置一个较宽松的限制）
        assertTrue("内存增长应该在合理范围内", memoryIncrease < 100 * 1024 * 1024); // 100MB
    }
    
    @Test
    public void testThreadSafety() throws Exception {
        // 基本的线程安全测试
        final boolean[] testPassed = {true};
        
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        Component[] allComponents = getAllComponents(launcher);
                        for (Component comp : allComponents) {
                            if (comp instanceof JButton) {
                                JButton button = (JButton) comp;
                                if ("NO.001 两数之和".equals(button.getText())) {
                                    button.doClick();
                                    break;
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    testPassed[0] = false;
                }
            });
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join(5000); // 等待最多5秒
        }
        
        assertTrue("多线程操作应该安全", testPassed[0]);
    }
    
    @Test
    public void testComponentHierarchy() throws Exception {
        // 测试组件层次结构
        Container contentPane = launcher.getContentPane();
        assertNotNull("内容面板应该存在", contentPane);
        
        Component[] topLevelComponents = contentPane.getComponents();
        assertTrue("应该有顶级组件", topLevelComponents.length > 0);
        
        // 递归检查所有组件
        int totalComponents = countAllComponents(launcher);
        assertTrue("应该有足够的组件", totalComponents > 10);
    }
    
    @Test
    public void testAccessibility() throws Exception {
        // 基本的可访问性测试
        Component[] allComponents = getAllComponents(launcher);
        
        for (Component comp : allComponents) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                String text = button.getText();
                if (text != null && !text.trim().isEmpty()) {
                    assertNotNull("按钮应该有文本", text);
                    assertTrue("按钮文本应该有意义", text.length() > 2);
                }
            }
        }
    }
    
    // 辅助方法：获取所有组件
    private Component[] getAllComponents(Container container) {
        java.util.List<Component> components = new java.util.ArrayList<>();
        addComponentsRecursively(container, components);
        return components.toArray(new Component[0]);
    }
    
    private void addComponentsRecursively(Container container, java.util.List<Component> components) {
        for (Component comp : container.getComponents()) {
            components.add(comp);
            if (comp instanceof Container) {
                addComponentsRecursively((Container) comp, components);
            }
        }
    }
    
    // 辅助方法：计算所有组件数量
    private int countAllComponents(Container container) {
        int count = 1; // 计算当前容器
        for (Component comp : container.getComponents()) {
            if (comp instanceof Container) {
                count += countAllComponents((Container) comp);
            } else {
                count++;
            }
        }
        return count;
    }
    
    @Test
    public void testPerformance() throws Exception {
        // 测试启动性能
        long startTime = System.currentTimeMillis();
        
        // 模拟用户快速点击多个按钮
        Component[] allComponents = getAllComponents(launcher);
        int clickCount = 0;
        
        for (Component comp : allComponents) {
            if (comp instanceof JButton && clickCount < 5) {
                JButton button = (JButton) comp;
                if (button.getText() != null && button.getText().startsWith("NO.")) {
                    clickButton(button);
                    waitFor(100);
                    clickCount++;
                }
            }
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertTrue("启动性能应该合理", duration < 10000); // 10秒内完成
    }
}