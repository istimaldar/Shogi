package components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HandCell extends Rectangle {

  private Field belongTo;
  private Figure atThisCell;
  private int mode = Cell.CAN_BE_SELECTED;
  private static int maxAtLine = 9;

  public HandCell(int number, double x, double y, double width, double height, Field belongTo) {
    super(x, y, width, height);
    atThisCell = null;
    this.belongTo = belongTo;
    setActions();
  }

  static public int getMaxAtLine() {
    return maxAtLine;
  }

  public void setMode(int mode) {
    this.mode = mode;
    switch (mode) {
      case Cell.CAN_BE_SELECTED:
        setFill(Color.WHITE);
        break;
      case Cell.IS_SELECTED:
        setFill(Color.BLUE);
        break;
      default:
        this.mode = Cell.CAN_BE_SELECTED;
        setFill(Color.WHITE);
        break;
    }
  }

  public void addFigure(Figure figure) {
    if (atThisCell != null)
      return;
    atThisCell = figure;
    atThisCell.setX(getX());
    atThisCell.setY(getY());
    atThisCell.setVisible(true);
    atThisCell.setPlacedAt(this);
  }

  public Figure removeFigure() {
    if (atThisCell == null)
      return null;
    atThisCell.setVisible(false);
    atThisCell.setPlacedAt(null);
    Figure removedFigure = atThisCell;
    atThisCell = null;
    return removedFigure;
  }

  public final Figure getAtThisCell() {
    return atThisCell;
  }

  public int getMode() {
    return mode;
  }
  
  public Field getBelongTo() {
    return belongTo;
  }

  public void setActions() {
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        if (mode == Cell.CAN_BE_SELECTED) {
          if (atThisCell == null) {
            belongTo.makeEveryCellWhite();
            return;
          }
          belongTo.setAllModes(belongTo.getModel().markForDrop(atThisCell.getModel()));
        }
      }
    });
  }

}
