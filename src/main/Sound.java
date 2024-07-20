package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sound {
    private static ArrayList<Clip> soundClips;
    private static boolean musicOn;
    private static boolean soundEffectOn;

    public Sound() {
        soundClips = new ArrayList<>();
        loadSound("bg_music");
        loadSound("gunshot");
        loadSound("sword");
        loadSound("select");
        loadSound("enter");
    }

    public static void loadSound(String fileName) {
        try {
            File audioFile = new File("res/Sound/" + fileName + ".wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            soundClips.add(clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading sound file: " + fileName);
            e.printStackTrace();
        }
    }

    public static boolean isMusicOn() {
        return musicOn;
    }

    public static void setMusicOn(boolean musicOn) {
        Sound.musicOn = musicOn;
    }

    public static boolean isSoundEffectOn(){
        return soundEffectOn;
    }

    public static void setSoundEffectOn(boolean soundEffectOn) {
        Sound.soundEffectOn = soundEffectOn;
    }

    public static void play(int i) {
        Clip clip = soundClips.get(i);
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static void loop(int i) {
        Clip clip = soundClips.get(i);
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopMusic(int i) {
        System.out.println(i);
        Clip clip = soundClips.get(i);
        clip.stop();
    }

    public static void playMusic(int i) {
        if (musicOn) {
            System.out.println("I am nln");
            play(i);
            loop(i);
        }
    }

    public static void playSE(int i) {
        if (soundEffectOn) {
            play(i);
        }
    }
}
