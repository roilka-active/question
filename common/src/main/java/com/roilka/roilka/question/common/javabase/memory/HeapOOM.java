package com.roilka.roilka.question.common.javabase.memory;

import java.util.ArrayList;

/**
 * @author zhanghui1
 * @ClassName HeapOOM
 * TODO
 * @date 2020/9/5 11:04
 **/
public class HeapOOM {
    private  static int i = 0;
    class OOMObject{
        public OOMObject() {
            System.out.println("init...."+i);
        }
    }

    public static void main(String[] args) {
        ArrayList<OOMObject> arrayList = new ArrayList<>();

        while (true){
            arrayList.add(new HeapOOM().new OOMObject());
            i++;
        }
    }
}
