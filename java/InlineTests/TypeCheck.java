import java.util.concurrent.ThreadLocalRandom;

public class TypeCheck {
    public static void main(String[] args) {
        TypeCheck test = new TypeCheck();
        test.run();
	    System.out.println("Finished");
    }

    public void run() {
        for (int i = 0; i < 2_000_000; i++) {
            handleRequest();
        }
    }

    public void handleRequest() {
        int requestType = 1 + ThreadLocalRandom.current().nextInt(3);
        switch(requestType) {
        case 1:
            handleImpl1();
            break;
        case 2:
            handleImpl2();
            break;
        case 3:
            handleImpl3();
            break;
        default:
            throw new RuntimeException("Unsupported type");
        }
    }

    public int handleImpl1() {
        Handler to = new Handler(new InnerImpl1());
        return to.doIt();
    }

    public int handleImpl2() {
        Handler to = new Handler(new InnerImpl2());
        return to.doIt();
    }

    public int handleImpl3() {
        Handler to = new Handler(new InnerImpl3());
        return to.doIt();
    }
}


