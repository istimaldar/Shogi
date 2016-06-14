package application;


import application.Storage;
import application.MenuWindow;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class NewGameSettingsWindow extends BorderPane {

  static private VBox center;

  public NewGameSettingsWindow() {

    center = new VBox();
    center.setPadding(new Insets(350, 0, 0, 350));
    center.setSpacing(65);

    ShogiButton twoPlayers = new ShogiButton("Vs. player");
    twoPlayers.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = false;
        Storage.isSecondPlayerAComputer = false;
        Storage.isReplay = false;
        Storage.isLoaded = false;
        Storage.isGenereting = false;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameWindow());
      }
    });

    ShogiButton onePlayer = new ShogiButton("Vs. computer");
    onePlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = false;
        Storage.isSecondPlayerAComputer = true;
        Storage.isReplay = false;
        Storage.isLoaded = false;
        Storage.isGenereting = false;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameWindow());
      }
    });

    ShogiButton zeroPlayers = new ShogiButton("Auto");
    zeroPlayers.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = true;
        Storage.isSecondPlayerAComputer = true;
        Storage.isReplay = false;
        Storage.isLoaded = false;
        Storage.isGenereting = false;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameWindow());
      }
    });

    ShogiButton back = new ShogiButton("Back");
    back.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new MenuWindow());
      }
    });

    center.getChildren().addAll(twoPlayers, onePlayer, zeroPlayers, back);
    setCenter(center);
  }

}
