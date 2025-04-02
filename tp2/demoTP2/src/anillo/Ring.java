package anillo;

import java.util.ArrayList;
import java.util.List;

public class Ring {
    private final List<Object> ringData = new ArrayList<>();
    private int currentPos = -1;

    public Ring next() {
        currentPos = (currentPos - 1 + ringData.size()) % ringData.size();
        return this;
    }


    public Object current() {
        return ringData.get(currentPos);
    }


    public Ring add(Object input){
        ringData.add(++currentPos % (ringData.size() + 1), input);
        return this;
    }


    public Ring remove() {
        ringData.remove(currentPos);
        return ringData.isEmpty() ? this : next();
    }


}