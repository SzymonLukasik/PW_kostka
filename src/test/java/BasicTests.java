import concurrentcube.Cube;
import org.junit.Test;

import java.util.function.Supplier;

public class BasicTests {
    @Test
    public void test_constructor() {
        Supplier<Cube> cube_supplier = () -> new Cube(3, (x, y) -> {}, (x, y) -> {}, () -> {}, () -> {});
        try  {
            for (int side = 0; side < 6; side++) {
                for (int layer = 0; layer < 3; layer++) {
                    System.out.println("ROTATING: SIDE " + side + " LAYER " + layer);
                    Cube cube = cube_supplier.get();
                    cube.show();
                    cube.rotate(side, layer);
                    cube.show();
                }
            }
        }
        catch (InterruptedException ignored) {
        }
    }
}

