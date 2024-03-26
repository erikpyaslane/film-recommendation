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
            throw new NotFoundSoManySeatsException("There are no so many seats for this session");
        if (seatCount > seats.length)
            return bestSeparateSeats(seats, seatCount);
        return bestSeats(seats, seatCount);
    }

    private static int[][] bestSeats(boolean[][] seats, int seatCount) {

        int middleRow = seats.length/2;
        int middleSeat = seats[0].length/2;

        int downRow = middleRow;
        int upRow = middleRow;

        if (seats.length % 2 == 0)
            downRow--;
        int[] freeSeats;

        while(downRow >= 0 || upRow < seats.length){
            if (downRow == upRow) {
                freeSeats = findSeatsInRow(seats[middleRow], middleSeat, seatCount);
                if (freeSeats != null)
                    return convertTo2DArray(freeSeats, middleSeat);
            }
            else {
                freeSeats = findSeatsInRow(seats[upRow], middleSeat, seatCount);
                if (freeSeats != null)
                    return convertTo2DArray(freeSeats, upRow);
                freeSeats = findSeatsInRow(seats[downRow], middleSeat, seatCount);
                if (freeSeats != null)
                    return convertTo2DArray(freeSeats, downRow);
                upRow++;
                downRow--;
            }
        }

        return bestSeparateSeats(seats, seatCount);
    }

    private static int[][] bestSeparateSeats(boolean[][] seats, int seatCount) {
        int middleRow = seats.length;
        int i = 0;
        //for (int j = middleRow + i; i < middleRow; i++, j++) {}
        return new int[][]{{2,1},{2,2},{2,3}};
    }



    private static int[][] convertTo2DArray(int[] freeSeats, int middleSeat) {
        int[][] seats = new int[freeSeats.length][2];

        for (int i = 0; i < freeSeats.length; i++) {
            seats[i][0] = middleSeat;
            seats[i][1] = freeSeats[i];
        }

        return seats;
    }

    private static int[] findSeatsInRow(boolean[] seat, int middleSeat, int seatCount) {
        Random random = new Random();
        int num = random.nextInt(0,2);
        if (num == 1)
            return null;
        return new int[]{2,3,4};
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
