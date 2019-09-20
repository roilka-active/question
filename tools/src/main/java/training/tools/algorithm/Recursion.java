package training.tools.algorithm;

import training.tools.utils.StringUtils;

import java.util.Date;

public class Recursion {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //int result = power(2, 24);
        String[] arr = {"as","fds","fds","fdsf"};
        arr = reverseArray(arr, 0, arr.length-1);
        long endTime = System.currentTimeMillis();
        long subTime = endTime - startTime;
        for (int i = 0; i < arr.length; i++) {
            System.out.println("结果是" + arr[i] + "。消耗时间为" + subTime + "毫秒");
        }

        Object a = null;
        String b = null;
        StringUtils.isNotBlank(b);
        Employee harry = new Employee();
        Date d = harry.getHireDay();
        d.setTime(d.getTime() - (long)213124324L);

    }

    /**
     * 递归求幂函数
     *
     * @param n
     * @param r
     * @return
     */
    public static int power(int n, int r) {
        if (r == 0) {
            return 1;
        }
        return n * power(n, r - 1);
    }

    public static <T extends Object> T[] reverseArray(T[] arr, int lo, int hi) {
        if (lo < hi) {
           swap(arr[lo],arr[hi]);
           return reverseArray(arr, lo +1, hi-1);
        }else {
            return arr;
        }
    }

    private static <T> void swap(T a, T b) {
        T c = b;
        b = a;
        b = c;
    }
}
