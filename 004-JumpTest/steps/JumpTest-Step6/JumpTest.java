import java.awt.Frame;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class JumpTest extends Frame {

    private GameObject player = null;

    private void clearScreen (Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 320, 240);
    }

    public void draw () {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();
            clearScreen (g);
            player.draw (g);
        } finally {
            g.dispose();
        }

        bf.show();

        Toolkit.getDefaultToolkit().sync();
    }

    public void pressKey (int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            System.exit (0);
        }
        if (code == KeyEvent.VK_RIGHT) {
            player.setDx (3);
        }
        if (code == KeyEvent.VK_LEFT) {
            player.setDx (-3);
        }
    }

    public void update () {
        player.update ();
    }

    public JumpTest() {
        this.addWindowListener(new JumpTestWindowAdapter());
        this.addKeyListener(new JumpTestKeyAdapter(this));
        this.setUndecorated(true);
        this.setSize(320, 240);
        this.setVisible(true);
        this.createBufferStrategy(2);

        player = new GameObject (48, 0, 16, 16, Color.WHITE);

        Timer t = new Timer(20, new JumpTestActionListener(this));
        t.start();
    }

    public static void main (String[] args) {
        new JumpTest();
    }

}
