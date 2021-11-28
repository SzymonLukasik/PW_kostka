import concurrentcube.Cube;
import org.junit.Test;
import org.junit.Assert;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SequentialTests {

    private Cube getCube(int size) {
        return new Cube(
            size,
            (x, y) -> {},
            (x, y) -> {},
            () -> {},
            () -> {}
        );
    }
    @Test
    public void testEasyRotations() {
        int size = 2;
        int turns = 1;
        for (int side = 0; side < 6; side++) {
            for (int layer = 0; layer < size; layer++) {
                System.out.println("ROTATING: SIDE " + side + " LAYER " + layer);
                System.out.println("BEFORE");
                Cube cube = getCube(size);
                try {
                    cube.show();
                    for (int i = 1; i <= turns; i++) {
                        System.out.println(i * 90 + " degrees");
                        cube.rotate(side, layer);
                        cube.show();
                    }
                } catch (InterruptedException ignore) {}
            }
        }
    }

    private class Rotation {
        int side;
        int layer;

        private Rotation(int side, int layer) {
            this.side = side;
            this.layer = layer;
        }
    }

    private Rotation rotate(int side, int layer) {
        return new Rotation(side, layer);
    }

    private void testSmallRotations(int size, Stream<Rotation> rotations, String expected) {
        Cube cube = getCube(size);
        rotations.forEach(rotation -> {
            try {
                cube.show();
                cube.rotate(rotation.side, rotation.layer);
            } catch (InterruptedException ignore) {}
        });
        try { Assert.assertEquals(expected, cube.show()); }
        catch (InterruptedException ignore) {}
    }

    @Test
    public void testSmallCrossing() {
        testSmallRotations(
            3,
            Stream.of(
                rotate(2, 0),
                rotate(3, 0),
                rotate(4, 1)),
            "002033112125105105223225225040353353144044044334111554"
        );
    }

    @Test
    public void testSmallFaceRotation() {
        testSmallRotations(
            4,
            Stream.of(
                rotate(3, 1),
                rotate(3, 2),
                rotate(4, 3)
            ),
            "022002200220333311101112111211102222555555552222533343334333533340044004400440041111544554455445"
        );
    }

    @Test
    public void testReturnToInitialState() throws InterruptedException {
        for (int size = 1; size <= 10; size++) {
            Cube cube = getCube(size);

            StringBuilder solved = new StringBuilder();
            for (int i = 0; i < 6; ++i) solved.append(String.valueOf(i).repeat(size * size));
            Assert.assertEquals(solved.toString(), cube.show());

            for (int i = 0; i < 1260; i++) {
                cube.rotate(3, 0);
                cube.rotate(0, 0);
                cube.rotate(0, 0);
                cube.rotate(5, 0);
                cube.rotate(5, 0);
                cube.rotate(5, 0);
                cube.rotate(4, 0);
                cube.rotate(5, 0);
                cube.rotate(5, 0);
                cube.rotate(5, 0);
            }

            Assert.assertEquals(solved.toString(), cube.show());
        }
    }

}

