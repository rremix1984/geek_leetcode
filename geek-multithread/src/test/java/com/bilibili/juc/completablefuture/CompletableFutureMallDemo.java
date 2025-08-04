package com.bilibili.juc.completablefuture;

import com.bilibili.juc.completablefuture.util.NetMall;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.bilibili.juc.completablefuture.util.NetMall.netMalls;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/**
    案例说明：电商比价需求，模拟如下情况：
    <p>
        1 需求：
          1.1 同一款产品，同时搜索出同款产品在各大电商平台的售价;
          1.2 同一款产品，同时搜索出本产品在同一个电商平台下，各个入驻卖家售价是多少
        2 输出：出来结果希望是同款产品的在不同地方的价格清单列表，返回一个List<String>
         《mysql》 in jd price is 88.05
         《mysql》 in dangdang price is 86.11
         《mysql》 in taobao price is 90.43
        3 技术要求
          3.1 函数式编程
          3.2 链式编程
          3.3 Stream流式计算
    </p>
 */
@SuppressWarnings("all")
public class CompletableFutureMallDemo {

    static List<NetMall> list = netMalls("jd","dangdang","taobao","pdd","tmall");

    @Test
    public void test() {
        long startTime = currentTimeMillis();
        getPrice(list, "mysql").forEach(out::println);
        out.println("----costTime: " + (currentTimeMillis() - startTime) + " 毫秒 \n --------------------");

        long startTime2 = currentTimeMillis();
        getPriceFuture(list, "mysql").forEach(out::println);
        out.println("----costTime: " + (currentTimeMillis() - startTime2) + " 毫秒");
    }

    /**
     * step by step 一家家搜查
     * List<NetMall> ----->map------> List<String>
     */
    public List<String> getPrice(List<NetMall> list, String productName) {
        //《mysql》 in taobao price is 90.43
        return list.stream().map(netMall ->
                    format(productName + " in %s price is %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    /**
     * List<NetMall> ----->List<CompletableFuture<String>>------> List<String>
     */
    public List<String> getPriceFuture(List<NetMall> list, String productName) {
        List<CompletableFuture<String>> futures = list.stream().map(netMall ->
                    CompletableFuture.supplyAsync(() -> format(productName + " in %s price is %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName))))
                .collect(Collectors.toList());
        
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

}