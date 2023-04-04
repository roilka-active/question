package training.tools.utils;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class DecisionUtils {
    public static void main(String[] args) {
        String ss = "180.165.237.160";
        ArrayList<String> strings = Lists.newArrayList("139.226.12.65","180.165.237.160"

        );
        strings.stream().forEach(sss -> {
            int i = Math.abs(sss.hashCode()) % 100;
            System.out.println(i);
        });

        /*int i = Math.abs(ss.hashCode()) % 100;
        System.out.println(i);*/
    }
}
