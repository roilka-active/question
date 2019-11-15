package training.tree.binarytree;

import java.util.Comparator;

/**
 * @ClassName BinarySearchTree
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/14 17:42
 **/
public class BinarySearchTree<T> {

    private BinaryNode<T> root;

    private Comparator<? super T> cmp;

    public BinarySearchTree(){
        this(null);
    }
    public BinarySearchTree(Comparator<? super T> c){
        root = null;
        cmp = c;
    }
    private int myCompare(T lhs,T rhs){
        if (cmp != null){
            return cmp.compare(lhs, rhs);
        }else {
            return ((Comparable)lhs).compareTo(rhs);
        }
    }
    private boolean contains(T x,BinaryNode<T> t){
        if (t == null){
            return false;
        }
        int compareResult = myCompare(x, t.element);

        if (compareResult < 0){
            return contains(x, t.left);
        }else if (compareResult > 0){
            return contains(x, t.right);
        }else {
            return true;
        }
    }

    private T findMin(BinaryNode<T> t){
        if (t == null){
            return null;
        }else if (t.left ==null){
            return t.element;
        }
        return findMin(t.left);
    }

    private T findMax(BinaryNode<T> t){
        if (t == null){
            return null;
        }else if (t.right ==null){
            return t.element;
        }
        return findMin(t.right);
    }

    private BinaryNode<T> insert(T x,BinaryNode<T> t){
        if (t == null){
            return new BinaryNode<T>(x,null,null);
        }
        int compareResult = myCompare(x, t.element);
        if (compareResult > 0){
            t.right = insert(x, t.right);
        }else if (compareResult < 0){
            t.left =insert(x, t.left);
        }
        return t;
    }
}
