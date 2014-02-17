import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bob on 2/15/14.
 */

/*
 * So what is the idea here?
 * I'll go through the code, but first, I want to explain the idea of a "model".
 * There are going to be two 10 x 10 grids in this program.  The first one is going to be the
 * JButton grid.  This will be the GUI grid, which will show everything on the screen.
 * The second 10 x 10 grid is going to be the model grid, which will keep all of the data about the
 * game -- think of it like a board that only us programmers can see, where everything is revealed already.
 * Here's a way to illustrate what the model is for: when we first start the game, we will go to the model and
 * randomly place 10 mines around the board.  Then, based on where those mines are, we will place our numbers.
 * The rest of the squares on the board will be blank.
 * That's what WE see.  What the player sees is the JFrame -- the grid of JButtons, all set to a blank tile.
 * So when the player clicks on a square, we call the "handler".  It goes to the model and looks at the tile
 * that the player clicked and the button that the player clicked, different things will happen (a flag will
 * be placed, a question mark will be placed, the algorithm that reveals empty squares and numbers will run,
 * ot a bomb will explode, ending the game).
 *
 * Ok.  Now that that's done, let's go through the code...
 *
 * First we create a class called Minesweeper.  Notice that it "extends" the JFrame class.
 * This is because we are inheriting the JFrame class, the class that has all of the gui stuff
 * in it.
 *
 * Then we create a main, and in that main, we create an instance of Minesweeper called "application."
 * Then we see the "setDefault... EXIT_ON_CLOSE".  This means that when this line is done -- the user
 * has hit the "X" button, closing the window -- that the program is finished.
 * So the entire program takes place within those two lines!
 * So the rest of the program MUST take place within the constructor of the application.  And it does.
 *
 * The next two lines create instances of the "MinesweeperModel" and an array of type "JButton",
 * We'll get to what the model is in a moment.
 * The array of JButton is just that -- an array of buttons, which are little windows inside of the container.
 * The container is the entire window.
 * That means that the buttons are each square in the Minesweeper game, and we now have 100 of them.  Perfect.
 *
 * Next we get into the constructor of Minesweeper.  The line "super(MineSweeper)" just puts the words "Minesweeper"
 * at the top of the container.  We can always change that to whatever.  Yonathens's Minesweeper or something.
 *
 * Line:
 * Next we create the container, and set the container to a layout style.  I chose "GridLayout" because that seemed
 * about right.
 *
 * Line:
 * Create an empty tile -- 100 of these, what the grid looks like at the beginning fo the game.
 *
 * Line:
 * Create the buttonHandler.  This does exactly what it says it does.
 *
 * Line:
 * Then we create a nested for loop, and put in 100 buttons.  We set them all to "tile", as that's all
 * that we want visible.  We also set the size of the button and add the button handler, which listens
 * to each button.
 *
 * At the moment, we only have three different kind of tiles utillized:  a mine icon, a tile icon, and a "greyed"
 * icon (meaning that there is neither a bomb nor a number in the square, and that is has been checked).
 *
 * As of this moment, the only thing the code does is this:  it looks at a tile.  If that tile has a mine in it,
 * then the mine is revealed.  If that tile does NOT have a mine in it, then a process starts:  first, the tile
 * is "greyed out", and the process checks the tile to the right and repeats the process.  The process stops when
 * either a tile is hit or the end of the row is hit.
 *
 * That's it for the time being.
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
    private JButton [][] buttons = new JButton[10][10];

    // set up GUI
    public Minesweeper()
    {
        super( "Minesweeper" );

        // get content pane and set its layout
        Container container = getContentPane();  // creates the container -- place to put content
        container.setLayout( new GridLayout(10,10) ); // gridLayout for buttons for minesweeper

        // create buttons
        Icon tile = new ImageIcon("tile.png");

        // create an instance of inner class ButtonHandler
        // to use for button event handling
        ButtonHandler handler = new ButtonHandler(); // use this is the for loop, too


        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
            {
                JButton button = new JButton(tile);
                container.add(button);
                buttons[i][j] = button;
                button.addActionListener(handler);
                button.setSize(20, 20);
            }

        setSize( 600, 600 );
        setVisible( true );

    } // end Minesweeper constructor


    // inner class for button event handling
    private class ButtonHandler implements ActionListener {

        // handle button event
        public void actionPerformed( ActionEvent event )
        {
            JButton button = (JButton) event.getSource();
            for (int i = 0; i < 10; i ++)
                for (int j = 0; j < 10; j++)
                {
                    if (button == buttons[i][j])
                    {
                        if (model.isBomb(i,j))
                        {
                            button.setIcon(new ImageIcon("mine.png"));
                            break;
                        }
                        else if (model.isOne(i,j))
                        {
                            button.setIcon(new ImageIcon("one.png"));
                        }
                        else if (model.isTwo(i,j))
                        {
                            button.setIcon(new ImageIcon("two.png"));
                        }
                        else if (model.isThree(i,j))
                        {
                            button.setIcon(new ImageIcon("three.png"));
                        }
                        else if (model.isFour(i,j))
                        {
                            button.setIcon(new ImageIcon("four.png"));
                        }
                        else if (model.isFive(i,j))
                        {
                            button.setIcon(new ImageIcon("five.png"));
                        }
                        else if (model.isSix(i,j))
                        {
                            button.setIcon(new ImageIcon("six.png"));
                        }
                        else if (model.isSeven(i,j))
                        {
                            button.setIcon(new ImageIcon("seven.png"));
                        }
                        else if (model.isEight(i,j))
                        {
                            button.setIcon(new ImageIcon("eight.png"));
                        }
                        sweepForward(i,j);
                        break;
                    }
                }
        }

        private void sweepForward(int i, int j)
        {
            System.out.println("HI THERE!");

        /*    boolean isBomb = false;
            boolean isOne = false;
            while (!isBomb)
            {
                isBomb = reveal(i,j);
                if (j%10 != 0) j++;
                else
                {
                    j = 0; i++;
                }

            }*/
        }

        private boolean reveal(int i, int j) // reveals what;s in the tile's location
        {
            MINESWEEPER_ELEMENT element = model.getElementAt(i,j);
            if (element == MINESWEEPER_ELEMENT.MINE)
            {
                buttons[i][j].setIcon(new ImageIcon("mine.png"));
                return true;
            }
            else if (element == MINESWEEPER_ELEMENT.ONE)
            {
                buttons[i][j].setIcon(new ImageIcon("one.png"));
                return true;
            }
            else
            {
                buttons[i][j].setIcon(new ImageIcon("aTile.ico"));
                return false;
            }
        }

    } // end private inner class ButtonHandler


    private enum MINESWEEPER_ELEMENT {ONE, TWO, THREE, FOUR, FIVE, SIX,SEVEN, 
                                      EIGHT, QUESTION_MARK, MINE, FLAG, BLANK};
    private class MinesweeperModel
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

            /*
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                {
                    if (i%2 == 0 && j%2 == 0) elements[i][j] = MINESWEEPER_ELEMENT.BOMB;   // TODO: randomize everything
                    else elements[i][j] = MINESWEEPER_ELEMENT.BLANK;
                }
            */
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

   /*     private void bottomRow()
        {
            int count = 0;
            for (int j = 0; j < 10; j++)
            {
                if (elements[j][9] == MINESWEEPER_ELEMENT.MINE) continue;
                else
                {
                    if (           elements[8][j] == MINESWEEPER_ELEMENT.MINE) count++; // look up
                    if (j < 9 && elements[9][j+1] == MINESWEEPER_ELEMENT.MINE) count++; // look right
                    if (j > 0 && elements[9][j-1] == MINESWEEPER_ELEMENT.MINE) count++; // look left
                    insertTheNumber(count,9,j);
                }
            }
        }


        private void topRow()
        {
          int count = 0;
          for (int j = 0; j < 10; j++)
          {
              if (elements[0][j] == MINESWEEPER_ELEMENT.MINE) continue;
              else
              {
                  if (j < 9 && elements[0][j+1] == MINESWEEPER_ELEMENT.MINE) count++; // look right


                  if (           elements[1][j] == MINESWEEPER_ELEMENT.MINE) count++; // look down
                  if (j < 9 && elements[0][j+1] == MINESWEEPER_ELEMENT.MINE) count++; // look right
                  if (j > 0 && elements[0][j-1] == MINESWEEPER_ELEMENT.MINE) count++; // look left
                  insertTheNumber(count,0,j);
              }
          }
        }
        private void insertTheNumber(int count, int i, int j)
        {
            System.out.println("The count is " + count);
            if (0 == count) return;
            else
            {
                if      (1 == count) elements[i][j] = MINESWEEPER_ELEMENT.ONE;
                else if (2 == count) elements[i][j] = MINESWEEPER_ELEMENT.TWO;
                else if (2 == count) elements[i][j] = MINESWEEPER_ELEMENT.THREE;
                else if (4 == count) elements[i][j] = MINESWEEPER_ELEMENT.FOUR;
                else                 elements[i][j] = MINESWEEPER_ELEMENT.FIVE;
            }
        }
        */

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