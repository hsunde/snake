class Controller {
    Model model;
    View view;

    int interval, rowCount, colCount, appleCount;
    String direction = "right";

    public Controller(int interval, int rowCount, int colCount, int appleCount) {
        this.interval = interval;
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.appleCount = appleCount;

        view = new View(this);
        model = new Model(this, view);
    }

    public void startGame() {
        t.start();
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public int getAppleCount() {
        return appleCount;
    }

    class MoveSnake implements Runnable {
        public void run() {
            boolean gameOver = false;
            while (!gameOver) {
                try {
                    Thread.sleep(interval);
                } catch(InterruptedException e){
                    gameOver = true;
                }

                try {
                    if (model.move(direction)) { // hit apple
                        interval = (int) (interval * 0.95); // TODO improve algorithm
                    }
                } catch (SelfCollisionException | ArrayIndexOutOfBoundsException e) {
                    view.endGame();
                    gameOver = true;
                }
            }
        }
    }

    Thread t = new Thread(new MoveSnake());
}