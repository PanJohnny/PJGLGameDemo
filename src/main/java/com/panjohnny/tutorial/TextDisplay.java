package com.panjohnny.tutorial;

import com.panjohnny.pjgl.adapt.desktop.G2DRenderer;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;

import java.awt.*;

public class TextDisplay extends GameObject {
    public Position position = addComponent(new Position(this));
    public G2DRenderer renderer = addComponent(new G2DRenderer(this, this::render));

    public String text;
    public TextDisplay(String text, int x, int y) {
        this.text = text;
        position.x = x;
        position.y = y;
    }

    public void render(Graphics2D g) {
        // Set font to arial 20px plain.
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        // x: 0, y: 0 because we don't want to move it away from the set position.
        g.drawString(text, 0, 0);
    }
}
