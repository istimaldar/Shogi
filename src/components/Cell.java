package components;

import java.util.concurrent.CountDownLatch;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import logic.Beat;
import logic.CellModel;
import logic.Drop;
import logic.Move;


public class Cell extends Rectangle {

  public final static int CAN_BE_SELECTED = 0;
  public final static int IS_SELECTED = 1;
  public final static int CAN_BE_MOVED = 2;
  public final static int CAN_BE_BEATED = 3;
  public final static int CAN_BE_DROPED = 4;
  private Field belongTo;
  private Figure atThisCell;
  private CellModel model;
  private int verticalNumber;
  private int horizontalNumber;
  private int mode;

  public Cell(int horizontalNumber, int verticalNumber, double width, double height, Paint fill,
      Field belongTo) {
    super(width, height, fill);
    atThisCell = null;
    this.verticalNumber = verticalNumber;
    this.horizontalNumber = horizontalNumber;
    this.belongTo = belongTo;
    setActions();
    mode = CAN_BE_SELECTED;
  }

  public Cell(int horizontalNumber, int verticalNumber, double x, double y, double width,
      double height, Field belongTo) {
    super(x, y, width, height);
    atThisCell = null;
    this.verticalNumber = verticalNumber;
    this.horizontalNumber = horizontalNumber;
    this.belongTo = belongTo;
    setActions();
    mode = CAN_BE_SELECTED;
  }

  public Cell(int horizontalNumber, int verticalNumber, double width, double height,
      Field belongTo) {
    this(horizontalNumber, verticalNumber, 0, 0, width, height, belongTo);
  }

  public Cell(int horizontalNumber, int verticalNumber, Field belongTo) {
    this(horizontalNumber, verticalNumber, 0, 0, 0, 0, belongTo);
  }

  public int getMode() {
    return mode;
  }

  public Field getBelongTo() {
    return belongTo;
  }

  public int getVerticalNumber() {
    return verticalNumber;
  }

  public int getHorizontalNumber() {
    return horizontalNumber;
  }

  public void addFigure(Figure figure) {
    if (atThisCell != null)
      return;
    atThisCell = figure;
    atThisCell.setX(getX());
    atThisCell.setY(getY());
    atThisCell.setVisible(true);
    atThisCell.setPlacedAt(this);
    atThisCell.xPos = horizontalNumber;
    atThisCell.yPos = verticalNumber;
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

  public void setMode(int mode) {
    this.mode = mode;
    switch (mode) {
      case CAN_BE_SELECTED:
        setFill(Color.WHITE);
        break;
      case IS_SELECTED:
        setFill(Color.BLUE);
        break;
      case CAN_BE_MOVED:
        setFill(Color.GREEN);
        break;
      case CAN_BE_BEATED:
        setFill(Color.RED);
        break;
      case CAN_BE_DROPED:
        setFill(Color.BROWN);
        break;
      default:
        this.mode = CAN_BE_SELECTED;
        setFill(Color.WHITE);
        break;
    }
  }

  private void setActions() {
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        turn();
      }
    });
  }

  public void turn() {
    boolean figureMoved = true;
    if (getMode() == CAN_BE_SELECTED) {
      drawPotentialMoves();
      figureMoved = false;
    } else if (getMode() == CAN_BE_MOVED)
      belongTo.getModel().newTurn(new Move(belongTo.getSelected().getAtThisCell(), this));
    else if (getMode() == CAN_BE_BEATED)
      belongTo.getModel().newTurn(new Beat(belongTo.getSelected().getAtThisCell(), this));
    else if (getMode() == CAN_BE_DROPED)
      belongTo.getModel().newTurn(new Drop(belongTo.getDropedFigure(), this));
    belongTo.viewTurns(figureMoved);
  }

  public CellModel getModel() {
    return model;
  }

  public void setModel(CellModel model) {
    this.model = model;
    if (atThisCell != null)
      model.addFigureFirst(atThisCell.getModel());
  }

  private void drawPotentialMoves() {
    if (atThisCell != null) {
      final CountDownLatch latch = new CountDownLatch(1);
      final int[][] turns = new int[9][9];
      Thread myThread = new Thread(new Runnable() {
        public void run() {
          int[][] turn = model.getMoves();
          for(int i=0;i<turn.length;i++) turns[i]=turn[i];
          latch.countDown();
        }
      });
      myThread.start();
      try {
        latch.await();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      myThread.interrupt();
      belongTo.setModes(turns);
    }
    else
      belongTo.makeEveryCellWhite();
  }

}
