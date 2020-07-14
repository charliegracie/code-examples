import java.util.concurrent.ThreadLocalRandom;

public class TypeCheck2 {
    public static void main(String[] args) {
        TypeCheck2 test = new TypeCheck2();
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
        Inner inner = new InnerImpl1();
        Handler to = new Handler(inner);
        return to.doIt();
    }

    public int handleImpl2() {
        Inner inner = new InnerImpl2();
        Handler to = new Handler(inner);
        return to.doIt();
    }

    public int handleImpl3() {
        Inner inner = new InnerImpl3();
        Handler to = new Handler(inner);
        return to.doIt();
    }
}


