package training.tools.guava;/**
 * Package: training.tools.guava
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/4/5 0:22
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * @author zhanghui
 * @description 字符串辅助操作
 * @date 2020/4/5
 */
public class StringOpera {

    private static final Joiner joiner = Joiner.on(",").skipNulls();

    private static final Splitter SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();
}
