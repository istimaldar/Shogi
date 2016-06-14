package components;

import application.Storage;
import javafx.scene.image.Image;
import logic.FigureModel;

public class Rook extends Figure {

  public Rook(int x, int y, int height, int width, boolean isWhite) {
    super(x, y, height, width, isWhite);
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Rook.png";
      else
        imageAdress = "/components/BlackRook.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/Dragon.png";
      else
        imageAdress = "/components/BlackDragon.png";
    }
    Image rook = new Image(imageAdress);
    setImage(rook);
    model = new FigureModel(isWhite, false, false, true, 0,"R");
    setMoves();
  }

  public void setMoves() {
    model.clearMoves();
    model.addLongMove(Storage.BACKWARD);
    model.addLongMove(Storage.RIGHT);
    model.addLongMove(Storage.LEFT);
    model.addLongMove(Storage.FORWARD);
    if (isTurned == true) {
      model.addShortMove(Storage.BACKWARD_LEFT);
      model.addShortMove(Storage.BACKWARD_RIGHT);
      model.addShortMove(Storage.FORWARD_LEFT);
      model.addShortMove(Storage.FORWARD_RIGHT);
    }
  }

  public void redraw() {
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Rook.png";
      else
        imageAdress = "/components/BlackRook.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/Dragon.png";
      else
        imageAdress = "/components/BlackDragon.png";
    }
    Image rook = new Image(imageAdress);
    setImage(rook);
  }

  public String toString() {
    return new String("R");
  }

}
