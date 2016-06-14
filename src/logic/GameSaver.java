package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import application.Storage;

public class GameSaver {

  private static final LinkedList<String> turns = new LinkedList<String>();
  private static ListIterator<String> current = turns.listIterator(0);
  
  static public void saveTurns(int number,LinkedList<Turn> turns, int winState) throws IOException {
    File file = new File("Saves/Save" + number + ".txt");
    file.createNewFile();
    Path path = Paths.get("Saves/Save" + number + ".txt");
    try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
      for(Turn turn : turns){
        writer.write(turn.toString());
        writer.newLine();
      }
      switch (winState) {
        case Storage.NO_WINNERS:
          writer.write("No winners");
          writer.newLine();
         break;
        case Storage.FIRST_PLAYER_WINS:
          writer.write("First player wins");
          writer.newLine();
          break;
        case Storage.SECOND_PLAYER_WINS:
          writer.write("Second player wins");
          writer.newLine();
          break;
      }
    }
  }
  
  static public void loadTurns(int number) throws IOException {
    Path path = Paths.get("Saves/Save" + number + ".txt");
    turns.clear();
    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
      while (scanner.hasNextLine()){
        turns.add(scanner.nextLine());
      }      
    }
    turns.removeLast();
    current = turns.listIterator(0);
  }
  
  static public String getNext() {
    if(current.hasNext()) return current.next();
    else return new String("");
  }
  
  static public int getNumberOfSavedGames() {
    File file = new File("Saves/");
    File[] list = file.listFiles();
    return list.length;
  }
  
  public static String [] getFileContent(int number) {
    LinkedList<String> content = new LinkedList<String>();
    Path path = Paths.get("Saves/Save" + number + ".txt");
    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
      while (scanner.hasNextLine()){
        content.add(scanner.nextLine());
      }      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String [] con = new String[content.size()];
    content.toArray(con);
    return con;
  }
  
  public static String [] getTargetCell(int number) {
    LinkedList<String> content = new LinkedList<String>();
    Path path = Paths.get("Saves/Save" + number + ".txt");
    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
      while (scanner.hasNextLine()){
        String line = scanner.nextLine();
        if(line.charAt(1)=='*') content.add(line.substring(2,4));
        else content.add(line.substring(4,6));
      }      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String [] con = new String[content.size()];
    content.toArray(con);
    return con;
  }
  
}
