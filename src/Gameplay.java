package src;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private GameEngine engine;
    private GameRenderer renderer;
    private Timer timer;
    private AtomicBoolean speedUp = new AtomicBoolean(true);
    
    public Gameplay() {
        try {
            engine = new GameEngine();
            renderer = new GameRenderer();
            
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    requestFocusInWindow();
                }
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    requestFocusInWindow();
                }
            });
            
            timer = new Timer(engine.getDelay(), this);
            timer.start();
            
            System.out.println("Game initialized successfully");
            System.out.println("Click on the game window to focus, then press SPACE to start");
        } catch (Exception e) {
            System.err.println("Error initializing game: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        try {
            renderer.render(g, engine);
            g.dispose();
        } catch (Exception e) {
            System.err.println("Paint error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            renderer.render(g, engine);
        } catch (Exception e) {
            System.err.println("Paint error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            engine.update();
            repaint();
        } catch (Exception ex) {
            System.err.println("Update error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println("Key detected: " + KeyEvent.getKeyText(e.getKeyCode())); // DEBUG
        
        try {
            Snake snake = engine.getSnake();
            
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SHIFT:
                    handleSpeedBoost();
                    break;
                case KeyEvent.VK_SPACE:
                    handleSpaceBar();
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    snake.moveRight();
                    break;
                    
                case KeyEvent.VK_LEFT:
                    snake.moveLeft();
                    break;
                    
                case KeyEvent.VK_UP:
                    snake.moveUp();
                    break;
                    
                case KeyEvent.VK_DOWN:
                    snake.moveDown();
                    break;
            }
        } catch (Exception ex) {
            System.err.println("Key press error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleSpeedBoost() {
        if (speedUp.compareAndSet(true, false)) {
            int currentDelay = engine.getDelay();
            if (currentDelay > 100) {
                timer.setDelay(currentDelay / 10);
            } else {
                timer.setDelay(10);
            }
        }
    }
    
    private void handleSpaceBar() {
        Snake snake = engine.getSnake();
        
        if (!engine.isGameStarted()) {
            System.out.println("Starting game...");
            engine.startGame();
        } else if (snake.getDeathStatus()) {
            System.out.println("Restarting game...");
            engine.getScore().saveNewScore();
            engine.resetGame();
            timer.setDelay(engine.getDelay());
            repaint();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speedUp.set(true);
            timer.setDelay(engine.getDelay());
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}