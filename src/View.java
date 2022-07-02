import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class View {
    Controller controller;

    int rowCount;
    int colCount;

    JFrame window;
    JPanel mainPanel;
    JPanel board;
    JLabel score;

    Square[][] squares;

    Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);

    class Square extends JLabel {
        public Square() {
            super("", SwingConstants.CENTER);
            this.setPreferredSize(new Dimension(40, 40));
            this.setBackground(Color.BLACK);
            this.setOpaque(true);
            this.setFont(boldFont);
        }

        public void setEmpty() {
            this.setBackground(Color.BLACK);
        }

        public void setHead() {
            this.setText(null);
            this.setBackground(Color.GREEN);
        }

        public void setApple() {
            this.setText("O");
            this.setForeground(Color.RED);
            this.setBackground(Color.BLACK);
        }
    }

    public Square getSquare(int row, int col) {
        return squares[row][col];
    }

    public void increaseScore(int score) {
        this.score.setText("Score: " + Integer.toString(score));
    }

    public void endGame() {
        this.score.setText("Game over! " + score.getText());
    }

    class KeyboardControl implements KeyListener {
        @Override
        public void keyPressed(KeyEvent k) {}

        @Override
        public void keyReleased(KeyEvent k) {
            int key = k.getKeyCode();

            if (key == 37) { // left
                controller.setDirection("left");
            } else if (key == 38) { // up
                controller.setDirection("up");
            } else if (key == 39) { // right
                controller.setDirection("right");
            } else if (key == 40) { // down
                controller.setDirection("down");
            }
        }

        @Override
        public void keyTyped(KeyEvent k) {}
    }

    public View(Controller controller) {
        this.controller = controller;

        rowCount = controller.getRowCount();
        colCount = controller.getColCount();
        squares = new Square[rowCount][colCount];

        window = new JFrame("Snake");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e);
        }

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        window.add(mainPanel);

        board = new JPanel();
        board.setBackground(Color.BLACK);

        GridLayout grid = new GridLayout(rowCount, colCount);
        grid.setHgap(1);
        grid.setVgap(1);
        board.setLayout(grid);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Square square = new Square();
                board.add(square);
                squares[i][j] = square;
            }
        }

        mainPanel.add(board);

        JPanel info = new JPanel();
        info.setLayout(new BorderLayout());
        score = new JLabel("Score: 0");
        score.setBorder(new EmptyBorder(10,10,10,10));
        info.add(score, BorderLayout.WEST);
        mainPanel.add(info);

        window.addKeyListener(new KeyboardControl());
        window.setFocusable(true); // for keyListener

        window.pack();
        window.setVisible(true);
    }
}