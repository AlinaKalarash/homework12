public class Task1 {
    private volatile static Integer t = 0;


    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() ->
        {
            while (t <= 100) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Минуло 5 секунд.");
            }
        });
        thread.setDaemon(true);

        thread.start();


        while (t <= 100) {
            System.out.println("З запуску програми минуло: " + t + "с.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (t) {
                t++;
            }
        }

    }

}