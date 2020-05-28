package training.tools.algorithm.Interview;

/**
 * @ClassName GenTest
 * @Author Roilka
 * @Description 面试常见算法
 * @Date 2020/2/22
 */
public class GenTest {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        int a = test04(5);
        int s = test05(123456789);
        long endTime = System.currentTimeMillis();
        System.out.println("本次计算耗时：" + (endTime - startTime) + "ms");
        System.out.println(s);
    }

    /**
     * 题目：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     */
    public void test01() {

    }

    /**
     * 题目：判断101-200之间有多少个素数，并输出所有素数。
     * 程序分析：判断素数的方法：用一个数分别去除2到sqrt(这个数)，如果能被整除， 则表明此数不是素数，反之是素数。
     */
    public void test02() {

    }

    /**
     * 题目：打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方。
     */
    public void test03() {

    }

    /**
     * 斐波那契数列数列
     * 如：1,1,2,3,5,8。。。
     *
     * @param n
     */
    public static int test04(int n) {

        if (n <= 2) {
            return 1;
        }
        int[] str = new int[n];
        str[0] = str[1] = 1;

        for (int i = 2; i < n; i++) {
            str[i] = str[i - 1] + str[i - 2];
        }
        return str[n - 1];
    }

    /**
     * 数字翻转
     *
     * @param n
     * @return
     */
    public static int test05(int n) {
        int x = 0;
        while (n != 0) {
            x = x * 10 + n % 10;
            n /= 10;
        }

        return x;
    }

    public static int test06(int i) {
        return 0;
    }
}
