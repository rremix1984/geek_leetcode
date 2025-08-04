package com.bilibili.juc.cas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.out;

@Getter
@ToString
class User {
    String userName;
    int age;
    
    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }
}

/**
 * @auther zzyy
 * @create 2022-02-24 14:50
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();

        User z3 = new User("z3", 22);
        User li4 = new User("li4", 28);

        atomicReference.set(z3);

        out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
        out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());

    }

}
