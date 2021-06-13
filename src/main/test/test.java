import com.sabu.entities.Board;

import static com.sabu.utils.Constants.*;

public class test {

    public static void main(String[] args) {

        printBoard(new Board(), new Board());


    }

    public static void printBoard(Board board, Board enemyBoard) {
        System.out.print("\tyour Board");
        System.out.println("\t\t\t\t\t enemy Board");
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
