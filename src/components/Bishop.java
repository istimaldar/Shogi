package components;

import javafx.scene.image.Image;
import logic.FigureModel;
import application.Storage;

public class Bishop extends Figure {

  public Bishop(int x, int y, int height, int width, boolean isWhite) {
    super(x, y, height, width, isWhite);
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Bishop.png";
      else
        imageAdress = "/components/BlackBishop.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/Horse.png";
      else
        imageAdress = "/components/BlackHorse.png";
    }
    Image bishop = new Image(imageAdress);
    setImage(bishop);
    model = new FigureModel(isWhite, false, false, true, 0,"B");
    setMoves();
  }

  public void setMoves() {
    model.clearMoves();
    model.addLongMove(Storage.BACKWARD_LEFT);
    model.addLongMove(Storage.BACKWARD_RIGHT);
    model.addLongMove(Storage.FORWARD_LEFT);
    model.addLongMove(Storage.FORWARD_RIGHT);
    if (isTurned == true) {
      model.addShortMove(Storage.BACKWARD);
      model.addShortMove(Storage.RIGHT);
      model.addShortMove(Storage.LEFT);
      model.addShortMove(Storage.FORWARD);
    }
  }

  public void redraw() {
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Bishop.png";
      else
        imageAdress = "/components/BlackBishop.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/Horse.png";
      else
        imageAdress = "/components/BlackHorse.png";
    }
    Image bishop = new Image(imageAdress);
    setImage(bishop);
  }
  
  public String toString() {
    return new String("B");
  }
  
}
