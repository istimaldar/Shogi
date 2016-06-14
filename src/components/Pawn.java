package components;

import javafx.scene.image.Image;
import logic.FigureModel;
import application.Storage;

public class Pawn extends Figure {

  public Pawn(int x, int y, int height, int width, boolean isWhite) {
    super(x, y, height, width, isWhite);
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Pawn.png";
      else
        imageAdress = "/components/BlackPawn.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/Tokin.png";
      else
        imageAdress = "/components/BlackTokin.png";
    }
    Image pawn = new Image(imageAdress);
    setImage(pawn);
    model = new FigureModel(isWhite, false, true, true, 1,"P");
    setMoves();
  }

  public void setMoves() {
    model.clearMoves();
    if (isTurned == false) {
      if (isWhite == true)
        model.addShortMove(Storage.BACKWARD);
      else
        model.addShortMove(Storage.FORWARD);
    } else {
      model.addShortMove(Storage.BACKWARD);
      model.addShortMove(Storage.RIGHT);
      model.addShortMove(Storage.LEFT);
      model.addShortMove(Storage.FORWARD);
      if (isWhite == true) {
        model.addShortMove(Storage.BACKWARD_LEFT);
        model.addShortMove(Storage.BACKWARD_RIGHT);
      } else {
        model.addShortMove(Storage.FORWARD_LEFT);
        model.addShortMove(Storage.FORWARD_RIGHT);
      }
    }
  }

  public void redraw() {
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Pawn.png";
      else
        imageAdress = "/components/BlackPawn.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/Tokin.png";
      else
        imageAdress = "/components/BlackTokin.png";
    }
    Image pawn = new Image(imageAdress);
    setImage(pawn);
  }

  public String toString() {
    return new String("P");
  }

}
