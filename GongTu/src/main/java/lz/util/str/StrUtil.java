package lz.util.str;

public class StrUtil {
    public static final String SUPER_ADMIN="lh";
    public static final String idCardRegex="[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]";
    public static final String PUBLIC_ROLE="17-18";
    public static final String CELL_PHONENUMBER_REGEX="[1][3,4,5,7,8,9][0-9]\\d{8}";//手机正则
    //大写首字母
    public  static String initCap(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }

}
