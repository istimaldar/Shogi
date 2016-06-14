package application;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

class ShogiButton extends Parent {

  private boolean isActive;
  private Text label;
  private EventHandler<? super MouseEvent> currentEvent;

  public ShogiButton(String text, int fontSize) {

    label = new Text(text);
    label.setFill(Color.WHITE);
    label.setFont(new Font(fontSize));
    getChildren().add(label);

    label.setOnMouseEntered(event -> {
      ScaleTransition increase = new ScaleTransition(Duration.seconds(0.4), label);
      increase.setToX(1.2);
      increase.setToY(1.2);
      increase.play();
      label.setFill(Color.RED);
    });

    label.setOnMouseExited(event -> {
      ScaleTransition decrease = new ScaleTransition(Duration.seconds(0.4), label);
      decrease.setFromX(1.2);
      decrease.setFromY(1.2);
      decrease.setToX(1);
      decrease.setToY(1);
      decrease.play();
      label.setFill(Color.WHITE);
    });
    label.setOnMousePressed(event -> {
      ScaleTransition decrease = new ScaleTransition(Duration.seconds(0.004), label);
      decrease.setFromX(1.2);
      decrease.setFromY(1.2);
      decrease.setToX(1);
      decrease.setToY(1);
      decrease.play();
    });
  }

  public ShogiButton(String text) {
    this(text, 20);
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
    if (isActive) {
      label.setFill(Color.WHITE);
      label.setOnMouseEntered(event -> {
        ScaleTransition increase = new ScaleTransition(Duration.seconds(0.4), label);
        increase.setToX(1.2);
        increase.setToY(1.2);
        increase.play();
        label.setFill(Color.RED);
      });

      label.setOnMouseExited(event -> {
        ScaleTransition decrease = new ScaleTransition(Duration.seconds(0.4), label);
        decrease.setFromX(1.2);
        decrease.setFromY(1.2);
        decrease.setToX(1);
        decrease.setToY(1);
        decrease.play();
        label.setFill(Color.WHITE);
      });
      setOnMouseClicked(currentEvent);
    } else {
      label.setFill(Color.GRAY);
      label.setOnMouseEntered(event -> {
      });
      label.setOnMouseExited(event -> {
      });
      currentEvent = getOnMouseClicked();
      setOnMouseClicked(event -> {
      });
    }
  }

  public boolean getActive() {
    return isActive;
  }

}
