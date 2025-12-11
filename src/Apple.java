package src;

public class Apple extends Item {
    
    public Apple(int offsetX, int offsetY, int maxWidth, int maxHeight, int gridSize) {
        super(offsetX, offsetY, maxWidth, maxHeight, gridSize);
    }
    
    @Override
    public void applyEffect(Snake snake, Score score) {
        snake.setSnakeLength(snake.getSnakeLength() + 1);;
        score.increaseScore();
    }
    
    @Override
    public boolean checkCollision(int snakeHeadX, int snakeHeadY) {
        return this.x == snakeHeadX && this.y == snakeHeadY;
    }
}