package sut.gametest.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import sut.gametest.core.MyGame;

public class MyGameActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new MyGame());
  }
}
