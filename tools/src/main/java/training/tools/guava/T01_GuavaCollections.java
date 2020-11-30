package training.tools.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class T01_GuavaCollections {

    public static void main(String[] args) {
        ArrayList<Object> arrayList = Lists.newArrayListWithCapacity(100 * 1000);


        List<List<Object>> partition = Lists.partition(arrayList, 1000);
        ArrayList<String> strings = Lists.newArrayList("a", "b", null, "c");
        String join = Joiner.on(",").useForNull("--").join(strings);
        System.out.println(join);
    }

}
