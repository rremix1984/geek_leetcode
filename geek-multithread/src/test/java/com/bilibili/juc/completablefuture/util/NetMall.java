package com.bilibili.juc.completablefuture.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.TimeUnit.SECONDS;

@Getter
public class NetMall {

    private final String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public String getNetMallName() {
        return netMallName;
    }

    public static List<NetMall> netMalls(String... netMallName) {
        List<NetMall> res = new ArrayList<>();
        for (String s : netMallName) {
            res.add(new NetMall(s));
        }
        return res;
    }

    public double calcPrice(String productName) {
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}