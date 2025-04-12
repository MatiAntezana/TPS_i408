package anillo;

import java.io.ObjectStreamException;

public abstract class RingElement {
    protected RingElement previous, next;
    protected Object data;

    public abstract Object getData();
    public abstract RingElement getPreviousElementRing();
    public abstract void CheckValidAccion();
    public abstract RingElement addNewElementRing(RingElement newElementRing);
    public abstract RingElement decideCurrent(RingElement current);
    public abstract RingElement getNext();
    public abstract RingElement decideCase(RingElement current);
}


class EmptyRingElement extends RingElement {

    public EmptyRingElement() {
        this.data = null;
        this.previous = this.next = this;
    }

    public RingElement getNext() {
        throw new RuntimeException("Empty ring");
    }

    public RingElement decideCase(RingElement ringElement) {
        ringElement.previous = ringElement;
        return ringElement;
    }

    public Object getData() {
        throw new RuntimeException("Empty ring");
    }

    public RingElement getPreviousElementRing() {
        throw new RuntimeException("Empty ring");
    }

    public void CheckValidAccion() {
        throw new RuntimeException("Empty ring");
    }

    public RingElement addNewElementRing(RingElement newElementRing) {
        newElementRing.next = newElementRing;
        newElementRing.previous = newElementRing;
        return newElementRing;
    }

    public RingElement decideCurrent(RingElement current) {
        return new EmptyRingElement();
    }
}


class RegularRingEement extends RingElement {

    public RegularRingEement(Object data) {
        this.data = data;
        this.previous = null;
        this.next = null;
    }
    public RingElement getNext() {
        return previous;
    }

    public RingElement decideCase(RingElement current) {
        return current;
    }

    public Object getData() {
        return data;
    }

    public RingElement getPreviousElementRing() {
        this.previous.next = this.next;
        return this.previous;
    }

    public void CheckValidAccion() {
    }

    public RingElement addNewElementRing(RingElement newElementRing) {
        newElementRing.next = this.next;
        newElementRing.previous = this;
        this.next.previous = newElementRing;
        this.next = newElementRing;
        return newElementRing;
    }

    public RingElement decideCurrent(RingElement current) {
        return current;
    }
}
/*
public class Link {
    private final Object data; //almacena el valor del eslabón. Es de tipo object para que pueda contener cualquier tipo de dato.
    private Link previous, next; //referencia al nodo anterior y siguiente en el anillo.


    public Link( Object data) { // constructor
        this.data = data;
        this.previous = this.next = this;
    }


    public void insertAfter(Link newLink) {
        newLink.next = this.next;
        newLink.previous = this;
        this.next.previous = newLink;
        this.next = newLink;

    }


    public void remove() {
        this.previous.next = this.next;
        this.next.previous = this.previous;
    }


        public Object getData() {
        return data;
    }


    public Link getPrevious() {
        return previous;
    }
}


/*
public class Link {
    private final Object data; //almacena el valor del eslabón. Es de tipo object para que pueda contener cualquier tipo de dato.
    private Link previous, next; //referencia al nodo anterior y siguiente en el anillo.


    public Link( Object data) { // constructor
        this.data = data;
        this.previous = this.next = this;
    }


    public void insertAfter(Link newLink) {
        newLink.next = this.next;
        newLink.previous = this;
        this.next.previous = newLink;
        this.next = newLink;

    }


    public void remove() {
        this.previous.next = this.next;
        this.next.previous = this.previous;
    }


        public Object getData() {
        return data;
    }


    public Link getPrevious() {
        return previous;
    }
}

/*
public class Link {
    private final Object data; //almacena el valor del eslabón. Es de tipo object para que pueda contener cualquier tipo de dato.
    private Link previous, next; //referencia al nodo anterior y siguiente en el anillo.


    public Link( Object data) { // constructor
        this.data = data;
        this.previous = this.next = this;
    }


    public void insertAfter(Link newLink) {
        newLink.next = this.next;
        newLink.previous = this;
        this.next.previous = newLink;
        this.next = newLink;

    }


    public void remove() {
        this.previous.next = this.next;
        this.next.previous = this.previous;
    }


        public Object getData() {
        return data;
    }


    public Link getPrevious() {
        return previous;
    }
}
 */