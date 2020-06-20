package training.tools.javase.collection;

import java.sql.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    public void testTreeMap() {
        TreeMap<Integer, Integer> a = new TreeMap<>();
    }

    public static void main(String[] args) {
        String a = "124324321543254364345654234547654787676547655476765486548765486548765487677676547346543256432543843758432937218957348927589473892589437689257894378925476548654765465876575676547654766543654375437654367543765345";
        String b = "543324356443577657654768654765454686754876554876548765476765476547657765476547654893432643264535435684739567843678526437826859657522";
        StringBuffer aB = new StringBuffer();
        StringBuffer bB = new StringBuffer();
        for (int i = 0; i < 1000000; i++) {
            aB.append((int)(Math.random()*10));
            bB.append((int)(Math.random()*10));
        }
        long start = System.currentTimeMillis();
        StringBuffer sb = addNum(a, b);
        /*for (char c : ss) {
            System.out.print(c);
        }*/
        System.out.print(sb.reverse().toString());
        long end = System.currentTimeMillis();
        System.out.println("-----");
        System.out.println("当前消耗时间：" + (end - start) + "毫秒");

        LinkedHashMap as = new LinkedHashMap();
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(1,1);
    }
    public static StringBuffer addNum(String a, String b) {
        char[] maxChars = new char[Math.max(a.length(), b.length())];
        char[] minChars = new char[Math.min(a.length(), b.length())];
        System.out.println("当前较长数字："+maxChars.length+"位");
        System.out.println("当前较短数字："+minChars.length+"位");
        if (a.length() > b.length()) {
            maxChars = a.toCharArray();
            minChars = b.toCharArray();
        } else {
            maxChars = b.toCharArray();
            minChars = a.toCharArray();
        }
        maxChars = reverse(maxChars);
        minChars = reverse(minChars);
        // 进位
        boolean carry = false;
        int temp = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < maxChars.length; i++) {

            if (i >= minChars.length) {
                temp = (int) maxChars[i] + 1 - (int) ('0');
            } else {
                temp = (int) maxChars[i] + (int) minChars[i] - 2 * (int) ('0');
            }

            if (carry) {
                temp++;
                carry = false;
            }
            if (temp >= 10) {
                temp %= 10;
                carry = true;
            }
            sb.append(temp);

        }
        if (carry){
          sb.append(1);
        }

        return sb;
    }

    public static char[] reverse(char[] arr) {
        //遍历数组
        for (int i = 0; i < arr.length / 2; i++) {
            //交换元素 因为i从0开始所以这里一定要再减去1
            char temp = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = arr[i];
            arr[i] = temp;
        }
        //返回反转后的结果
        return arr;
    }
}
