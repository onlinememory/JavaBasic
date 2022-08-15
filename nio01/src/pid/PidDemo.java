package pid;

import java.util.HashMap;
import java.util.Map;

public class PidDemo {
    public static void main(String[] args) {


        HashMap g1 = new HashMap();
        g1.put("VDU", new int[]{123456, 81});
        g1.put("ATB", new int[]{223456, 82});
        g1.put("BTP", new int[]{323456, 83});
        g1.put("DCP", new int[]{423456, 84});

        Map groups = new HashMap();
        groups.put("g1", g1);
    }
}
