package sut.gametest.core;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;


import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class SecondScreen extends Screen {

    protected final Clock.Source clock = new Clock.Source(25);

    public static float M_per_Pixel = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;

    private World world;
    private DebugDrawBox2D debugDraw;
    private boolean showDebugDraw = true;


    private ScreenStack ss = new ScreenStack();
    private ImageLayer bgLayer;
    private Image bgImage;
    private Zealot z;

    public SecondScreen(ScreenStack ss) {
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        super.wasAdded();

        Image bgImage = assets().getImage("images/bg.png");
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

        Image bgImage3 = assets().getImage("images/forward.png");
        bgImage2.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image image) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
        ImageLayer bgLayer3 = graphics().createImageLayer(bgImage3);
        layer.add(bgLayer3);
        bgLayer3.setTranslation(512,0);

        Vec2 gravity = new Vec2(10.0f,0.0f);
        world = new World(gravity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        if (showDebugDraw) {
            CanvasImage image = graphics().createImage((int) (width / SecondScreen.M_per_Pixel),
                                                       (int) (height / SecondScreen.M_per_Pixel));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDrawBox2D.e_aabbBit |
                               DebugDraw.e_jointBit |
                               DebugDrawBox2D.e_shapeBit);
            debugDraw.setCamera(0,0,1f / SecondScreen.M_per_Pixel);
            world.setDebugDraw(debugDraw);
        }

        Body ground1 = world.createBody(new BodyDef());
        PolygonShape groundShape1 = new PolygonShape();
        groundShape1.setAsEdge(new Vec2(width-2,0f),
                              new Vec2(width-2,height+1f));
        ground1.createFixture(groundShape1,0.0f);

        Body ground2 = world.createBody(new BodyDef());
        PolygonShape groundShape2 = new PolygonShape();
        groundShape2.setAsEdge(new Vec2(2f,height-2),
                new Vec2(width-2f,height-2));
        ground2.createFixture(groundShape2,0.0f);

        Body ground3 = world.createBody(new BodyDef());
        PolygonShape groundShape3 = new PolygonShape();
        groundShape3.setAsEdge(new Vec2(2f,0f),
                new Vec2(2f,height+1f));
        ground3.createFixture(groundShape3,0.0f);

        createBox();

        z = new Zealot(world,560f,400f);
        this.layer.add(z.layer());

        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if(event.key() == Key.SPACE) {
                    Vec2 gravity = new Vec2(-10.0f,0.0f);
                }
            }
        });
        bgLayer3.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.push(new ThirdScreen(ss));
            }
        });
        bgLayer2.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(ss.top());
            }
        });



    }
    private  void createBox() {
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0,0);

        Body body = world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1.0f,1.0f);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 10f;
        fd.friction = 0.1f;
        fd.restitution = 1f;
        body.createFixture(fd);
        body.setLinearDamping(0.5f);
        body.setTransform(new Vec2(15f,9f),0);
    }
    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f,10,10);
        z.update(delta);
    }
    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        z.paint(clock);
        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }
}

