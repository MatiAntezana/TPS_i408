package anillo;
import java.util.Stack;

public class Ring {
    private Link current;
    Stack<Link> stack = new Stack<>();

    public Ring(){
        current = new NullObjectPacket();
        stack.push(current);
    }

    public Ring next() {
        stack.peek().accion();
        current = current.getPrevious();
        return this;
    }

    public Object current() {
        stack.peek().accion();
        return current.getData();
    }

    public Ring add( Object input ) {
        True_Eslabon new_eslabon = new True_Eslabon(input);
        current.Insert(new_eslabon);
        current = new_eslabon;
        stack.push(current);
        return this;
    }

    public Ring remove() {
        Link toRemove = current;
        current = current.getPrevious();
        stack.pop();
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