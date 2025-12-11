package src;

import java.util.Random;

public abstract class Item {
    protected int x, y;
    protected Random random = new Random();
    
    protected int offsetX;
    protected int offsetY;
    protected int maxWidth;
    protected int maxHeight;
    protected int gridSize;
    
    public Item(int offsetX, int offsetY, int maxWidth, int maxHeight, int gridSize) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.gridSize = gridSize;
    }
    
    public void generateRandomPosition(int[] snakeX, int[] snakeY, int lengthOfSnake) {
        do {
            int gridXCount = maxWidth / gridSize;
            int gridYCount = maxHeight / gridSize;
            
            int randXIndex = random.nextInt(gridXCount);
            int randYIndex = random.nextInt(gridYCount);
            
            x = offsetX + (randXIndex * gridSize);
            y = offsetY + (randYIndex * gridSize);
            
        } while (isPositionOnSnake(x, y, snakeX, snakeY, lengthOfSnake));
    }
    
    private boolean isPositionOnSnake(int checkX, int checkY, int[] snakeX, int[] snakeY, int lengthOfSnake) {
        for (int i = 0; i < lengthOfSnake; i++) {
            if (checkX == snakeX[i] && checkY == snakeY[i]) {
                return true;
            }
        }
        return false;
    }

    public abstract boolean checkCollision(int snakeHeadX, int snakeHeadY);
    public abstract void applyEffect(Snake snake, Score score);
    
    public int getX() { return x; }
    public int getY() { return y; }
}