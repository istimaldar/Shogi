package logic;

import java.util.ArrayDeque;
import java.util.Deque;

public class FigureModel {

  private Deque<int[]> shortMoves;
  private Deque<int[]> longMoves;
  private Deque<int[]> jumpMoves;
  private boolean isKing;
  private boolean isWhite;
  private boolean isPawn;
  private int forbiddenZone;
  private boolean isTurnable;
  private boolean isTurned;
  private CellModel placedAt;
  String type;

  public FigureModel(boolean isWhite, boolean isKing, boolean isPawn, boolean isTurnable,
      int forbiddenZone,String type) {
    this.isWhite = isWhite;
    this.isKing = isKing;
    this.isPawn = isPawn;
    this.isTurnable = isTurnable;
    this.forbiddenZone = forbiddenZone;
    shortMoves = new ArrayDeque<int[]>();
    longMoves = new ArrayDeque<int[]>();
    jumpMoves = new ArrayDeque<int[]>();
    this.type = type;
  }

  public void clearMoves() {
    shortMoves.clear();
    longMoves.clear();
    jumpMoves.clear();
  }

  public void addShortMove(int[] move) {
    shortMoves.add(move);
  }

  public void addLongMove(int[] move) {
    longMoves.add(move);
  }

  public void addJumpMove(int[] move) {
    jumpMoves.add(move);
  }

  public Deque<int[]> getShortMoves() {
    return shortMoves;
  }

  public Deque<int[]> getLongMoves() {
    return longMoves;
  }

  public Deque<int[]> getJumpMoves() {
    return jumpMoves;
  }

  public void setShortMoves(Deque<int[]> shortMoves) {
    this.shortMoves = shortMoves;
  }

  public void setLongMoves(Deque<int[]> longMoves) {
    this.longMoves = longMoves;
  }

  public void setJumpMoves(Deque<int[]> jumpMoves) {
    this.jumpMoves = jumpMoves;
  }

  public boolean belogToTheSamePlayer(FigureModel compared) {
    return (isWhite == compared.isWhite);
  }

  public boolean getIsWhite() {
    return isWhite;
  }

  public void changeSide() {
    if (isWhite == true)
      isWhite = false;
    else
      isWhite = true;
  }

  public void turnFront() {
    if (isTurnable == true)
      isTurned = false;
  }

  public void turnBack() {
    if (isTurnable == true)
      isTurned = true;
  }

  public void setPlacedAt(CellModel placedAt) {
    this.placedAt = placedAt;
  }

  public CellModel getPlacedAt() {
    return placedAt;
  }

  public boolean getIsKing() {
    return isKing;
  }

  public boolean getIsTurned() {
    return isTurned;
  }

  void setIsTurned(boolean isTurned) {
    this.isTurned = isTurned;
  }

  int getForbiddenZone() {
    return forbiddenZone;
  }

  boolean getIsPawn() {
    return isPawn;
  }
  
  public String getType() {
    return type;
  }
  
  public int getFigurePrice() {
    if(type=="K") return 0;
    else if(type=="B"||type=="R") return 5;
    return 1;
  }

}
