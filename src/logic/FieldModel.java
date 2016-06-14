package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import application.GameWindow;
import application.MenuWindow;
import application.Storage;
import components.Cell;
import components.Field;
import components.Figure;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class FieldModel {
  private static CellModel[][] cellModels;
  private boolean isFirstPlayersTurn = true;
  private FigureModel whiteKing;
  private FigureModel blackKing;
  private FigureModel readyForDrop;
  private int verticalSize;
  private int horizontalSize;
  private static final LinkedList<FigureModel> firstPlayerOnField = new LinkedList<FigureModel>();
  private static final LinkedList<FigureModel> secondPlayerOnField = new LinkedList<FigureModel>();
  private static final LinkedList<FigureModel> firstPlayerInHand = new LinkedList<FigureModel>();
  private static final LinkedList<FigureModel> secondPlayerInHand = new LinkedList<FigureModel>();
  private Field mapping;
  private static final LinkedList<Turn> turnsList = new LinkedList<Turn>();
  public ListIterator<Turn> currentTurn;
  private static int numberOfGame = 1;
  private int winningState = Storage.NO_WINNERS;

  public class Modes {
    public int[][] moves;
    public int[][] hand;
  }

  public FieldModel(int verticalSize, int horizontalSize, Cell[][] cells, Field mapping) {
    firstPlayerOnField.clear();
    secondPlayerOnField.clear();
    firstPlayerInHand.clear();
    secondPlayerInHand.clear();
    turnsList.clear();
    currentTurn = turnsList.listIterator();
    this.verticalSize = verticalSize;
    this.horizontalSize = horizontalSize;
    this.mapping = mapping;
    cellModels = new CellModel[this.horizontalSize][this.verticalSize];
    for (int i = 0; i < this.horizontalSize; i++) {
      for (int j = 0; j < this.verticalSize; j++) {
        cellModels[i][j] = new CellModel(this, i, j);
        cells[i][j].setModel(cellModels[i][j]);
      }
    }
    for (FigureModel first : firstPlayerOnField)
      if (first.getIsKing())
        whiteKing = first;
    for (FigureModel second : secondPlayerOnField)
      if (second.getIsKing())
        blackKing = second;
  }

  public void setBlackKing(FigureModel blackKing) {
    this.blackKing = blackKing;
  }

  public void setWhiteKing(FigureModel whiteKing) {
    this.whiteKing = whiteKing;
  }

  public int getVerticalSize() {
    return verticalSize;
  }

  public int getHorizontalSize() {
    return horizontalSize;
  }

  public CellModel getCell(int horizontalNumber, int verticalNumber) {
    return cellModels[horizontalNumber][verticalNumber];

  }

  public void addFirstPlayerFigure(FigureModel figure) {
    firstPlayerOnField.add(figure);
  }

  public void addSecondPlayerFigure(FigureModel figure) {
    secondPlayerOnField.add(figure);
  }

  public void removeFirstPlayerFigure(FigureModel figure) {
    firstPlayerOnField.remove(firstPlayerOnField.indexOf(figure));
  }

  public void removeSecondPlayerFigure(FigureModel figure) {
    secondPlayerOnField.remove(secondPlayerOnField.indexOf(figure));
  }

  public int addFirstPlayerToHand(FigureModel figure) {
    firstPlayerInHand.add(figure);
    return firstPlayerInHand.size();
  }

  public int addSecondPlayerToHand(FigureModel figure) {
    secondPlayerInHand.add(figure);
    return secondPlayerInHand.size();
  }

  public int removeFirstPlayerFromHand(FigureModel figure) {
    int index = firstPlayerInHand.indexOf(figure);
    firstPlayerInHand.remove(index);
    return index;
  }

  public int removeSecondPlayerFromHand(FigureModel figure) {
    int index = secondPlayerInHand.indexOf(figure);
    secondPlayerInHand.remove(index);
    return index;
  }

  public boolean move(CellModel from, CellModel to) {
    return true;
  }

  int[][] getDangerousCells() { // Obtain the attacked cells
    int[][] dangerousCells = new int[horizontalSize][verticalSize];
    for (int[] current : dangerousCells)
      Arrays.fill(current, Cell.CAN_BE_SELECTED);
    LinkedList<FigureModel> figures;
    if (isFirstPlayersTurn == false)
      figures = firstPlayerOnField;
    else
      figures = secondPlayerOnField;
    for (FigureModel current : figures) {
      if (current.getPlacedAt() == null)
        continue;
      int[][] enemyMoves = current.getPlacedAt().getMoves(false, false);
      for (int i = 0; i < enemyMoves.length; i++) {
        for (int j = 0; j < enemyMoves[i].length; j++) {
          if (enemyMoves[i][j] == Cell.CAN_BE_MOVED || enemyMoves[i][j] == Cell.CAN_BE_BEATED)
            dangerousCells[i][j] = 1;
        }
      }
    }
    return dangerousCells;
  }

  int[][] getDangerousCellsAfterMove(CellModel from, CellModel to) {
    FigureModel temp = to.removeFigure();
    to.addFigure(from.removeFigure());
    int[][] dangerousCells = getDangerousCells();
    from.addFigure(to.removeFigure());
    to.addFigure(temp);
    return dangerousCells;
  }

  int[][] getDangerousCellsAfterDrope(FigureModel what, CellModel where) {
    where.addFigure(what);
    int[][] dangerousCells = getDangerousCells();
    where.removeFigure();
    return dangerousCells;
  }

  FigureModel getCurrentKing() {
    if (isFirstPlayersTurn == true)
      return whiteKing;
    else
      return blackKing;
  }

  public boolean getIsFirstPlayersTurn() {
    return isFirstPlayersTurn;
  }

  public void endTurn() {
    isFirstPlayersTurn = !isFirstPlayersTurn;
    readyForDrop = null;

  }

  public boolean checkWinningState() {
    if (turnsList.size() >= 200 || getTurnNum(isFirstPlayersTurn) == 0) {
      int secondPlayerScore = 0;
      for (FigureModel current : secondPlayerInHand)
        secondPlayerScore += current.getFigurePrice();
      for (FigureModel current : secondPlayerOnField)
        secondPlayerScore += current.getFigurePrice();
      Alert winning = new Alert(AlertType.INFORMATION);
      if (secondPlayerScore < 29) {
      if (!Storage.isGenereting) winning.setContentText("Second player wins");
        winningState = Storage.FIRST_PLAYER_WINS;
      } else {
      if (!Storage.isGenereting) winning.setContentText("First player wins");
        winningState = Storage.SECOND_PLAYER_WINS;
      }
      try {
        save();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      Storage.hasSavedGames = true;
      firstPlayerOnField.clear();
      secondPlayerOnField.clear();
      firstPlayerInHand.clear();
      secondPlayerInHand.clear();
      turnsList.clear();
      currentTurn = turnsList.listIterator();
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      if (!Storage.isGenereting) {
        numberOfGame++;
        System.out.println(numberOfGame);
        if (numberOfGame >= Storage.gamesLimit)
          System.exit(0);
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameWindow());
      }
      else {
        winning.showAndWait();
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new MenuWindow());
      }
      return true;
    }
    return false;
  }

  public void endOfMapping() { // All graphic operations is finished, computer can male a move
    if (winningState != Storage.NO_WINNERS)
      return;
    if (Storage.isReplay == true) {
      Thread myThread = new Thread(new Runnable() {
        public void run() {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          Figure what;
          Cell where;
          String turn = GameSaver.getNext();
          if (turn.length() < 3)
            return;
          if (turn.toCharArray()[1] == '*') {
            LinkedList<FigureModel> temp;
            if (isFirstPlayersTurn)
              temp = firstPlayerInHand;
            else
              temp = secondPlayerInHand;
            int figureNumber = 0;
            for (int i = 0; i < temp.size(); i++) {
              FigureModel current = temp.get(i);
              if (current.getType().toCharArray()[0] == turn.toCharArray()[0])
                figureNumber = i;
            }
            what = mapping.getHandCell(isFirstPlayersTurn, figureNumber).getAtThisCell();
            where = mapping.getCell(9 - ((int) turn.toCharArray()[3] - Storage.CODE_OF_0),
                (int) turn.toCharArray()[2] - Storage.CODE_OF_A);
            Platform.runLater(() -> newTurn(new Drop(what, where)));
          } else if (turn.toCharArray()[3] == '-') {
            what = mapping.getCell(9 - ((int) turn.toCharArray()[2] - Storage.CODE_OF_0),
                (int) turn.toCharArray()[1] - Storage.CODE_OF_A).getAtThisCell();
            where = mapping.getCell(9 - ((int) turn.toCharArray()[5] - Storage.CODE_OF_0),
                (int) turn.toCharArray()[4] - Storage.CODE_OF_A);
            if (turn.length() > 6)
              Platform.runLater(() -> newTurn(new Move(what, where, true)));
            else
              Platform.runLater(() -> newTurn(new Move(what, where)));
          } else if (turn.toCharArray()[3] == 'x') {
            what = mapping.getCell(9 - ((int) turn.toCharArray()[2] - Storage.CODE_OF_0),
                (int) turn.toCharArray()[1] - Storage.CODE_OF_A).getAtThisCell();
            where = mapping.getCell(9 - ((int) turn.toCharArray()[5] - Storage.CODE_OF_0),
                (int) turn.toCharArray()[4] - Storage.CODE_OF_A);
            if (turn.length() > 6)
              Platform.runLater(() -> newTurn(new Beat(what, where, true)));
            else
              Platform.runLater(() -> newTurn(new Beat(what, where)));
          }
          Platform.runLater(() -> mapping.viewTurns(true));
        }
      });
      myThread.start();
      return;
    }
    Random rand = new Random();
    if (Storage.isSecondPlayerAComputer == true && isFirstPlayersTurn == false) {
      Thread myThread = new Thread(new Runnable() {
        public void run() {
          int turnsNum = 0;
          ArrayList<int[]> turns = null;
          FigureModel figure = null;
          while (turnsNum == 0) {
            figure = secondPlayerOnField.get(rand.nextInt(secondPlayerOnField.size()));
            turns = getTurns(figure);
            turnsNum = turns.size();
          }
          int horizNumber = figure.getPlacedAt().getHorizontalNumber();
          int vertNumber = figure.getPlacedAt().getVerticalNumber();
          Platform.runLater(() -> mapping.getCell(horizNumber, vertNumber).turn());
          if (!Storage.isGenereting) {
            try {
              Thread.sleep(50);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          int[] dest = turns.get(rand.nextInt(turns.size()));
          Platform.runLater(() -> mapping.getCell(dest[0], dest[1]).turn());

        }
      });
      myThread.start();
    }
    if (Storage.isFirstPlayerAComputer == true && isFirstPlayersTurn == true) {
      Thread myThread = new Thread(new Runnable() {
        public void run() {
          int turnsNum = 0;
          ArrayList<int[]> turns = null;
          FigureModel figure = null;
          while (turnsNum == 0) {
            figure = firstPlayerOnField.get(rand.nextInt(firstPlayerOnField.size()));
            turns = getTurns(figure);
            turnsNum = turns.size();
          }
          int horizNumber = figure.getPlacedAt().getHorizontalNumber();
          int vertNumber = figure.getPlacedAt().getVerticalNumber();
          Platform.runLater(() -> mapping.getCell(horizNumber, vertNumber).turn());
          if (!Storage.isGenereting) {
            try {
              Thread.sleep(50);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          int[] dest = turns.get(rand.nextInt(turns.size()));
          Platform.runLater(() -> mapping.getCell(dest[0], dest[1]).turn());
        }
      });
      myThread.start();
    }
  }

  public Modes markForDrop(FigureModel dropedFigure) {
    int[][] moves = new int[horizontalSize][verticalSize]; // Field cells state
    int hands[][] = new int[2][20]; // Player's hand's cells state
    Modes modes = new Modes();
    modes.moves = moves;
    modes.hand = hands;
    for (int[] current : hands) // Make every cell white
      Arrays.fill(current, Cell.CAN_BE_SELECTED);
    if (dropedFigure.getIsWhite() != isFirstPlayersTurn) { // Return if player try to drop
      for (int[] current : moves) // opponent's figures
        Arrays.fill(current, Cell.CAN_BE_SELECTED);
      return modes;
    }
    for (int[] current : moves) // Figures can be dropped everywhere
      Arrays.fill(current, Cell.CAN_BE_DROPED);
    for (FigureModel figure : firstPlayerOnField)
      moves[figure.getPlacedAt().getHorizontalNumber()][figure.getPlacedAt().getVerticalNumber()] =
          Cell.CAN_BE_SELECTED;
    for (FigureModel figure : secondPlayerOnField)
      moves[figure.getPlacedAt().getHorizontalNumber()][figure.getPlacedAt().getVerticalNumber()] =
          Cell.CAN_BE_SELECTED;
    if (dropedFigure.getIsWhite()) {
      hands[0][firstPlayerInHand.indexOf(dropedFigure)] = Cell.IS_SELECTED;
      for (int i = 0; i < moves.length; i++)
        for (int j = 0; j < dropedFigure.getForbiddenZone(); j++)
          moves[i][j] = Cell.CAN_BE_SELECTED; // But not in forbidden zone
      for (FigureModel figure : firstPlayerOnField)
        if (figure.getIsPawn() && !figure.getIsTurned() && dropedFigure.getIsPawn())
          Arrays.fill(moves[figure.getPlacedAt().getHorizontalNumber()], Cell.CAN_BE_SELECTED);
    } else {
      hands[1][secondPlayerInHand.indexOf(dropedFigure)] = Cell.IS_SELECTED;
      for (int i = 0; i < moves.length; i++)
        for (int j = 0; j < dropedFigure.getForbiddenZone(); j++)
          moves[i][moves[i].length - j - 1] = Cell.CAN_BE_SELECTED;
      for (FigureModel figure : secondPlayerOnField)
        if (figure.getIsPawn() && !figure.getIsTurned() && dropedFigure.getIsPawn())
          Arrays.fill(moves[figure.getPlacedAt().getHorizontalNumber()], Cell.CAN_BE_SELECTED);
    }
    readyForDrop = dropedFigure;
    return modes;
  }

  ArrayList<int[]> getTurns(FigureModel figure) {
    ArrayList<int[]> turns = new ArrayList<int[]>();
    if (figure.getIsWhite() != isFirstPlayersTurn)
      return turns;
    int[][] moves;
    if (isFirstPlayersTurn) {
      if (firstPlayerOnField.indexOf(figure) != -1)
        moves = figure.getPlacedAt().getMoves();
      else
        moves = markForDrop(figure).moves;
    } else {
      if (secondPlayerOnField.indexOf(figure) != -1)
        moves = figure.getPlacedAt().getMoves();
      else
        moves = markForDrop(figure).moves;
    }
    for (int i = 0; i < moves.length; i++) {
      for (int j = 0; j < moves.length; j++) {
        if (moves[i][j] > Cell.IS_SELECTED) {
          int[] move = new int[2];
          move[0] = i;
          move[1] = j;
          turns.add(move);
        }
      }
    }
    return turns;
  }

  int getTurnNum(boolean isFirstPlayer) {
    int turns = 0;
    if (isFirstPlayer)
      for (FigureModel current : firstPlayerOnField)
        turns += getTurns(current).size();
    else
      for (FigureModel current : secondPlayerOnField)
        turns += getTurns(current).size();
    return turns;
  }

  public FigureModel getReadyForDrop() {
    return readyForDrop;
  }

  void setReadyForDrop(FigureModel ready) {
    readyForDrop = ready;
  }

  public ArrayList<String> getStringTurns() {
    ArrayList<String> stringTurns = new ArrayList<String>();
    for (Turn current : turnsList)
      stringTurns.add(current.toString());
    return stringTurns;
  }

  public void newTurn(Turn turn) {
    while (currentTurn.nextIndex() < turnsList.size()) {
      turnsList.removeLast();
    }
    turnsList.add(turn);
    currentTurn = turnsList.listIterator(turnsList.size());
  }

  public void cancelTurns(int howMany) {
    while (howMany != 0) {
      if (howMany > 0) {
        currentTurn.previous().cancel();
        howMany--;
      } else {
        currentTurn.next().executeAgain();
        howMany++;
      }
    }
  }

  public void changeFiguresSide(FigureModel figure) {
    if (figure.getIsWhite()) {
      if (firstPlayerOnField.contains(figure)) {
        firstPlayerOnField.remove(figure);
        secondPlayerOnField.add(figure);
      } else {
        firstPlayerInHand.remove(figure);
        secondPlayerInHand.add(figure);
      }
    } else {
      if (secondPlayerOnField.contains(figure)) {
        secondPlayerOnField.remove(figure);
        firstPlayerOnField.add(figure);
      } else {
        secondPlayerInHand.remove(figure);
        firstPlayerInHand.add(figure);
      }
    }
    figure.changeSide();
    isFirstPlayersTurn = !isFirstPlayersTurn;
  }

  public void save() throws IOException {
    GameSaver.saveTurns(numberOfGame, turnsList, winningState);
  }

  public void load(int number) throws IOException {
    GameSaver.loadTurns(number);
  }

  public void loadLastState() {
    Figure what;
    Cell where;
    String turn = GameSaver.getNext();
    while (turn.length() > 3) {
      if (turn.toCharArray()[1] == '*') {
        LinkedList<FigureModel> temp;
        if (isFirstPlayersTurn)
          temp = firstPlayerInHand;
        else
          temp = secondPlayerInHand;
        int figureNumber = 0;
        for (int i = 0; i < temp.size(); i++) {
          FigureModel current = temp.get(i);
          if (current.getType().toCharArray()[0] == turn.toCharArray()[0])
            figureNumber = i;
        }
        what = mapping.getHandCell(isFirstPlayersTurn, figureNumber).getAtThisCell();
        where = mapping.getCell(9 - ((int) turn.toCharArray()[3] - Storage.CODE_OF_0),
            (int) turn.toCharArray()[2] - Storage.CODE_OF_A);
        newTurn(new Drop(what, where));
      } else if (turn.toCharArray()[3] == '-') {
        what = mapping.getCell(9 - ((int) turn.toCharArray()[2] - Storage.CODE_OF_0),
            (int) turn.toCharArray()[1] - Storage.CODE_OF_A).getAtThisCell();
        where = mapping.getCell(9 - ((int) turn.toCharArray()[5] - Storage.CODE_OF_0),
            (int) turn.toCharArray()[4] - Storage.CODE_OF_A);
        if (turn.length() > 6)
          new Move(what, where, true);
        else
          newTurn(new Move(what, where));
      } else if (turn.toCharArray()[3] == 'x') {
        what = mapping.getCell(9 - ((int) turn.toCharArray()[2] - Storage.CODE_OF_0),
            (int) turn.toCharArray()[1] - Storage.CODE_OF_A).getAtThisCell();
        where = mapping.getCell(9 - ((int) turn.toCharArray()[5] - Storage.CODE_OF_0),
            (int) turn.toCharArray()[4] - Storage.CODE_OF_A);
        if (turn.length() > 6)
          newTurn(new Beat(what, where, true));
        else
          newTurn(new Beat(what, where));
      }
      Platform.runLater(() -> mapping.viewTurns(true));
      turn = GameSaver.getNext();
    }
  }
}


