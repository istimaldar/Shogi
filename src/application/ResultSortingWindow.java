package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import sort.ScalaSorter;

public class ResultSortingWindow extends BorderPane {

  static private VBox center;

  public ResultSortingWindow() {
    center = new VBox();
    center.setPadding(new Insets(100, 0, 0, 350));
    center.setSpacing(60);

    ShogiButton thousandsOfGames = new ShogiButton("1,000 games");
    thousandsOfGames.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = true;
        Storage.isSecondPlayerAComputer = true;
        Storage.isReplay = false;
        Storage.isLoaded = false;
        Storage.isGenereting = false;
        Storage.gamesLimit = 1000;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameWindow());
      }
    });

    ShogiButton tenThousandsOfGames = new ShogiButton("10,000 games");
    tenThousandsOfGames.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = true;
        Storage.isSecondPlayerAComputer = true;
        Storage.isReplay = false;
        Storage.isLoaded = false;
        Storage.isGenereting = false;
        Storage.gamesLimit = 10000;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameWindow());
      }
    });

    ShogiButton hundredThousandGames = new ShogiButton("100,000 games");
    hundredThousandGames.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = true;
        Storage.isSecondPlayerAComputer = true;
        Storage.isReplay = false;
        Storage.isLoaded = false;
        Storage.isGenereting = false;
        Storage.gamesLimit = 10000;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameWindow());
      }
    });
    
    ShogiButton javaSort = new ShogiButton("Java Sort");
    javaSort.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = false;
        Storage.isSecondPlayerAComputer = false;
        Storage.isReplay = true;
        Storage.isLoaded = false;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameSelectionWindow(true,false));
      }
    });
    
    ShogiButton scalaSort = new ShogiButton("Scala Sort");
    scalaSort.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.isFirstPlayerAComputer = false;
        Storage.isSecondPlayerAComputer = false;
        Storage.isReplay = true;
        Storage.isLoaded = false;
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new GameSelectionWindow(true,true));
      }
    });
    
    ShogiButton statistics = new ShogiButton("Statistics");
    statistics.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Alert result = new Alert(AlertType.INFORMATION);
        result.setTitle("Results");
        result.setHeaderText(null);
        result.setHeaderText(ScalaSorter.getMU());
        result.showAndWait();
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

    center.getChildren().addAll(thousandsOfGames,tenThousandsOfGames,hundredThousandGames,javaSort,scalaSort,statistics,back);
    setCenter(center);
  }

}
