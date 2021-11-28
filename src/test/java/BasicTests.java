import concurrentcube.sequentialcube.SequentialCube;
import org.junit.Test;

import java.util.function.Supplier;

public class BasicTests {
    @Test
    public void test_constructor() {
        Supplier<SequentialCube> cube_supplier = () -> new SequentialCube(3);
        try  {
            for (int side = 0; side < 6; side++) {
                for (int layer = 0; layer < 3; layer++) {
                    System.out.println("ROTATING: SIDE " + side + " LAYER " + layer);
                    SequentialCube cube = cube_supplier.get();
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

