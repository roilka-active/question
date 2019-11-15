package training.lintcode.bit;

import java.util.HashMap;
import java.util.Map;

public class BitTest {
    public static void main(String args[])
    {
        String s = "s";
        int _s = Integer.parseInt(s);
        int a=20;
        int b=5;
        System.out.println(myAdd(a,b));
        System.out.println(mySub(a,-b));//20-5会溢出得到错误结果
        System.out.println(myMuti(a,b));
        System.out.println(myDiv(a,b));
        System.out.println(myDiv(a,-b));
    }

    public  static  String myAdd(String a,String b){
        int a_ = a.length();
        int b_ = b.length();
        boolean carry = false;
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < Math.min(a_, b_); i++) {
          if (a.charAt(i) == b.charAt(i)){
              carry = a.charAt(i) =='1';result.append(a.charAt(i));
          }else {

              result.append(Math.max(a.charAt(i), b.charAt(i)));
          }
        }
       return result.toString();
    }
    public static int myAdd(int a,int b)
    {
        int sum;
        int carry;
        do
        {
            sum=a^b;
            carry=(a&b)<<1;//进位需要左移一位
            a=sum;
            b=carry;
        }while(carry!=0);
        return sum;
    }

    public static int mySub(int a,int b)
    {
        if (a > b){
            return a ^ b;
        }else {
           return b ^ a;
        }

    }

    public static long myMuti(int a,int b)
    {
        boolean flag=(b<0);
        if(flag) b=-b;
        Map<Integer,Integer> map=new HashMap<Integer,Integer>();
        for(int i=0;i<32;i++)
        {
            map.put(1<<i, i);
        }
        int sum=0;
        while(b>0)
        {
            int last=b&(~b+1); //取得最后一个1
            int count=map.get(last);//取得相关的移位
            sum+=a<<count;
            b=b&(b-1);
        }
        if(flag) sum=-sum;
        return sum;
    }

    public static int myDiv(int a,int b)
    {
        boolean flag=(a<0)^(b<0);
        if(a<0) a=-a;
        if(b<0) b=-b;
        if(a<b) return 0;
        int msb=0;
        while((b<<msb)<a)
        {
            msb++;
        }
        int q=0;
        for(int i=msb;i>=0;i--)
        {
            if((b<<i)>a) continue;
            q|=(1<<i);
            a-=(b<<i);
        }
        if(flag) return -q;
        return q;
    }

}
