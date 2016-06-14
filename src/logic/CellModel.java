package logic;

import java.util.Arrays;
import java.util.Optional;

import application.Storage;
import components.Cell;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class CellModel {
  private int verticalNumber;
  private int horizontalNumber;
  boolean isUnderFirstPlayersAttack;
  boolean isUnderSecondPlayersAttack;
  FigureModel atThisCell;
  FieldModel belongTo;

  public CellModel(FieldModel belongTo, int horizontalNumber, int verticalNumber) {
    this.belongTo = belongTo;
    this.verticalNumber = verticalNumber;
    this.horizontalNumber = horizontalNumber;
  }

  public int[][] getMoves() {
    return getMoves(true, true);
  }

  int[][] getMoves(boolean checkForCheck, boolean checkForTurn) {
    int[][] moves = new int[belongTo.getHorizontalSize()][belongTo.getVerticalSize()];
    for (int[] current : moves)
      Arrays.fill(current, Cell.CAN_BE_SELECTED);
    if (checkForTurn && belongTo.getIsFirstPlayersTurn() != atThisCell.getIsWhite())
      return moves;
    moves[horizontalNumber][verticalNumber] = Cell.IS_SELECTED;
    for (int[] move : atThisCell.getShortMoves()) {
      if ((verticalNumber + move[0] < 9) && (verticalNumber + move[0] >= 0)
          && (horizontalNumber + move[1] < 9) && (horizontalNumber + move[1] >= 0)) {
        if (belongTo.getCell(horizontalNumber + move[1],
            verticalNumber + move[0]).atThisCell == null) {
          moves[horizontalNumber + move[1]][verticalNumber + move[0]] = Cell.CAN_BE_MOVED;
        } else if (belongTo.getCell(horizontalNumber + move[1], verticalNumber + move[0]).atThisCell
            .belogToTheSamePlayer(atThisCell) == false) {
          moves[horizontalNumber + move[1]][verticalNumber + move[0]] = Cell.CAN_BE_BEATED;
        }
      }
    }
    for (int[] move : atThisCell.getLongMoves()) {
      int currentVerticalNumber = verticalNumber, currentHorizontalNumber = horizontalNumber;
      while ((currentVerticalNumber + move[0] < 9) && (currentVerticalNumber + move[0] >= 0)
          && (currentHorizontalNumber + move[1] < 9) && (currentHorizontalNumber + move[1] >= 0)) {
        if (belongTo.getCell(currentHorizontalNumber + move[1],
            currentVerticalNumber + move[0]).atThisCell == null) {
          moves[currentHorizontalNumber + move[1]][currentVerticalNumber + move[0]] =
              Cell.CAN_BE_MOVED;
          currentHorizontalNumber += move[1];
          currentVerticalNumber += move[0];
        } else if (belongTo.getCell(currentHorizontalNumber + move[1],
            currentVerticalNumber + move[0]).atThisCell.belogToTheSamePlayer(atThisCell) == false) {
          moves[currentHorizontalNumber + move[1]][currentVerticalNumber + move[0]] =
              Cell.CAN_BE_BEATED;
          break;
        } else
          break;
      }
    }
    for (int[] move : atThisCell.getJumpMoves()) {
      if ((verticalNumber + move[0] < 9) && (verticalNumber + move[0] >= 0)
          && (horizontalNumber + move[1] < 9) && (horizontalNumber + move[1] >= 0)) {
        if (belongTo.getCell(horizontalNumber + move[1],
            verticalNumber + move[0]).atThisCell == null) {
          moves[horizontalNumber + move[1]][verticalNumber + move[0]] = Cell.CAN_BE_MOVED;
        } else if (belongTo.getCell(horizontalNumber + move[1], verticalNumber + move[0]).atThisCell
            .belogToTheSamePlayer(atThisCell) == false) {
          moves[horizontalNumber + move[1]][verticalNumber + move[0]] = Cell.CAN_BE_BEATED;
        }
      }
    }
    if (checkForCheck == false)
      return moves;
    for (int i = 0; i < moves.length; i++) {
      for (int j = 0; j < moves[i].length; j++) {
        if (moves[i][j] == Cell.CAN_BE_MOVED || moves[i][j] == Cell.CAN_BE_BEATED) {
          int[][] dangeorousMoves =
              belongTo.getDangerousCellsAfterMove(this, belongTo.getCell(i, j));
          if (belongTo.getCurrentKing().getPlacedAt() != this) {
            if (dangeorousMoves[belongTo.getCurrentKing().getPlacedAt().horizontalNumber][belongTo
                .getCurrentKing().getPlacedAt().verticalNumber] == 1) {
              moves[i][j] = Cell.CAN_BE_SELECTED;
            }
          } else {
            if (dangeorousMoves[i][j] == 1)
              moves[i][j] = Cell.CAN_BE_SELECTED;
          }
        }
      }
    }
    return moves;
  }

  public void addFigureFirst(FigureModel model) {
    atThisCell = model;
    if (model == null)
      return;
    if (model.getIsWhite() == true)
      belongTo.addFirstPlayerFigure(model);
    else
      belongTo.addSecondPlayerFigure(model);
    model.setPlacedAt(this);
  }

  public void addFigure(FigureModel model) {
    atThisCell = model;
    if (model == null)
      return;
    model.setPlacedAt(this);
  }

  public FigureModel removeFigure() {
    FigureModel model = atThisCell;
    if (model == null)
      return null;
    atThisCell.setPlacedAt(null);
    atThisCell = null;
    return model;
  }

  public void move(CellModel from) {
    addFigure(from.removeFigure());
    belongTo.endTurn();
  }

  public boolean askForTurn() {
    if (atThisCell.getIsTurned() == true)
      return false;
    if ((atThisCell.getIsWhite() && verticalNumber < 3) // If figure made a move into turnable zone
        || (!atThisCell.getIsWhite() && verticalNumber > 5)) {
      if (Storage.isSecondPlayerAComputer == true && atThisCell.getIsWhite() == false)
        return true;
      if (Storage.isFirstPlayerAComputer == true && atThisCell.getIsWhite() == true)
        return true;
      if (Storage.isReplay)
        return false;
      Alert alert = new Alert(AlertType.CONFIRMATION); // Ask for the confirmation
      alert.setTitle("Notification");
      alert.setHeaderText("Now you can turn the figue");
      alert.setContentText("Do you want to turn the figure?");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        atThisCell.setIsTurned(true);
        belongTo.endTurn();
        return true; // Figure is turned
      }
    }
    return false;
  }
  
  public int beat() {
    int numberOfCell;
    atThisCell.setIsTurned(false);
    if (atThisCell.getIsWhite() == true) {
      numberOfCell = belongTo.addSecondPlayerToHand(atThisCell) - 1;
      belongTo.removeFirstPlayerFigure(atThisCell);
    } else {
      numberOfCell = belongTo.addFirstPlayerToHand(atThisCell) - 1;
      belongTo.removeSecondPlayerFigure(atThisCell);
    }
    atThisCell.changeSide();
    removeFigure();
    return numberOfCell;
  }

  public int drop() {
    int numberOfCell;
    if (belongTo.getReadyForDrop().getIsWhite()) {
      numberOfCell = belongTo.removeFirstPlayerFromHand(belongTo.getReadyForDrop());
      belongTo.addFirstPlayerFigure(belongTo.getReadyForDrop());
    } else {
      numberOfCell = belongTo.removeSecondPlayerFromHand(belongTo.getReadyForDrop());
      belongTo.addSecondPlayerFigure(belongTo.getReadyForDrop());
    }
    addFigure(belongTo.getReadyForDrop());
    belongTo.endTurn();
    return numberOfCell;
  }


  public int getVerticalNumber() {
    return verticalNumber;
  }

  public int getHorizontalNumber() {
    return horizontalNumber;
  }

  public boolean isWhiteFigureDroped() {
    return belongTo.getReadyForDrop().getIsWhite();
  }

  public void endOfMapping() {
    belongTo.endOfMapping();
  }

  public FieldModel getBelongTo() {
    return belongTo;
  }
}
