/**
 * 测试AlgorithmTreeLauncher标题是否正确显示JMX端口号
 */
public class TitleTest {
    public static void main(String[] args) {
        // 模拟JMX端口设置
        System.setProperty("com.sun.management.jmxremote.port", "9999");
        
        // 获取JMX端口号并构建标题
        String jmxPort = System.getProperty("com.sun.management.jmxremote.port", "未启用");
        String title = "LeetCode算法动画演示系统 - 树形分类版";
        if (!"未启用".equals(jmxPort)) {
            title += " [JMX端口: " + jmxPort + "]";
        }
        
        System.out.println("=== 标题测试结果 ===");
        System.out.println("JMX端口: " + jmxPort);
        System.out.println("完整标题: " + title);
        System.out.println("==================");
        
        // 测试未设置JMX端口的情况
        System.clearProperty("com.sun.management.jmxremote.port");
        String jmxPortEmpty = System.getProperty("com.sun.management.jmxremote.port", "未启用");
        String titleEmpty = "LeetCode算法动画演示系统 - 树形分类版";
        if (!"未启用".equals(jmxPortEmpty)) {
            titleEmpty += " [JMX端口: " + jmxPortEmpty + "]";
        }
        
        System.out.println("=== 无JMX端口测试 ===");
        System.out.println("JMX端口: " + jmxPortEmpty);
        System.out.println("完整标题: " + titleEmpty);
        System.out.println("==================");
    }
}