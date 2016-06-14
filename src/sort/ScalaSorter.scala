package sort
import java.util.LinkedList;
import scala.collection.JavaConversions.asScalaBuffer;
import scala.collection.mutable.Buffer;
import logic.GameSaver;
import scala.util.Sorting.stableSort;
import scala.collection.mutable.Map

object ScalaSorter {

  def getScore(content: Array[String]) : Int = {
    val last = content(content.length - 1)
    (200 - content.length + 1) * 2500 + {
      if (last == "First player wins") 1000000
      else if (last == "Second player wins") 500000 else 0
    } +
      getBeatScore(content)
  }

  def getBeatScore(content: Array[String]) : Int = {
    if (content.length == 0) 0 else {
      if (content.head(3) == 'x') 100 + getBeatScore(content.tail)
      else 0 + getBeatScore(content.tail)
    }
  }

  def sortFiles() : Array[Int] = {
    val numberOfFiles = GameSaver.getNumberOfSavedGames
    val elements  = for (i <- 1 to numberOfFiles) yield { (i, getScore(GameSaver.getFileContent(i))) }
    val newElements = elements.sortBy(_._2)
    val order = newElements.collect({case (x,y) => x})
    order.toArray
  }
  
  def getMU() : String = {
    def fun[T](b:Map[T,Int]) = (a: (T,Int)) => if(b.contains(a._1)) (a._1 -> (b.get(a._1).head + a._2)) else (a._1 -> a._2)
    def getFile[T](content: Array[T]) : Map[T,Int] = {
       val temp = fun(Map(content.head -> 1))
       if(content.length==1) Map(content.head -> 1)
       else Map(content.head -> 1) ++ getFile(content.tail).map(temp)
    }
    def getAll(limit : Int) : Map[String,Int] = {
      val data = getFile(GameSaver.getTargetCell(limit-1))
      val temp = fun(data)
      if(limit==2) data
      else data ++ getAll(limit-1).map(temp)
    }
    def getTurnNum(limit : Int) : Int = {
      if(limit==1) GameSaver.getFileContent(limit).size - 1
      else GameSaver.getFileContent(limit).size -1 + getTurnNum(limit-1)
    }
    def getTotalScore(limit : Int) : Int = {
      if(limit==1) getScore(GameSaver.getFileContent(limit))
      else getScore(GameSaver.getFileContent(limit)) + getTotalScore(limit-1)
    }
    val total = GameSaver.getNumberOfSavedGames
    "Total turns: " + getTurnNum(total) + "\nAverage turns: " + getTurnNum(total)/total +
    "\nAverage score: " + getTotalScore(total)/total + "\nMost commonly used field: " + 
    getAll(total).maxBy(_._2)._1 
  }
  
  def printTextReplay(content : Array[String]) : Unit = {
    content.head.charAt(3) match {
      case 'x' => print("Beat enemy figure by")
      case '-' => print("Move")
      case _ => print("Drop")  
    }
    content.head.charAt(0) match {
      case 'P' => print(" pawn")
      case 'B' => print(" bishop")
      case 'G' => print(" gold")
      case 'K' => print(" king")
      case 'N' => print(" knight")
      case 'L' => print(" lance")
      case 'R' => print(" rook")
      case 'S' => print(" silver")
    }
    content.head.charAt(3) match {
      case 'x' => print(" and move it from "+content.head.substring(1, 3).toUpperCase() + " to " + content.head.substring(4, 6).toUpperCase())
      case '-' => print(" from "+content.head.substring(1, 3).toUpperCase() + " to " + content.head.substring(4, 6).toUpperCase())
      case _ => print(" to " + content.head.substring(2, 4).toUpperCase())
    }
    if(content.head.size>6) print(" and turn")
    print('\n')
    if(content.size>2) printTextReplay(content.tail) else if(content.size==2) print(content.tail.head + '\n') 
  }
  
}