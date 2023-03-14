package code.core;

import java.awt.image.BufferedImage;

import code.math.MathHelp;

public class Bridge {
  private final int width;
  private final int length;
  
  private final double[] heights;
  private final double[] prevHeights;
  
  private double segmentLength = 1;

  private int tension = 100;
  
  public Bridge(int width, int length) {
    this.width  = width;
    this.length = length;
    
    this.heights     = new double[length];
    this.prevHeights = new double[length];
  }
  
  public int getEndOffset() {
    return (int)heights[length-1];
  }
  
  public double getSegmentLength() {
    return segmentLength;
  }
  
  public double[] getHeights() {
    return heights;
  }

  public int getTension() {
    return tension;
  }
  
  public int getLength() {
    return length;
  }
  
  public int getWidth() {
    return width;
  }
  
  public void setEndOffset(int endOffset) {
    this.heights[length-1] = endOffset;
  }
  
  public void setSegmentLength(double segmentLength) {
    this.segmentLength = Math.max(segmentLength, 1);
  }

  public void setTension(int tension) {
    this.tension = tension;
  }

  public void settleXTimes(int x) {
    for (int i = 0; i < x; i++) {
      settleOnce();
    }
  }
  
  public void settleOnce() {
    for (int i = 1; i < heights.length-1; i++) {
      double h = heights[i];
      heights[i] -= 1 - (heights[i] - prevHeights[i]);
      prevHeights[i] = h;
    }

    double heightOffset = Math.sqrt(segmentLength*segmentLength - 1) / 2;

    for (int i = 0; i < tension; i++) {
      for (int connection = 1; connection < heights.length; connection++) {
        int dir = heights[connection-1] > heights[connection] ? -1 : 1;
        double avg = MathHelp.avg(heights[connection-1], heights[connection]);
        if (connection > 1)                heights[connection-1] = avg - dir*heightOffset;
        if (connection < heights.length-1) heights[connection]   = avg + dir*heightOffset;
      }
    }
  }
  
  public void draw(BufferedImage img) {
    for (int i = 0; i < heights.length; i++) {
      int height = (int)(img.getHeight() - (heights[i]+img.getHeight()/2));
      height = (int)MathHelp.clamp(height, 0, img.getHeight()-1);
      img.setRGB(i*2  , height  , Integer.MAX_VALUE);
      img.setRGB(i*2+1, height  , Integer.MAX_VALUE);
      img.setRGB(i*2  , height-1, Integer.MAX_VALUE);
      img.setRGB(i*2+1, height-1, Integer.MAX_VALUE);
    }
  }
}
