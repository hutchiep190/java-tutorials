import java.util.HashSet;
import java.util.Set;

import java.awt.Frame;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class JumpTest extends Frame {

    private Set<Integer> keys = new HashSet<Integer>();
    private Set<Integer> prevKeys = new HashSet<Integer>();

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
        keys.add (code);
    }

    public void releaseKey (int code) {
        keys.remove (code);
    }

    private boolean keyIsDown (int code) {
        return keys.contains(code);
    }

    private boolean keyWasJustPressed (int code) {
        return keys.contains(code) && !prevKeys.contains(code);
    }

    public void handleEvents () {
        if (keys.contains(KeyEvent.VK_ESCAPE)) {
            System.exit (0);
        }
        if (keyIsDown(KeyEvent.VK_RIGHT) && !keyIsDown(KeyEvent.VK_LEFT)) {
            player.setDx (3);
        } else if (keyIsDown(KeyEvent.VK_LEFT) && !keyIsDown(KeyEvent.VK_RIGHT)) {
            player.setDx (-3);
        } else {
            player.setDx (0);
        }
        if (keyWasJustPressed(KeyEvent.VK_UP)) {
            player.setDy (-8);
        }
        prevKeys.clear();
        for (Integer code : keys) {
            prevKeys.add (code);
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
