package training.leetcode;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 *  最长公共前缀
 */
public class LeetCode_001 {
    public static void main(String[] args) {
        ArrayList<Integer> integers = Lists.newArrayList(1, 4, 2, 5, 7, 8);
        Random random = new Random();
        HashSet<Integer> objects = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            int asInt = random.ints(0, integers.size() ).limit(1).findFirst().getAsInt();
            System.out.println("index = "+asInt);
            objects.add(asInt);
            System.out.println("result = "+integers.get(asInt));
        }
        System.out.println("objects = "+objects);
        String[] strs = {"flower", "flow", "flight"};

    }

    public String longestCommonPrefix(String[] strs) {

        return null;
    }
}
