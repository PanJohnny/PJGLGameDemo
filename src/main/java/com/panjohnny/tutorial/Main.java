package com.panjohnny.tutorial;

import com.panjohnny.pjgl.adapt.desktop.JDInitializer;
import com.panjohnny.pjgl.api.PJGL;

public class Main {
    public static void main(String[] args) {
        // Initialize the library with the java desktop adaptation.
        PJGL.init(new JDInitializer("My cool game window title!"));
        final PJGL pjgl = PJGL.getInstance();
        // Start!
        pjgl.start();

        final Arnold arnold = new Arnold();
        pjgl.getManager().addObject(arnold);
        pjgl.getManager().addObject(new Zombie(arnold));

        try {
            Track track = new Track("/music.wav", "Industrial background - PossessedSinner", "industrial_bg");

            track.loop();
            track.setVolume(0.1f);
            track.play();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}