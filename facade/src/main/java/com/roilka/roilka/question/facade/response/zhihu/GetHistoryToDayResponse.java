package com.roilka.roilka.question.facade.response.zhihu;

import lombok.Data;

import java.util.List;

/**
 * @ClassName GetHistoryToDayResponse
 * @Description 历史的今天返回对象
 * @Author zhanghui1
 * @Date 2019/12/26 19:24
 **/

@Data
public class GetHistoryToDayResponse {


    /**
     * total : 20
     * result : [{"year":1982,"month":12,"day":15,"title":"首届茅盾文学奖授奖仪式举行","type":2},{"year":1980,"month":12,"day":15,"title":"中国体育科学学会成立","type":2},{"year":1978,"month":12,"day":15,"title":"《天安门诗抄》出版","type":2},{"year":1966,"month":12,"day":15,"title":"美国动画制作家迪斯尼逝世","type":2},{"year":1965,"month":12,"day":15,"title":"美国两艘飞船在太空轨道相会","type":2},{"year":1961,"month":12,"day":15,"title":"纳粹分子艾希曼在以色列被判死刑 ","type":2},{"year":1949,"month":12,"day":15,"title":"中国人民外交学会成立 ","type":2},{"year":1946,"month":12,"day":15,"title":"中国军事法庭设立","type":2},{"year":1944,"month":12,"day":15,"title":"美国建立五星上将军衔","type":2},{"year":1939,"month":12,"day":15,"title":"电影《乱世佳人》举行首映式","type":2},{"year":1931,"month":12,"day":15,"title":"蒋介石第二次下野","type":2},{"year":1931,"month":12,"day":15,"title":"黄显声组建\u201c辽宁抗日义勇军\u201d","type":2},{"year":1912,"month":12,"day":15,"title":"北京政府颁布《戒严法》","type":2},{"year":1904,"month":12,"day":15,"title":"上海发生亚齐夫事件","type":2},{"year":1859,"month":12,"day":15,"title":"语言学家、世界语创造者拉扎鲁斯·柴门霍夫出生","type":2},{"year":1852,"month":12,"day":15,"title":"天然放射性的发现者亨利·贝克勒尔出生","type":2},{"year":1832,"month":12,"day":15,"title":"法国土木工程师艾菲尔出生","type":2},{"year":1161,"month":12,"day":15,"title":"金朝第四代皇帝金海陵王辞世","type":2},{"year":1025,"month":12,"day":15,"title":"拜占庭皇帝巴西尔二世逝世","type":2},{"year":37,"month":12,"day":15,"title":"古罗马暴君末代皇帝尼禄出生","type":2}]
     * error_code : 0
     * reason : Succes
     */

    private int total;
    private int error_code;
    private String reason;
    private List<ResultBean> result;



    @Data
    public static class ResultBean {
        /**
         * year : 1982
         * month : 12
         * day : 15
         * title : 首届茅盾文学奖授奖仪式举行
         * type : 2
         */

        private int year;
        private int month;
        private int day;
        private String title;
        private int type;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "year=" + year +
                    ", month=" + month +
                    ", day=" + day +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}
