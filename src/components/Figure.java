package components;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import logic.FigureModel;

public abstract class Figure extends ImageView {

  protected String imageAdress;
  protected Shape placedAt;
  protected boolean isWhite;
  protected boolean isTurned;
  int xPos;
  int yPos;
  protected FigureModel model;

  public Figure(int x, int y, int height, int width, boolean isWhite) {
    setX(x);
    setY(y);

    this.isWhite = isWhite;
    isTurned = false;
    xPos = -1;
    yPos = -1;
  }

  public FigureModel getModel() {
    return model;
  }

  abstract public void setMoves();

  public boolean getIsTurned() {
    return isTurned;
  }

  public void setPlacedAt(Shape placedAt) {
    this.placedAt = placedAt;
    setActions();
  }
  
  public Shape getPlacedAt() {
    return placedAt;
  }

  public boolean belogToTheSamePlayer(Figure compared) {
    return (isWhite == compared.isWhite);
  }

  public boolean getIsWhite() {
    return isWhite;
  }

  public void changeSide() {
    if (isWhite == true)
      isWhite = false;
    else
      isWhite = true;
  }

  public void turnFront() {
    isTurned = false;
    model.turnFront();
    setMoves();
  }

  public void turnBack() {
    isTurned = true;
    model.turnBack();
    setMoves();
  }

  abstract public void redraw();

  private void setActions() {
    if (placedAt != null)
      this.setOnMouseClicked(placedAt.getOnMouseClicked());
    else {
      this.setOnMouseClicked(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
          System.out.println("It's magic! How could you click on figure witch not exesits?");
        }
      });
    }
  }

  abstract public String toString();
  
}
