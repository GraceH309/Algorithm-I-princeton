public class DoubleEqualsTest {
    public static void main(String[] args) {
        // 场景1：+0.0 和 -0.0
        double a = +0.0;
        double b = -0.0;
        
        // 基本类型比较：true（数值相等）
        System.out.println("a == b: " + (a == b)); 
        
        // 包装类 equals 比较：false（区分正负0）
        Double x = a;
        Double y = b;
        System.out.println("x.equals(y): " + x.equals(y)); 
    }
}
public class DoubleEqualsTest {
    public static void main(String[] args) {
        // 场景2：两个 NaN（可以是直接定义或计算得到）
        double a = Double.NaN; // 直接获取 NaN
        double b = 0.0 / 0.0;  // 计算得到 NaN（同样是 NaN）
        
        // 基本类型比较：false（NaN 不等于任何值，包括自身）
        System.out.println("a == b: " + (a == b)); 
        
        // 包装类 equals 比较：true（两个 NaN 视为相等）
        Double x = a;
        Double y = b;
        System.out.println("x.equals(y): " + x.equals(y)); 
    }
}