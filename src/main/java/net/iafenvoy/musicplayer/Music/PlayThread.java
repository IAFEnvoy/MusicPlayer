package net.iafenvoy.musicplayer.Music;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.iafenvoy.musicplayer.Configs.Configs;

public class PlayThread {
  private boolean hasStop = false;// 是否停止
  private boolean isStop = false;// 强行打断
  private String fileName;
  private SourceDataLine sourceDataLine;// 输出设备
  private AudioInputStream audioInputStream;// 音频输入流
  private AudioFormat audioFormat;// 音频格式
  private int counter = 0;
  public String text = "111";

  public PlayThread(String fileName) {
    this.fileName = fileName;
  }

  public boolean isComplete() {
    return this.hasStop;
  }

  public void stop() {
    this.isStop = true;
  }

  public void play() {
    try {
      audioInputStream=AudioSystem.getAudioInputStream(new File(fileName));
      audioFormat=audioInputStream.getFormat();

      DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
      sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
      sourceDataLine.open(audioFormat);
      sourceDataLine.start();

      isStop = false;
      Thread playThread = new Thread(new PlayerThread());
      playThread.start();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  class PlayerThread extends Thread {
    public void run() {
      try {
        byte tempBuffer[] = new byte[audioFormat.getFrameSize() * 64];
        int cnt;
        LRCParser lrcParser = new LRCParser(fileName.replace(".wav",
            ".lrc").replace(".mp3", ".lrc"));
        hasStop = false;
        // 读取数据到缓存数据
        while ((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
          counter++;
          text = lrcParser.getText(counter * 64 / audioFormat.getFrameRate());
          // 实时调整音量
          if (sourceDataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volume = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(Math.max(volume.getMinimum(), Math.min(volume.getMaximum(),
                (float) (10 * Math.log10(Configs.General.volumn.getDoubleValue() / 100) +
                    6.0206f))));
          }
          if (isStop)
            break;
          if (cnt > 0) {
            // 写入缓存数据
            sourceDataLine.write(tempBuffer, 0, cnt);
          }
        }
        // Block等待临时数据被输出为空
        sourceDataLine.drain();
        sourceDataLine.close();
        hasStop = true;
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
  }
}
