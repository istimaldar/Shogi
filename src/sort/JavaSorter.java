package sort;

import java.util.ArrayList;
import java.util.Comparator;


import logic.GameSaver;

class SortedElement {
  int number;
  int score;

  public SortedElement(int number,int score) {
    this.number=number;
    this.score=score;
  }
  
  public String toString() {
    return number + " " + score;
  }
  
}

class SortedElementComporator implements Comparator<SortedElement> {

  @Override
  public int compare(SortedElement o1, SortedElement o2) {
    return o1.score-o2.score;
  }
  
}

public class JavaSorter {

  public static int getScore(String [] content) {
    int score = 0;
    String last = content[content.length - 1];
    if (last.compareTo("First player wins")==0)
      score += 1000000;
    else if (last.compareTo("Second player wins")==0)
      score += 500000;
    score += (200 - content.length + 1) * 2500;
    for (String current : content)
      if (current.charAt(3) == 'x')
        score += 100;
    return score;
  }

  public static int [] sortFiles() {
    ArrayList<SortedElement> elements = new ArrayList<SortedElement>();
    int numberOfFiles = GameSaver.getNumberOfSavedGames();
    for (int i = 1; i <= numberOfFiles; i++) elements.add(new SortedElement(i,getScore(GameSaver.getFileContent(i))));
    elements.sort(new SortedElementComporator());
    int [] result = new int[elements.size()];
    for(int i=0;i<elements.size();i++) result[i] = elements.get(i).number;
    return result;
  }

}
