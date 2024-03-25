package com.pankaj.asteroids;


import java.util.Random;

public class Asteroid extends  Character{

    private  double rotationalMovement;

    public Asteroid(int x, int y){
        super(new PolygonFactory().CreatePolygon(),x,y);
        Random rnd = new Random();
        super.getCharacter().setRotate(rnd.nextInt(360));
        int accelerationAmt = 1+rnd.nextInt(10);
        for(int i = 0 ; i < accelerationAmt;i++){
            accelerate();
        }

        this.rotationalMovement = 0.5 - rnd.nextDouble();

    }
    @Override
    public void move(){
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate()+rotationalMovement);
    }
}
