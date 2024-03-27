package film.recommendation.filmrecommendation.utils;

import film.recommendation.filmrecommendation.exceptions.NotFoundSoManySeatsException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Component
public class SeatRecommender {

    public static int[][] recommendFreeSeats(boolean[][] seats, int seatCount) {
        int reservedSeats = getReservedSeatCount(seats);
        int totalSeats = seats.length * seats[0].length;
        if (totalSeats - reservedSeats < seatCount)
            throw new NullPointerException();
        if (seatCount > seats.length)
            return bestSeparateSeats(seats, seatCount);
        return bestSeats(seats, seatCount);
    }

    private static int[][] bestSeats(boolean[][] seats, int neededSeatCount) {

        int middleRow = seats.length / 2;
        int middleSeat = seats[0].length / 2;
        int downRow = seats.length % 2 == 0 ? middleRow - 1 : middleRow;
        int upRow = middleRow;

        int[] freeSeatsInRow;
        if (downRow == upRow) {
            freeSeatsInRow = findSeatsInRow(seats[middleRow], middleSeat, neededSeatCount);
            upRow++;
            downRow--;
            if (freeSeatsInRow != null)
                return convertTo2DArray(freeSeatsInRow, middleSeat);
        }

        while (downRow >= 0 || upRow < seats.length) {

            freeSeatsInRow = findSeatsInRow(seats[upRow], middleSeat, neededSeatCount);
            if (freeSeatsInRow != null)
                return convertTo2DArray(freeSeatsInRow, upRow);
            freeSeatsInRow = findSeatsInRow(seats[downRow], middleSeat, neededSeatCount);
            if (freeSeatsInRow != null)
                return convertTo2DArray(freeSeatsInRow, downRow);
            upRow++;
            downRow--;

        }

        return bestSeparateSeats(seats, neededSeatCount);
    }

    private static int[][] bestSeparateSeats(boolean[][] seats, int seatCount) {
        int middleRow = seats.length;
        int i = 0;
        //for (int j = middleRow + i; i < middleRow; i++, j++) {}
        return new int[][]{{2, 1}, {2, 2}, {2, 3}};
    }


    private static int[][] convertTo2DArray(int[] freeSeats, int middleSeat) {
        int[][] seats = new int[freeSeats.length][2];

        for (int i = 0; i < freeSeats.length; i++) {
            seats[i][0] = middleSeat;
            seats[i][1] = freeSeats[i];
        }

        return seats;
    }

    private static int[] findSeatsInRow(boolean[] seats, int middleSeat, int seatCount) {
        int[] seatsToBook = new int[seatCount];
        int left = middleSeat;
        int right = middleSeat;

        while (left >= 0 && right < seats.length){
            if (seatCount == 0)
                return seatsToBook;
            else if (right == left) {
                if (!seats[middleSeat]){
                    seatCount--;
                    seatsToBook[seatCount] = middleSeat;
                }
                right++;
                left--;
            }
            else if (!seats[left]){
                seatCount--;
                seatsToBook[seatCount] = left;
                left--;
            }
            else if (!seats[right]) {
                seatCount--;
                seatsToBook[seatCount] = right;
                right++;
            }
        }

        if (seatCount == 0)
            return seatsToBook;
        return new int[]{1,2,3};
    }

    private static int getReservedSeatCount(boolean[][] seats) {
        int reservedSeats = 0;
        for (boolean[] row : seats) {
            for (boolean seat : row) {
                if (seat)
                    reservedSeats++;
            }
        }
        return reservedSeats;
    }
}
