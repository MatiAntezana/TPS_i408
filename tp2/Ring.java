package anillo;
import java.util.Stack;

public class Ring {
    private RingElement current;
    private final Stack <RingElement> stackElementRings = new Stack<>();

    public Ring(){
        current = new EmptyRingElement();
        stackElementRings.push(current);
    }

    public Ring next() {
        stackElementRings.peek().checkValidAction();
        current = current.getPrevious();
        return this;
    }

    public Object current() {
        stackElementRings.peek().checkValidAction();
        return current.getData();
    }

    public Ring add( Object input ) {
        LoadedRingElement newElementRing = new LoadedRingElement(input);
        current = current.addNewElementRing(newElementRing);
        stackElementRings.push(current);
        return this;
    }

    public Ring remove() {
        stackElementRings.peek().checkValidAction();
        current = current.removeNewElementRing();
        stackElementRings.pop();
        current = stackElementRings.peek().decideCurrent(current);
        return this;
    }
}