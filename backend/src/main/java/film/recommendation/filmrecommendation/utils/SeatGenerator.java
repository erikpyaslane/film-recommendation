package film.recommendation.filmrecommendation.utils;

import java.util.Random;

public class SeatGenerator {

    public static boolean[][] fillSeats(int rows, int columns) {
        Random random = new Random();
        boolean[][] seats = generateSeats(rows, columns);
        int numberOfSeatsToFill = random.nextInt(0, rows*columns);

        for (int i = 0; i < numberOfSeatsToFill; i++) {
            while (true){
                int rowNumber = random.nextInt(0,rows);
                int colNumber = random.nextInt(0, columns);
                if (seats[rowNumber][colNumber])
                    continue;
                seats[rowNumber][colNumber] = true;
                break;
            }
        }
        return seats;
    }

    private static boolean[][] generateSeats(int rows, int columns) {
        boolean[][] seats = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seats[i][j] = false;
            }
        }
        return seats;
    }

}
