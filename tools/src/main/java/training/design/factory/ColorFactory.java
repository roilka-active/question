package training.design.factory;

/**
 * @program: parent
 * @description: 动物工厂
 * @author: zhanghui
 * @date: 2023-04-04 10:21
 **/
public class ColorFactory {
    public Color create(String type){
        if ("green".equals(type)){
            return new Green();
        }
        return null;
    }
}
