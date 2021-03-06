import java.awt.Frame;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.Timer;

public class AnimTest extends Frame {

    private int t = 0;

    private void clearScreen (Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 320, 240);
    }

    private void drawSquare (Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(48, t % 224, 16, 16);
    }

    public void draw () {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();
            clearScreen (g);
            drawSquare (g);
        } finally {
            g.dispose();
        }

        bf.show();

        Toolkit.getDefaultToolkit().sync();
    }

    public void update () {
        t = t + 1;
    }

    public AnimTest() {
        this.addWindowListener(new AnimTestWindowAdapter());
        this.setUndecorated(true);
        this.setSize(320, 240);
        this.setVisible(true);
        this.createBufferStrategy(2);

        Timer t = new Timer(20, new AnimTestActionListener(this));
        t.start();
    }

    public static void main (String[] args) {
        new AnimTest();
    }

}
