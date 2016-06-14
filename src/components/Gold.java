package components;

import javafx.scene.image.Image;
import logic.FigureModel;
import application.Storage;

public class Gold extends Figure {

  public Gold(int x, int y, int height, int width, boolean isWhite) {
    super(x, y, height, width, isWhite);
    if (isWhite == true)
      imageAdress = "/components/Gold.png";
    else
      imageAdress = "/components/BlackGold.png";
    Image gold = new Image(imageAdress);
    setImage(gold);
    model = new FigureModel(isWhite, false, false, false, 0,"G");
    setMoves();
  }

  public void setMoves() {
    model.clearMoves();
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

  public void redraw() {
    if (isWhite == true)
      imageAdress = "/components/Gold.png";
    else
      imageAdress = "/components/BlackGold.png";
    Image gold = new Image(imageAdress);
    setImage(gold);
  }

  public String toString() {
    return new String("G");
  }

}
