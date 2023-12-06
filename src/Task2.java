import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class Task2 extends Thread {

    private int num;

    private Consumer<Integer> processor;

    private AtomicBoolean isProcessed = new AtomicBoolean(true);


    public Task2(Consumer<Integer> processor) {
        this.processor = processor;
    }
    public boolean isProcessed() {
        return isProcessed.get();
    }

    public void process(int num) {
        this.num = num;
        this.isProcessed.set(false);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isProcessed.get()) {
                continue;
            }

            processor.accept(num);

            isProcessed.set(true);
        }
    }

    public static void main(String[] args) {

        List<String> result = new ArrayList<>();

        Task2 t1 = new Task2((n) -> {
            if (n % 3 == 0 && n % 5 != 0) {
                result.add("fizz");
            }
        });
        Task2 t2 = new Task2((n) -> {
            if (n % 3 != 0 && n % 5 == 0) {
                result.add("buzz");
            }
        });
        Task2 t3 = new Task2((n) -> {
            if (n % 3 != 0 && n % 5 != 0) {
                result.add(String.valueOf(n));
            }
        });
        Task2 t4 = new Task2((n) -> {
            if (n % 3 == 0 && n % 5 == 0) {
                result.add("fizzbuzz");
            }
        });

        List<Task2> task = new ArrayList<>();
        task.add(t1);
        task.add(t2);
        task.add(t3);
        task.add(t4);

        for (Task2 t : task) {
            t.start();
        }

        for (int i = 1; i <= 15; i++) {
            for (Task2 t : task) {
                t.process(i);
            }

            while (true) {
                int processedNumberCounter = 0;
                for (Task2 t : task) {
                    if (t.isProcessed()) {
                        processedNumberCounter++;
                    }
                }

                if (processedNumberCounter == 4) {
                    break;
                }
            }
        }

        System.out.println(result);


    }

}
