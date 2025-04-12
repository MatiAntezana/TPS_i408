package anillo;
import java.util.Stack;

public class Ring {
    private RingElement current;
    private final Stack <RingElement> stackTypeElementRing = new Stack<>();

    public Ring(){
        current = new EmptyRingElement();
        stackTypeElementRing.push(current);
    }

    public Ring next() {
        stackTypeElementRing.peek().CheckValidAccion();
        RingElement headStack = stackTypeElementRing.pop();
        current = stackTypeElementRing.peek().decideCase(current);
        current = current.getNext();
        stackTypeElementRing.push(headStack);
        return this;
    }

    public Object current() {
        stackTypeElementRing.peek().CheckValidAccion();
        return current.getData();
    }

    public Ring add( Object input ) {
        RegularRingEement newElementRing = new RegularRingEement(input);
        current = current.addNewElementRing(newElementRing);
        stackTypeElementRing.push(current);
        return this;
    }

    public Ring remove() {
        stackTypeElementRing.peek().CheckValidAccion();
        current = current.getPreviousElementRing();
        stackTypeElementRing.pop();
        current = stackTypeElementRing.peek().decideCurrent(current);
        return this;
    }

}


//public class Ring {
//    private final List<Object> ringData = new ArrayList<>();
//    private int currentPos = -1;
//
//    public Ring next() {
//        currentPos = (currentPos - 1 + ringData.size()) % ringData.size();
//        return this;
//    }
//
//
//    public Object current() {
//        return ringData.get(currentPos);
//    }
//
//
//    public Ring add(Object input){
//        ringData.add(++currentPos % (ringData.size() + 1), input);
//        return this;
//    }
//
//
//    public Ring remove() {
//        ringData.remove(currentPos);
//        return ringData.isEmpty() ? this : next();
//    }
//
//
//}