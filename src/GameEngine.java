package src;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private Snake snake;
    private Apple apple;
    private List<Bomb> bombs;
    private Score score;
    
    private final int MAX_POSITION_TRIES = 1000;
    
    private int delay;
    private boolean gameStarted;
    
    public GameEngine() {
        snake = new Snake();
        score = new Score();
        bombs = new ArrayList<>();
        delay = GameConfig.INITIAL_DELAY;
        gameStarted = false;
    
        initializeSnakePosition();
        
        apple = new Apple(
            GameConfig.GAME_X_OFFSET, 
            GameConfig.GAME_Y_OFFSET, 
            GameConfig.GAME_WIDTH, 
            GameConfig.GAME_HEIGHT, 
            GameConfig.GRID_SIZE
        );
        generateApplePosition();
        
        initializeBombs();
    }

    private void initializeSnakePosition() {
        // Start snake in center of grid
        int centerX = GameConfig.GAME_X_OFFSET + (GameConfig.GRID_COLS / 2) * GameConfig.GRID_SIZE;
        int centerY = GameConfig.GAME_Y_OFFSET + (GameConfig.GRID_ROWS / 2) * GameConfig.GRID_SIZE;
        
        for (int i = 0; i < GameConfig.INITIAL_SNAKE_LENGTH; i++) {
            snake.snakeLenX[i] = centerX - (i * GameConfig.GRID_SIZE);
            snake.snakeLenY[i] = centerY;
        }
        snake.setSnakeLength(GameConfig.INITIAL_SNAKE_LENGTH);
        snake.moves = 0;
        snake.setDeathStatus(false);
    }
    
    private void initializeBombs() {
        bombs.clear();
        for (int i = 0; i < GameConfig.NUM_BOMBS; i++) {
            Bomb bomb = new Bomb(
                GameConfig.GAME_X_OFFSET, 
                GameConfig.GAME_Y_OFFSET, 
                GameConfig.GAME_WIDTH, 
                GameConfig.GAME_HEIGHT, 
                GameConfig.GRID_SIZE
            );
            generateBombPosition(bomb);
            bombs.add(bomb);
        }
    }
    
    private void generateApplePosition() {
        int tries = 0;
        do {
            apple.generateRandomPosition(snake.snakeLenX, snake.snakeLenY, snake.getSnakeLength());
            tries++;
            if (tries > MAX_POSITION_TRIES) break;
        } while (isPositionOccupiedByBomb(apple.getX(), apple.getY()));
    }
    
    private void generateBombPosition(Bomb bomb) {
        int tries = 0;
        do {
            bomb.generateRandomPosition(snake.snakeLenX, snake.snakeLenY, snake.getSnakeLength());
            tries++;
            if (tries > MAX_POSITION_TRIES) break;
        } while (isPositionOccupied(bomb.getX(), bomb.getY()));
    }
    
    private boolean isPositionOccupied(int x, int y) {
        if (x == apple.getX() && y == apple.getY()) {
            return true;
        }
        
        for (Bomb existingBomb : bombs) {
            if (x == existingBomb.getX() && y == existingBomb.getY()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isPositionOccupiedByBomb(int x, int y) {
        for (Bomb bomb : bombs) {
            if (x == bomb.getX() && y == bomb.getY()) {
                return true;
            }
        }
        return false;
    }
    
    public void startGame() {
        if (!gameStarted) {
            gameStarted = true;
            snake.moves++;
            snake.setRight(true);
        }
    }
    
    public void update() {
        if (!gameStarted || snake.getDeathStatus()) {
            return;
        }
        
        // Move snake
        if (snake.getRight()) {
            snake.movementRight();
        }
        if (snake.getLeft()) {
            snake.movementLeft();
        }
        if (snake.getUp()) {
            snake.movementUp();
        }
        if (snake.getDown()) {
            snake.movementDown();
        }
        
        // Check collisions
        checkBoundaryCollision();
        checkAppleCollision();
        checkBombCollision();
        checkSelfCollision();
    }
    
    private void checkBoundaryCollision() {
        int headX = snake.snakeLenX[0];
        int headY = snake.snakeLenY[0];
        
        // Check if head is outside game area
        if (headX < GameConfig.GAME_X_OFFSET || 
            headX >= GameConfig.GAME_X_OFFSET + GameConfig.GAME_WIDTH ||
            headY < GameConfig.GAME_Y_OFFSET || 
            headY >= GameConfig.GAME_Y_OFFSET + GameConfig.GAME_HEIGHT) {
            snake.dead();
        }
    }
    
    private void checkAppleCollision() {
        if (apple.checkCollision(snake.snakeLenX[0], snake.snakeLenY[0])) {
            apple.applyEffect(snake, score);
            
            generateApplePosition();
            
            for (Bomb bomb : bombs) {
                generateBombPosition(bomb);
            }
            
            if (score.getScore() % 5 == 0 && score.getScore() != 0) {
                increaseSpeed();
            }
        }
    }
    
    private void checkBombCollision() {
        for (Bomb bomb : bombs) {
            if (bomb.checkCollision(snake.snakeLenX[0], snake.snakeLenY[0])) {
                bomb.applyEffect(snake, score);
                break;
            }
        }
    }
    
    private void checkSelfCollision() {
        for (int i = 1; i < snake.getSnakeLength(); i++) {
            if (snake.snakeLenX[i] == snake.snakeLenX[0] && 
                snake.snakeLenY[i] == snake.snakeLenY[0]) {
                snake.dead();
            }
        }
    }
    
    private void increaseSpeed() {
        if (delay > 100) {
            delay = delay - 100;
        } else if (delay == 100) {
            delay = delay - 50;
        } else if (delay <= 50 && delay > 20) {
            delay = delay - 10;
        } else {
            delay = 20;
        }
    }
    
    public void resetGame() {
        snake.moves = 0;
        snake.setDeathStatus(false);
        score.resetScore();
        delay = GameConfig.INITIAL_DELAY;
        gameStarted = false;
        
        initializeSnakePosition();
        generateApplePosition();
        
        for (Bomb bomb : bombs) {
            generateBombPosition(bomb);
        }
    }
    
    // Getters
    public Snake getSnake() { return snake; }
    public Apple getApple() { return apple; }
    public List<Bomb> getBombs() { return bombs; }
    public Score getScore() { return score; }
    public int getDelay() { return delay; }
    public boolean isGameStarted() { return gameStarted; }
}