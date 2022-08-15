package basic;

import java.security.Provider;
import java.security.Security;
import java.util.Map;

public class ShowProviders {
    public static void main(String[] args) {
        for (Provider provider : Security.getProviders()) {
            System.out.println("##########################\n" + provider);
            for (Map.Entry<Object, Object> entry : provider.entrySet()) {
                System.out.println("\t" + entry.getKey());
//                System.out.println("\t" + entry.getKey() + " : " + entry.getValue());
            }
        }
    }
}
