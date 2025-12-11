package src;

public class Bomb extends Item {
    
    public Bomb(int offsetX, int offsetY, int maxWidth, int maxHeight, int gridSize) {
        super(offsetX, offsetY, maxWidth, maxHeight, gridSize);
    }

    @Override
    public void applyEffect(Snake snake, Score score) {
        snake.dead();
    }
    
    @Override
    public boolean checkCollision(int snakeHeadX, int snakeHeadY) {
        return this.x == snakeHeadX && this.y == snakeHeadY;
    }
}