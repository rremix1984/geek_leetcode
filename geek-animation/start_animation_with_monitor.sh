#!/bin/bash

# 算法动画程序 + JVM监控工具 综合启动脚本
# 作者: AI Assistant
# 版本: 1.0
# 用途: 同时启动算法动画程序和JVM监控工具，实现实时监控

echo "=========================================="
echo "算法动画程序 + JVM监控工具 综合启动"
echo "=========================================="

# 检查Java版本
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo "Java版本: $JAVA_VERSION"

# 设置JMX端口
JMX_PORT=9999
echo "JMX监控端口: $JMX_PORT"

# 检查端口是否被占用
if lsof -Pi :$JMX_PORT -sTCP:LISTEN -t >/dev/null ; then
    echo "⚠️  警告: 端口 $JMX_PORT 已被占用"
    echo "请先停止占用端口的程序，然后重新运行此脚本"
    exit 1
fi

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

# 创建日志目录
mkdir -p logs

# 算法动画程序JVM参数配置
ANIMATION_JVM_OPTS=""

# 内存配置 (针对Java 8优化)
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Xms2g"                    # 初始堆内存2GB
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Xmx6g"                    # 最大堆内存6GB
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:NewRatio=3"            # 新生代与老年代比例

# CodeCache配置 (Java 8最大2048MB)
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:ReservedCodeCacheSize=2048m"    # 代码缓存最大2048MB
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:InitialCodeCacheSize=256m"      # 初始代码缓存256MB
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:+UseCodeCacheFlushing"          # 启用代码缓存清理

# 垃圾回收器配置 (G1GC)
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:+UseG1GC"              # 使用G1垃圾回收器
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:MaxGCPauseMillis=200"  # 最大GC暂停时间200ms
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:G1HeapRegionSize=16m"  # G1堆区域大小16MB

# JMX远程监控配置
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Dcom.sun.management.jmxremote"                    # 启用JMX
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Dcom.sun.management.jmxremote.port=$JMX_PORT"     # JMX端口
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Dcom.sun.management.jmxremote.authenticate=false" # 不需要认证
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Dcom.sun.management.jmxremote.ssl=false"          # 不使用SSL
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Dcom.sun.management.jmxremote.local.only=false"   # 允许远程连接
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Djava.rmi.server.hostname=localhost"              # RMI服务器主机名

# 图形渲染优化
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Dsun.java2d.opengl=true"  # 启用OpenGL硬件加速
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -Dsun.java2d.d3d=true"     # 启用Direct3D加速

# 性能监控和调试
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:+PrintGCDetails"       # 打印GC详细信息
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:+PrintGCTimeStamps"    # 打印GC时间戳
ANIMATION_JVM_OPTS="$ANIMATION_JVM_OPTS -XX:+PrintCodeCache"       # 打印CodeCache信息

# JVM监控工具参数配置
MONITOR_JVM_OPTS=""

# 内存配置
MONITOR_JVM_OPTS="$MONITOR_JVM_OPTS -Xms512m"                  # 初始堆内存512MB
MONITOR_JVM_OPTS="$MONITOR_JVM_OPTS -Xmx1g"                    # 最大堆内存1GB

# 垃圾回收器配置
MONITOR_JVM_OPTS="$MONITOR_JVM_OPTS -XX:+UseG1GC"              # 使用G1垃圾回收器
MONITOR_JVM_OPTS="$MONITOR_JVM_OPTS -XX:MaxGCPauseMillis=100"  # 最大GC暂停时间100ms

# 图形渲染优化
MONITOR_JVM_OPTS="$MONITOR_JVM_OPTS -Dsun.java2d.opengl=true"  # 启用OpenGL硬件加速
MONITOR_JVM_OPTS="$MONITOR_JVM_OPTS -Dsun.java2d.d3d=true"     # 启用Direct3D加速

