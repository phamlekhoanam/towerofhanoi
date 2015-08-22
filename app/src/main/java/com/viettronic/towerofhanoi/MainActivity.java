package com.viettronic.towerofhanoi;

import android.widget.Toast;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.input.touch.TouchEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;




public class MainActivity extends SimpleBaseGameActivity {

    private static int CAMERA_WIDTH=1280;
    private static int CAMERA_HEIGHT=720;
    private ITextureRegion mBackgroundTextureRegion, mTowerTextureRegion, mRing1, mRing2, mRing3;
    private Sprite backgroundSprite, mTower1, mTower2, mTower3;
    private Stack mStack1, mStack2, mStack3;

    @Override
    public EngineOptions onCreateEngineOptions(){
        final Camera camera=new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
        final RatioResolutionPolicy rrp=new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT);
        final EngineOptions eo=new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED,rrp,camera);//just downscale
        return eo; //new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED,new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT),camera);
    }

    @Override
    protected void onCreateResources(){
        try{
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

            ITexture backgroundTextture=new BitmapTexture(this.getTextureManager(),new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/background.png");
                }
            });
            backgroundTextture.load();
            this.mBackgroundTextureRegion=TextureRegionFactory.extractFromTexture(backgroundTextture);

            ITexture towerTexture=new BitmapTexture(this.getTextureManager(),new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/tower.png");
                }
            });
            towerTexture.load();
            this.mTowerTextureRegion=TextureRegionFactory.extractFromTexture(towerTexture);

            ITexture ring1=new BitmapTexture(this.getTextureManager(),new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/ring1.png");
                }
            });
            ring1.load();
            this.mRing1=TextureRegionFactory.extractFromTexture(ring1);

            ITexture ring2=new BitmapTexture(this.getTextureManager(),new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/ring2.png");
                }
            });
            ring2.load();
            this.mRing2=TextureRegionFactory.extractFromTexture(ring2);

            ITexture ring3=new BitmapTexture(this.getTextureManager(),new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/ring3.png");
                }
            });
            ring3.load();
            this.mRing3=TextureRegionFactory.extractFromTexture(ring3);

            this.mStack1=new Stack();
            this.mStack2=new Stack();
            this.mStack3=new Stack();

        } catch (IOException e){
            Debug.e(e);
        }

    }

    @Override
    protected Scene onCreateScene(){
        final Scene scene=new Scene();
        //Ring ring1, ring2, ring3;
        backgroundSprite=new Sprite(640,360, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
        scene.attachChild(backgroundSprite);

        mTower1=new Sprite(400,370, this.mTowerTextureRegion,getVertexBufferObjectManager());
        scene.attachChild(mTower1);
        mTower2=new Sprite(640,370, this.mTowerTextureRegion, getVertexBufferObjectManager());
        scene.attachChild(mTower2);
        mTower3=new Sprite(880,370, this.mTowerTextureRegion, getVertexBufferObjectManager());
        scene.attachChild(mTower3);

        //View Subclassing technique for control the object ring1

        Ring ring1=new Ring(1,400,370, this.mRing1, getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX,float pTouchAreaLocalY) {
                if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight()) {
                    //String msg="Please select above ring!";
                    //gameToast(msg);
                    return false;
                }
                //switch (pSceneTouchEvent.getAction())
                {
                    //case TouchEvent.ACTION_DOWN:
                    //    this.setScale(1.2f);
                    //    break;
                    //case TouchEvent.ACTION_MOVE:
                    this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                    //if (this.getY()< )
                    //this.setX(pSceneTouchEvent.getX());
                    //   break;
                    //case TouchEvent.ACTION_UP:
                    {
                        //        this.setScale(1.0f);
                        //       checkForCollisionWithTower(this);
                        //       break;
                    }
                }

                //this.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, pSceneTouchEvent.getY()-this.getHeight()/2);
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    checkForCollisionWithTower(this);
                    //}

                } else if (pSceneTouchEvent.getAction()==TouchEvent.ACTION_MOVE){

                }
                return true;
            }

        };
        scene.attachChild(ring1);

        //View Subclassing technique for control the object ring2

        Ring ring2=new Ring(2,400,325, this.mRing2, getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (((Ring) this.getmStack().peek()).getmWeight() !=this.getmWeight()) {
                    //String msg="Please select ring 1!";
                    //gameToast(msg);
                    return false;
                }
                this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                //this.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, pSceneTouchEvent.getY()- this.getHeight()/2);
                if (pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP) {
                    checkForCollisionWithTower(this);
                    //scene.attachChild(this);
                }  else if (pSceneTouchEvent.getAction()==TouchEvent.ACTION_MOVE) {

                }
                return true;
            }
             /*   switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setScale(1.2f);
                        break;
                    case TouchEvent.ACTION_MOVE:
                        this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                        //if (this.getY()< )
                        //this.setX(pSceneTouchEvent.getX());
                        break;
                    case TouchEvent.ACTION_UP: {
                        this.setScale(1.0f);
                        checkForCollisionWithTower(this);
                        break;
                    }
                }
                return true;
              */

        };
        scene.attachChild(ring2);


        final Ring ring3=new Ring(3,400,276, this.mRing3, getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){

                if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight()) {
                    //String msg="Please select ring 1 or 2!";
                    //gameToast(msg);
                    return false;
                }
                this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                //this.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, pSceneTouchEvent.getY() - this.getHeight()/2);
                if (pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP) {
                    checkForCollisionWithTower(this);
                    //scene.attachChild(this);
                } else if (pSceneTouchEvent.getAction()==TouchEvent.ACTION_MOVE){

                }
                /*
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setScale(1.2f);
                        break;
                    case TouchEvent.ACTION_MOVE:
                        this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                        //if (this.getY()< )
                        //this.setX(pSceneTouchEvent.getX());
                        break;
                    case TouchEvent.ACTION_UP: {
                        this.setScale(1.0f);
                        checkForCollisionWithTower(this);
                        break;
                    }
                }
                */
                return true;
            }
        };
        scene.attachChild(ring3);

        this.mStack1.add(ring3);
        ring2.setPosition(mTower1.getX(), ((Ring) mStack1.peek()).getY() + ring2.getHeight());
        this.mStack1.add(ring2);
        ring1.setPosition(mTower1.getX(), ((Ring) mStack1.peek()).getY() + ring1.getHeight());
        this.mStack1.add(ring1);

        ring1.setmStack(mStack1);
        ring2.setmStack(mStack1);
        ring3.setmStack(mStack1);

        ring1.setmTower(mTower1);
        ring2.setmTower(mTower1);
        ring3.setmTower(mTower1);

        scene.registerTouchArea(ring1);
        scene.registerTouchArea(ring2);
        scene.registerTouchArea(ring3);
        scene.setTouchAreaBindingOnActionDownEnabled(true);

        return scene;
    }

    private void checkForCollisionWithTower(Ring ring){
        Stack stack=null;
        Sprite tower=null;
        if (ring.collidesWith(mTower1)&&(mStack1.size()==0 || ring.getmWeight()< ((Ring) mStack1.peek()).getmWeight())){
            stack = mStack1;
            tower= mTower1;
        } else if (ring.collidesWith(mTower2)&& (mStack2.size()==0 || ring.getmWeight()<((Ring) mStack2.peek()).getmWeight())) {
            stack=mStack2;
            tower = mTower2;
        } else if (ring.collidesWith(mTower3)&& (mStack3.size()==0 || ring.getmWeight()< ((Ring) mStack3.peek()).getmWeight())) {
            stack = mStack3;
            tower = mTower3;
        } else {
            stack =ring.getmStack();
            tower = ring.getmTower();
        }
        ring.getmStack().remove(ring);
        if ( stack != null && tower != null && stack.size()==0){
            ring.setPosition(tower.getX(), tower.getY() - tower.getHeight()/2 + ring.getHeight()/2);
            //ring.setPosition(tower.getX() + tower.getWidth()/2 - ring.getWidth()/2, tower.getY() + tower.getHeight() - ring.getHeight());
        } else if (stack != null && tower != null && stack.size() > 0) {
            ring.setPosition(tower.getX(), ((Ring) stack.peek()).getY() + ring.getHeight());
            //ring.setPosition(tower.getX() + tower.getWidth()/2 - ring.getWidth()/2, ((Ring) stack.peek()).getY() - ring.getHeight());
        }
        stack.add(ring);
        ring.setmStack(stack);
        ring.setmTower(tower);
    }

    public void gameToast(final String msg){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
