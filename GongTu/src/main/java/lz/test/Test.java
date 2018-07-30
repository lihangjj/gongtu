package lz.test;


public class Test<K> {


    public static void main(String[] args) throws Exception {
        System.err.println(System.currentTimeMillis());
        long x=0;
        while (x<10000000000L){
            String condition;
            String s = getCondition("", "=", "");
            if ("".equals(s)) {
                condition = " dflag=0";
            } else {
                condition = s + " AND dflag=0";
            }
            x++;
        }
        System.err.println(System.currentTimeMillis());


    }


    private static String getCondition(String parameterName, String condition, String parameterValue) {
        if (parameterName == null || "".equals(parameterName) || parameterValue == null || "".equals(parameterValue)) {
            return "";
        } else {
            if (condition == null || "".equals(condition)) {
                condition = "=";
            }
            return parameterName + condition +"'"+ parameterValue+"'";
        }
    }
}
