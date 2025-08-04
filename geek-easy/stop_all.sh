#!/bin/bash

# 停止所有程序脚本
# 作者: AI Assistant
# 版本: 1.0
# 用途: 停止算法动画程序和JVM监控工具

echo "🛑 停止所有程序"
echo "================"

# 查找并停止算法动画程序
ANIMATION_PIDS=$(pgrep -f "AlgorithmTreeLauncher")
if [ ! -z "$ANIMATION_PIDS" ]; then
    echo "停止算法动画程序..."
    for pid in $ANIMATION_PIDS; do
        echo "- 停止进程 $pid"
        kill $pid 2>/dev/null
    done
    sleep 2
    # 强制停止未响应的进程
    for pid in $ANIMATION_PIDS; do
        if kill -0 $pid 2>/dev/null; then
            echo "- 强制停止进程 $pid"
            kill -9 $pid 2>/dev/null
        fi
    done
    echo "✅ 算法动画程序已停止"
else
    echo "ℹ️  未找到运行中的算法动画程序"
fi

# 查找并停止JVM监控工具
MONITOR_PIDS=$(pgrep -f "JVMProcessMonitor")
if [ ! -z "$MONITOR_PIDS" ]; then
    echo "停止JVM监控工具..."
    for pid in $MONITOR_PIDS; do
        echo "- 停止进程 $pid"
        kill $pid 2>/dev/null
    done
    sleep 2
    # 强制停止未响应的进程
    for pid in $MONITOR_PIDS; do
        if kill -0 $pid 2>/dev/null; then
            echo "- 强制停止进程 $pid"
            kill -9 $pid 2>/dev/null
        fi
    done
    echo "✅ JVM监控工具已停止"
else
    echo "ℹ️  未找到运行中的JVM监控工具"
fi

# 检查JMX端口是否释放
if lsof -Pi :9999 -sTCP:LISTEN -t >/dev/null ; then
    echo "⚠️  端口9999仍被占用，尝试释放..."
    JMX_PID=$(lsof -Pi :9999 -sTCP:LISTEN -t)
    if [ ! -z "$JMX_PID" ]; then
        kill -9 $JMX_PID 2>/dev/null
        echo "✅ 端口9999已释放"
    fi
else
    echo "✅ 端口9999已释放"
fi

echo ""
echo "🎉 所有程序已停止"
echo "================"

# 显示当前相关进程状态
echo "📊 当前进程状态:"
REMAINING_ANIMATION=$(pgrep -f "AlgorithmTreeLauncher" | wc -l)
REMAINING_MONITOR=$(pgrep -f "JVMProcessMonitor" | wc -l)

echo "- 算法动画程序: $REMAINING_ANIMATION 个进程"
echo "- JVM监控工具: $REMAINING_MONITOR 个进程"

if [ $REMAINING_ANIMATION -eq 0 ] && [ $REMAINING_MONITOR -eq 0 ]; then
    echo "✅ 所有相关程序已完全停止"
else
    echo "⚠️  仍有程序在运行，可能需要手动处理"
fi