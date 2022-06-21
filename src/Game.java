import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Set;

public class Game extends JComponent {
    public static final int
            FIELD_EMPTY = 0,
            FIELD_X = 1,
            FIELD_O = 2,
            FIELD_DRAW = 3;
    int[][] field;
    boolean IsOTurn;

    public Game () {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[Const.CELL_CNT][Const.CELL_CNT];
        init();
    }

    public void init () {
        for (int i = 0; i < Const.CELL_CNT; ++i) {
            for (int j = 0; j < Const.CELL_CNT; ++j) {
                field[i][j] = FIELD_EMPTY;
            }
        }
        IsOTurn = false;
    }

    @Override
    protected void processMouseEvent (MouseEvent event) {
        super.processMouseEvent(event);
        if (event.getButton() == MouseEvent.BUTTON1) {
            int y = event.getY();
            int x = event.getX();
            if (x <= Const.SX || y <= Const.SY || x >= Const.W - Const.SX || y >= Const.H - Const.SY) {
                return;
            }
            x -= Const.SX;
            y -= Const.SY;
            x /= Const.CELL_SIZE;
            y /= Const.CELL_SIZE;
            if (field[x][y] != FIELD_EMPTY) {
                return;
            }
            field[x][y] = IsOTurn ? FIELD_O : FIELD_X;
            IsOTurn = !IsOTurn;
            repaint();
            int res = checkWinner();
            Log.status(String.valueOf(res));
            if (res == 0) {
                return;
            }
            finishGame(res);
            init();
            repaint();
        }
    }

    void finishGame (int x) {
        if (x == FIELD_X) {
            JOptionPane.showConfirmDialog(this, Const.X_WIN, Const.X_WIN, JOptionPane.INFORMATION_MESSAGE);
        }
        if (x == FIELD_O) {
            JOptionPane.showConfirmDialog(this, Const.O_WIN, Const.O_WIN, JOptionPane.INFORMATION_MESSAGE);
        }
        if (x == FIELD_DRAW) {
            JOptionPane.showConfirmDialog(this, Const.DRAW, Const.DRAW, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void drawX (Graphics graph, int x, int y) {
        graph.setColor(Color.BLUE);
        ((Graphics2D) graph).setStroke(new BasicStroke(Const.STROKE_WEIGHT));
        graph.drawLine(x, y, x + Const.CELL_SIZE, y + Const.CELL_SIZE);
        graph.drawLine(x + Const.CELL_SIZE, y, x, y + Const.CELL_SIZE);
    }

    void drawO (Graphics graph, int x, int y) {
        graph.setColor(Color.RED);
        ((Graphics2D) graph).setStroke(new BasicStroke(Const.STROKE_WEIGHT));
        graph.drawOval(x, y, Const.CELL_SIZE, Const.CELL_SIZE);
    }

    int checkWinner () {
        for (int i = 0; i < Const.CELL_CNT; ++i) {
            int x = field[i][0], f = 1;
            for (int j = 1; j < Const.CELL_CNT; ++j) {
                if (field[i][j] != x) {
                    f = 0;
                }
            }
            if (f == 1 && x != FIELD_EMPTY) {
                return x;
            }
        }
        for (int j = 0; j < Const.CELL_CNT; ++j) {
            int x = field[0][j], f = 1;
            for (int i = 1; i < Const.CELL_CNT; ++i) {
                if (field[i][j] != x) {
                    f = 0;
                }
            }
            if (f == 1 && x != FIELD_EMPTY) {
                return x;
            }
        }
        {
            int x = field[0][0];
            if (field[1][1] == x && field[2][2] == x && x != FIELD_EMPTY) {
                return x;
            }
        }
        {
            int x = field[0][2];
            if (field[1][1] == x && field[2][0] == x && x != FIELD_EMPTY) {
                return x;
            }
        }
        int cnt = 0;
        for (int i = 0; i < Const.CELL_CNT; ++i) {
            for (int j = 0; j < Const.CELL_CNT; ++j) {
                if (field[i][j] == FIELD_EMPTY) {
                    ++cnt;
                }
            }
        }
        if (cnt == 0) {
            return FIELD_DRAW;
        }
        return FIELD_EMPTY;
    }

    @Override
    protected void paintComponent (Graphics graph) {
        super.paintComponent(graph);
        graph.setColor(Color.BLACK);
        for (int Y = Const.SY, X = Const.SX, i = 0; i <= Const.CELL_CNT; ++i, Y += Const.CELL_SIZE, X += Const.CELL_SIZE) {
            graph.drawLine(Const.SX, Y, Const.SX + Const.CELL_SIZE * Const.CELL_CNT, Y);
            graph.drawLine(X, Const.SY, X, Const.SY + Const.CELL_SIZE * Const.CELL_CNT);
        }
        for (int X = Const.SX, i = 0; i < Const.CELL_CNT; ++i, X += Const.CELL_SIZE) {
            for (int Y = Const.SY, j = 0; j < Const.CELL_CNT; ++j, Y += Const.CELL_SIZE) {
                if (field[i][j] == FIELD_EMPTY) {
                    continue;
                }
                if (field[i][j] == FIELD_O) {
                    drawO(graph, X, Y);
                }
                if (field[i][j] == FIELD_X) {
                    drawX(graph, X, Y);
                }
            }
        }
    }
}
