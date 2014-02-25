package sut.gametest.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.util.Callback;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.game.Screen;
import tripleplay.ui.Button;
import tripleplay.ui.Root;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class ThirdScreen extends Screen{

    private ScreenStack ss = new ScreenStack();
    private ImageLayer bgLayer;
    private Image bgImage;

    public ThirdScreen(ScreenStack ss) {
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        super.wasAdded();
        Image bgImage = assets().getImage("images/bag.png");
        bgImage.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image image) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        layer.add(bgLayer);

        Image bgImage2 = assets().getImage("images/back.png");
        bgImage2.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image image) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
        ImageLayer bgLayer2 = graphics().createImageLayer(bgImage2);
        layer.add(bgLayer2);

        bgLayer2.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(ss.top());
            }
        });
    }

}
