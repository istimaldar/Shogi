package components;

import javafx.scene.image.Image;
import logic.FigureModel;
import application.Storage;

public class Knight extends Figure {

  public Knight(int x, int y, int height, int width, boolean isWhite) {
    super(x, y, height, width, isWhite);
    if (isTurned == false) {
      if (isWhite == true)
        imageAdress = "/components/Knight.png";
      else
        imageAdress = "/components/BlackKnight.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/PromotedKnight.png";
      else
        imageAdress = "/components/BlackPromotedKnight.png";
    }
    Image knight = new Image(imageAdress);
    setImage(knight);
    model = new FigureModel(isWhite, false, false, true, 2,"N");
    setMoves();
  }

  public void setMoves() {
    model.clearMoves();
    if (isTurned == false) {
      if (isWhite) {
        model.addJumpMove(Storage.JUMP_BACKWARD_LEFT);
        model.addJumpMove(Storage.JUMP_BACKWARD_RIGHT);
      } else {
        model.addJumpMove(Storage.JUMP_FORWARD_LEFT);
        model.addJumpMove(Storage.JUMP_FORWARD_RIGHT);
      }
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
        imageAdress = "/components/Knight.png";
      else
        imageAdress = "/components/BlackKnight.png";
    } else {
      if (isWhite == true)
        imageAdress = "/components/PromotedKnight.png";
      else
        imageAdress = "/components/BlackPromotedKnight.png";
    }
    Image knight = new Image(imageAdress);
    setImage(knight);
  }

  public String toString() {
    return new String("N");
  }

}
