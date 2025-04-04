package anillo;

import java.util.ArrayList;
import java.util.List;


public class Ring {
    private Link current; // puntero al nodo actual dentro del anillo.

    public Ring next() {
        current = current.getPrevious();
        return this;
    }


    public Object current() {
        emptyRingException();
        return current.getData();
    }


    private void emptyRingException() {
        if (current == null) throw new RuntimeException("Empty ring");
    }


    public Ring add( Object input ) {
        Link newLink = new Link( input ); // se crea un nuevo eslabon link con el valor proporcionado.

        if (current != null) {
            current.add(newLink);
        }
        current = newLink;
        return this;
    }


    public Ring remove() {
        emptyRingException();
        if (current.getPrevious() == current) {
            current = null;
        } else {
            Link toRemove = current;
            current = current.getPrevious();
            toRemove.remove();
        }
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