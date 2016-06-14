package application;

import application.Storage;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import application.NewGameSettingsWindow;

public class MenuWindow extends BorderPane {

  static private VBox center;
  static private HBox bottom;
  static private VBox left;

  public MenuWindow() {
    bottom = new HBox();
    center = new VBox();
    left = new VBox();
    center.setPadding(new Insets(350, 0, 0, 350));
    center.setSpacing(50);

    ShogiButton continueGame = new ShogiButton("Load game");
    continueGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = false;
        Storage.isSecondPlayerAComputer = false;
        Storage.isReplay = false;
        Storage.isLoaded = true;
        Storage.isGenereting = false;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameSelectionWindow());
      }
    });
    //if(Storage.hasSavedGames == false) continueGame.setActive(false);
    
    ShogiButton newGame = new ShogiButton("New game");
    newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new NewGameSettingsWindow());
      }
    });

    ShogiButton records = new ShogiButton("Recorded games");
    records.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = false;
        Storage.isSecondPlayerAComputer = false;
        Storage.isReplay = true;
        Storage.isLoaded = false;
        Storage.isGenereting = false;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameSelectionWindow());
      }
    });
    //if(Storage.hasSavedGames == false) records.setActive(false);
    
    ShogiButton resultSorting = new ShogiButton("Result sorting");
    resultSorting.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new ResultSortingWindow());
      }
    });

    ShogiButton rules = new ShogiButton("Rules");
    rules.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new RulesWindow());
      }
    });

    ShogiButton exit = new ShogiButton("Exit");
    exit.setOnMouseClicked(event -> System.exit(0));

    center.getChildren().addAll(continueGame, newGame, records, resultSorting, rules, exit);
    setBottom(bottom);
    setCenter(center);
    setLeft(left);
    setPrefSize(800, 900);
  }

}
