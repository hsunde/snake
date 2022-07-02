import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

class Model {
    Random rand = new Random();

    Controller controller;
    View view;

    int rowCount, colCount, appleCount;

    Square[][] squares;
    Queue<SnakeSquare> snake = new LinkedList<SnakeSquare>();

    int headRow = 5;
    int headCol = 5;
    int score = 0;

    public void generateApple() {
        int row = rand.nextInt(rowCount - 1);
        int col = rand.nextInt(colCount - 1);

        while(squares[row][col] != null) {
            row = rand.nextInt(rowCount - 1);
            col = rand.nextInt(colCount - 1);
        }

        AppleSquare apple = new AppleSquare(row, col);
        squares[row][col] = apple;
        view.getSquare(row, col).setApple();       
    }

    public void generateHead() {
        SnakeSquare head = new SnakeSquare(headRow, headCol);
        snake.add(head);
        squares[headRow][headCol] = head;
        view.getSquare(headRow, headCol).setHead();        
    }

    public Model(Controller controller, View view) {
        this.controller = controller;
        this.view = view;

        rowCount = controller.getRowCount();
        colCount = controller.getColCount();
        appleCount = controller.getAppleCount();
        squares = new Square[rowCount][colCount];

        generateHead();
        
        for (int i = 0; i < appleCount; i++) {
            generateApple();
        }
    }
 
    public boolean move(String direction) throws ArrayIndexOutOfBoundsException, SelfCollisionException {
        switch (direction) {
            case "up":
                headRow--;
                break;
            case "down":
                headRow++;
                break;
            case "left":
                headCol--;
                break;
            case "right":
                headCol++;
                break;
        }

        Square nextSquare = squares[headRow][headCol];
        if (nextSquare == null) {
            Square lastSnakeSquare = snake.remove();

            int row = lastSnakeSquare.getRow();
            int col = lastSnakeSquare.getCol();
            squares[row][col] = null;
            view.getSquare(row, col).setEmpty();
        } else if (nextSquare instanceof AppleSquare) {
            view.increaseScore(++score);
            generateApple();
        } else if (nextSquare instanceof SnakeSquare) {
            throw new SelfCollisionException();
        }

        generateHead();

        return nextSquare instanceof AppleSquare;
    }

    abstract class Square {
        int row;
        int col;

        public Square(int rad, int kol) {
            this.row = rad;
            this.col = kol;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }

    class SnakeSquare extends Square {
        public SnakeSquare(int row, int col) {
            super(row, col);
        }
    }

    class AppleSquare extends Square {
        public AppleSquare(int row, int col) {
            super(row, col);
        }
    }
}