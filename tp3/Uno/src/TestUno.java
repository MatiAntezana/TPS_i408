import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestUno {
    @Test
    public void testPoolCardSize() {
        Uno juego = new Uno(25L);
        System.out.println("Tamaño de PoolCard: " + juego.PoolCard.size());
        System.out.println("Tamaño de PoolCard: " + IntStream.range(0,9));
        System.out.println("Tamaño de PoolCard: " + juego.WellCard);

        juego.addPlayer("Matias", "Guillermina");
    }
}
