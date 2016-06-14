package components;


import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.FieldModel;

import java.io.IOException;
import java.util.LinkedList;

import application.Storage;
import components.Cell;

public class Field extends Parent {

  protected Cell[][] cells;
  protected Cell selected;
  protected HandCell[] firstPlayerHand, secondPlayerHand;
  private int verticalSize;
  private int horizontalSize;
  private int numberOfFigures;
  private double width;
  private double height;
  private double x;
  private double y;
  protected FieldModel model;
  private ListView<String> turnsList;
  private ListProperty<String> listProperty;
  private LinkedList<String> turns;
  int turn = 0;

  public Field(int verticalSize, int horizontalSize, int numberOfFigures, double x, double y,
      double width, double height) {
    this.verticalSize = verticalSize;
    this.horizontalSize = horizontalSize;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.numberOfFigures = numberOfFigures;
    cells = new Cell[this.horizontalSize][this.verticalSize];
    for (int i = 0; i < this.horizontalSize; i++) {
      for (int j = 0; j < this.verticalSize; j++) {
        cells[i][j] = new Cell(i, j, this.x + i * (this.width / this.horizontalSize),
            this.y + j * (this.height / this.verticalSize), this.width / this.horizontalSize,
            this.height / this.verticalSize, this);
        cells[i][j].setMode(Cell.CAN_BE_SELECTED);
        cells[i][j].setArcWidth(2);
        cells[i][j].setArcHeight(2);
        cells[i][j].setStroke(Color.BLACK);
        getChildren().add(cells[i][j]);
      }
    }

    Text FirstPlayersHand = new Text("First player's hand:");
    FirstPlayersHand.setX(this.x);
    FirstPlayersHand.setY(this.y + this.height + 25);
    FirstPlayersHand.setFill(Color.WHITE);
    FirstPlayersHand.setFont(new Font(25));
    Text SecondPlayersHand = new Text("Second player's hand:");
    SecondPlayersHand.setX(this.x);
    SecondPlayersHand.setY(this.y + this.height + 25 + 4 * (this.height / this.verticalSize));
    SecondPlayersHand.setFill(Color.WHITE);
    SecondPlayersHand.setFont(new Font(25));
    getChildren().addAll(FirstPlayersHand, SecondPlayersHand);

    firstPlayerHand = new HandCell[this.numberOfFigures];
    secondPlayerHand = new HandCell[this.numberOfFigures];
    for (int i = 0; i < this.numberOfFigures; i++) {
      firstPlayerHand[i] = new HandCell(i,
          this.x + (i % HandCell.getMaxAtLine()) * (this.width / this.horizontalSize),
          this.y + (this.verticalSize + 1 + (i / HandCell.getMaxAtLine()))
              * (this.height / this.verticalSize),
          this.width / this.horizontalSize, this.height / this.verticalSize, this);
      firstPlayerHand[i].setFill(Color.WHITE);
      firstPlayerHand[i].setArcWidth(2);
      firstPlayerHand[i].setArcHeight(2);
      getChildren().add(firstPlayerHand[i]);
      secondPlayerHand[i] = new HandCell(i,
          this.x + (i % HandCell.getMaxAtLine()) * (this.width / this.horizontalSize),
          this.y + (this.verticalSize + 3 + (i / HandCell.getMaxAtLine())
              + this.numberOfFigures / HandCell.getMaxAtLine()) * (this.height / this.verticalSize),
          this.width / this.horizontalSize, this.height / this.verticalSize, this);
      secondPlayerHand[i].setFill(Color.WHITE);
      secondPlayerHand[i].setArcWidth(2);
      secondPlayerHand[i].setArcHeight(2);
      getChildren().add(secondPlayerHand[i]);
    }
    turns = new LinkedList<String>();
    turnsList = new ListView<String>();
    turnsList.setLayoutX(x + width + 10);
    turnsList.setLayoutY(y);
    listProperty = new SimpleListProperty<>();
    turnsList.itemsProperty().bind(listProperty);
    turnsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(model!=null) model.cancelTurns(turn-turnsList.getSelectionModel().getSelectedIndex());
        turn = turnsList.getSelectionModel().getSelectedIndex();
      }
    });
    listProperty.set(FXCollections.observableArrayList(turns));
    getChildren().add(turnsList);
  }

  public Cell getCell(int horizontalNumber, int verticalNumber) {
    return cells[horizontalNumber][verticalNumber];
  }
  
  public HandCell getHandCell(boolean isFirstPlayer, int number) {
    if(isFirstPlayer) return firstPlayerHand[number];
    else return secondPlayerHand[number];
  }

  public void makeEveryCellWhite() {
    selected = null;
    for (int i = 0; i < horizontalSize; i++) {
      for (int j = 0; j < verticalSize; j++) {
        cells[i][j].setMode(Cell.CAN_BE_SELECTED);
      }
    }
    for (int i = 0; i < this.numberOfFigures; i++) {
      firstPlayerHand[i].setMode(Cell.CAN_BE_SELECTED);
      secondPlayerHand[i].setMode(Cell.CAN_BE_SELECTED);
    }
  }

  public void setModes(int[][] modes) {
    makeEveryCellWhite();
    for (int i = 0; i < horizontalSize; i++) {
      for (int j = 0; j < verticalSize; j++) {
        cells[i][j].setMode(modes[i][j]);
        if (modes[i][j] == Cell.IS_SELECTED)
          selected = cells[i][j];
      }
    }
  }

  public void setAllModes(FieldModel.Modes modes) {
    setModes(modes.moves);
    for (int i = 0; i < firstPlayerHand.length; i++)
      firstPlayerHand[i].setMode(modes.hand[0][i]);
    for (int i = 0; i < secondPlayerHand.length; i++)
      secondPlayerHand[i].setMode(modes.hand[1][i]);
  }

  public void moveFigureToTheHand(Figure figure, int number) {
    figure.turnFront();
    if (figure.getIsWhite() == true)
      secondPlayerHand[number].addFigure(figure);
    else
      firstPlayerHand[number].addFigure(figure);
    figure.changeSide();
    figure.redraw();
  }

  public Cell getSelected() {
    return selected;
  }

  public FieldModel getModel() {
    return model;
  }

  public Figure dropFirstPlayerFigure(int index) {
    Figure figure = firstPlayerHand[index].removeFigure();
    for (int i = 0; firstPlayerHand[i + 1].getAtThisCell() != null; i++)
      firstPlayerHand[i].addFigure(firstPlayerHand[i + 1].removeFigure());
    return figure;
  }

  public Figure dropSecondPlayerFigure(int index) {
    Figure figure = secondPlayerHand[index].removeFigure();
    for (int i = 0; secondPlayerHand[i + 1].getAtThisCell() != null; i++)
      secondPlayerHand[i].addFigure(secondPlayerHand[i + 1].removeFigure());
    return figure;
  }

  Figure getDropedFigure() {
    for (HandCell current : firstPlayerHand)
      if (current.getMode() == Cell.IS_SELECTED)
        return current.getAtThisCell();
    for (HandCell current : secondPlayerHand)
      if (current.getMode() == Cell.IS_SELECTED)
        return current.getAtThisCell();
    return null;
  }

  public void viewTurns(boolean toLast) {
    turns.clear();
    turns.add("Game started");
    for (String current : model.getStringTurns())
      turns.add(current);
    listProperty.set(FXCollections.observableArrayList(turns));
    if(toLast) {
      turnsList.getSelectionModel().select(turns.size()-1);
    }
    turn = turnsList.getSelectionModel().getSelectedIndex();
  }
  
  public void loadLastState() {
    model.loadLastState();
  }
  
  public void save() throws IOException {
    model.save();
  }
  
  public void load(int number) throws IOException {
    model.load(number);
    if(Storage.isReplay == true) model.endOfMapping();
  }

}
