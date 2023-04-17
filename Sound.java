//Source: RyiSnow on Youtube video titled "Sound-How to Make a 2D Game in Java #9"
// A class for playing audio clips which are added to a URL[] to reference

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound
{
    Clip clip;
    URL soundURL[] = new URL[5];
    //Can change soundURL size to use more or fewer sounds

    public Sound() 
    {
        //Assign sound clips to positions in the array
        soundURL[0] = getClass().getResource("Sound\\mixkit-game-ball-tap-2073.wav");
        soundURL[1] = getClass().getResource("Sound\\mixkit-player-jumping-in-a-video-game-2043.wav");
        soundURL[2] = getClass().getResource("Sound\\mixkit-martial-arts-fast-punch-2047.wav");
    }

    //Determines which sound from those specified will be played
    public void setFile(int i)
    {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e)
        {
            System.out.println("Couldn't play audio");
        }
    }

    public void play()
    {
        clip.start();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()
    {
        clip.stop();
    }
}
