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

/**
 * @author zhanghui
 * @description
 * @date 2019/10/11
 */
public class ArrayAlg {

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
