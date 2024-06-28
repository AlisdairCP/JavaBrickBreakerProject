import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class Core extends JPanel implements ActionListener, KeyListener {
    private boolean activeGame = true;
    
    private int score = 0;
    private int brickCount = 25;
    private int speed = 5;
    private int xPosPlayer = 350;
    private int xPosBall = 300;
    private int yPosBall = 300;
    private int xDirBall = -1;
    private int yDirBall = 3;

    private Room aRoom;

    private Timer timer;

    static boolean active_left = false;
    static boolean active_right = false;

    JButton start_button = new JButton("Start Game");

    public Core() {
        aRoom = new Room(5, 5);

        setFocusable(true);

        addKeyListener(this);

        timer = new Timer(speed, this);
        timer.start();
    }

    public void paint(Graphics graphicSet) {
        graphicSet.setColor(Color.BLACK);
        graphicSet.fillRect(1, 1, 790, 590);
        
        graphicSet.setColor(Color.GREEN);
        graphicSet.fillRect(0, 0, 3, 590);
        graphicSet.fillRect(0, 0, 790, 3);
        graphicSet.fillRect(789, 0, 3, 590);

        aRoom.draw((Graphics2D)graphicSet);

        graphicSet.setColor(Color.WHITE);
        graphicSet.setFont(new Font("Tahoma", Font.BOLD, 20));
        graphicSet.drawString(("" + score), (745 - ((String.valueOf(score)).length() - 1)), 40);

        graphicSet.setColor(Color.BLUE);
        graphicSet.fillRect(xPosPlayer, 550, 100, 8);

        graphicSet.setColor(Color.WHITE);
        graphicSet.fillOval(xPosBall, yPosBall, 15, 15);

        if(yPosBall > 570) {
            activeGame = false;

            active_left = false;
            active_right = false;

            xDirBall = 0;
            yDirBall = 0;

            graphicSet.setFont(new Font("Tahoma", Font.BOLD, 40));
            graphicSet.drawString("GAME OVER", 270, 320);
            graphicSet.setFont(new Font("Tahoma", Font.BOLD, 25));
            graphicSet.drawString(("Final score: " + score), 295, 375);
            graphicSet.drawString("Press Enter to play again" , 240, 425);
        }

        if(brickCount == 0) {
            activeGame = false;

            active_left = false;
            active_right = false;

            xDirBall = 0;
            yDirBall = 0;

            graphicSet.setFont(new Font("Tahoma", Font.BOLD, 40));
            graphicSet.drawString("ALL CLEAR", 270, 320);
            graphicSet.setFont(new Font("Tahoma", Font.BOLD, 25));
            graphicSet.drawString("Press Enter to play again" , 230, 375);
        }


        graphicSet.dispose();

    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            active_left = false;
            active_right = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            active_left = true;
            active_right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!activeGame) {
                activeGame = true;

                defaultPara();
                aRoom = new Room(5, 5);

                repaint();
            }
        }
    }

    public void toRight() {
        xPosPlayer += 10;
    } 
    
    public void toLeft() {
        xPosPlayer -= 10;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            active_right = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            active_left = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (activeGame) {
            requestFocus();
            requestFocusInWindow();
            if (new Rectangle(xPosBall, yPosBall, 15, 15).intersects(new Rectangle(xPosPlayer, 550, 100, 8))){
                yDirBall = -yDirBall;
            }
            if (active_left == true) {
                if(xPosPlayer <= 3) {
                    xPosPlayer = 3;
                }
                else {
                    toLeft();
                }
            }
            if (active_right == true) {
                if(xPosPlayer >= 687) {
                    xPosPlayer = 687;
                }
                else {
                    active_right = true;
                    toRight();
                }
            }
            for(int r = 0; r < aRoom.room.length; r++) {
                for (int c = 0; c < aRoom.room[0].length; c++) {
                    if (aRoom.room[r][c] > 0) {
                        int rowBrick = c * aRoom.brickWid + 80;
                        int colBrick = r * aRoom.brickHei + 50;
                        int brickHei = aRoom.brickHei;
                        int brickWid = aRoom.brickWid;

                        Rectangle brick = new Rectangle(rowBrick, colBrick, brickWid, brickHei);
                        Rectangle aBall = new Rectangle(xPosBall, yPosBall, 15, 15);

                        if (aBall.intersects(brick)) {
                            aRoom.manageBrickState(0, r, c);

                            brickCount--;
                            if (yDirBall >= 0 && yDirBall < 7) {
                                yDirBall++;
                            }
                            else if (yDirBall < 0 && yDirBall > -7) {
                                yDirBall--;
                            }
                            score += 10;

                            if ((xPosBall + 19) <= brick.x || (xPosBall + 1) >= (brick.x + brick.width)) {
                                xDirBall = -xDirBall;
                            }
                            else {
                                yDirBall = -yDirBall;
                            }
                        }
                    }
                }
            }
            
            xPosBall += xDirBall;
            yPosBall += yDirBall;
            if(xPosBall < 0 || xPosBall > 775) {
                xDirBall = -xDirBall;
            }
            if(yPosBall < 0) {
                yDirBall = -yDirBall;
            }
            repaint();
        }
    }

    public void defaultPara() {
        score = 0;
        brickCount = 25;
        xPosPlayer = 350;
        xPosBall = 300;
        yPosBall = 300;
        xDirBall = -1;
        yDirBall = -3;
    }

}
