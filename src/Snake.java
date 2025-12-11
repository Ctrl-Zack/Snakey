package src;

public class Snake {
    public int[] snakeLenX = new int[GameConfig.GRID_COLS * GameConfig.GRID_ROWS];
    public int[] snakeLenY = new int[GameConfig.GRID_COLS * GameConfig.GRID_ROWS];
    
    private int len;
    public int moves;
    private boolean left, right, up, down, death;

    public Snake(){
        this.up = false;
        this.down = false;
        this.right = false;
        this.left = false;
        this.death = false;
        this.moves = 0;
        this.len = 1;
    }

    // Getters
    public boolean getUp() { return up; }
    public boolean getDown() { return down; }
    public boolean getRight() { return right; }
    public boolean getLeft() { return left; }
    public boolean getDeathStatus() { return death; }
    public int getSnakeLength() { return len; }
    
    // Setters
    public void setUp(boolean up) { this.up = up; }
    public void setDown(boolean down) { this.down = down; }
    public void setRight(boolean right) { this.right = right; }
    public void setLeft(boolean left) { this.left = left; }
    public void setDeathStatus(boolean death) { this.death = death; }
    public void setSnakeLength(int len) { this.len = len; }

    public void moveRight(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.left) {
                this.right = true;
            } else {
                this.right = false;
                this.left = true;
            }
            this.up = false;
            this.down = false;
        }
    }

    public void moveLeft(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.right) {
                this.left = true;
            } else {
                this.left = false;
                this.right = true;
            }
            this.up = false;
            this.down = false;
        }
    }

    public void moveUp(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.down) {
                this.up = true;
            } else {
                this.up = false;
                this.down = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    public void moveDown(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.up) {
                this.down = true;
            } else {
                this.down = false;
                this.up = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    public void dead() {
        this.up = false;
        this.down = false;
        this.right = false;
        this.left = false;
        this.death = true;
    }

    public void movementRight(){
        // Shift body
        for (int i = this.len - 1; i >= 0; i--) {
            this.snakeLenY[i + 1] = this.snakeLenY[i];
        }
        for (int i = this.len - 1; i >= 0; i--) {
            if (i == 0) {
                this.snakeLenX[i] = this.snakeLenX[i] + GameConfig.GRID_SIZE;
            } else {
                this.snakeLenX[i] = this.snakeLenX[i - 1];
            }
        }
    }

    public void movementLeft(){
        for (int i = this.len - 1; i >= 0; i--) {
            this.snakeLenY[i + 1] = this.snakeLenY[i];
        }
        for (int i = this.len - 1; i >= 0; i--) {
            if (i == 0) {
                this.snakeLenX[i] = this.snakeLenX[i] - GameConfig.GRID_SIZE;
            } else {
                this.snakeLenX[i] = this.snakeLenX[i - 1];
            }
        }
    }

    public void movementUp(){
        for (int i = this.len - 1; i >= 0; i--) {
            this.snakeLenX[i + 1] = this.snakeLenX[i];
        }
        for (int i = this.len - 1; i >= 0; i--) {
            if (i == 0) {
                this.snakeLenY[i] = this.snakeLenY[i] - GameConfig.GRID_SIZE;
            } else {
                this.snakeLenY[i] = this.snakeLenY[i - 1];
            }
        }
    }

    public void movementDown(){
        for (int i = this.len - 1; i >= 0; i--) {
            this.snakeLenX[i + 1] = this.snakeLenX[i];
        }
        for (int i = this.len - 1; i >= 0; i--) {
            if (i == 0) {
                this.snakeLenY[i] = this.snakeLenY[i] + GameConfig.GRID_SIZE;
            } else {
                this.snakeLenY[i] = this.snakeLenY[i - 1];
            }
        }
    }
}