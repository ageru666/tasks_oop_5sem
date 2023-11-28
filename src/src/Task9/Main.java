package Task9;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int num = 3;

        Phaser phaserInst = new Phaser(1);
        Thread[] threadsArr = new Thread[num];

        for (int i = 0; i < num; i++) {
            phaserInst.register();

            threadsArr[i] = new Thread(() -> {

                arriveAndWaitForOthers(phaserInst,3);

                phaserInst.arriveAndDeregister();
            });
            threadsArr[i].start();
        }

        arriveAndSkipPhase(phaserInst,3);

    }

    public static void arriveAndWaitForOthers(Phaser phaserInst,int quantityOfPhases)
    {
        for (int i = 0; i < quantityOfPhases - 1; i++) {
            System.out.println(Thread.currentThread().getName() +
                    " on phase " + phaserInst.getPhase());
            phaserInst.arrive();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Thread.currentThread().getName() +
                " on phase " + phaserInst.getPhase());
    }

    public static void arriveAndSkipPhase(Phaser phaserInst, int quantityOfPhases) throws InterruptedException {
        for (int i = 0; i < quantityOfPhases; i++) {
            phaserInst.arriveAndAwaitAdvance();
            System.out.println("Phase " + phaserInst.getPhase() + " end");
        }
        phaserInst.arriveAndAwaitAdvance();
    }
}