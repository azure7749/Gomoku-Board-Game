import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MYFrame extends JFrame implements MouseListener {
    private int X0 = 50, Y0 = 70, LINE = 15, SIZE = 50, CHESS = 50;  // Adjusted SIZE to make it more sensitive
    private int[][] chessArr;
    private List<Point> ovals;  // Store the positions of the ovals
    private int currentPlayer;  // 1 for black, 2 for white

    public MYFrame() {
        chessArr = new int[LINE][LINE];
        ovals = new ArrayList<>();
        addMouseListener(this);
        currentPlayer = 1;  // Start with black
    }

    public void showUI() {
        MYFrame jf = new MYFrame();
        jf.setSize(880, 800);
        jf.setTitle("Gomoku");
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the button and add it to the west side
        BorderLayout flow = new BorderLayout();
        jf.setLayout(flow);

        JPanel ePanel = new JPanel();
        ePanel.setPreferredSize(new Dimension(80,0));
        jf.add(ePanel, BorderLayout.EAST);

        JButton jbu = new JButton("START");
        ePanel.add(jbu);
        jf.getContentPane().setBackground(Color.lightGray);

        jf.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw the grid
        for (int i = 0; i < LINE; i++) {
            g.setColor(Color.black);
            g.drawLine(X0, Y0 + i * SIZE, (LINE - 1) * SIZE + X0, Y0 + i * SIZE);
            g.drawLine(X0 + i * SIZE, Y0, X0 + i * SIZE, (LINE - 1) * SIZE + Y0);
        }

        // Call the method to draw ovals
        drawOvals(g);
    }

    private void drawOvals(Graphics g) {
        // Draw the stored ovals
        for (Point p : ovals) {
            int chessX = p.x;
            int chessY = p.y;

            if (chessArr[chessX][chessY] == 1) {
                g.setColor(Color.BLACK);
            } else if (chessArr[chessX][chessY] == 2) {
                g.setColor(Color.WHITE);
            }

            g.fillOval(X0 + chessX * SIZE - CHESS / 2, Y0 + chessY * SIZE - CHESS / 2, CHESS, CHESS);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int chessX = (x - X0 + SIZE / 2) / SIZE;
        int chessY = (y - Y0 + SIZE / 2) / SIZE;

        if (chessX >= 0 && chessX < LINE && chessY >= 0 && chessY < LINE && chessArr[chessX][chessY] == 0) {
            if (currentPlayer == 1) {
                chessArr[chessX][chessY] = 1;
                currentPlayer = 2;
            } else {
                chessArr[chessX][chessY] = 2;
                currentPlayer = 1;
            }

            // Store the position of the oval
            ovals.add(new Point(chessX, chessY));

            // Request a repaint to update the ovals
            drawOvals(getGraphics());

            // Check for a win after each move
            checkWin(chessArr[chessX][chessY], chessX, chessY);
        }
    }

    private void clearBoard() {
        ovals.clear();
        for (int i = 0; i < LINE; i++) {
            for (int j = 0; j < LINE; j++) {
                chessArr[i][j] = 0;
            }
        }
    }

    public void checkWin(int player, int x, int y) {
        int countH = 1;
        int countV = 1;
        int countD1 = 1;
        int countD2 = 1;

        // Check horizontally
        for (int i = x + 1; i < LINE && chessArr[i][y] == player; i++) {
            countH++;
        }
        for (int i = x - 1; i >= 0 && chessArr[i][y] == player; i--) {
            countH++;
        }

        // Check vertically
        for (int i = y + 1; i < LINE && chessArr[x][i] == player; i++) {
            countV++;
        }
        for (int i = y - 1; i >= 0 && chessArr[x][i] == player; i--) {
            countV++;
        }

        // Check diagonally (from top-left to bottom-right)
        for (int i = x + 1, j = y + 1; i < LINE && j < LINE && chessArr[i][j] == player; i++, j++) {
            countD1++;
        }
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0 && chessArr[i][j] == player; i--, j--) {
            countD1++;
        }

        // Check diagonally (from top-right to bottom-left)
        for (int i = x + 1, j = y - 1; i < LINE && j >= 0 && chessArr[i][j] == player; i++, j--) {
            countD2++;
        }
        for (int i = x - 1, j = y + 1; i >= 0 && j < LINE && chessArr[i][j] == player; i--, j++) {
            countD2++;
        }


        if (countH >= 5 || countV >= 5 || countD1 >= 5 || countD2 >= 5) {
            if (chessArr[x][y] == 1) {
                System.out.println("BLACK WIN");
                JOptionPane.showMessageDialog(null, "BLACK WIN");
            } else if (chessArr[x][y] == 2) {
                System.out.println("WHITE WIN");
                JOptionPane.showMessageDialog(null, "WHITE WIN");
            }

            // Clear the board after a win
            clearBoard();

            // Repaint to remove the ovals from the board
            repaint();
        }
    }





    @Override
    public void mousePressed(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Implement if needed
    }

    public static void main(String[] args) {
        MYFrame frame = new MYFrame();
        frame.showUI();
    }
}
