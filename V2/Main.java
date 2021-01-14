package com.company;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;


/**
 * class Position
 * creates object with coordinates of a board position
 */
class Position{
    private int row;
    private int column;

    public Position(){
        row = 0;
        column = 0;
    }

    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
}

/**
 * Version 2: randomizes a board with a start and final position
 * and will figure out if there is a path leading to the final position
 * in a iterative way
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

        Stack<Position> st = new Stack<Position>();
        //pushes object Position that contains the coordinates of start position
        st.push(new Position(startRow,startColumn));
        //sets start position as visited
        visited[startRow][startColumn] = true;
        boolean solvable = true;
        int row;
        int column;


        //while loop that will go through all the possible movements to determine if the board is solvable
        // will loop until stack is not empty and position is not the final one
        while ( !st.empty() && arr[st.peek().getRow()][st.peek().getColumn()] != 0) {
            row = st.peek().getRow();
            column = st.peek().getColumn();

            //if its possible, moves down (possible only if position has not been visited and position is on the board)
            if ((row + arr[row][column]) < visited.length && !(visited[row + arr[row][column]][column])) {
                st.push(new Position(row + arr[row][column],column)); //pushes the new position on top of the stack
                visited[st.peek().getRow()][st.peek().getColumn()] = true; //sets new position as visited
            }
            //if its possible, moves up
            else if ((row - arr[row][column]) >= 0 && !(visited[row - arr[row][column]][column])) {
                st.push(new Position(row - arr[row][column],column));
                visited[st.peek().getRow()][st.peek().getColumn()] = true;
            }
            //if its possible, moves right
            else if ((column + arr[row][column]) < visited.length && !(visited[row][column + arr[row][column]])){
                st.push(new Position(row ,column + arr[row][column]));
                visited[st.peek().getRow()][st.peek().getColumn()] = true;
            }
            //if its possible, moves left
            else if ((column - arr[row][column]) >=0 && !(visited[row][column - arr[row][column]])){
                st.push(new Position(row ,column - arr[row][column]));
                visited[st.peek().getRow()][st.peek().getColumn()] = true;
            }

            //if no positions are possible, this is a dead end
            // last element is out of the stack
            else {
                st.pop();
            }
        }
        //if stack is empty no possible solutions
        if (st.empty()){
            solvable = false;
        }

        return solvable;
    }


    public static void main(String[] args) {

        System.out.println("||<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<||\n"
                + "       ||      MagicBoard Version 2      ||\n"
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