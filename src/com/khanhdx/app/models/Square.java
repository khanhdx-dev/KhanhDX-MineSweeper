package com.khanhdx.app.models;

public abstract class Square
{
  private int x;
  private int y;
  private boolean visible;

  public Square(int x, int y)
  {
    this.x = x;
    this.y = y;
    this.visible = false;
  }

  public int x()
  {
    return this.x;
  }

  public int y()
  {
    return this.y;
  }

  public boolean isVisible()
  {
    return this.visible;
  }

  public void setVisible(boolean visible)
  {
    this.visible = visible;
  }

  public abstract char c();
}
