package lz.util;

public class MathUtil {
    /**
     *
     * @param oldDouble 要四舍五入的小数
     * @param weishu 保留几位小数
     * @return
     */
    public static Double round(Double oldDouble, int weishu) {
//            Math.pow(x,y)表示x的y次幂，round函数只能四舍五入一位小数
        return Math.round(oldDouble * Math.pow(10, weishu)) / Math.pow(10, weishu);

    }
}
