public class LetterByLetterPrinter implements Runnable {
    private String message;
    private long delay;

    public LetterByLetterPrinter(String message, long delay) {
        this.message = message;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            for (char letter : message.toCharArray()) {
                System.out.print(letter);
                Thread.sleep(delay);
            }
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
