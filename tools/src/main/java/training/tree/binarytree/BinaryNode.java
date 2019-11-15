package training.tree.binarytree;

/**
 * @ClassName BinaryNode
 * @Description 二叉树节点
 * @Author zhanghui1
 * @Date 2019/11/14 17:43
 **/
public class BinaryNode<T> {
    T element;
    BinaryNode<T> left;
    BinaryNode<T> right;

    BinaryNode(T otherElement){
        this(otherElement,null,null);
    }
    BinaryNode(T otherElement,BinaryNode<T> otherLeft,BinaryNode<T> otherRight){
        this.element = otherElement;
        this.left = otherLeft;
        this.right = otherRight;
    }
}
