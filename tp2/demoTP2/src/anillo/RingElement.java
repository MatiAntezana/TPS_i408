package anillo;
public abstract class Link {
    protected Link previous, next;
    protected Object data;

    public abstract Object getData();
    public abstract Link getPrevious();
    public abstract void action();
    public abstract void addLink(Link newLink);
    public abstract Link decideCurrent(Link current);
    public Link getNext(){return previous;};
    public abstract Link decideCase(Link current);
}


class EmptyRingElement extends Link {

    public EmptyRingElement() {
        this.data = null;
        this.previous = this.next = this;
    }

    public Link decideCase(Link current) {
        current.previous = current;
        return current;
    }

    public Object getData() {
        throw new RuntimeException("Empty ring");
    }

    public Link getPrevious() { throw new RuntimeException("Empty ring"); }

    public void action() {
        throw new RuntimeException("Empty ring");
    }

    public void addLink(Link newLink) {
        newLink.next = newLink;
        newLink.previous = newLink;
    }

    public Link decideCurrent(Link current) {
        return new EmptyRingElement();
    }
}


class RegularRingEement extends Link {

    public RegularRingEement(Object data) {
        this.data = data;
        this.previous = null;
        this.next = null;
    }

    public Link decideCase(Link current) {
        return current;
    }

    public Object getData() {
        return data;
    }

    public Link getPrevious() {
        this.previous.next = this.next;
        return this.previous;
    }

    public void action() {
    }

    public void addLink(Link newLink) {
        newLink.next = this.next;
        newLink.previous = this;
        this.next.previous = newLink;
        this.next = newLink;
    }

    public Link decideCurrent(Link current) {
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