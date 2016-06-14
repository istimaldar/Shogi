package application;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RulesText extends Text {

  public RulesText(String text) {
    super(text);
    setFill(Color.WHITE);
    setFont(new Font(15));
  }

}
