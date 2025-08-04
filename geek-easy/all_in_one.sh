#!/bin/bash

# ========================================
# 算法动画系统 - 统一管理脚本 (All-in-One)
# ========================================
# 作者: AI Assistant
# 版本: 2.0
# 功能: 启动、停止、重启、状态查询、日志查看等

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# 配置参数
JMX_PORT=9999
ANIMATION_CLASS="com.leetcode.animation.AlgorithmTreeLauncher"
MONITOR_CLASS="com.leetcode.tools.JVMProcessMonitor"
MEMORY_MONITOR_CLASS="com.leetcode.tools.JVMMemoryMonitor"
LOG_DIR="logs"

# 显示帮助信息
show_help() {
    echo -e "${CYAN}========================================${NC}"
    echo -e "${CYAN}  算法动画系统 - 统一管理脚本 v2.0${NC}"
    echo -e "${CYAN}========================================${NC}"
    echo ""
    echo -e "${YELLOW}用法:${NC} $0 [命令] [选项]"
    echo ""
    echo -e "${YELLOW}命令:${NC}"
    echo -e "  ${GREEN}start${NC}     启动服务"
    echo -e "  ${GREEN}stop${NC}      停止服务"
    echo -e "  ${GREEN}restart${NC}   重启服务"
    echo -e "  ${GREEN}status${NC}    查看状态"
    echo -e "  ${GREEN}logs${NC}      查看日志"
    echo -e "  ${GREEN}clean${NC}     清理日志"
    echo -e "  ${GREEN}compile${NC}   编译项目"
    echo -e "  ${GREEN}verify${NC}    验证JMX连接"
    echo -e "  ${GREEN}help${NC}      显示帮助"
    echo ""
    echo -e "${YELLOW}启动选项:${NC}"
    echo -e "  ${BLUE}--animation${NC}     只启动算法动画程序"
    echo -e "  ${BLUE}--monitor${NC}       只启动JVM进程监控"
    echo -e "  ${BLUE}--memory${NC}        只启动JVM内存监控"
    echo -e "  ${BLUE}--optimized${NC}     使用优化参数启动"
    echo -e "  ${BLUE}--background${NC}    后台运行模式"
    echo ""
    echo -e "${YELLOW}日志选项:${NC}"
    echo -e "  ${BLUE}--animation${NC}     查看算法程序日志"
    echo -e "  ${BLUE}--monitor${NC}       查看监控工具日志"
    echo -e "  ${BLUE}--follow${NC}        实时跟踪日志"
    echo ""
    echo -e "${YELLOW}示例:${NC}"
    echo -e "  $0 start                    # 启动所有服务"
    echo -e "  $0 start --animation        # 只启动算法程序"
    echo -e "  $0 start --optimized        # 使用优化参数启动"
    echo -e "  $0 logs --animation --follow # 实时查看算法程序日志"
    echo -e "  $0 status                   # 查看所有服务状态"
    echo ""
}

# 检查进程是否运行
check_process() {
    local class_name=$1
    pgrep -f "$class_name" > /dev/null
    return $?
}

# 获取进程PID
get_pid() {
    local class_name=$1
    pgrep -f "$class_name"
}

# 检查端口是否被占用
check_port() {
    local port=$1
    lsof -Pi :$port -sTCP:LISTEN -t >/dev/null 2>&1
    return $?
}

# 编译项目
compile_project() {
    echo -e "${BLUE}📦 编译项目...${NC}"
    if [ ! -f "pom.xml" ]; then
        echo -e "${RED}❌ 未找到pom.xml文件${NC}"
        return 1
    fi
    
    mvn compile -q
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ 编译成功${NC}"
        return 0
    else
        echo -e "${RED}❌ 编译失败${NC}"
        return 1
    fi
}

# 创建日志目录
create_log_dir() {
    if [ ! -d "$LOG_DIR" ]; then
        mkdir -p "$LOG_DIR"
        echo -e "${GREEN}📁 创建日志目录: $LOG_DIR${NC}"
    fi
}

