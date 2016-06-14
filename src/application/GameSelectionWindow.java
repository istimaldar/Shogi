package application;

import java.util.LinkedList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameSaver;
import sort.ScalaSorter;
import sort.JavaSorter;

public class GameSelectionWindow extends BorderPane {

	static private VBox bottom;
	static private VBox center;

	public GameSelectionWindow(boolean needToSort, boolean scalaSort) {
		center = new VBox();
		center.setPadding(new Insets(100, 0, 0, 300));
		center.setSpacing(10);
		bottom = new VBox();
		bottom.setPadding(new Insets(100, 0, 0, 350));
		bottom.setSpacing(40);

		Text selectGame = new Text("Select game to load:");
		selectGame.setFill(Color.WHITE);
		selectGame.setFont(new Font(20));

		ListProperty<String> listProperty = new SimpleListProperty<>();
		LinkedList<String> turns = new LinkedList<String>();
		int numberOfSavedGames = GameSaver.getNumberOfSavedGames();
		if (needToSort) {
			int[] games;
			if (scalaSort) {
				games = ScalaSorter.sortFiles();
			} else {
				games = JavaSorter.sortFiles();
			}
			for (Integer current : games)
				turns.add(String.valueOf(current));
		} else
			for (int i = 1; i <= numberOfSavedGames; i++)
				turns.add(String.valueOf(i));
		ListView<String> gamesList = new ListView<String>();
		gamesList.itemsProperty().bind(listProperty);
		listProperty.set(FXCollections.observableArrayList(turns));
		gamesList.getSelectionModel().selectFirst();

		ShogiButton textReplay = new ShogiButton("Text replay");
		textReplay.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ScalaSorter.printTextReplay(GameSaver.getFileContent(Integer.parseInt(
						turns.get(gamesList.getSelectionModel().getSelectedIndex()))));
			}
		});

		ShogiButton load = new ShogiButton("Load");
		load.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Storage.currentGame = Integer.parseInt(turns.get(gamesList.getSelectionModel().getSelectedIndex()));
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

		center.getChildren().addAll(selectGame, gamesList);
		setCenter(center);
		bottom.getChildren().addAll(textReplay, load, back);
		setBottom(bottom);
	}

	public GameSelectionWindow() {
		this(false, false);
	}

}
