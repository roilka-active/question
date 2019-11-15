package training.tools.javase.collection;

import training.tools.utils.SysTools;

import java.util.Stack;

public class StackTest {
    public static void main(String[] args) {
        Stack stack = new Stack();
        if (stack.empty()){
            stack.push(1);
        }
        if (!stack.empty()){
            //返回栈顶元素
            SysTools._out(stack.peek(),"last element");
            //弹出并返回栈顶元素
            SysTools._out(stack.pop(),"last element");
            ;
        }
    }
}
