#!/bin/bash

# 快速启动脚本 - 算法动画 + JVM监控
# 作者: AI Assistant
# 版本: 1.0
# 用途: 一键启动算法动画程序和JVM监控工具

echo "🚀 快速启动 - 算法动画 + JVM监控"
echo "=================================="

# 检查是否已有程序在运行
if lsof -Pi :9999 -sTCP:LISTEN -t >/dev/null ; then
    echo "⚠️  端口9999已被占用，正在停止现有程序..."
    pkill -f "AlgorithmTreeLauncher" 2>/dev/null
    pkill -f "JVMProcessMonitor" 2>/dev/null
    sleep 2
fi

# 编译项目
if [ ! -d "target/classes" ]; then
    echo "📦 编译项目..."
    mvn compile -q
fi

# 创建日志目录
mkdir -p logs

echo "🎯 启动算法动画程序..."
# 启动算法动画程序（带JMX）
java -Xms2g -Xmx6g \
     -XX:ReservedCodeCacheSize=2048m \
     -XX:+UseG1GC \
     -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.port=9999 \
     -Dcom.sun.management.jmxremote.authenticate=false \
     -Dcom.sun.management.jmxremote.ssl=false \
     -Dcom.sun.management.jmxremote.local.only=false \
     -Djava.rmi.server.hostname=localhost \
     -cp target/classes com.leetcode.animation.AlgorithmTreeLauncher > logs/animation.log 2>&1 &

ANIMATION_PID=$!
echo "算法程序 PID: $ANIMATION_PID"

# 等待JMX端口开启
echo "⏳ 等待JMX服务启动..."
for i in {1..15}; do
    if lsof -Pi :9999 -sTCP:LISTEN -t >/dev/null ; then
        echo "✅ JMX服务已启动"
        break
    fi
    sleep 1
    echo -n "."
done

echo ""
echo "🔍 启动JVM监控工具..."
# 启动JVM监控工具
java -Xms512m -Xmx1g \
     -XX:+UseG1GC \
     -cp target/classes com.leetcode.tools.JVMProcessMonitor > logs/monitor.log 2>&1 &

MONITOR_PID=$!
echo "监控工具 PID: $MONITOR_PID"

sleep 2

echo ""
echo "🎉 启动完成！"
echo "=================================="
echo "📊 程序信息:"
echo "- 算法程序: PID $ANIMATION_PID (端口9999)"
echo "- 监控工具: PID $MONITOR_PID"
echo ""
echo "📋 下一步操作:"
echo "1. 在监控工具界面选择'手动输入JMX端口'"
echo "2. 输入端口号: 9999"
echo "3. 点击连接开始监控"
echo ""
echo "📝 查看日志:"
echo "- 算法程序: tail -f logs/animation.log"
echo "- 监控工具: tail -f logs/monitor.log"
echo ""
echo "🛑 停止程序:"
echo "- 停止算法程序: kill $ANIMATION_PID"
echo "- 停止监控工具: kill $MONITOR_PID"
echo "- 停止所有: pkill -f 'AlgorithmTreeLauncher|JVMProcessMonitor'"

# 清理函数
cleanup() {
    echo ""
    echo "🛑 正在停止程序..."
    kill $ANIMATION_PID $MONITOR_PID 2>/dev/null
    echo "✅ 程序已停止"
    exit 0
}

trap cleanup SIGINT SIGTERM

echo ""
echo "程序正在后台运行，按 Ctrl+C 停止所有程序"
echo "或者直接关闭此终端，程序将继续在后台运行"

# 保持脚本运行
while true; do
    if ! kill -0 $ANIMATION_PID 2>/dev/null || ! kill -0 $MONITOR_PID 2>/dev/null; then
        echo "有程序意外退出，正在清理..."
        cleanup
    fi
    sleep 5
done