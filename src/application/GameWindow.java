package application;

import java.io.IOException;

import components.StandartShoigiField;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameWindow extends BorderPane {

  static private VBox right;
  static private Pane left;

  public GameWindow() {
    resize(800, 900);
    setPrefSize(800, 900);
    left = new Pane();
    right = new VBox();
    right.setPadding(new Insets(0, 50, 0, 0));
    StandartShoigiField field =
        new StandartShoigiField(20, 20, Storage.FIELD_SIZE, Storage.FIELD_SIZE);
    left.getChildren().addAll(field);
    field.createStartPosition();
    if(Storage.isReplay || Storage.isLoaded)
      try {
        field.load(Storage.currentGame);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } 
    if(Storage.isLoaded) field.loadLastState();
    ShogiButton back = new ShogiButton("Back", 25);
    back.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        try {
          field.save();
          Storage.hasSavedGames = true;
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new MenuWindow());
      }
    });
    right.getChildren().add(back);
    setRight(right);
    setLeft(left);

  }

}
