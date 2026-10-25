class BankTask implements Runnable {
    String operation;

    BankTask(String operation) {
        this.operation = operation;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            if (operation.equals("Deposit"))
                System.out.println("Deposited: Rs. " + (i * 100));
            else
                System.out.println("Withdrawn: Rs. " + (i * 50));
        }
    }
}

public class Assignment26b {
    public static void main(String[] args) {
        Thread t1 = new Thread(new BankTask("Deposit"));
        Thread t2 = new Thread(new BankTask("Withdraw"));

        t1.start();
        t2.start();
    }
}