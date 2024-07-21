package game_file;

import java.io.Serializable;

public class SettingFile implements Serializable {
    private boolean musicOn;
    private boolean soundEffectOn;

    public SettingFile(boolean musicOn, boolean soundEffectOn) {
        this.musicOn = musicOn;
        this.soundEffectOn = soundEffectOn;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    public boolean isSoundEffectOn() {
        return soundEffectOn;
    }

    public void setSoundEffectOn(boolean soundEffectOn) {
        this.soundEffectOn = soundEffectOn;
    }
}
