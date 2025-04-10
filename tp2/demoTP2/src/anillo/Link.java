package anillo;
public abstract class Link {
    protected Link previous, next; //referencia al nodo anterior y siguiente en el anillo.
    protected Object data; //almacena el valor del eslabón. Es de tipo object para que pueda contener cualquier tipo de dato.

    public abstract Object getData();
    public abstract Link getPrevious();
    public abstract void accion();
    public abstract void Insert(Link new_eslabon);
    public abstract void Remove();
}
class NullObjectPacket extends Link {
    public NullObjectPacket() {
        this.data = null;
        this.previous = this.next = this;
    }
    public void accion() {
        throw new RuntimeException("Empty ring");
    }

    public Object getData() {
        throw new RuntimeException("Empty ring");
    }

    public Link getPrevious() {
        throw new RuntimeException("Empty ring");
    }

    public void Insert(Link new_eslabon) {
        new_eslabon.next = new_eslabon;
        new_eslabon.previous = new_eslabon;
        this.next = new_eslabon;
    }


    @Override
    public void Remove() {
        throw new RuntimeException("Empty ring");
        // El NullObject no se debería remover nunca
    }
}

class True_Eslabon extends Link {
    public True_Eslabon(Object data) {
        this.data = data;
        this.previous = this.next = this;
    }
    public void accion() {}
    public Link getPrevious() {return previous;}

    public Object getData() {return data;}

    public void Insert(Link new_eslabon) {
        new_eslabon.next = this.next;
        new_eslabon.previous = this;
        this.next.previous = new_eslabon;
        this.next = new_eslabon;
    }
    public void Remove() {
        this.previous.next = this.next;
        this.next.previous = this.previous;
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