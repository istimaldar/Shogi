package components;

import application.Storage;
import javafx.scene.image.Image;
import logic.FigureModel;

public class Silver extends Figure {

  public Silver(int x, int y, int height, int width, boolean isWhite) {
    super(x, y, height, width, isWhite);
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Silver.png";
      else
        imageAdress = "/components/BlackSilver.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/PromotedSilver.png";
      else
        imageAdress = "/components/BlackPromotedSilver.png";
    }
    Image silver = new Image(imageAdress);
    setImage(silver);
    model = new FigureModel(isWhite, false, false, true, 0,"S");
    setMoves();
  }

  public void setMoves() {
    model.clearMoves();
    if (isTurned == false) {
      model.addShortMove(Storage.BACKWARD_LEFT);
      model.addShortMove(Storage.BACKWARD_RIGHT);
      model.addShortMove(Storage.FORWARD_LEFT);
      model.addShortMove(Storage.FORWARD_RIGHT);
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
        imageAdress = "/components/Silver.png";
      else
        imageAdress = "/components/BlackSilver.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/PromotedSilver.png";
      else
        imageAdress = "/components/BlackPromotedSilver.png";
    }
    Image silver = new Image(imageAdress);
    setImage(silver);
  }

  public String toString() {
    return new String("S");
  }

}
