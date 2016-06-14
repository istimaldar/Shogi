package components;

import javafx.scene.image.Image;
import logic.FigureModel;
import application.Storage;

public class King extends Figure {

  public King(int x, int y, int height, int width, boolean isWhite) {
    super(x, y, height, width, isWhite);
    if (isWhite == true)
      imageAdress = "/components/King.png";
    else
      imageAdress = "/components/JeweledGeneral.png";
    Image king = new Image(imageAdress);
    setImage(king);
    model = new FigureModel(isWhite, true, false, false, 0,"K");
    setMoves();
  }

  public void setMoves() {
    model.clearMoves();
    model.addShortMove(Storage.BACKWARD);
    model.addShortMove(Storage.RIGHT);
    model.addShortMove(Storage.LEFT);
    model.addShortMove(Storage.FORWARD);
    model.addShortMove(Storage.BACKWARD_LEFT);
    model.addShortMove(Storage.BACKWARD_RIGHT);
    model.addShortMove(Storage.FORWARD_LEFT);
    model.addShortMove(Storage.FORWARD_RIGHT);
  }

  public void redraw() {
    if (isWhite == true)
      imageAdress = "/components/King.png";
    else
      imageAdress = "/components/JeweledGeneral.png";
    Image king = new Image(imageAdress);
    setImage(king);
  }

  public String toString() {
    return new String("K");
  }

}
