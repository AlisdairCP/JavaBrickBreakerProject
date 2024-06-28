import java.awt.*;

public class Room {
    public int room[][];
    public int brickWid;
    public int brickHei;

    public Room(int x, int y) {
        room = new int[x][y];

        for (int i = 0; i < room.length; i++) {
            for (int n = 0; n < room[0].length; n++) {
                room[i][n] = 1;
            }
        }

        brickHei = 200/x;
        brickWid = 625/y;

    }

    public void draw(Graphics2D v) {
        for (int i = 0; i < room.length; i++) {
            for (int n = 0; n < room[0].length; n++) {
                if (room[i][n] > 0) {
                    v.setColor(Color.orange);
                    v.fillRect((n * brickWid + 80), (i * brickHei + 50), brickWid, brickHei);
                    
                    v.setStroke(new BasicStroke(1));
                    v.setColor(Color.black);
                    v.drawRect((n * brickWid + 80), (i * brickHei + 50), brickWid, brickHei);

                }
            }
        }
    }

    public void manageBrickState(int state, int x, int y) {
        room[x][y] = state;
    }
}
