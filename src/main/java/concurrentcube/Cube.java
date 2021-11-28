package concurrentcube;

import concurrentcube.sequentialcube.SequentialCube;
import concurrentcube.sequentialcube.axis.Axis;
import concurrentcube.sequentialcube.enums.Direction;
import concurrentcube.sequentialcube.enums.Rotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Cube extends SequentialCube {
    private final BiConsumer<Integer, Integer> beforeRotation;
    private final BiConsumer<Integer, Integer> afterRotation;
    private final Runnable beforeShowing;
    private final Runnable afterShowing;

    private final Semaphore mutex;
    private final Semaphore firstProcesses;
    private final Semaphore finishedProcesses;
    private Action workingAction;
    private final EnumMap<Action, Semaphore> otherProcesses;
    private final EnumMap<Action, Integer> numberOfWaitingProcesses;
    private final EnumMap<Action, ArrayList<Semaphore>> workingLayers;
    private int numberOfWorkingProcesses;
    private int numberOfFinishedProcesses;
    private int numberOfWaitingActions;

    public Cube(int size,
                BiConsumer<Integer, Integer> beforeRotation,
                BiConsumer<Integer, Integer> afterRotation,
                Runnable beforeShowing,
                Runnable afterShowing) {
        super(size);
        this.beforeRotation = beforeRotation;
        this.afterRotation = afterRotation;
        this.beforeShowing = beforeShowing;
        this.afterShowing = afterShowing;

        mutex = new Semaphore(1);
        firstProcesses = new Semaphore(0);
        otherProcesses = Arrays.stream(Action.values())
            .collect(Collectors.toMap(
                Function.identity(),
                (action -> new Semaphore(0)),
                (l, r) -> {throw new IllegalArgumentException();}, // This will not happen.
                () -> new EnumMap<>(Action.class)));
        finishedProcesses = new Semaphore(0);
        workingAction = null;
        numberOfWorkingProcesses = 0;
        numberOfWaitingProcesses = Arrays.stream(Action.values())
            .collect(Collectors.toMap(
                Function.identity(),
                (action -> 0),
                (l, r) -> {throw new IllegalArgumentException();}, // This will not happen.
                () -> new EnumMap<>(Action.class)));
        workingLayers = Stream.of(Action.UpAxis, Action.LeftAxis, Action.FrontAxis)
            .collect(Collectors.toMap(
                Function.identity(),
                action -> IntStream.range(0, size).mapToObj(i -> new Semaphore(1)).collect(Collectors.toCollection(ArrayList::new)),
                (l, r) -> {throw new IllegalArgumentException();}, // This will not happen.
                () -> new EnumMap<>(Action.class)));
        numberOfFinishedProcesses = 0;
        numberOfWaitingActions = 0;
    }

    private void initialProtocol(Action action) throws  InterruptedException {
        mutex.acquire();
        if (workingAction == null)
            workingAction = action;
        else if (workingAction != action) {
            Integer number_of_waiting_processes = numberOfWaitingProcesses.get(action) + 1;
            numberOfWaitingProcesses.put(action, number_of_waiting_processes);
            if (number_of_waiting_processes.equals(1)) {
                numberOfWaitingActions += 1;
                mutex.release();
                firstProcesses.acquire();
                numberOfWaitingActions -= 1;
                workingAction = action;
            } else {
                mutex.release();
                otherProcesses.get(action).acquire();
            }
            number_of_waiting_processes = numberOfWaitingProcesses.get(action);
            numberOfWaitingProcesses.put(action, number_of_waiting_processes - 1);
        }

        numberOfWorkingProcesses += 1;
        if (numberOfWaitingProcesses.get(action) > 0)
            otherProcesses.get(action).release();
        else
            mutex.release();
    }

    private void closingProtocol(Action action) throws InterruptedException {
        mutex.acquire();
        numberOfWorkingProcesses -= 1;
        if (numberOfWorkingProcesses > 0) {
            numberOfFinishedProcesses += 1;
            mutex.release();
            finishedProcesses.acquire();
            numberOfFinishedProcesses -= 1;
        }
        if (numberOfFinishedProcesses > 0)
            finishedProcesses.release();
        else {
            if (numberOfWaitingActions > 0)
                firstProcesses.release();
            else {
                workingAction = null;
                mutex.release();
            }
        }
    }

    public void rotate(int side, int layer) throws InterruptedException {
        checkRotateArguments(side, layer);
        Direction direction = Direction.getDirection(side);
        Action action = Action.getAxisAction(direction);
        initialProtocol(action);

        Rotation rotation = Rotation.getRotation(direction);
        Axis axis = getAxis(direction);
        int index = axis.getIndex(rotation, layer);
        workingLayers.get(action).get(index).acquire();

        beforeRotation.accept(side, layer);
        axis.rotate(rotation, layer);
        afterRotation.accept(side, layer);

        workingLayers.get(action).get(index).release();
        closingProtocol(action);
    }

    public String show() throws InterruptedException {
        initialProtocol(Action.Show);
        beforeShowing.run();
        String s = super.showSequential();
        afterShowing.run();
        closingProtocol(Action.Show);
        return s;
    }

}