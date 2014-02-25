package sut.gametest.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

public class MyGame extends Game.Default {


    public static final int UPDATE_RATE = 25;
    private ScreenStack ss = new ScreenStack();
    protected final Clock.Source clock = new Clock.Source(UPDATE_RATE);
    public MyGame() {
        super(UPDATE_RATE);
    }

  @Override
  public void init() {
      ss.push(new HomeScreen(ss));
//    Image bgImage = assets().getImage("images/bg.png");
//    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
//    graphics().rootLayer().add(bgLayer);
  }

  @Override
  public void update(int delta) {
      ss.update(delta);
  }

  @Override
  public void paint(float alpha) {
      clock.paint(alpha);
      ss.paint(clock);
  }
}
