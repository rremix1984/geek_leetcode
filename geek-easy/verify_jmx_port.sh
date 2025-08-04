#!/bin/bash

echo "🔍 JMX端口验证"
echo "=============="

# 检查进程是否运行
ALGORITHM_PID=$(pgrep -f "AlgorithmTreeLauncher")
if [ -z "$ALGORITHM_PID" ]; then
    echo "❌ 算法动画程序未运行"
    exit 1
fi

echo "✅ 算法动画程序正在运行 (PID: $ALGORITHM_PID)"

# 检查JMX端口
JMX_PORT_CHECK=$(netstat -an | grep ":9999" | grep LISTEN)
if [ -n "$JMX_PORT_CHECK" ]; then
    echo "✅ JMX端口9999正在监听"
    echo "   $JMX_PORT_CHECK"
else
    echo "❌ JMX端口9999未监听"
fi

# 检查进程的JMX参数
echo ""
echo "📋 进程JMX参数:"
ps aux | grep "$ALGORITHM_PID" | grep -o "\-Dcom\.sun\.management\.jmxremote[^[:space:]]*" | head -5

echo ""
echo "🎯 预期标题格式:"
echo "   LeetCode算法动画演示系统 - 树形分类版 [JMX端口: 9999]"

echo ""
echo "✅ 验证完成"