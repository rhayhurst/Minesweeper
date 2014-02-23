import javax.swing.*;
import javax.swing.Icon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bob on 2/15/14.
 */
/*
 * todo: - have the game end when the user hits a mine
 * todo: - create a container that will contain two windows.  The container can have
 * todo:   type "flowlayout" (or whatever one allows you to place things "north", "center", etc.
 * todo: - create a third window that fits into the "masterWindow".  Create three displays for this
 * todo:   window.  It will eventually hold three pieces of data:
 * todo:   (1) data that shows the number of mines versus the number of flags placed (so, "x/10")
 * todo:   (2) a reset button.  Perhaps the happy face?  Restarts the entire game.
 * todo:   (3) a timer that starts when the first button is clicked and ends when the first mine is found
 * todo: - Two drop down menus.
 * todo    (1) Game: The "Game" down menus will have three buttons:
 * todo        i- Reset:  resets the game.  You will be able to hit the button and hit "r" to reset
 * todo       ii- Top Ten: this is a whole lot of stuff
 * todo      iii- eXit:  you will be able to hit the button and hit "x" to exit
 * todo     (2) Help
 * todo:       i- help:  will give a little text for help
 * todo:      ii- about:  a simple about screen
 */

public class Minesweeper extends JFrame
{
    public static void main( String args[] )
    {
        Minesweeper application = new Minesweeper();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    // give the Minesweeper the Minesweeper Model data
    private MinesweeperModel model = new MinesweeperModel();
    private ButtonExtender [][] buttons = new ButtonExtender[10][10];

    // set up GUI
    public Minesweeper()
    {
        super( "Minesweeper" );

        // get content pane and set its layout
        Container container = getContentPane();  // creates the container -- place to put content
        container.setLayout( new GridLayout(10,10) ); // gridLayout for buttons for minesweeper

        // create buttons
        Icon tile = new ImageIcon("tile.png");

        // create an instance of inner class HandlerClass
        // to use for mouse event handling
        HandlerClass handler = new HandlerClass(); // use this is the for loop, too



        // this goes through and creates all of the buttons
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
            {
                ButtonExtender button = new ButtonExtender(tile);
                container.add(button);
                buttons[i][j] = button;
                button.addMouseListener(handler);
                button.setSize(20, 20);
                button.setLocationI(i);
                button.setLocationJ(j);
                button.setUsed(false);
            }

        setSize( 600, 600 );
        setVisible( true );
        setIsNumBombTile();

    } // end Minesweeper constructor

    private void setIsNumBombTile()
    {
        for (int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
            {
                if (model.isOne(i,j)  || model.isTwo(i,j) || model.isThree(i,j) || model.isFour(i,j) ||
                        model.isFive(i,j) || model.isSix(i,j) || model.isSeven(i,j) || model.isEight(i,j))
                    buttons[i][j].setNum(true);
                else if (model.isBomb(i,j)) buttons[i][j].setBomb(true);
                else buttons[i][j].setTile(true);
            }
    }


    // inner class for button event handling
    private class HandlerClass implements MouseListener
    {
        // handle button event
        public void mousePressed(MouseEvent event)
        {
            if (SwingUtilities.isLeftMouseButton(event))
            {
                ButtonExtender button = (ButtonExtender) event.getSource();
                button.setNumRightClicks(1);
                int i = button.getLocationI();
                int j = button.getLocationJ();
                if (button == buttons[i][j])
                {
                    if (model.isBomb(i,j))
                    {
                        int count = 0;
                        button.setIcon(new ImageIcon("mine.png"));
                        for (int k = 0; k < 10; k++)
                            for (int l = 0; l < 10; l++)
                                if (model.isBomb(k,l) && (k != i || l != j))
                                    buttons[k][l].setIcon(new ImageIcon("unexplodedBomb.png"));
                    }

                    else if (model.isOne(i,j))
                    {
                        button.setIcon(new ImageIcon("one.png"));
                        button.setUsed(true);
                    }
                    else if (model.isTwo(i,j))
                    {
                        button.setIcon(new ImageIcon("two.png"));
                        button.setUsed(true);
                    }
                    else if (model.isThree(i,j))
                    {
                        button.setIcon(new ImageIcon("three.png"));
                        button.setUsed(true);
                    }
                    else if (model.isFour(i,j))
                    {
                        button.setIcon(new ImageIcon("four.png"));
                        button.setUsed(true);
                    }
                    else if (model.isFive(i,j))
                    {
                        button.setIcon(new ImageIcon("five.png"));
                        button.setUsed(true);
                    }
                    else if (model.isSix(i,j))
                    {
                        button.setIcon(new ImageIcon("six.png"));
                        button.setUsed(true);
                    }
                    else if (model.isSeven(i,j))
                    {
                        button.setIcon(new ImageIcon("seven.png"));
                        button.setUsed(true);
                    }
                    else if (model.isEight(i,j))
                    {
                        button.setIcon(new ImageIcon("eight.png"));
                        button.setUsed(true);
                    }
                    else
                    {
                        System.out.println("Need to install opening algorithm");
                        //openSquares(i, j);
                    }
                }
            }
            else if (SwingUtilities.isRightMouseButton(event))
            {
                ButtonExtender button = (ButtonExtender) event.getSource();
                for (int i = 0; i < 10; i ++)
                    for (int j = 0; j < 10; j++)
                    {
                        boolean val = button.getIsUsed();
                        int n = button.getNumRightClicks();
                        if (!val)
                        {
                            if (n%3 == 1) button.setIcon(new ImageIcon("questionMark.png")); // ?
                            if (n%3 == 2) button.setIcon(new ImageIcon("tile.png")); // tile
                            if (n%3 == 0) button.setIcon(new ImageIcon("flag.png"));
                            break;
                        }
                    }
                button.setNumRightClicks(button.getNumRightClicks()+1); // iterates the num of rt clicks
            }
        }



        private void openSquares(int row, int col)
        {
            floodFill(col, row);
        }
        private void floodFill(int col, int row)
        {
            if (buttons[row][col].isBomb()) return;
            else if (!buttons[row][col].getIsUsed())
            {
                if (buttons[col][row].isNum())
                {
                    openButton(col,row);
                    buttons[col][row].setUsed(true);
                }
                else if (col != 0 || col != 9 || row != 0 || row != 9)
                {
                    openButton(col,row);
                    buttons[col][row].setUsed(true);
                }

                System.out.println("FLLLOOOOOOOODDDDDDD FFFFFFIIIIIILLLLLLLL");
                System.out.println("Upper Left acout to commence");
                floodFill(col - 1, row - 1);  // upper left
                System.out.println("UUUUUUPPPPPPPPPP");
                floodFill(col-1, row);    // up
                System.out.println("UUUUUPPPP right");
                floodFill(col-1, row+1);  // upper right
                System.out.println("RIGHT");
                floodFill(col,   row+1);  // right
                System.out.println("LOWER RIGHT");
                floodFill(col+1, row+1);  // lower right
                System.out.println("DWN");
                floodFill(col+1, row);    // down
                System.out.println("LOWER LET");
                floodFill(col+1, row-1);  // lower left
                System.out.println("LEFT");
                floodFill(col,   row-1);  // left

            }
        }
        private void openButton(int col, int row)
        {
            if (buttons[col][row].isTile())
                buttons[col][row].setIcon(new ImageIcon("sophia.png"));
            else if (buttons[col][row].isNum())
            {
                if      (model.isOne(col,row))   buttons[col][row].setIcon(new ImageIcon("one.png"));
                else if (model.isTwo(col,row))   buttons[col][row].setIcon(new ImageIcon("two.png"));
                else if (model.isThree(col,row)) buttons[col][row].setIcon(new ImageIcon("three.png"));
                else if (model.isFour(col,row))  buttons[col][row].setIcon(new ImageIcon("four.png"));
                else if (model.isFive(col,row))  buttons[col][row].setIcon(new ImageIcon("five.png"));
                else if (model.isSix(col,row))   buttons[col][row].setIcon(new ImageIcon("six.png"));
                else if (model.isSeven(col,row)) buttons[col][row].setIcon(new ImageIcon("seven.png"));
                else                             buttons[col][row].setIcon(new ImageIcon("eight.png"));
            }
            else
            {
                System.out.println("\n\n\n***** SANITY CHECK FAIL ******* \"openButton()\"\n\n\n");
                System.exit(0);
            }
        }
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {}

    /*    @Override
        public void mousePressed(MouseEvent mouseEvent) {}*/

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {}

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {}

        @Override
        public void mouseExited(MouseEvent mouseEvent) {}
    } // end private inner class HandlerClass


    private enum MINESWEEPER_ELEMENT {ONE, TWO, THREE, FOUR, FIVE, SIX,SEVEN, EIGHT, MINE};
    public class MinesweeperModel
    {
        private MINESWEEPER_ELEMENT[][] elements = new MINESWEEPER_ELEMENT[10][10];


        public MinesweeperModel()
        {
            ArrayList<Integer> X     = new ArrayList<Integer>();
            ArrayList<Integer> Y     = new ArrayList<Integer>();


            getTenRandomUniqueCoordinates(X, Y);
            for (int i = 0; i < 10; i++) System.out.println(i + ":\tx: " + X.get(i) + "\ty: " + Y.get(i));

            // give the array random values for bombs
            insertMines(X,Y);
            insertNumbers();


        }

        private void insertNumbers()
        {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                {
                    if (elements[i][j] != MINESWEEPER_ELEMENT.MINE)
                    {
                        int count = 0;
                        if (i > 0 && j > 0) { int localCount = lookForMine(i-1,j-1); count += localCount;} // look upper left
                        if (i > 0)          { int localCount = lookForMine(i-1,j);   count += localCount;} // look up
                        if (i > 0 && j < 9) { int localCount = lookForMine(i-1,j+1); count += localCount;} // look upper right
                        if (j < 9)          { int localCount = lookForMine(i,j+1);   count += localCount;} // look right
                        if (i < 9 && j < 9) { int localCount = lookForMine(i+1,j+1); count += localCount;} // look lower right
                        if (i < 9)          { int localCount = lookForMine(i+1,j);   count += localCount;} // look down
                        if (i < 9 && j > 0) { int localCount = lookForMine(i+1,j-1); count += localCount;} // look lower left
                        if (j > 0)          { int localCount = lookForMine(i,j-1);   count += localCount;} // look left
                        // insert the number into the grid
                        if (count > 0) insertTheNumber(count,i,j);
                    }
                }
        }

        private void insertTheNumber(int count, int i, int j)
        {
            if      (1 == count) elements[i][j] = MINESWEEPER_ELEMENT.ONE;
            else if (2 == count) elements[i][j] = MINESWEEPER_ELEMENT.TWO;
            else if (3 == count) elements[i][j] = MINESWEEPER_ELEMENT.THREE;
            else if (4 == count) elements[i][j] = MINESWEEPER_ELEMENT.FOUR;
            else if (5 == count) elements[i][j] = MINESWEEPER_ELEMENT.FIVE;
            else if (6 == count) elements[i][j] = MINESWEEPER_ELEMENT.SIX;
            else if (7 == count) elements[i][j] = MINESWEEPER_ELEMENT.SEVEN;
            else                 elements[i][j] = MINESWEEPER_ELEMENT.EIGHT;

        }

        private int lookForMine(int i, int j)
        {
            if (elements[i][j] == MINESWEEPER_ELEMENT.MINE) return 1;
            else return 0;
        }

        private void insertMines(ArrayList<Integer> x, ArrayList<Integer> y)
        {
            for (int i = 0; i < 10; i++)
            {
                elements[x.get(i)][y.get(i)] = MINESWEEPER_ELEMENT.MINE;
            }
        }

        public void getTenRandomUniqueCoordinates(ArrayList<Integer> X, ArrayList<Integer> Y)
        {
            // get the first set of coordinates
            int x = getRandomNum(); X.add(x);
            int y = getRandomNum(); Y.add(y);

            // then get nine more, coding for uniqueness
            for (int i = 0; i < 10; i++)
            {
                boolean areUnique = true;
                x = getRandomNum();
                y = getRandomNum();
                for (int j = 0; j < X.size(); j++)
                    if (x == X.get(j) && y == Y.get(j))
                    {
                        areUnique = false;
                        break;
                    }
                if (areUnique) { X.add(x); Y.add(y);}
                else i--;
            }
        }

        private int getRandomNum()
        {
            Random seed = new Random();
            return seed.nextInt(10);
        }

        public boolean isBomb(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.MINE);
        }
        public boolean isOne(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.ONE);
        }
        public boolean isTwo(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.TWO);
        }
        public boolean isThree(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.THREE);
        }
        public boolean isFour(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.FOUR);
        }
        public boolean isFive(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.FIVE);
        }
        public boolean isSix(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.SIX);
        }
        public boolean isSeven(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.SEVEN);
        }
        public boolean isEight(int i, int j)
        {
            return (elements[i][j] == MINESWEEPER_ELEMENT.EIGHT);

        }
        public MINESWEEPER_ELEMENT getElementAt(int i, int j)
        {
            return elements[i][j];
        }
    }

} // end class M