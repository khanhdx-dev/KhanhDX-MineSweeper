package com.khanhdx.app.models;

public class Marker extends Square
{
  public Marker(int x, int y)
  {
    super(x, y);
    this.setVisible(true);
  }

  public char c()
  {
    return 'X';
  }
}
