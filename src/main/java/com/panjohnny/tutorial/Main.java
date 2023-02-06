package com.panjohnny.tutorial;

import com.panjohnny.pjgl.adapt.desktop.JDInitializer;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.util.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {
    public static Track hit;
    public static int bestScore = 0;
    public static void main(String[] args) {
        final File maxScore = new File("maxScore.dat");

        if (!maxScore.exists()) {
            try {
                maxScore.createNewFile();
            } catch (IOException e) {
                System.getLogger("PJGL-test").log(System.Logger.Level.WARNING, "Failed to create max score file");
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(maxScore))) {
            bestScore = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            System.getLogger("PJGL-test").log(System.Logger.Level.WARNING, "Failed to load max score");
        }

        // Initialize the library with the java desktop adaptation.
        PJGL.init(new JDInitializer("My cool game window title!"));
        final PJGL pjgl = PJGL.getInstance();


        final Arnold arnold = new Arnold();
        pjgl.getManager().addObject(new Background());
        pjgl.getManager().addObject(arnold);
        pjgl.getManager().addObject(new Zombie(arnold));

        // Start!
        pjgl.start();
        try {
            Track track = new Track("/music.wav", "Industrial background - PossessedSinner", "industrial_bg");
            hit = new Track("/hit.wav", "Hit sound effect", "hit");
            hit.setVolume(0.1f);

            track.loop();
            track.setVolume(0.1f);
            track.play();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
        Transparent cursor
         */
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        ((JFrame) PJGL.getInstance().getWindow()).getContentPane().setCursor(blankCursor);

        PJGLEvents.PJGL_EXIT_EVENT.listen((a) -> {
            try (FileWriter writer = new FileWriter(maxScore)) {
                writer.write(String.valueOf(bestScore));
            } catch (IOException e) {
                System.getLogger("PJGL-test").log(System.Logger.Level.WARNING, "Failed to save max score");
            }
        });
    }
}