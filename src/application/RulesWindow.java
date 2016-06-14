package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RulesWindow extends BorderPane {

  static private VBox firstPage;
  static private GridPane secondPage;
  static private GridPane thirdPage;
  static private HBox bottom;

  public RulesWindow() {
    resize(800, 900);
    setPrefSize(800, 900);
    firstPage = new VBox();
    firstPage.setPadding(new Insets(20, 0, 0, 20));
    firstPage.setSpacing(45);

    RulesText text = new RulesText(Storage.FIRST_PAGE_TEXT);
    firstPage.getChildren().addAll(text);

    bottom = new HBox();
    bottom.setPadding(new Insets(0, 0, 20, 20));
    bottom.setSpacing(40);

    ShogiButton back = new ShogiButton("Back", 25);
    back.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Storage.root.getChildren().clear();
        Storage.root.getChildren().add(new MenuWindow());
      }
    });

    final Text pageNumber = new Text("Page 1 out of 3. General rules.");
    pageNumber.setFill(Color.WHITE);
    pageNumber.setFont(new Font(15));
    final ShogiButton next = new ShogiButton("Next page", 25);
    final ShogiButton previous = new ShogiButton("Previous page", 25);
    previous.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (getCenter() == secondPage) {
          setCenter(firstPage);
          pageNumber.setText("Page 1 out of 3. General rules.");
          previous.setActive(false);
        } else if (getCenter() == thirdPage) {
          setCenter(secondPage);
          pageNumber.setText("Page 2 out of 3. Moves.");
          next.setActive(true);
        }
      }
    });
    previous.setActive(false);

  
    next.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (getCenter() == firstPage) {
          setCenter(secondPage);
          pageNumber.setText("Page 2 out of 3. Moves.");
          previous.setActive(true);
        } else if (getCenter() == secondPage) {
          setCenter(thirdPage);
          pageNumber.setText("Page 3 out of 3. Moves 2.");
          next.setActive(false);
        }
      }
    });

    bottom.getChildren().addAll(back, previous, pageNumber, next);
    setCenter(firstPage);
    setBottom(bottom);

    secondPage = new GridPane();
    secondPage.setPadding(new Insets(20, 0, 0, 20));
    secondPage.add(new RulesText("Not turned"), 1, 0);
    secondPage.add(new RulesText("Turned"), 4, 0);
    for (int i = 0; i < 4; i += 3) {
      secondPage.add(new RulesText("Name"), 0 + i, 1);
      secondPage.add(new RulesText("Designation"), 1 + i, 1);
      secondPage.add(new RulesText("Moves"), 2 + i, 1);
    }

    thirdPage = new GridPane();
    thirdPage.setPadding(new Insets(20, 0, 0, 20));
    thirdPage.add(new RulesText("Not turned"), 1, 0);
    thirdPage.add(new RulesText("Turned"), 4, 0);
    for (int i = 0; i < 4; i += 3) {
      thirdPage.add(new RulesText("Name"), 0 + i, 1);
      thirdPage.add(new RulesText("Designation"), 1 + i, 1);
      thirdPage.add(new RulesText("Moves"), 2 + i, 1);
    }

    secondPage.add(new RulesText("Not turned"), 1, 0);
    for (int i = 0; i < Storage.FIGURE_NAMES.length; i++) {
      RulesText name = new RulesText(Storage.FIGURE_NAMES[i]);
      ImageView designation = new ImageView();
      designation.setImage(new Image("/components/" + Storage.FIGURE_NAMES[i] + ".png"));
      ImageView turns = new ImageView();
      turns.setImage(new Image("/application/" + Storage.FIGURE_NAMES[i] + "Turnes.png"));
      if (i < Storage.FIGURES_IN_COLUMN) {
        secondPage.add(name, 0, 2 + i);
        secondPage.add(designation, 1, 2 + i);
        secondPage.add(turns, 2, 2 + i);
      } else {
        thirdPage.add(name, 0, 2 + i - Storage.FIGURES_IN_COLUMN);
        thirdPage.add(designation, 1, 2 + i - Storage.FIGURES_IN_COLUMN);
        thirdPage.add(turns, 2, 2 + i - Storage.FIGURES_IN_COLUMN);
      }
    }
    for (int i = 0; i < Storage.TURNED_FIGURE_NAMES.length; i++) {
      RulesText name = new RulesText(Storage.TURNED_FIGURE_NAMES[i]);
      ImageView designation = new ImageView();
      designation.setImage(new Image(
          "/components/" + Storage.TURNED_FIGURE_NAMES[i].replaceAll("\\s+", "") + ".png"));
      ImageView turns = new ImageView();
      turns.setImage(new Image(
          "/application/" + Storage.TURNED_FIGURE_NAMES[i].replaceAll("\\s+", "") + "Turnes.png"));
      if (i < Storage.FIGURES_IN_COLUMN) {
        secondPage.add(name, 3, 2 + i);
        secondPage.add(designation, 4, 2 + i);
        secondPage.add(turns, 5, 2 + i);
      } else {
        thirdPage.add(name, 3, 2 + i - Storage.FIGURES_IN_COLUMN);
        thirdPage.add(designation, 4, 2 + i - Storage.FIGURES_IN_COLUMN);
        thirdPage.add(turns, 5, 2 + i - Storage.FIGURES_IN_COLUMN);
      }
    }
  }
}