# 启动算法动画程序
start_animation() {
    local optimized=$1
    local background=$2
    
    if check_process "$ANIMATION_CLASS"; then
        echo -e "${YELLOW}⚠️  算法动画程序已在运行${NC}"
        return 0
    fi
    
    echo -e "${BLUE}🎯 启动算法动画程序...${NC}"
    
    # JVM参数配置
    if [ "$optimized" = "true" ]; then
        JVM_OPTS="-Xms2g -Xmx6g \
                  -XX:ReservedCodeCacheSize=2048m \
                  -XX:InitialCodeCacheSize=256m \
                  -XX:+UseCodeCacheFlushing \
                  -XX:+UseG1GC \
                  -XX:MaxGCPauseMillis=200 \
                  -XX:+UseStringDeduplication \
                  -XX:+OptimizeStringConcat \
                  -XX:+UseFastAccessorMethods \
                  -XX:+UseCompressedOops \
                  -XX:+UseCompressedClassPointers \
                  -Djava.awt.headless=false \
                  -Dfile.encoding=UTF-8 \
                  -Dsun.java2d.opengl=true \
                  -Dswing.aatext=true \
                  -Dawt.useSystemAAFontSettings=on"
    else
        JVM_OPTS="-Xms2g -Xmx6g \
                  -XX:ReservedCodeCacheSize=2048m \
                  -XX:+UseG1GC"
    fi
    
    # JMX参数
    JMX_OPTS="-Dcom.sun.management.jmxremote \
              -Dcom.sun.management.jmxremote.port=$JMX_PORT \
              -Dcom.sun.management.jmxremote.authenticate=false \
              -Dcom.sun.management.jmxremote.ssl=false \
              -Dcom.sun.management.jmxremote.local.only=false \
              -Djava.rmi.server.hostname=localhost"
    
    create_log_dir
    
    if [ "$background" = "true" ]; then
        java $JVM_OPTS $JMX_OPTS -cp target/classes $ANIMATION_CLASS > $LOG_DIR/animation.log 2>&1 &
        local pid=$!
        echo -e "${GREEN}✅ 算法动画程序已启动 (PID: $pid)${NC}"
    else
        java $JVM_OPTS $JMX_OPTS -cp target/classes $ANIMATION_CLASS
    fi
}

# 启动JVM进程监控
start_monitor() {
    local background=$1
    
    if check_process "$MONITOR_CLASS"; then
        echo -e "${YELLOW}⚠️  JVM进程监控已在运行${NC}"
        return 0
    fi
    
    echo -e "${BLUE}🔍 启动JVM进程监控...${NC}"
    
    JVM_OPTS="-Xms512m -Xmx1g \
              -XX:+UseG1GC \
              -Djava.awt.headless=false \
              -Dfile.encoding=UTF-8"
    
    create_log_dir
    
    if [ "$background" = "true" ]; then
        java $JVM_OPTS -cp target/classes $MONITOR_CLASS > $LOG_DIR/monitor.log 2>&1 &
        local pid=$!
        echo -e "${GREEN}✅ JVM进程监控已启动 (PID: $pid)${NC}"
    else
        java $JVM_OPTS -cp target/classes $MONITOR_CLASS
    fi
}

# 启动JVM内存监控
start_memory_monitor() {
    local background=$1
    
    if check_process "$MEMORY_MONITOR_CLASS"; then
        echo -e "${YELLOW}⚠️  JVM内存监控已在运行${NC}"
        return 0
    fi
    
    echo -e "${BLUE}📊 启动JVM内存监控...${NC}"
    
    JVM_OPTS="-Xms256m -Xmx1g \
              -XX:+UseG1GC \
              -Djava.awt.headless=false \
              -Dfile.encoding=UTF-8"
    
    create_log_dir
    
    if [ "$background" = "true" ]; then
        java $JVM_OPTS -cp target/classes $MEMORY_MONITOR_CLASS > $LOG_DIR/memory.log 2>&1 &
        local pid=$!
        echo -e "${GREEN}✅ JVM内存监控已启动 (PID: $pid)${NC}"
    else
        java $JVM_OPTS -cp target/classes $MEMORY_MONITOR_CLASS
    fi
}

# 停止进程
stop_process() {
    local class_name=$1
    local name=$2
    
    local pids=$(get_pid "$class_name")
    if [ -z "$pids" ]; then
        echo -e "${YELLOW}ℹ️  $name 未运行${NC}"
        return 0
    fi
    
    echo -e "${BLUE}🛑 停止 $name...${NC}"
    for pid in $pids; do
        echo -e "  停止进程 $pid"
        kill $pid 2>/dev/null
    done
    
    sleep 2
    
    # 强制停止未响应的进程
    for pid in $pids; do
        if kill -0 $pid 2>/dev/null; then
            echo -e "  强制停止进程 $pid"
            kill -9 $pid 2>/dev/null
        fi
    done
    
    echo -e "${GREEN}✅ $name 已停止${NC}"
}

