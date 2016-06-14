package components;

import javafx.scene.image.Image;
import logic.FigureModel;
import application.Storage;

public class Lance extends Figure {

  public Lance(int x, int y, int height, int width, boolean isWhite) {
    super(x, y, height, width, isWhite);
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Lance.png";
      else
        imageAdress = "/components/BlackLance.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/PromotedLance.png";
      else
        imageAdress = "/components/BlackPromotedLance.png";
    }
    Image lance = new Image(imageAdress);
    setImage(lance);
    model = new FigureModel(isWhite, false, false, true, 1,"L");
    setMoves();
  }

  public void setMoves() {
    model.clearMoves();
    if (isTurned == false) {
      if (isWhite)
        model.addLongMove(Storage.BACKWARD);
      else
        model.addLongMove(Storage.FORWARD);
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
        imageAdress = "/components/Lance.png";
      else
        imageAdress = "/components/BlackLance.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/PromotedLance.png";
      else
        imageAdress = "/components/BlackPromotedLance.png";
    }
    Image lance = new Image(imageAdress);
    setImage(lance);
  }

  public String toString() {
    return new String("L");
  }

}
