package huayin;

public class StringDemo {
    public static void main(String[] args) {

        String str = "abc-deab";
//        System.out.println(str.substring(0, str.indexOf("-")));
        System.out.println(str.replaceFirst("ab","XY"));
    }
}
