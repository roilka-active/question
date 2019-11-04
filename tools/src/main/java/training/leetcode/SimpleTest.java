package training.leetcode;

import training.leetcode.entity.ListNode;
import training.tools.utils.SysTools;

import java.util.*;

public class SimpleTest {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(8);
        l1.next = new ListNode(3);
        l1.next.next = new ListNode(4);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        String s = "auesafsudsu";
        long start = System.currentTimeMillis();
        int[] a = {1,3};
        int[] b = {2};
        SysTools._out(findMedianSortedArrays(a,b), "返回结果");
        SysTools._out(System.currentTimeMillis() - start, "本次运行消耗时间", "ms");
        List<String> testList = new ArrayList<String>() {{
            add("aa");
            add("bb");
            add("cc");
        }};

        //使用toArray(T[] a)方法
        String[] array2 = testList.toArray(new String[testList.size()]);
    }

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * 给定 nums = [2, 7, 11, 15], target = 9
     * <p>
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * @param nums
     * @param target
     * @return
     * @time 5ms
     * @memory 36.8 MB
     */
    public int[] twoSum1(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            if (map.containsKey(temp) && map.get(temp) != i) {
                return new int[]{i, map.get(temp)};
            }
        }
        throw new IllegalArgumentException("No such num");
    }

    /**
     * @param nums
     * @param target
     * @return
     * @time 24 ms
     * @memory 37.7 MB
     */
    public int[] twoSum2(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {

                    return new int[]{i, j};
                }
            }

        }
        throw new IllegalArgumentException("No two num solution");
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;


    }


    public static int lengthOfLongestSubstring(String s) {
        int length = s.length();
        int result = 0;
        Map<Integer,Integer> map = new HashMap<>();
        for (int start = 0,end = 0;end < s.length();end ++){
            int temp = s.charAt(end);
            if (map.containsKey(temp)){
             start = Math.max(map.get(temp), start);
            }
            result = Math.max(result, end - start +1);
            map.put(temp, end +1);
        }
        return result;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums ;

        if (nums1.length ==0){
            nums = nums2;
        }else if (nums2.length ==0){
            nums = nums1;
        }else {
            nums = Arrays.copyOf(nums1, nums1.length + nums2.length);
            System.arraycopy(nums2, 0,nums,nums1.length,nums2.length);
        }

        if (nums.length%2==1){
            return nums[nums.length/2];
        }else {
            return (double) (nums[nums.length/2-1] + nums[nums.length/2])/2;
        }
    }
}
