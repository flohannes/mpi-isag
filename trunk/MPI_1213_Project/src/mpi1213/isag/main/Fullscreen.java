package mpi1213.isag.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import processing.core.PApplet;

public class Fullscreen {
  private static boolean FULL_SCREEN = false;
  public static Dimension APP_SIZE = new Dimension(800, 600);
  
  private static void setChrome(PApplet papplet, boolean full) {
    papplet.frame.setUndecorated(full);
  }

  private static void setLocation(PApplet papplet, boolean full) {
    papplet.frame.setLocation(0, 0);      
  }

  private static void setSize(PApplet papplet, boolean full) {
    if (full) {
      papplet.frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    } else {
      papplet.frame.setSize(APP_SIZE);
    }    
  }
    
  public static void toggle(PApplet papplet) {
    FULL_SCREEN = !FULL_SCREEN; 
    papplet.frame.dispose();
    papplet.frame.setResizable(!FULL_SCREEN);
    setSize(papplet, FULL_SCREEN);
    setChrome(papplet, FULL_SCREEN);
    setLocation(papplet, FULL_SCREEN);
    papplet.frame.setVisible(true);
  }
}
