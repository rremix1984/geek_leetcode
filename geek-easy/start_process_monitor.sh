#!/bin/bash

# JVM进程监控工具启动脚本 v2.0
# 作者: AI Assistant
# 版本: 2.0
# 用途: 启动增强版JVM进程监控工具，支持远程JMX连接

echo "=========================================="
echo "JVM进程监控工具 v2.0 启动"
echo "=========================================="

# 检查Java版本
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo "Java版本: $JAVA_VERSION"

# 编译项目（如果需要）
if [ ! -d "target/classes" ]; then
    echo "正在编译项目..."
    mvn compile
    if [ $? -ne 0 ]; then
        echo "❌ 编译失败，请检查代码"
        exit 1
    fi
    echo "✅ 编译完成"
fi

# JVM参数配置
JVM_OPTS=""

# 内存配置
JVM_OPTS="$JVM_OPTS -Xms512m"                  # 初始堆内存512MB
JVM_OPTS="$JVM_OPTS -Xmx1g"                    # 最大堆内存1GB

# 垃圾回收器配置
JVM_OPTS="$JVM_OPTS -XX:+UseG1GC"              # 使用G1垃圾回收器
JVM_OPTS="$JVM_OPTS -XX:MaxGCPauseMillis=100"  # 最大GC暂停时间100ms

# 图形渲染优化
JVM_OPTS="$JVM_OPTS -Dsun.java2d.opengl=true"  # 启用OpenGL硬件加速
JVM_OPTS="$JVM_OPTS -Dsun.java2d.d3d=true"     # 启用Direct3D加速

# 显示配置信息
echo ""
echo "监控工具配置:"
echo "- 堆内存: 512MB ~ 1GB"
echo "- 垃圾回收器: G1GC"
echo "- 图形加速: 已启用"
echo ""

echo "🎯 使用说明:"
echo "1. 工具启动后默认监控自身JVM"
echo "2. 要监控算法动画程序，请:"
echo "   a) 使用 ./start_algorithm_with_jmx.sh 启动算法程序"
echo "   b) 在监控工具中选择'手动输入JMX端口'"
echo "   c) 输入端口号: 9999"
echo "3. 连接成功后即可实时监控目标程序"
echo ""
echo "正在启动JVM进程监控工具..."
echo "=========================================="

# 执行启动命令
java $JVM_OPTS -cp target/classes com.leetcode.tools.JVMProcessMonitor

echo ""
echo "监控工具已退出"