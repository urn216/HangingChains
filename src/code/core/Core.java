package code.core;

import java.awt.image.BufferedImage;

import code.math.IOHelp;
import code.models.CubeBridge;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Core {

  public static final Window WINDOW = new Window();

  private static final int MAP_WIDTH    = 92;
  private static final int MAP_HEIGHT   = 50;
  private static final double MAP_RATIO = 1.0*MAP_WIDTH/MAP_HEIGHT;

  private static volatile BufferedImage img = new BufferedImage(MAP_WIDTH*2, MAP_HEIGHT*2, 2);
  private static final Bridge bridge = new Bridge(4, MAP_WIDTH);

  static {
    WINDOW.setFullscreen(false);

    WINDOW.FRAME.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
          case KeyEvent.VK_MINUS:
          bridge.setSegmentLength(bridge.getSegmentLength()-0.01);
          break;
          case KeyEvent.VK_EQUALS:
          bridge.setSegmentLength(bridge.getSegmentLength()+0.01);
          break;
          case KeyEvent.VK_COMMA:
          bridge.setTension(bridge.getTension()-1);
          break;
          case KeyEvent.VK_PERIOD:
          bridge.setTension(bridge.getTension()+1);
          break;
          case KeyEvent.VK_UP:
          bridge.setEndOffset(bridge.getEndOffset()+1);
          break;
          case KeyEvent.VK_DOWN:
          bridge.setEndOffset(bridge.getEndOffset()-1);
          break;
          case KeyEvent.VK_ENTER:
          IOHelp.saveToFile("../results/bridge.obj", new CubeBridge(bridge).toString());
          break;
          default:
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {}
    });
  }

  public static void main(String[] args) throws InterruptedException {
    while (true) {
      bridge.settleOnce();
      WINDOW.PANEL.repaint();
      Thread.sleep(10);
    }
  }

  public static void clearImage() {
    for (int i = 0; i < img.getWidth(); i++) {
      for (int j = 0; j < img.getHeight(); j++) {
        img.setRGB(i, j, 255<<24);
      }
    }
  }

  /**
  * Paints the contents of the program to the given {@code Graphics} object.
  * 
  * @param gra the supplied {@code Graphics} object
  */
  public static void paintComponent(Graphics gra) {
    clearImage();
    bridge.draw(img);
    int size = Math.min((int)(WINDOW.screenWidth()/MAP_RATIO), WINDOW.screenHeight());
    gra.drawImage(img.getScaledInstance((int)(size*MAP_RATIO), size, BufferedImage.SCALE_DEFAULT), 0, 0, null);
  }
}
