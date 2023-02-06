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

import java.awt.Image;

public class Projectile extends GameObject {
    public static final Sprite<Image> SPRITE = SpriteUtil.createImageSprite("projectile", "/projectile.png");

    // Spawn out of the screen.
    public Position position = addComponent(new Position(this, -100, -100));

    public Size size = addComponent(new Size(this, 20, 20));

    public SpriteRenderer renderer = addComponent(new SpriteRenderer(this, SPRITE));

    // Collider for future use
    public Collider collider = addComponent(new Collider(this));

    private final Position.Direction direction;
    public Projectile(Arnold arnold, Position.Direction direction) {
        this.direction = direction;

        position.x = arnold.position.x;
        position.y = arnold.position.y;
    }

    private int time = 1;
    @Override
    public void update(long deltaTime) {
        // In this case collider needs update to check for collisions.
        super.update(deltaTime);

        // shooting fast and slowing down
        final int velocity = Math.max(10, 100/time);
        time++;

        // Move.
        position.add(velocity * direction.x, velocity * direction.y);

        // Check if it is still in window.
        final JDWindow window = PJGL.getInstance().getWindow();

        // Remove the element if out of screen.
        if (position.x < 0 || position.x > window.getWidth() || position.y < 0 || position.y > window.getHeight()) {
            // Queue removal to prevent ConcurrentModificationException
            PJGL.getInstance().getManager().queueRemoval(this);
        }
    }
}
