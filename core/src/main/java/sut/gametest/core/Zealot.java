package sut.gametest.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;

public class Zealot {

    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;

    public Layer layer() {
        return sprite.layer();
    }

    public enum State {
        IDLE,RUN,JUMP,FALL
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 0;

    private Body initPhysicsBody(World world,float x,float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(56*SecondScreen.M_per_Pixel / 2,sprite.layer().height()*
                        SecondScreen.M_per_Pixel / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }

    public Zealot(final World world,final float x_px,final float y_px) {
        sprite = SpriteLoader.getSprite("images/cloud.png");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px + 13f);
                body = initPhysicsBody(world,SecondScreen.M_per_Pixel * x_px,
                                             SecondScreen.M_per_Pixel * y_px);
                hasLoaded = true;

            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error(" !!!! Error loading image !!!! ",cause);

            }
        });
    }
    public void update(int delta){
        if(!hasLoaded) return;
        e += delta;

    }
    public void paint(Clock clock) {
        if (!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x / SecondScreen.M_per_Pixel) - 10,
                body.getPosition().y / SecondScreen.M_per_Pixel);
    }
}

