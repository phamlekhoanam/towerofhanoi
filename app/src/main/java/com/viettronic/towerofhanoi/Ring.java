package com.viettronic.towerofhanoi;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Stack;

/**
 * Created by khoa nam on 3/1/2015.
 */
public class Ring extends Sprite {
    private int mWeight;
    private Stack mStack;
    private Sprite mTower;

    public Ring(int weight, float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager){
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.mWeight=weight;
    }

    public int getmWeight(){
        return mWeight;
    }

    public Stack getmStack(){
        return mStack;
    }

    public void setmStack(Stack mStack){
        this.mStack=mStack;
    }

    public Sprite getmTower(){
        return mTower;
    }

    public void setmTower(Sprite mTower){
        this.mTower=mTower;
    }



}
