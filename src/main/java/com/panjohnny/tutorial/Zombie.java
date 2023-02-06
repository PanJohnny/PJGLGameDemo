package com.panjohnny.tutorial;

import com.panjohnny.pjgl.adapt.desktop.JDWindow;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Sprite;
import com.panjohnny.pjgl.api.asset.img.SpriteUtil;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Collider;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;

import java.awt.*;
import java.util.Random;

public class Zombie extends GameObject {
    public static final Sprite<Image> SPRITE = SpriteUtil.createImageSprite("projectile", "/zombie.png");

    // Spawn out of the screen.
    public Position position = addComponent(new Position(this, 300, 300));

    public Size size = addComponent(new Size(this, 60, 60));

    public SpriteRenderer renderer = addComponent(new SpriteRenderer(this, SPRITE));

    // Collider for future use
    public Collider collider = addComponent(new Collider(this));

    private final Arnold arnold;
    public Zombie(Arnold arnold) {
        this.arnold = arnold;
    }

    public Zombie(Arnold arnold, int x, int y) {
        this.arnold = arnold;
        position.x = x;
        position.y = y;
    }

    @Override
    public void update(long deltaTime) {
        // In this case collider needs update to check for collisions.
        super.update(deltaTime);

        // 3 px/frame.
        final int velocity = 2;

        // Get direction to Arnold.
        Position.Direction direction = Position.Direction.fromTo(position, arnold.position);

        // Move.
        position.add(velocity * direction.x, velocity * direction.y);

        // Zombie is colliding.
        if (!collider.getCollisions().isEmpty()) {
            if (collider.getCollisions().contains(arnold)) {
                // The zombie killed Arnold.
                System.out.println("You lost!");

                // Remove all objects
                for (GameObject o : PJGL.getInstance().getManager().getObjects()) {
                    PJGL.getInstance().getManager().queueRemoval(o);
                }

                PJGL.getInstance().getManager().queueAddition(new TextDisplay("Game Over!", 100, 100));
                PJGL.getInstance().getManager().queueAddition(new TextDisplay("Final score: " + arnold.score, 100, 200));
            } else {
                // The zombie got hit by projectile.
                PJGL.getInstance().getManager().queueRemoval(this);

                // Remove projectile / projectiles
                for (GameObject o : collider.getCollisions()) {
                    PJGL.getInstance().getManager().queueRemoval(o);
                }

                final JDWindow window = PJGL.getInstance().getWindow();

                arnold.score += 100;

                Random random = new Random();

                PJGL.getInstance().getManager().queueAddition(new Zombie(arnold, 0, random.nextInt(window.getHeight())));
                if (random.nextBoolean())
                    PJGL.getInstance().getManager().queueAddition(new Zombie(arnold, window.getWidth(), random.nextInt(window.getHeight())));
            }
        }
    }
}
