import com.sabu.entities.Board;
import com.sabu.entities.pieces.Ninja;

import static com.sabu.utils.Constants.*;

public class test {

    public static void main(String[] args) {

        Board enemyBoard = new Board();
        enemyBoard.setUnit(new Ninja(true,0,0));
        enemyBoard.setUnit(new Ninja(false,2,0));
        enemyBoard.setUnit(new Ninja(false,1,3));


        Board board = new Board();
        board.setUnit(new Ninja(true,4,2));
        board.setUnit(new Ninja(false,2,3));
        board.setUnit(new Ninja(false,4,3));

        printBoard(board, enemyBoard);


    }

    public static void printBoard(Board board, Board enemyBoard) {
        System.out.print("\tyour Board");
        System.out.println("\t\t\t\t\t Enemy Board");
        System.out.print("   A   B   C   D   E");
        System.out.println("\t\t   A   B   C   D   E");
        System.out.print(SPACE + HIGHLINE);
        System.out.println("\t\t" + SPACE + HIGHLINE);

        for (int y = 0; y < MAX_BOARD_SIZE; ++y) {
            System.out.print((y + 1));
            System.out.print(COLUMN);

            for (int x = 0; x < MAX_BOARD_SIZE; ++x) {
                System.out.print(SPACE + board.getUnitAt(x, y).getUnitType() + SPACE + COLUMN);
            }
            System.out.print("\t\t" + (y + 1));
            System.out.print(COLUMN);

            for (int x = 0; x < MAX_BOARD_SIZE; ++x) {
                System.out.print(SPACE + enemyBoard.getUnitAt(x, y).getUnitType() + SPACE + COLUMN);
            }
            System.out.println();
            System.out.print(SPACE + HIGHLINE);
            System.out.println("\t\t" + SPACE + HIGHLINE);

        }
    }

}
