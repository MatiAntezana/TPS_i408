package anillo;
import java.util.Stack;

public class Ring {
    private RingElement current;
    private final Stack <RingElement> stackElementRing = new Stack<>();

    public Ring(){
        current = new EmptyRingElement();
        stackElementRing.push(current);
    }

    public Ring next() {
        current = current.getPrevious();
        return this;
    }

    public Object current() {
        return current.getData();
    }

    public Ring add( Object input ) {
        LoadedRingElement newElementRing = new LoadedRingElement(input);
        current = current.addNewElementRing(newElementRing);
        stackElementRing.push(current);
        return this;
    }

    public Ring remove() {
        current = current.removeElementRing();
        stackElementRing.pop();
        current = stackElementRing.peek().decideCurrent(current);
        return this;
    }
}