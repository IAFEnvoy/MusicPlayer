package net.iafenvoy.musicplayer.Music.Download;

import java.util.ArrayList;
import java.util.List;

public class FFmpeg {
  private static String FFmpegPath="./ffmpeg/ffmpeg.exe";
  public static void mp3_2_wav(String path){
    List<String> command=new ArrayList<String>();
    command.add(FFmpegPath);
    command.add("-i");
    command.add(path);
    command.add(path.substring(0,path.length()-3)+"wav");
    try {
      ProcessBuilder builder=new ProcessBuilder(command);
      builder.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
