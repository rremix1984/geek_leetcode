/**
 * @auther zzyy
 * @create 2022-03-06 16:48
 */
package com.bilibili.juc.objecthead;

import com.bilibili.juc.objecthead.util.Customer;
import org.openjdk.jol.info.ClassLayout;
import static java.lang.System.out;

/**
 *
 */
public class JOLDemo {

    public static void main(String[] args) {
        Object o = new Object();//16 bytes
        //out.println(ClassLayout.parseInstance(o).toPrintable());
        out.println(
            ClassLayout.parseInstance(
                new Customer()
            ).toPrintable()
        );
    }

}

/*
 * 1 默认配置，启动了压缩指针，-XX:+UseCompressedClassPointers，
 * 12 + 4(对齐填充) = 一个对象16字节
 * <p>
 * 2 手动配置，关闭了压缩指-XX:-UseCompressedClassPointers，针，
 * 8 + 8 = 一个对象16字节
 */