package application;

import javafx.scene.Group;

public abstract class Storage extends Object {

  public static Group root = new Group();

  public static final double BUTTON_HEIGHT = 20;
  public static final double BUTTON_WIDTH = 200;
  public static final double FIELD_SIZE = 450;
  public static final int FIGURES_IN_COLUMN = 5;
  public static final int CODE_OF_A = 97;
  public static final int CODE_OF_0 = 48;

  public static final String FIRST_PAGE_TEXT = "Shogi, also known as Japanese chess or "
      + "the Generals' Game, is a two-player strategy board game in\nthe same "
      + "family as Western (international) chess, chaturanga, makruk, shatranj, "
      + "janggi and xiangqi,\nand is the most popular of a family of chess variants"
      + "native to Japan. Shōgi means general's (shō 将)\nboard game (gi 棋).\n\n"
      + "Two players, Sente 先手 (Black; more literally, person with the first move) "
      + "and Gote 後手 (White;\nperson with the second move), play on a board "
      + "composed of rectangles in a grid of 9 ranks (rows)\nby 9 files (columns). "
      + "The rectangles are undifferentiated by marking or color. The board is "
      + "nearly\nalways rectangular; square boards are uncommon. Pairs of dots "
      + "mark the players' promotion zones.\n\n"
      + "Each player has a set of 20 wedge-shaped pieces of slightly different "
      + "sizes. Except for the kings,\nopposing pieces are undifferentiated by "
      + "marking or color. Pieces face forward (toward the opponent's\nside); this "
      + "shows who controls the piece during play. The pieces from largest (most "
      + "important) to\nsmallest (least important) are:\n\n1 king\n1 rook \n"
      + "1 bishop\n2 gold generals\n2 silver generals\n2 knights\n2 lances\n" + "9 pawns\n\n"
      + "Several of these names were chosen to correspond to their rough "
      + "equivalents in international chess,\nand not as literal translations of "
      + "the Japanese names.\n\n"
      + "Each piece has its name written on its surface in the form of two kanji, "
      + "usually in black ink. On the\nreverse side of each piece, other than the "
      + "king and gold general, are one or two other characters,\nin amateur sets "
      + "often in a different color (usually red); this side is turned face up "
      + "during play to\nindicate that the piece has been promoted.";
  public static final String[] FIGURE_NAMES =
      {"Rook", "Bishop", "Silver", "Knight", "Pawn", "Lance", "King", "Gold"};
  public static final String[] TURNED_FIGURE_NAMES =
      {"Dragon", "Horse", "Promoted Silver", "Promoted Knight", "Tokin", "Promoted Lance"};

  public static final int[] BACKWARD_LEFT = {-1, -1};
  public static final int[] BACKWARD = {-1, 0};
  public static final int[] BACKWARD_RIGHT = {-1, 1};
  public static final int[] LEFT = {0, -1};
  public static final int[] RIGHT = {0, 1};
  public static final int[] FORWARD_LEFT = {1, -1};
  public static final int[] FORWARD = {1, 0};
  public static final int[] FORWARD_RIGHT = {1, 1};
  public static final int[] JUMP_FORWARD_RIGHT = {2, 1};
  public static final int[] JUMP_FORWARD_LEFT = {2, -1};
  public static final int[] JUMP_BACKWARD_RIGHT = {-2, 1};
  public static final int[] JUMP_BACKWARD_LEFT = {-2, -1};
  public static final int NO_WINNERS = 0;
  public static final int FIRST_PLAYER_WINS = 1;
  public static final int SECOND_PLAYER_WINS = 2;
  public static boolean isFirstPlayerAComputer = false;
  public static boolean isSecondPlayerAComputer = false;
  public static boolean isReplay = false;
  public static boolean isLoaded = false;
  public static boolean hasSavedGames = false;
  public static int currentGame = 0;
  public static boolean isGenereting = false;
  public static int gamesLimit = 0;
}
