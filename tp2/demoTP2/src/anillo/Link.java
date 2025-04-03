package anillo;

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
