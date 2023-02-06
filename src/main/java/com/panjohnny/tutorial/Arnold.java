package com.panjohnny.tutorial;

import com.panjohnny.pjgl.adapt.desktop.JDKeyboard;
import com.panjohnny.pjgl.adapt.desktop.JDMouse;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Sprite;
import com.panjohnny.pjgl.api.asset.img.SpriteUtil;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Collider;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;
import com.panjohnny.pjgl.core.adapters.MouseAdapter;

import java.awt.Image;

public class Arnold extends GameObject {
    public static final Sprite<Image> SPRITE = SpriteUtil.createImageSprite("player", "/arnold.png");

    // Position of Arnold on screen. (in px)
    public Position position = addComponent(new Position(this, 100, 100));

    // Size (to hint renderer how large should Arnold be). (in px)
    public Size size = addComponent(new Size(this, 100, 100));

    // This component will make sure that Arnold gets rendered.
    public SpriteRenderer renderer = addComponent(new SpriteRenderer(this, SPRITE));

    public Collider collider = addComponent(new Collider(this));

    public int score = 0;

    @Override
    public void update(long deltaTime) {
        // Keep in super.update(deltaTime) is not needed in this scenario, we don't have any component that requires updating, but it is good practice not to remove it.
        super.update(deltaTime);

        // Get the mouse.
        final JDMouse mouse = PJGL.getInstance().getMouse();

        // Fix the position.
        position.x = mouse.getX();
        position.y = mouse.getY();

        if (mouse.isKeyDown(MouseAdapter.BUTTON_LEFT)) {
            final JDKeyboard keyboard = PJGL.getInstance().getKeyboard();

            // Get the direction which can be combined.
            Position.Direction directionX = Position.Direction.NONE;
            Position.Direction directionY = Position.Direction.NONE;

            if (keyboard.isKeyDown('a')) {
                directionX = Position.Direction.LEFT;
            }

            if (keyboard.isKeyDown('d')) {
                if (directionX != Position.Direction.NONE) {
                    directionX = Position.Direction.NONE;
                } else
                    directionX = Position.Direction.RIGHT;
            }

            if (keyboard.isKeyDown('w')) {
                directionY = Position.Direction.UP;
            }

            if (keyboard.isKeyDown('s')) {
                if (directionY != Position.Direction.NONE) {
                    directionY = Position.Direction.NONE;
                } else
                    directionY = Position.Direction.DOWN;
            }

            Position.Direction direction = directionX.combine(directionY);

            if (direction != Position.Direction.NONE) {
                // Spawn new projectile. (Queued to prevent concurrent modification)
                PJGL.getInstance().getManager().queueAddition(new Projectile(this, direction));
            }
        }
    }
}
