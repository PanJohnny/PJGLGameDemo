package com.panjohnny.tutorial;

import com.panjohnny.pjgl.api.PJGL;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class Track {
    private String name, id;
    private Clip clip;

    public Track(String file, String name, String id) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(PJGL.class.getResourceAsStream(file))));
        this.name = name;
        this.id = id;
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void close() {
        clip.close();
    }

    public void setPlaybackSpeed(float speed) {
        clip.setMicrosecondPosition((long) (clip.getMicrosecondLength() * speed));
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Clip getClip() {
        return clip;
    }
}
