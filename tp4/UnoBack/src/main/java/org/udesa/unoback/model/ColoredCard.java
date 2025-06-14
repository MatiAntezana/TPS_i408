package org.udesa.unoback.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class ColoredCard extends Card {
    protected String color = "";
    protected List<String> validColors = new ArrayList<>(Arrays.asList("Red", "Blue", "Green", "Yellow"));
    public static String NotValidColor = "Not a valid color";

    public ColoredCard( String aColor ) {
        if(!validColors.contains(aColor)) { throw new IllegalArgumentException(NotValidColor); }
        this.color = aColor;
    }
    public boolean acceptsOnTop( Card aCard ) { return  aCard.yourColorIs( color() );   }
    public boolean yourColorIs( String aColor ) { return color.equals( aColor );  }
    public String color() { return color;  }

    public boolean equals( Object o ) { return super.equals( o ) && color.equals( ColoredCard.class.cast( o ).color );  }
    public int hashCode() {             return Objects.hash( color );}

    public JsonCard asJson() { return new JsonCard( color, null, getClass().getSimpleName(), unoShouted() ); }
}
