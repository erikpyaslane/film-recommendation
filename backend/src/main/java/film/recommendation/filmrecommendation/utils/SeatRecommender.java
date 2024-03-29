package film.recommendation.filmrecommendation.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

@Component
public class SeatRecommender {

    public static int[][] recommendFreeSeats(boolean[][] seats, int seatCount) {
        int reservedSeats = getReservedSeatCount(seats);
        int totalSeats = seats.length * seats[0].length;
        if (totalSeats - reservedSeats < seatCount)
            return new int[][]{};
        if (seatCount > seats.length)
            return bestSeparateSeats(seats, seatCount);
        return bestSeats(seats, seatCount);
    }

    private static int[][] bestSeats(boolean[][] seats, int neededSeatCount) {

        int totalFreeSeats = seats.length * seats[0].length - getReservedSeatCount(seats);
        int[] temporaryResult;

        if (neededSeatCount > totalFreeSeats)
            return null;

        int middleRow = seats.length / 2;
        int upRow = middleRow;
        int downRow;

        if (seats.length % 2 == 0)
            downRow = upRow-1;
        else
            downRow = upRow;

        while (upRow < seats.length || downRow >= 0) {
            if (upRow == downRow) {
                temporaryResult = seatsInOneRow(seats[middleRow], neededSeatCount);
                if (temporaryResult != null)
                    return convertTo2DArray(temporaryResult,middleRow);
                upRow++;
            }
            else {
                temporaryResult = seatsInOneRow(seats[upRow], neededSeatCount);
                if (temporaryResult != null)
                    return convertTo2DArray(temporaryResult, upRow);
                upRow++;
                temporaryResult = seatsInOneRow(seats[downRow], neededSeatCount);
                if (temporaryResult != null)
                    return convertTo2DArray(temporaryResult, downRow);
            }
            downRow--;
        }

        return bestSeparateSeats(seats, neededSeatCount);
    }

    private static int[][] bestSeparateSeats(boolean[][] seats, int seatCount) {
        int[][] result = new int[seatCount][2];
        int counter = 0;
        int middleRow = seats.length/2;
        int upRow = middleRow;
        int downRow;

        if (seats.length % 2 == 0)
            downRow = upRow-1;
        else
            downRow = upRow;

        while ((downRow >= 0 || upRow < seats.length) && seatCount != counter) {
            if (downRow == upRow) {
                counter = reserveFreeSeats(seats[middleRow], middleRow, counter, result);
                downRow--;
                upRow++;
            }
            else {
                counter = reserveFreeSeats(seats[upRow], upRow, counter, result);
                upRow++;
                counter = reserveFreeSeats(seats[downRow], downRow, counter, result);
                downRow--;
            }
        }
        return result;
    }


    private static int reserveFreeSeats(boolean[] seats, int rowNumber, int counter, int[][] temporaryResult) {
        int center = seats.length / 2;
        int left, right;
        if (seats.length % 2 == 0) {
            right = center;
            left = right - 1;
        }
        else left = right = center;

        while ((left >= 0 || right < seats.length) && counter != temporaryResult.length){
            if (left == right) {
                if (!seats[center]) {
                    temporaryResult[counter][0] = rowNumber;
                    temporaryResult[counter][1] = center;
                    counter++;
                    left--;
                    right++;
                }
            }
            else {
                if (!seats[left]) {
                    temporaryResult[counter][0] = rowNumber;
                    temporaryResult[counter][1] = left;
                    counter++;
                }
                left--;
                if (counter == temporaryResult.length)
                    break;
                if (!seats[right]) {
                    temporaryResult[counter][0] = rowNumber;
                    temporaryResult[counter][1] = right;
                    counter++;
                }
                right++;
            }
        }
        return counter;
    }


    public static int[] seatsInOneRow(boolean[] seats, int numOfSeats) {
        if (numOfSeats > seats.length) {
            return null;
        }

        boolean[] resultNeeded = new boolean[numOfSeats];
        Arrays.fill(resultNeeded, false);


        int centerOfRow = seats.length / 2;
        int left, right;
        if (seats.length % 2 == 0) {
            if (resultNeeded.length % 2 == 0)
                left = right = centerOfRow - resultNeeded.length / 2;
            else {
                right = centerOfRow - resultNeeded.length / 2;
                left = right - 1;
            }

        } else {
            if (resultNeeded.length % 2 == 1) {
                left = right = centerOfRow - resultNeeded.length / 2;
            } else {
                left = centerOfRow - resultNeeded.length / 2;
                right = left + 1;
            }
        }


        while (left >= 0 || right+numOfSeats < seats.length) {
            if (left == right) {
                if (Arrays.equals(resultNeeded, copyOfRange(
                        seats, left, left + resultNeeded.length))) {
                    return createIntArray(left, numOfSeats);
                }
                left--;
                right++;
            } else {

                if (Arrays.equals(resultNeeded, copyOfRange(
                        seats, left, left + numOfSeats))) {
                    return createIntArray(left, numOfSeats);
                }
                else if (Arrays.equals(resultNeeded, copyOfRange(
                        seats, right, right + numOfSeats))) {
                    return createIntArray(right, numOfSeats);
                }

                left--;
                right++;
            }
        }

        return null;
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

    public static int[] createIntArray(int startIndex, int countOfSeats) {
        int[] output = new int[countOfSeats];
        for (int index = 0, seat = startIndex; index < countOfSeats; index++, seat++)
            output[index] = seat;
        return output;
    }

    private static int[][] convertTo2DArray(int[] freeSeats, int middleSeat) {
        int[][] seats = new int[freeSeats.length][2];

        for (int i = 0; i < freeSeats.length; i++) {
            seats[i][0] = middleSeat;
            seats[i][1] = freeSeats[i];
        }

        return seats;
    }
}