package com.panjohnny.tutorial;

import com.panjohnny.pjgl.adapt.desktop.JDRenderer;
import com.panjohnny.pjgl.adapt.desktop.JDWindow;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Sprite;
import com.panjohnny.pjgl.api.asset.img.SpriteUtil;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;

import java.awt.*;

public class Background extends GameObject {
    public static final Sprite<Image> SPRITE = SpriteUtil.createImageSprite("bg", "/background.png");

    // Position of Arnold on screen. (in px)
    public Position position = addComponent(new Position(this, 0, 0));

    // Size (to hint renderer how large should Arnold be). (in px)
    public Size size = addComponent(new Size(this, 400, 400));

    // This component will make sure that Arnold gets rendered.
    public SpriteRenderer renderer = addComponent(new SpriteRenderer(this, SPRITE));

    @Override
    public void update(long deltaTime) {
        JDRenderer window = PJGL.getInstance().getRenderer();
        size.width = window.getWidth();
        size.height = window.getHeight();
    }
}
