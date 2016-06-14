package components;

import application.Storage;
import components.Field;
import logic.FieldModel;

public class StandartShoigiField extends Field {

  public StandartShoigiField(double x, double y, double width, double height) {
    super(9, 9, 20, x, y, width, height);

  }

  public void createStartPosition() {
    Figure figure = new King(0, 0, 50, 50, true);
    getChildren().add(figure);
    cells[4][8].addFigure(figure);
    figure = new Bishop(0, 0, 50, 50, true);
    getChildren().add(figure);
    cells[1][7].addFigure(figure);
    figure = new Rook(0, 0, 50, 50, true);
    getChildren().add(figure);
    cells[7][7].addFigure(figure);
    for (int i = 0; i < 2; i++) {
      figure = new Gold(0, 0, 50, 50, true);
      getChildren().add(figure);
      cells[Math.abs(i * 8 - 3)][8].addFigure(figure);
      figure = new Silver(0, 0, 50, 50, true);
      getChildren().add(figure);
      cells[Math.abs(i * 8 - 2)][8].addFigure(figure);
      figure = new Knight(0, 0, 50, 50, true);
      getChildren().add(figure);
      cells[Math.abs(i * 8 - 1)][8].addFigure(figure);
      figure = new Lance(0, 0, 50, 50, true);
      getChildren().add(figure);
      cells[Math.abs(i * 8 - 0)][8].addFigure(figure);
    }
    for (int i = 0; i < 9; i++) {
      figure = new Pawn(0, 0, 50, 50, true);
      getChildren().add(figure);
      cells[i][6].addFigure(figure);
    }
    figure = new King(0, 0, 50, 40, false);
    getChildren().add(figure);
    cells[4][0].addFigure(figure);
    figure = new Bishop(0, 0, 50, 40, false);
    getChildren().add(figure);
    cells[7][1].addFigure(figure);
    figure = new Rook(0, 0, 50, 40, false);
    getChildren().add(figure);
    cells[1][1].addFigure(figure);
    for (int i = 0; i < 2; i++) {
      figure = new Gold(0, 0, 50, 50, false);
      getChildren().add(figure);
      cells[Math.abs(i * 8 - 3)][0].addFigure(figure);
      figure = new Silver(0, 0, 50, 50, false);
      getChildren().add(figure);
      cells[Math.abs(i * 8 - 2)][0].addFigure(figure);
      figure = new Knight(0, 0, 50, 50, false);
      getChildren().add(figure);
      cells[Math.abs(i * 8 - 1)][0].addFigure(figure);
      figure = new Lance(0, 0, 50, 50, false);
      getChildren().add(figure);
      cells[Math.abs(i * 8 - 0)][0].addFigure(figure);
    }
    for (int i = 0; i < 9; i++) {
      figure = new Pawn(0, 0, 50, 50, false);
      getChildren().add(figure);
      cells[i][2].addFigure(figure);
    }
    model = new FieldModel(9, 9, cells, this);
    if (Storage.isFirstPlayerAComputer == true)
      model.endOfMapping();
  }
}
