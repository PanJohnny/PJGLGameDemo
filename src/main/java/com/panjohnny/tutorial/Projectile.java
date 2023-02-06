package com.panjohnny.tutorial;

import com.panjohnny.pjgl.adapt.desktop.JDMouse;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Sprite;
import com.panjohnny.pjgl.api.asset.img.SpriteUtil;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;

import java.awt.*;

public class Arnold extends GameObject {
    public static final Sprite<Image> SPRITE = SpriteUtil.createImageSprite("player", "/arnold.png");

    // Position of Arnold on screen. (in px)
    public Position position = addComponent(new Position(this, 100, 100));

    // Size (to hint renderer how large should Arnold be). (in px)
    public Size size = addComponent(new Size(this, 100, 100));

    // This component will make sure that Arnold gets rendered.
    public SpriteRenderer renderer = addComponent(new SpriteRenderer(this, SPRITE));

    @Override
    public void update(long deltaTime) {
        // Keep in super.update(deltaTime) is not needed in this scenario, we don't have any component that requires updating, but it is good practice not to remove it.
        super.update(deltaTime);

        // Get the mouse.
        final JDMouse mouse = PJGL.getInstance().getMouse();

        // Fix the position.
        position.x = mouse.getX();
        position.y = mouse.getY();
    }
}
