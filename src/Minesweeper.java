import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
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
                        else
                        {
                            sweepForward(i,j);
                            break;
                        }
                    }
                }
        }

        private void sweepForward(int i, int j)
        {
            boolean isBomb = false;
            while (!isBomb)
            {
                isBomb = reveal(i,j);
                if (j%10 != 0) j++;
                else
                {
                    j = 0; i++;
                }

            }
        }

        private boolean reveal(int i, int j) // reveals what;s in the tile's location
        {
            MINESWEEPER_ELEMENT element = model.getElementAt(i,j);
            if (element == MINESWEEPER_ELEMENT.BOMB)
            {
                buttons[i][j].setIcon(new ImageIcon("mine.png"));
                return true;
            }
            else
            {
                buttons[i][j].setIcon(new ImageIcon("revealedTile.png"));
                return false;
            }
        }

    } // end private inner class ButtonHandler


    private enum MINESWEEPER_ELEMENT {ONE, TWO, THREE, FOUR, FIVE, QUESTION_MARK, BOMB, FLAG, BLANK};
    private class MinesweeperModel
    {
        private MINESWEEPER_ELEMENT[][] elements = new MINESWEEPER_ELEMENT[10][10];


        // so we want to generate 10 random numbers
        public MinesweeperModel()
        {
            ArrayList<Integer> ranNumArr = new ArrayList<Integer>();
            ranNumArr = getTenRandomNumbers();
            System.out.println("\n\nInside model asdfasdfasdfasdfasdfasdfasdf.\n\n");
            for (int j = 0; j < 10; j++)
            {
                System.out.println(j + ": " + ranNumArr.get(j));
            }
            // give the array random values for bombs
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                {
                    if (i%2 == 0 && j%2 == 0) elements[i][j] = MINESWEEPER_ELEMENT.BOMB;   // TODO: randomize everything
                    else elements[i][j] = MINESWEEPER_ELEMENT.BLANK;
                }

        }
        public ArrayList getTenRandomNumbers()
        {
            ArrayList<Integer> ranNumArr = new ArrayList<Integer>();
            Random seed = new Random();
            int num;
            for (int i = 0; i < 10; i++)
            {
                num = 1 + seed.nextInt(100);
                if (0 == i) ranNumArr.add(num);
                else
                {
                    for (int j = 0; j < Array.getLength(ranNumArr); j++)
                    {
                        if (ranNumArr.get(j) != num)
                        {
                            ranNumArr.add(num);
                            break;
                        }
                    }
                }
            }
            return ranNumArr;
        }

        public boolean isBomb(int i, int j)
        {
            if (elements[i][j] == MINESWEEPER_ELEMENT.BOMB) return true;
            return false;
        }

        public MINESWEEPER_ELEMENT getElementAt(int i, int j)
        {
            return elements[i][j];
        }
    }

} // end class M