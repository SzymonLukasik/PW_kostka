import concurrentcube.sequentialcube.SequentialCube;
import org.junit.Test;
import org.junit.Assert;

import java.util.function.Supplier;

public class SequentialTests {
    @Test
    public void testEasyRotations() {
        int size = 2;
        int turns = 1;
        Supplier<SequentialCube> cube_supplier = () -> new SequentialCube(size);
        for (int side = 0; side < 6; side++) {
            for (int layer = 0; layer < size; layer++) {
                System.out.println("ROTATING: SIDE " + side + " LAYER " + layer);
                System.out.println("BEFORE");
                SequentialCube cube = cube_supplier.get();
                cube.showSequential();
                for (int i = 1; i <= turns; i++) {
                    System.out.println(i * 90 + " degrees");
                    cube.rotateSequential(side, layer);
                    cube.showSequential();
                }
            }
        }
    }

    @Test
    public void testComplexRotations() {
        int size = 3;
        SequentialCube cube = new SequentialCube(size);
        cube.rotateSequential(2, 0);
        cube.showSequential();
        cube.rotateSequential(3, 0);
        cube.showSequential();
        cube.rotateSequential(4, 1);
        String EXPECTED = "002033112125105105223225225040353353144044044334111554";
        Assert.assertEquals(EXPECTED, cube.showSequential());

    }
}

