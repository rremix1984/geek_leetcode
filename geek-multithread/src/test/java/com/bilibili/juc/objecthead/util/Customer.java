package com.bilibili.juc.objecthead.util;

public class Customer {//只有一个对象头的实例对象，16字节（忽略压缩指针的影响）+4字节+1字节=21字节----》对其填充，24字节
    //1 第一种情况，只有对象头，没有其它任何实例数据

    //2 第二种情况，int + boolean，默认满足对其填充，24 bytes
    /*int id;
    boolean flag = false;
    boolean flag2 = false;*/
}