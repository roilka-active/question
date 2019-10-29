package training.tools.javase.iter;/**
 * Package: training.tools.javase.iter
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/10/11 0:19
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import java.io.Serializable;

/**
 * @author zhanghui
 * @description
 * @date 2019/10/11
 */
public class ArrayAlg {

    public static <T> T getMiddle(T... a){
        return a[a.length / 2];
    }
    public static <T extends Comparable> T min(T[] a){
        if (a == null || a.length == 0){
            return null;
        }
        T smallest = a[0];
        for (int i = 0; i < a.length; i++) {
            if (smallest.compareTo(a[i]) > 0){
                smallest = a[i];
            }
        }
        return smallest;
    }
    public static class Pair {

        private double first;
        private double second;

        public Pair(double f, double s) {
            first = f;
            second = s;
        }

        public double getFirst() {
            return first;
        }

        public Pair setFirst(double first) {
            this.first = first;
            return this;
        }

        public double getSecond() {
            return second;
        }

        public Pair setSecond(double second) {
            this.second = second;
            return this;
        }
    }

    public static Pair minmax(double[] values) {
        double min = Double.MIN_VALUE;
        double max = Double.MAX_VALUE;
        for (double v : values) {
            if (min > v) {
                min = v;
            }
            if (max < v) {
                max = v;
            }
        }
        return new Pair(min, max);
    }
}
