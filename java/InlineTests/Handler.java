
public class Handler {
    Inner inner;

    public Handler(Inner f) {
        inner = f;
    }

    public int doIt() {
        return inner.getValue();
    }
}
