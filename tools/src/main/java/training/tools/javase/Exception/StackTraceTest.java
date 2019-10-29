package training.tools.javase.Exception;

import training.tools.utils.SysTools;

import java.util.Scanner;

public class StackTraceTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        SysTools._out("Enter n:");
        int n = in.nextInt();
        factorial(n);

    }
    public static int factorial(int n) {
        SysTools._out(n, "第", "个");
        Throwable t = new Throwable();
        StackTraceElement[] frames = t.getStackTrace();
        for (StackTraceElement f : frames) {
            SysTools._out(f);
        }
        int r;
        if (n <= 1) {
            r = 1;
        }else {
            r = n * factorial(n - 1);
        }
        SysTools._out(r,"return  ");
        return r;
    }
}
