#!/bin/bash

# 算法动画程序启动脚本 - 支持JMX远程监控
# 作者: AI Assistant
# 版本: 1.0
# 用途: 启动算法动画程序并开启JMX监控端口

echo "=========================================="
echo "算法动画程序启动 - JMX监控版本"
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
    echo "请检查是否有其他程序在使用此端口"
    read -p "是否继续启动? (y/n): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "启动已取消"
        exit 1
    fi
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

# JVM参数配置
JVM_OPTS=""

# 内存配置 (针对Java 8优化)
JVM_OPTS="$JVM_OPTS -Xms2g"                    # 初始堆内存2GB
JVM_OPTS="$JVM_OPTS -Xmx6g"                    # 最大堆内存6GB
JVM_OPTS="$JVM_OPTS -XX:NewRatio=3"            # 新生代与老年代比例

# CodeCache配置 (Java 8最大2048MB)
JVM_OPTS="$JVM_OPTS -XX:ReservedCodeCacheSize=2048m"    # 代码缓存最大2048MB
JVM_OPTS="$JVM_OPTS -XX:InitialCodeCacheSize=256m"      # 初始代码缓存256MB
JVM_OPTS="$JVM_OPTS -XX:+UseCodeCacheFlushing"          # 启用代码缓存清理

# 垃圾回收器配置 (G1GC)
JVM_OPTS="$JVM_OPTS -XX:+UseG1GC"              # 使用G1垃圾回收器
JVM_OPTS="$JVM_OPTS -XX:MaxGCPauseMillis=200"  # 最大GC暂停时间200ms
JVM_OPTS="$JVM_OPTS -XX:G1HeapRegionSize=16m"  # G1堆区域大小16MB

# JMX远程监控配置
JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote"                    # 启用JMX
JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote.port=$JMX_PORT"     # JMX端口
JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote.authenticate=false" # 不需要认证
JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote.ssl=false"          # 不使用SSL
JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote.local.only=false"   # 允许远程连接
JVM_OPTS="$JVM_OPTS -Djava.rmi.server.hostname=localhost"              # RMI服务器主机名

# 图形渲染优化
JVM_OPTS="$JVM_OPTS -Dsun.java2d.opengl=true"  # 启用OpenGL硬件加速
JVM_OPTS="$JVM_OPTS -Dsun.java2d.d3d=true"     # 启用Direct3D加速

# 性能监控和调试
JVM_OPTS="$JVM_OPTS -XX:+PrintGCDetails"       # 打印GC详细信息
JVM_OPTS="$JVM_OPTS -XX:+PrintGCTimeStamps"    # 打印GC时间戳
JVM_OPTS="$JVM_OPTS -XX:+PrintCodeCache"       # 打印CodeCache信息

# 显示配置信息
echo ""
echo "JVM配置参数:"
echo "- 堆内存: 2GB ~ 6GB"
echo "- CodeCache: 256MB ~ 2048MB (Java 8最大值)"
echo "- 垃圾回收器: G1GC"
echo "- JMX端口: $JMX_PORT"
echo "- 图形加速: 已启用"
echo ""

# 启动程序
echo "正在启动算法动画程序..."
echo "JMX监控地址: service:jmx:rmi:///jndi/rmi://localhost:$JMX_PORT/jmxrmi"
echo ""
echo "🎯 监控提示:"
echo "1. 启动JVM进程监控工具"
echo "2. 选择'手动输入JMX端口'"
echo "3. 输入端口号: $JMX_PORT"
echo "4. 即可实时监控此程序的内存使用情况"
echo ""
echo "按 Ctrl+C 停止程序"
echo "=========================================="

# 执行启动命令
java $JVM_OPTS -cp target/classes com.animation.launcher.AlgorithmTreeLauncher

echo ""
echo "程序已退出"