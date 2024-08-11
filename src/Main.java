import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static ArrayList<Square> squares;
    private static ArrayList<Marker> markers;
    private static ArrayList<Mine> mines;
    private static boolean running;
    private static boolean won;
    private static Integer colNRow;
    private static Integer minesAmount;

//    This method will be called whenever the game restarts
    private static void initialProperties(){
        squares = new ArrayList<>();
        markers = new ArrayList<>();
        mines = new ArrayList<>();
        running = true;
        won = false;
        colNRow = 3;
        minesAmount = 2;
    }


    public static void main(String[] args) {

        System.out.println("Welcome to Minesweeper!");

        initialProperties();

        Scanner sc = new Scanner(System.in);

        //validate input
        do {
            if (colNRow <= 2) System.out.println("Minimum size of the grid is 2");
            if (colNRow > 10) System.out.println("Maximum size of the grid is 10");
            System.out.print("Enter the size of the grid (e.g 4 for a 4x4 grid): ");
            colNRow = sc.nextInt();
        } while(colNRow < 2 || colNRow > 10);
        do {
            if (minesAmount >= (int) (Math.pow(colNRow,2)) * (35.0f / 100.0f)) {
                System.out.println("Invalid input, pls try again!!!");
            } else if (minesAmount < 2) {
                System.out.println("Invalid input, pls try again!!!");
            }
            System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
            minesAmount = sc.nextInt();
        } while ((minesAmount >= (int) (Math.pow(colNRow,2)) * (35.0f / 100.0f)) || minesAmount < 2);

        int minesLeft = minesAmount;

        //random generate mines
        while(mines.size() < minesAmount)
        {
            int x = randomInt(1, colNRow);
            int y = randomInt(1, colNRow);

            if(getMine(x, y) == null) mines.add(new Mine(x, y));
        }

//        System.out.println(mines.get(0).x() +" " +mines.get(0).y());   //For debugging purpose

        // Setup near mines amount for each Square
        for(int x = 1; x < colNRow + 1; x++)
        {
            for(int y = 1; y < colNRow + 1; y++)
            {
                if(getMine(x, y) == null)
                {
                    int nearMinesAmount = getNearMinesNumber(x, y);
                    if(nearMinesAmount > 0) squares.add(new Number(x, y, nearMinesAmount));
                    else squares.add(new Safe(x, y));
                }
            }
        }

        Scanner scanner = new Scanner(System.in);

        // Begin the game
        while(running)
        {
            drawGrid();

            System.out.println("Remaining Marker: " + minesLeft);
            System.out.println("R - Reveal, M - Mark");
            String input = scanner.nextLine().toUpperCase();

            if(input.equals("")) continue;

            char action = input.charAt(0);
            String[] sorted = input.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            int inputX = (int)sorted[0].charAt(1) - 64;
            int inputY = Integer.valueOf(sorted[1]);

            if(action == 'R') {
                Mine mine = getMine(inputX, inputY);

                if(mine != null) {
                    running = false;
                }

                recursiveShow(inputX, inputY);
            }
            else if(action == 'M') {
                Marker marker = getMarker(inputX, inputY);
                if(marker != null)
                {
                    markers.remove(marker);
                    minesLeft++;
                }
                else
                {
                    if(minesLeft != 0) {
                        markers.add(new Marker(inputX, inputY));
                        minesLeft--;
                    }
                }
            }

            won = true;
            mines.forEach(mine -> {
                if(getMarker(mine.x(), mine.y()) == null)
                    won = false;
            });

            if(won)
            {
                running = false;

                squares.forEach(square -> {
                    if(!square.isVisible())
                        square.setVisible(true);
                });
            }
        }

        mines.forEach(mine -> {
            squares.add(mine);
            mine.setVisible(true);
        });

        drawGrid();

        if(won) {
            System.out.println("Congratulations, you have won the game!");
        }
        else {
            System.out.println("Oh no, you detonated a mine! Game over.");
        }
        System.out.println("Press any key to try again...");
        scanner.next();

        initialProperties();
        main(null);

        scanner.close();
    }

    public static int randomInt(int min, int max)
    {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static int getNearMinesNumber(int x, int y)
    {
        int count = 0;

        // check top left
        if(x > 1 && y > 1)
            if(getMine(x - 1, y - 1) != null)
                count++;

        // check top
        if(y > 1)
            if(getMine(x, y - 1) != null)
                count++;

        // check top right
        if(x < colNRow && y > 1)
            if(getMine(x + 1, y - 1) != null)
                count++;

        // check left
        if(x > 1)
            if(getMine(x - 1, y) != null)
                count++;

        // check right
        if(x < colNRow)
            if(getMine(x + 1, y) != null)
                count++;

        // check bottom left
        if(x > 1 && y < colNRow)
            if(getMine(x - 1, y + 1) != null)
                count++;

        // check bottom
        if(y < colNRow)
            if(getMine(x, y + 1) != null)
                count++;

        // check bottom right
        if(x < colNRow && y < colNRow)
            if(getMine(x + 1, y + 1) != null)
                count++;

        return count;
    }

    public static void drawGrid()
    {
        char innerVertical = ' ';

        StringBuilder letters = new StringBuilder();
        letters.append("   ");
        for(int i = 0; i < colNRow; i++)
            letters.append(((char)(i + 65)) + " ");

        System.out.println(letters);

        for(int i = 0; i < colNRow * 2 - 1; i++)
        {
            if(i % 2 == 0)
            {
                StringBuilder line = new StringBuilder();
                line.append(i / 2 + 1).append("  ");
                for(int j = 0; j < colNRow * 2 - 1; j++)
                {
                    if(j % 2 == 0)
                    {
                        Marker marker = null;
                        for(Marker m : markers)
                            if(m.x() == j / 2 + 1 && m.y() == i / 2 + 1)
                                marker = m;

                        if(marker != null)
                            line.append(marker.c());
                        else
                        {
                            Square square = null;
                            for(Square t : squares)
                                if(t.x() == j / 2 + 1 && t.y() == i / 2 + 1)
                                    square = t;

                            if(square != null)
                            {
                                if(square.isVisible())
                                    line.append(square.c());
                                else
                                    line.append("_");
                            }
                            else
                                line.append("_");
                        }
                    }
                    else if(j % 2 == 1)
                        line.append(innerVertical);
                }
                System.out.println(line);
            }
        }
    }

//    Method to show the square
    public static void recursiveShow(int x, int y) {
        Square square = getSquare(x, y);
        if(square == null)
            return;
        square.setVisible(true);


        if(square instanceof Safe) {
            // check top left
            if(x > 1 && y > 1)
                if(!getSquare(x - 1, y - 1).isVisible())
                    recursiveShow(x - 1, y - 1);

            // check top
            if(y > 1)
                if(!getSquare(x, y - 1).isVisible())
                    recursiveShow(x, y - 1);

            // check top right
            if(x < colNRow && y > 1)
                if(!getSquare(x + 1, y - 1).isVisible())
                    recursiveShow(x + 1, y - 1);

            // check left
            if(x > 1)
                if(!getSquare(x - 1, y).isVisible())
                    recursiveShow(x - 1, y);

            // check right
            if(x < colNRow)
                if(!getSquare(x + 1, y).isVisible())
                    recursiveShow(x + 1, y);

            // check bottom left
            if(x > 1 && y < colNRow)
                if(!getSquare(x - 1, y + 1).isVisible())
                    recursiveShow(x - 1, y + 1);

            // check bottom
            if(y < colNRow)
                if(!getSquare(x, y + 1).isVisible())
                    recursiveShow(x, y + 1);

            // check bottom right
            if(x < colNRow && y < colNRow)
                if(!getSquare(x + 1, y + 1).isVisible())
                    recursiveShow(x + 1, y + 1);
        }
    }

    public static Square getSquare(int x, int y) {
        return squares.stream()
                .filter(square -> square.x() == x && square.y() == y)
                .findFirst()
                .orElse(null);
    }

    public static Marker getMarker(int x, int y) {
        return markers.stream()
                .filter(marker -> marker.x() == x && marker.y() == y)
                .findFirst()
                .orElse(null);
    }

    public static Mine getMine(int x, int y) {
        return mines.stream()
                .filter(mine -> mine.x() == x && mine.y() == y)
                .findFirst()
                .orElse(null);
    }
}
