package training.tools.utils;

public class DataUtils {

    /*
      设 { begin ，finish } 集合为 A ，{ start ， end} 集合为 B，分以下八种情况
      集合A、B不分先后
      只要存在交集，立刻返回
  */
    public static <T extends Comparable> boolean intersectionData(T start, T end, T begin, T finish) {
        boolean result = false;

        // （1）集合 B 为 ∞
        if (start == null && end == null) {
            return true;
        }

        // （2）集合 A 为 ∞
        if (begin == null && finish == null) {
            return true;
        }

        //  (3) 集合A、B 同时向下 ∞  或者 集合A、B 同时向上∞
        if ((begin == null && start == null) || (finish == null && end == null)){
            return true;
        }

        // （4）集合 A 为向上 ∞ 或者 集合B 为向下  ∞
        if ((start == null && end != null) || (begin != null && finish == null)) {
            result = end.compareTo(begin) >= 0;
            return result;
        }

        // （5）集合 A 为向下 ∞ 或者 集合B 为向上  ∞
        if ((start != null && end == null) || (begin == null && finish != null)) {
            result = finish.compareTo(start) >= 0;
            return result;
        }

        // （6） A ∈ B
        result = begin.compareTo(start) >= 0 && finish.compareTo(end) <= 0;
        if (result){
            return result;
        }

        // （7） A ∩ B
        result = begin.compareTo(start) > 0 && begin.compareTo(end) <= 0;
        if (result){
            return result;
        }
        result = finish.compareTo(end) < 0 && finish.compareTo(start) >= 0;
        if (result){
            return result;
        }

        // （8） B ∈ A
        result = begin.compareTo(start) < 0 && finish.compareTo(end) > 0;

        return result;
    }

}
