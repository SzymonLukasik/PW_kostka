import concurrentcube.Color;
import concurrentcube.Cube;
import org.junit.Test;

import concurrentcube.Face;

public class BasicTests {
    @Test
    public void test_constructor() {
        Cube cube = new Cube(3, (x, y) -> {}, (x,  y) -> {}, () -> {}, () -> {});
        try  {
            System.out.println(cube.show());
            cube.rotate(2, 0);
            System.out.println(cube.show());
        }
        catch (InterruptedException ignored) {
        }
    }
}

