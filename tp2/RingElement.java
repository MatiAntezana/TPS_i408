package anillo;

public abstract class RingElement {
    protected RingElement previous, next;
    protected Object data;

    public abstract Object getData();
    public abstract RingElement removeElementRing();
    public abstract RingElement addNewElementRing(RingElement newElementRing);
    public abstract RingElement decideCurrent(RingElement current);
    public abstract RingElement getPrevious();
}


class EmptyRingElement extends RingElement {
    private static String CurrentRingElementInEmptyRing = "Error. The ring element cannot be accessed because the ring is empty.";
    private static String GetPreviousRingElementInEmptyRing = "Error. The previous ring element cannot be accessed because the ring is empty.";
    private static String RemoveRingElementInEmptyRing = "Error. Cannot remove a ring element in an empty ring.";

    public EmptyRingElement() {
        this.data = null;
        this.previous = this.next = this;
    }

    public Object getData() {throw new RuntimeException(CurrentRingElementInEmptyRing);}

    public RingElement removeElementRing() {throw new RuntimeException(RemoveRingElementInEmptyRing);}

    public RingElement addNewElementRing(RingElement newElementRing) {
        newElementRing.next = newElementRing;
        newElementRing.previous = newElementRing;
        return newElementRing;
    }

    public RingElement decideCurrent(RingElement current) {return new EmptyRingElement();}

    public RingElement getPrevious() {throw new RuntimeException(GetPreviousRingElementInEmptyRing);}

}


class LoadedRingElement extends RingElement {

    public LoadedRingElement(Object data) {
        this.data = data;
        this.previous = null;
        this.next = null;
    }

    public Object getData() {return data;}

    public RingElement removeElementRing() {
        this.previous.next = this.next;
        this.next.previous = this.previous;
        RingElement temp = this.previous;
        this.next = null;
        this.previous = null;
        return temp;
    }

    public RingElement addNewElementRing(RingElement newElementRing) {
        newElementRing.next = this.next;
        newElementRing.previous = this;
        this.next.previous = newElementRing;
        this.next = newElementRing;
        return newElementRing;
    }

    public RingElement decideCurrent(RingElement current) {return current;}

    public RingElement getPrevious() {return previous;}

}