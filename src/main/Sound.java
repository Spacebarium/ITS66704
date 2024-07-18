package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
public class Sound {
    Clip clip;
    URL soundURL[] = new URL[69];

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/bg_music.wav");
        soundURL[1] = getClass().getResource("/sound/gunshot.wav");
        soundURL[2] = getClass().getResource("/sound/sword.wav");
        soundURL[3] = getClass().getResource("/sound/select.wav");
        soundURL[4] = getClass().getResource("/sound/enter.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception ignored) {

        }
    }
    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }

}
