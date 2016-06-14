package logic;

import java.util.concurrent.CountDownLatch;

import application.Storage;
import components.Cell;
import components.HandCell;
import components.Figure;

public class Drop implements Turn {

  private int toX;
  private int toY;
  private Figure what;

  public Drop(Figure what, Cell where) {
    execute(what, where);
  }

  @Override
  public void execute(Figure what, Cell where, boolean trn) {
    final CountDownLatch latch = new CountDownLatch(1);
    final int[] par = new int[1];
    final boolean[] par2 = new boolean[1];
    Thread myThread = new Thread(new Runnable() {
      public void run() {
        where.getBelongTo().getModel().setReadyForDrop(what.getModel());
        par2[0] = where.getModel().isWhiteFigureDroped();
        par[0] = where.getModel().drop();
        latch.countDown();
        return;
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
    boolean isWhite=par2[0];
    int index = par[0];
    Figure figure;
    if (isWhite)
      figure = where.getBelongTo().dropFirstPlayerFigure(index);
    else
      figure = where.getBelongTo().dropSecondPlayerFigure(index);
    where.addFigure(figure);
    figure.turnFront();
    figure.redraw();
    where.getBelongTo().makeEveryCellWhite();
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
    what.changeSide();
    what.setMoves();
    what.getModel().getPlacedAt().getBelongTo().changeFiguresSide(what.getModel());
    Cell where = ((Cell) what.getPlacedAt());
    int numberOfCell = where.getModel().beat();
    where.removeFigure();
    where.getBelongTo().moveFigureToTheHand(what, numberOfCell);
    where.getModel().endOfMapping();
  }

  @Override
  public void executeAgain() {
    Cell where = ((HandCell) what.getPlacedAt()).getBelongTo().getCell(toX, toY);
    execute(what, where);
  }

  @Override
  public String toString() {
    int toX = 9 - this.toX;
    char toY = (char) (Storage.CODE_OF_A + this.toY);
    return new String(what.toString() + "*" + toY + toX);
  }

}