# 显示状态
show_status() {
    echo -e "${CYAN}📊 系统状态${NC}"
    echo -e "${CYAN}============${NC}"
    
    # 算法动画程序状态
    if check_process "$ANIMATION_CLASS"; then
        local pid=$(get_pid "$ANIMATION_CLASS")
        echo -e "${GREEN}✅ 算法动画程序: 运行中 (PID: $pid)${NC}"
    else
        echo -e "${RED}❌ 算法动画程序: 未运行${NC}"
    fi
    
    # JVM进程监控状态
    if check_process "$MONITOR_CLASS"; then
        local pid=$(get_pid "$MONITOR_CLASS")
        echo -e "${GREEN}✅ JVM进程监控: 运行中 (PID: $pid)${NC}"
    else
        echo -e "${RED}❌ JVM进程监控: 未运行${NC}"
    fi
    
    # JVM内存监控状态
    if check_process "$MEMORY_MONITOR_CLASS"; then
        local pid=$(get_pid "$MEMORY_MONITOR_CLASS")
        echo -e "${GREEN}✅ JVM内存监控: 运行中 (PID: $pid)${NC}"
    else
        echo -e "${RED}❌ JVM内存监控: 未运行${NC}"
    fi
    
    # JMX端口状态
    if check_port $JMX_PORT; then
        echo -e "${GREEN}✅ JMX端口 $JMX_PORT: 监听中${NC}"
    else
        echo -e "${RED}❌ JMX端口 $JMX_PORT: 未监听${NC}"
    fi
    
    echo ""
    echo -e "${BLUE}💾 内存使用情况:${NC}"
    ps aux | grep -E "(AlgorithmTreeLauncher|JVMProcessMonitor|JVMMemoryMonitor)" | grep -v grep | awk '{printf "  PID: %-8s CPU: %-6s MEM: %-6s CMD: %s\n", $2, $3"%", $4"%", $11}'
}

