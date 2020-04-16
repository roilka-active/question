package com.roilka.roilka.question.common.concurrent.entity;

/**
 * @ClassName HelloWorld
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/13 18:51
 **/
public class HelloWorld implements Hello {

    @Override
    public void morning(String name) {
        System.out.println("Good morning " + name);
    }

    @Override
    public void bye(String a, String b, String... arr) {
        System.out.println("a:" + a + "\n b:" + b);
        if (arr.length == 0)
            return;
        for (String ar : arr) {
            System.out.println("数组是：" + ar);
        }
    }
    private int n;
    private String desc;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
