package training.tools.javase.collection.map;
/**
 * Package: training.tools.javase.collection.map
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/16 23:26
 * <p>
 * Modified By:
 */

import com.google.common.collect.HashBiMap;

import java.util.HashMap;

/**
 * @author zhanghui
 * @description hashmap 测试
 * @date 2020/6/16
 */
public class T01_HashMap {

    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put(1,1);

        new HashMap<>(17);
        int n = 1254543 - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(n);
    }

}
