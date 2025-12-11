package src;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameRenderer {
    private ImageIcon titleImage;
    private ImageIcon snakeHead;
    private ImageIcon snakeBody;
    private ImageIcon appleImage;
    private ImageIcon bombImage;
    private ImageIcon arrowImage;
    private ImageIcon shiftImage;
    
    public GameRenderer() {
        loadImages();
    }
    
    private void loadImages() {
        try {
            titleImage = new ImageIcon("images/title.png");
            arrowImage = new ImageIcon("images/keyboardArrow.png");
            shiftImage = new ImageIcon("images/shift.png");
            
            snakeHead = scaleImage(new ImageIcon("images/snakeHead.png"), GameConfig.GRID_SIZE, GameConfig.GRID_SIZE);
            snakeBody = scaleImage(new ImageIcon("images/snakeBody.png"), GameConfig.GRID_SIZE, GameConfig.GRID_SIZE);
            appleImage = scaleImage(new ImageIcon("images/apple.png"), GameConfig.GRID_SIZE, GameConfig.GRID_SIZE);
            bombImage = scaleImage(new ImageIcon("images/bomb.png"), GameConfig.GRID_SIZE, GameConfig.GRID_SIZE);
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    private ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private Image rotateImage(Image img, double angle) {
        BufferedImage bimg = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2 = bimg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(angle), img.getWidth(null) / 2.0, img.getHeight(null) / 2.0);

        g2.drawImage(img, at, null);
        g2.dispose();

        return bimg;
    }
    
    public void render(Graphics g, GameEngine engine) {
        try {
            drawBorders(g);
            drawScoreBoard(g, engine);
            drawGameArea(g, engine);
            drawControls(g);
            
            if (!engine.isGameStarted()) {
                drawStartMessage(g);
            }
            
            if (engine.getSnake().getDeathStatus()) {
                drawGameOver(g, engine.getScore().getScore());
            }
        } catch (Exception e) {
            System.err.println("Render error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void drawBorders(Graphics g) {
        // Title border
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, GameConfig.TITLE_HEIGHT - 10);
        if (titleImage != null) {
            titleImage.paintIcon(null, g, 25, 11);
        }
        
        // Game area border
        g.setColor(Color.WHITE);
        g.drawRect(
            GameConfig.GAME_X_OFFSET - 1, 
            GameConfig.GAME_Y_OFFSET - 1, 
            GameConfig.GAME_WIDTH + 1, 
            GameConfig.GAME_HEIGHT + 1
        );
        g.setColor(Color.BLACK);
        g.fillRect(
            GameConfig.GAME_X_OFFSET, 
            GameConfig.GAME_Y_OFFSET, 
            GameConfig.GAME_WIDTH, 
            GameConfig.GAME_HEIGHT
        );
        
        // Scoreboard border
        g.setColor(Color.WHITE);
        g.drawRect(
            GameConfig.SCOREBOARD_X - 1, 
            GameConfig.GAME_Y_OFFSET - 1, 
            GameConfig.SCOREBOARD_WIDTH + 1, 
            GameConfig.GAME_HEIGHT + 1
        );
        g.setColor(Color.BLACK);
        g.fillRect(
            GameConfig.SCOREBOARD_X, 
            GameConfig.GAME_Y_OFFSET, 
            GameConfig.SCOREBOARD_WIDTH, 
            GameConfig.GAME_HEIGHT
        );
    }
    
    private void drawScoreBoard(Graphics g, GameEngine engine) {
        try {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 20));
            g.drawString("SCORE: " + engine.getScore().getScore(), GameConfig.SCOREBOARD_X + 60, 110);
            g.drawRect(GameConfig.SCOREBOARD_X, 130, GameConfig.SCOREBOARD_WIDTH - 2, 1);
            
            engine.getScore().sortHighScore();
            String highScore = engine.getScore().getHighScore();
            g.drawString("HIGHSCORE", GameConfig.SCOREBOARD_X + 50, 180);
            drawMultiLineString(g, highScore, GameConfig.SCOREBOARD_X + 50, 200);
        } catch (Exception e) {
            System.err.println("Error drawing scoreboard: " + e.getMessage());
        }
    }
    
    private void drawGameArea(Graphics g, GameEngine engine) {
        try {
            Snake snake = engine.getSnake();
            
            for (int i = 0; i < snake.getSnakeLength(); i++) {
                if (i == 0 && snakeHead != null) {
                    if(snake.getLeft()) {
                        Image rotatedImg = rotateImage(snakeHead.getImage(), 270);
                        g.drawImage(rotatedImg, snake.snakeLenX[i], snake.snakeLenY[i], null);
                    } else if (snake.getRight()) {
                        Image rotatedImg = rotateImage(snakeHead.getImage(), 90);
                        g.drawImage(rotatedImg, snake.snakeLenX[i], snake.snakeLenY[i], null);
                    } else if (snake.getDown()) {
                        Image rotatedImg = rotateImage(snakeHead.getImage(), 180);
                        g.drawImage(rotatedImg, snake.snakeLenX[i], snake.snakeLenY[i], null);
                    } else {
                        snakeHead.paintIcon(null, g, snake.snakeLenX[i], snake.snakeLenY[i]);
                    }
                } else if (snakeBody != null) {
                    snakeBody.paintIcon(null, g, snake.snakeLenX[i], snake.snakeLenY[i]);
                }
            }
            
            if (engine.isGameStarted()) {
                if (appleImage != null) {
                    appleImage.paintIcon(null, g, engine.getApple().getX(), engine.getApple().getY());
                }
                
                if (bombImage != null) {
                    for (Bomb bomb : engine.getBombs()) {
                        bombImage.paintIcon(null, g, bomb.getX(), bomb.getY());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error drawing game area: " + e.getMessage());
        }
    }
    
    private void drawControls(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(GameConfig.SCOREBOARD_X, 490, GameConfig.SCOREBOARD_WIDTH - 2, 1);
        g.setFont(new Font("Helvetica", Font.BOLD, 25));
        g.drawString("CONTROLS", GameConfig.SCOREBOARD_X + 35, 530);
        
        if (arrowImage != null) {
            arrowImage.paintIcon(null, g, GameConfig.SCOREBOARD_X + 15, 560);
        }
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString("Movement", GameConfig.SCOREBOARD_X + 115, 590);
        
        if (shiftImage != null) {
            shiftImage.paintIcon(null, g, GameConfig.SCOREBOARD_X + 40, 625);
        }
        g.drawString("Boost", GameConfig.SCOREBOARD_X + 115, 640);
    }
    
    private void drawStartMessage(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString("Press SPACE to Start!", 150, 370);
    }
    
    private void drawGameOver(Graphics g, int finalScore) {
        g.setColor(Color.RED);
        g.setFont(new Font("Courier New", Font.BOLD, 50));
        g.drawString("Game Over!", 175, 320);
        
        g.setColor(Color.GREEN);
        g.setFont(new Font("Courier New", Font.BOLD, 18));
        g.drawString("Your Score: " + finalScore, 240, 360);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Press SPACE to restart!", 165, 400);
    }
    
    private void drawMultiLineString(Graphics g, String text, int x, int y) {
        if (text == null) return;
        for (String line : text.split("\n")) {
            g.drawString(line, x, y);
            y += g.getFontMetrics().getHeight();
        }
    }
}