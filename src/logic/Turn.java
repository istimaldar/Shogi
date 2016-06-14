package logic;

import components.Cell;
import components.Figure;

public interface Turn {

  void execute(Figure what, Cell where);
  
  void execute(Figure what, Cell where, boolean trn);
  
  void executeAgain();

  void cancel();

  String toString();
}
