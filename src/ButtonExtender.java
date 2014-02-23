/**
 * Created by bob on 2/19/14.
 */
import javax.swing.*;
import javax.swing.Icon;
public class ButtonExtender extends JButton
{
    ButtonExtender(Icon tile){
        super(tile);
    }

    private boolean isUsed;
    public boolean getIsUsed() {
        return isUsed;
    }
    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
    private int numRightClicks;

    public int getNumRightClicks() {
        return numRightClicks;
    }

    public void setNumRightClicks(int numRightClicks) {
        this.numRightClicks = numRightClicks;
    }

    private int locationI;
    private int locationJ;

    public int getLocationI() {
        return locationI;
    }

    public void setLocationI(int locationI) {
        this.locationI = locationI;
    }

    public int getLocationJ() {
        return locationJ;
    }

    public void setLocationJ(int locationJ) {
        this.locationJ = locationJ;
    }

    private boolean isNum;
    private boolean isBomb;
    private boolean isTile;

    public boolean isNum() {
        return isNum;
    }

    public void setNum(boolean isNum) {
        this.isNum = isNum;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public boolean isTile() {
        return isTile;
    }

    public void setTile(boolean isTile) {
        this.isTile = isTile;
    }
}