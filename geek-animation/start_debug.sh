#!/bin/bash

# 设置Maven的JVM参数，开启远程调试
export MAVEN_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

echo "🐞 正在以调试模式启动应用，调试端口: 5005"

# 调用原始的启动脚本
./start.sh