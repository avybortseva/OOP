package ru.nsu.g.a.vybortseva.pizza;

/**
 * Класс, представляющий пекаря.
 * Пекарь берет заказы из очереди, готовит пиццу в течение заданного времени
 * и передает ее на склад готовой продукции.
 */
public class Baker implements Runnable {
    private final int id;
    private final int speed;
    private Pizza curPizza;
    private State state;
    private final OrderQueue queue;
    private final Warehouse warehouse;

    /**
     * Возможные состояния пекаря
     */
    enum State {
        FREE,
        COOK,
        WAIT
    }

    /**
     * Создает экземпляр пекаря.
     */
    public Baker(int id, int speed, OrderQueue queue, Warehouse warehouse) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed can't be negative");
        }
        this.id = id;
        this.speed = speed;
        this.state = State.FREE;
        this.queue = queue;
        this.warehouse = warehouse;
    }

    /**
     * Основной цикл работы пекаря.
     * Выполняется до тех пор, пока поток не будет прерван.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() || queue.getCurrentSize() > 0) {
                try {
                    curPizza = queue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                this.state = State.COOK;
                System.out.println("[" + curPizza.getId() + "] [is cooking]");

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                this.state = State.WAIT;
                boolean added = false;
                while (!added) {
                    try {
                        warehouse.add(curPizza);
                        added = true;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println("[" + curPizza.getId() + "] [on warehouse]");

                this.state = State.FREE;
                curPizza = null;
            }
        } finally {
            System.out.println("Пекарь " + id + " закончил работу.");
        }
    }
}
