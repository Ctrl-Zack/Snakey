package src;

public class GameConfig {
    // Grid Configuration
    public static final int GRID_COLS = 40;
    public static final int GRID_ROWS = 40;
    public static final int GRID_SIZE = 15;
    
    // Game Area
    public static final int GAME_X_OFFSET = 25;
    public static final int GAME_Y_OFFSET = 75;
    public static final int GAME_WIDTH = GRID_COLS * GRID_SIZE;   // 600
    public static final int GAME_HEIGHT = GRID_ROWS * GRID_SIZE;  // 600
    
    // Game Settings
    public static final int INITIAL_SNAKE_LENGTH = 3;
    public static final int NUM_BOMBS = 15;
    public static final int INITIAL_DELAY = 500;
    
    // UI Layout
    public static final int WINDOW_WIDTH = 910;
    public static final int WINDOW_HEIGHT = 750;
    public static final int TITLE_HEIGHT = 65;
    
    // Scoreboard
    public static final int SCOREBOARD_X = GAME_X_OFFSET + GAME_WIDTH + 11; // 636
    public static final int SCOREBOARD_WIDTH = 240;
}