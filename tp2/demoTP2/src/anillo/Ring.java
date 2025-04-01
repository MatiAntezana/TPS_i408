package anillo;

import java.util.ArrayList;
import java.util.List;

public class Ring {
    private final List<Object> ring_data = new ArrayList<>();
    private int pos_actual = -1;

    public Ring next() {
        if (ring_data.isEmpty()) {
            throw new RuntimeException();
        } else if (pos_actual - 1 < 0) {
            pos_actual = ring_data.size() - 1;
        } else {
            pos_actual = (pos_actual - 1) % ring_data.size();
        }
        return this;
    }

    public Object current() {
        if (ring_data.isEmpty()) {
            throw new RuntimeException();
        } else if (pos_actual == ring_data.size()) {
            pos_actual = pos_actual % ring_data.size();
        }
        return ring_data.get(pos_actual);
        // Puede ser que se pueda sacar el if
    }

    public Ring add(Object input) {
        if (ring_data.isEmpty()) {
            ring_data.add(input);
            pos_actual += 1;
        } else {
            pos_actual += 1;
            ring_data.add(pos_actual, input);
        }
        return this;
    }

    public Ring remove() {
        ring_data.remove(pos_actual);
        pos_actual -= 1;
        if (pos_actual < 0) {
            pos_actual = ring_data.size() - 1;
        }
        return this;
    }
}