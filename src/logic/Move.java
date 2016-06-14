package logic;

import java.util.concurrent.CountDownLatch;

import application.Storage;
import components.Cell;
import components.Figure;

public class Move implements Turn {

  private int fromX;
  private int fromY;
  private int toX;
  private int toY;
  private Figure what;
  private boolean turn = false;

  public Move(Figure what, Cell where, boolean trn) {
    execute(what, where, trn);
  }

  public Move(Figure what, Cell where) {
    execute(what, where);
  }

  @Override
  public void execute(Figure what, Cell where, boolean trn) {
    if (turn == false && trn == true)
      turn = true;
    Cell from = (Cell) what.getPlacedAt();
    final CountDownLatch latch = new CountDownLatch(1);
    Thread myThread = new Thread(new Runnable() {
      public void run() {
        where.getModel().move(from.getModel());
        latch.countDown();
        return;
      }
    });
    myThread.start();
    where.addFigure(from.removeFigure());
    where.getBelongTo().makeEveryCellWhite();
    try {
      latch.await();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    myThread.interrupt();
    if(!turn) turn = where.getModel().askForTurn();
    if (turn) {
      where.getAtThisCell().turnBack();
      where.getAtThisCell().redraw();
    }
    fromX = from.getHorizontalNumber();
    fromY = from.getVerticalNumber();
    toX = where.getHorizontalNumber();
    toY = where.getVerticalNumber();
    this.what = what;
    if (!where.getModel().getBelongTo().checkWinningState())
      where.getModel().endOfMapping();
  }

  public void execute(Figure what, Cell where) {
    execute(what, where, false);
  }

  @Override
  public void cancel() {
    Cell where = ((Cell) what.getPlacedAt()).getBelongTo().getCell(fromX, fromY);
    Cell from = (Cell) what.getPlacedAt();
    where.getModel().move(from.getModel());
    where.addFigure(from.removeFigure());
    where.getBelongTo().makeEveryCellWhite();
    if (turn) {
      where.getAtThisCell().turnFront();
      where.getAtThisCell().redraw();
    }
    where.getModel().endOfMapping();
  }

  @Override
  public void executeAgain() {
    Cell where = ((Cell) what.getPlacedAt()).getBelongTo().getCell(toX, toY);
    execute(what, where);
  }

  @Override
  public String toString() {
    int fromX = 9 - this.fromX;
    int toX = 9 - this.toX;
    char fromY = (char) (Storage.CODE_OF_A + this.fromY);
    char toY = (char) (Storage.CODE_OF_A + this.toY);
    String promote = "";
    if (turn)
      promote = "+";
    return new String(what.toString() + fromY + fromX + "-" + toY + toX + promote);
  }

}