# 查看日志
show_logs() {
    local target=$1
    local follow=$2
    
    if [ ! -d "$LOG_DIR" ]; then
        echo -e "${RED}❌ 日志目录不存在${NC}"
        return 1
    fi
    
    case $target in
        "animation")
            local log_file="$LOG_DIR/animation.log"
            ;;
        "monitor")
            local log_file="$LOG_DIR/monitor.log"
            ;;
        "memory")
            local log_file="$LOG_DIR/memory.log"
            ;;
        *)
            echo -e "${BLUE}📝 可用日志文件:${NC}"
            ls -la $LOG_DIR/*.log 2>/dev/null || echo -e "${YELLOW}  无日志文件${NC}"
            return 0
            ;;
    esac
    
    if [ ! -f "$log_file" ]; then
        echo -e "${RED}❌ 日志文件不存在: $log_file${NC}"
        return 1
    fi
    
    echo -e "${BLUE}📝 查看日志: $log_file${NC}"
    echo -e "${CYAN}================================${NC}"
    
    if [ "$follow" = "true" ]; then
        tail -f "$log_file"
    else
        tail -50 "$log_file"
    fi
}

# 清理日志
clean_logs() {
    if [ -d "$LOG_DIR" ]; then
        echo -e "${BLUE}🧹 清理日志文件...${NC}"
        rm -f $LOG_DIR/*.log
        echo -e "${GREEN}✅ 日志已清理${NC}"
    else
        echo -e "${YELLOW}ℹ️  日志目录不存在${NC}"
    fi
}

# 验证JMX连接
verify_jmx() {
    echo -e "${BLUE}🔍 验证JMX连接${NC}"
    echo -e "${CYAN}=================${NC}"
    
    # 检查算法程序是否运行
    if ! check_process "$ANIMATION_CLASS"; then
        echo -e "${RED}❌ 算法动画程序未运行${NC}"
        return 1
    fi
    
    local pid=$(get_pid "$ANIMATION_CLASS")
    echo -e "${GREEN}✅ 算法动画程序正在运行 (PID: $pid)${NC}"
    
    # 检查JMX端口
    if check_port $JMX_PORT; then
        echo -e "${GREEN}✅ JMX端口 $JMX_PORT 正在监听${NC}"
        netstat -an | grep ":$JMX_PORT" | grep LISTEN
    else
        echo -e "${RED}❌ JMX端口 $JMX_PORT 未监听${NC}"
    fi
    
    # 检查JMX参数
    echo ""
    echo -e "${BLUE}📋 JMX参数:${NC}"
    ps aux | grep "$pid" | grep -o "\-Dcom\.sun\.management\.jmxremote[^[:space:]]*" | head -5
    
    echo ""
    echo -e "${GREEN}✅ 验证完成${NC}"
}

# 主函数
main() {
    # 检查是否在正确的目录
    if [ ! -f "pom.xml" ]; then
        echo -e "${RED}❌ 请在项目根目录下运行此脚本${NC}"
        exit 1
    fi
    
    # 解析参数
    local command=$1
    shift
    
    local animation_only=false
    local monitor_only=false
    local memory_only=false
    local optimized=false
    local background=false
    local follow=false
    local target=""
    
    while [[ $# -gt 0 ]]; do
        case $1 in
            --animation)
                if [ "$command" = "logs" ]; then
                    target="animation"
                else
                    animation_only=true
                fi
                shift
                ;;
            --monitor)
                if [ "$command" = "logs" ]; then
                    target="monitor"
                else
                    monitor_only=true
                fi
                shift
                ;;
            --memory)
                if [ "$command" = "logs" ]; then
                    target="memory"
                else
                    memory_only=true
                fi
                shift
                ;;
            --optimized)
                optimized=true
                shift
                ;;
            --background)
                background=true
                shift
                ;;
            --follow)
                follow=true
                shift
                ;;
            *)
                echo -e "${RED}❌ 未知参数: $1${NC}"
                show_help
                exit 1
                ;;
        esac
    done
    
    # 执行命令
    case $command in
        "start")
            echo -e "${CYAN}🚀 启动算法动画系统${NC}"
            echo -e "${CYAN}=======================${NC}"
            
            # 检查编译
            if [ ! -d "target/classes" ]; then
                compile_project || exit 1
            fi
            
            if [ "$animation_only" = "true" ]; then
                start_animation "$optimized" "$background"
            elif [ "$monitor_only" = "true" ]; then
                start_monitor "$background"
            elif [ "$memory_only" = "true" ]; then
                start_memory_monitor "$background"
            else
                # 启动所有服务
                start_animation "$optimized" true
                sleep 3
                start_monitor true
                
                echo ""
                echo -e "${GREEN}🎉 启动完成！${NC}"
                echo -e "${CYAN}================================${NC}"
                show_status
                echo ""
                echo -e "${YELLOW}📋 下一步操作:${NC}"
                echo -e "1. 在监控工具界面选择'手动输入JMX端口'"
                echo -e "2. 输入端口号: $JMX_PORT"
                echo -e "3. 点击连接开始监控"
            fi
            ;;
        "stop")
            echo -e "${CYAN}🛑 停止算法动画系统${NC}"
            echo -e "${CYAN}======================${NC}"
            
            if [ "$animation_only" = "true" ]; then
                stop_process "$ANIMATION_CLASS" "算法动画程序"
            elif [ "$monitor_only" = "true" ]; then
                stop_process "$MONITOR_CLASS" "JVM进程监控"
            elif [ "$memory_only" = "true" ]; then
                stop_process "$MEMORY_MONITOR_CLASS" "JVM内存监控"
            else
                stop_process "$ANIMATION_CLASS" "算法动画程序"
                stop_process "$MONITOR_CLASS" "JVM进程监控"
                stop_process "$MEMORY_MONITOR_CLASS" "JVM内存监控"
                
                # 检查JMX端口
                if check_port $JMX_PORT; then
                    echo -e "${YELLOW}⚠️  端口 $JMX_PORT 仍被占用，尝试释放...${NC}"
                    local jmx_pid=$(lsof -Pi :$JMX_PORT -sTCP:LISTEN -t)
                    if [ ! -z "$jmx_pid" ]; then
                        kill -9 $jmx_pid 2>/dev/null
                        echo -e "${GREEN}✅ 端口 $JMX_PORT 已释放${NC}"
                    fi
                fi
                
                echo ""
                echo -e "${GREEN}🎉 所有服务已停止${NC}"
            fi
            ;;
        "restart")
            echo -e "${CYAN}🔄 重启算法动画系统${NC}"
            echo -e "${CYAN}======================${NC}"
            
            # 先停止
            $0 stop $@
            sleep 2
            # 再启动
            $0 start $@
            ;;
        "status")
            show_status
            ;;
        "logs")
            show_logs "$target" "$follow"
            ;;
        "clean")
            clean_logs
            ;;
        "compile")
            compile_project
            ;;
        "verify")
            verify_jmx
            ;;
        "help"|"--help"|"-h"|"")
            show_help
            ;;
        *)
            echo -e "${RED}❌ 未知命令: $command${NC}"
            show_help
            exit 1
            ;;
    esac
}

# 运行主函数
main "$@"