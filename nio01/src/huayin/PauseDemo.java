package huayin;

public class PauseDemo {
    public static void main(String[] args) {
        slowMethod();
//        sleepMethod();
//        fastMethod();
    }

    public static void slowMethod(){
        for(int i=0;i<1000000;i++) {
            System.out.print(i);
            System.err.println("\t++++++");
        }
    }

    public static void sleepMethod(){

        System.out.println("start.....");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end.....");
    }

    public static void fastMethod(){
        System.err.println("I am fast ###################################");
    }
}