# 显示配置信息
echo ""
echo "🎯 算法动画程序配置:"
echo "- 堆内存: 2GB ~ 6GB"
echo "- CodeCache: 256MB ~ 2048MB"
echo "- 垃圾回收器: G1GC"
echo "- JMX端口: $JMX_PORT"
echo ""
echo "🔍 JVM监控工具配置:"
echo "- 堆内存: 512MB ~ 1GB"
echo "- 垃圾回收器: G1GC"
echo "- 监控目标: 算法动画程序"
echo ""

# 清理函数
cleanup() {
    echo ""
    echo "正在停止所有程序..."
    if [ ! -z "$ANIMATION_PID" ]; then
        kill $ANIMATION_PID 2>/dev/null
        echo "算法动画程序已停止"
    fi
    if [ ! -z "$MONITOR_PID" ]; then
        kill $MONITOR_PID 2>/dev/null
        echo "JVM监控工具已停止"
    fi
    echo "所有程序已停止"
    exit 0
}

# 设置信号处理
trap cleanup SIGINT SIGTERM

echo "正在启动程序..."
echo "=========================================="

# 1. 启动算法动画程序（后台运行）
echo "🚀 启动算法动画程序..."
java $ANIMATION_JVM_OPTS -cp target/classes com.animation.launcher.AlgorithmTreeLauncher > logs/animation.log 2>&1 &
ANIMATION_PID=$!

# 等待算法程序启动并开启JMX端口
echo "等待算法程序启动JMX服务..."
sleep 5

# 检查JMX端口是否开启
JMX_READY=false
for i in {1..10}; do
    if lsof -Pi :$JMX_PORT -sTCP:LISTEN -t >/dev/null ; then
        JMX_READY=true
        break
    fi
    echo "等待JMX端口开启... ($i/10)"
    sleep 2
done

if [ "$JMX_READY" = false ]; then
    echo "❌ JMX端口未能正常开启，请检查算法程序启动情况"
    kill $ANIMATION_PID 2>/dev/null
    exit 1
fi

echo "✅ 算法程序已启动，JMX端口已开启"

# 2. 启动JVM监控工具（后台运行）
echo "🔍 启动JVM监控工具..."
java $MONITOR_JVM_OPTS -cp target/classes com.leetcode.tools.JVMProcessMonitor > logs/monitor.log 2>&1 &
MONITOR_PID=$!

sleep 3
echo "✅ JVM监控工具已启动"

echo ""
echo "=========================================="
echo "🎉 所有程序启动完成！"
echo "=========================================="
echo ""
echo "📊 程序状态:"
echo "- 算法动画程序 PID: $ANIMATION_PID"
echo "- JVM监控工具 PID: $MONITOR_PID"
echo "- JMX监控端口: $JMX_PORT"
echo ""
echo "📝 使用说明:"
echo "1. 算法动画程序已在后台运行"
echo "2. JVM监控工具已启动，请在界面中:"
echo "   a) 选择'手动输入JMX端口'"
echo "   b) 输入端口号: $JMX_PORT"
echo "   c) 点击连接即可开始监控"
echo ""
echo "📋 日志文件:"
echo "- 算法程序日志: logs/animation.log"
echo "- 监控工具日志: logs/monitor.log"
echo ""
echo "⚠️  注意事项:"
echo "- 按 Ctrl+C 可同时停止所有程序"
echo "- 如需查看算法程序输出: tail -f logs/animation.log"
echo "- 如需查看监控工具输出: tail -f logs/monitor.log"
echo ""
echo "程序正在运行中，按 Ctrl+C 停止..."

# 等待用户中断
while true; do
    # 检查进程是否还在运行
    if ! kill -0 $ANIMATION_PID 2>/dev/null; then
        echo "算法动画程序已意外退出"
        break
    fi
    if ! kill -0 $MONITOR_PID 2>/dev/null; then
        echo "JVM监控工具已意外退出"
        break
    fi
    sleep 5
done

# 清理并退出
cleanup