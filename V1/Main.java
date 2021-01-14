package com.company;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


/**
 * Version 1: randomizes a board with a start and final position
 * and will figure out if there is a path leading to the final position
 * in a recursive way
 */
public class Main {

    /**
     * Method to figure out if board is solvable
     * @param arr 2d array with board values
     * @param visited 2d array to keep track of which position was already visited
     * @param startRow row of start position
     * @param startColumn column of start position
     * @return
     */
    private static boolean findPath( int[][] arr,boolean[][] visited, int startRow, int startColumn) {
        int value = arr[startRow][startColumn];
        //sets start position as visited
        visited[startRow][startColumn] = true;

        //base case if final position is reached
        if (arr[startRow][startColumn] == 0) {
            return true;
        }
        else {
            //if its possible, moves down (possible only if position has not been visited and position is on the board)
            if ((startRow + value) < visited.length) {
                if (!(visited[startRow + arr[startRow][startColumn]][startColumn])) {
                    //recursive call with new position coordinates, returns true only if its possible
                    if (findPath(arr, visited, startRow + arr[startRow][startColumn], startColumn))
                        return true;
                }
            }
            //if its possible, moves up
            if ((startRow - value) >= 0){
                if(!(visited[startRow - arr[startRow][startColumn]][startColumn])){
                    if (findPath(arr, visited, startRow - arr[startRow][startColumn], startColumn))
                        return true;
                }
            }
            //if its possible, moves right
            if ((startColumn + value) < visited.length){
                if (!(visited[startRow][startColumn + arr[startRow][startColumn]])) {
                    if (findPath(arr, visited, startRow, startColumn + arr[startRow][startColumn]))
                        return true;
                }
            }
            //if its possible, moves left
            if ((startColumn - value) >= 0){
                if (!(visited[startRow][startColumn - arr[startRow][startColumn]])){
                    if (findPath(arr, visited, startRow, startColumn - arr[startRow][startColumn]))
                        return true;
                 }
            }
            //if no paths are possible returns false
            return false;
        }
    }


    public static void main(String[] args) {

        System.out.println("||<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<||\n"
                + "       ||      MagicBoard Version 1      ||\n"
                + "||>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>||\n");

        System.out.print("Please enter board dimensions: ");
        Scanner sc = new Scanner(System.in);
        int boardSize = sc.nextInt();

        int[][] board = new int[boardSize][boardSize];
        Random rand = new Random();

        //creating randomized board with values from 1 to n-1
        for (int i = 0; i< boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] =  rand.nextInt(boardSize - 1) + 1;
            }
        }

        //setting final position
        int finalRow = rand.nextInt(boardSize);
        int finalColumn = rand.nextInt(boardSize);
        board[finalRow][finalColumn] = 0;

        int startRow = finalRow;
        int startColumn = finalColumn;

        //setting start position
        while (board[startRow][startColumn] == 0) {
            startRow = rand.nextInt(2);
            startColumn = rand.nextInt(2);
            if (startRow == 1)
                startRow = boardSize - 1;
            if (startColumn == 1)
                startColumn = boardSize - 1;
        }

        //creating 2d board to keep track of visited position
        boolean[][] visited = new boolean[board.length][board[0].length];

        //printing board information
        System.out.println("Here is the created board" );

        for (int i = 0; i< boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print( board[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("Start position is (" + startRow + "," + startColumn + ")");
        System.out.println("Final position is (" + finalRow + "," + finalColumn + ")");

        final double start = System.nanoTime();

        Boolean isPath = findPath(board,visited,startRow,startColumn);
        System.out.println("Solvable: " + isPath );

        final double end = System.nanoTime();

        System.out.println("\nTime in seconds: " + (end - start) / 1000000000);

        //printing to file
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new FileOutputStream("Out.txt", true));
        }
        catch(FileNotFoundException e) {
            System.out.println("Could not open the file to write to.");
            System.out.print("Program will terminate.");
            System.exit(0);
        }

        //writing to file the possible combinations of strings
        pw.println("Board dimensions: " + boardSize + " x " + boardSize);

        for (int i = 0; i< boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                pw.print( board[i][j] + "\t");
            }
            pw.println();
        }
        pw.println("Start position is (" + startRow + "," + startColumn + ")");
        pw.println("Final position is (" + finalRow + "," + finalColumn + ")");
        pw.println("Solvable: " + isPath );

        pw.println("\nTime in seconds: " + (end - start) / 1000000000 + "\n");
        pw.close();

    }
}





