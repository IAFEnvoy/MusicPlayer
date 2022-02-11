package net.iafenvoy.musicplayer.Music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MusicManager {
  public static final String musicPath = "./music/";
  private static List<String> musicList = new ArrayList<>();
  private static ModeType type = ModeType.Loop;
  private static boolean jump = false;
  public static PlayThread player = null;
  private static boolean playMusic = false;

  public static void getFiles() {
    musicList = new ArrayList<>();
    File file = new File(musicPath);
    File[] tempList = file.listFiles();

    for (int i = 0; i < tempList.length; i++) {
      if (tempList[i].isFile())
        if (tempList[i].getName().contains(".wav"))
          musicList.add(tempList[i].getName());
      if (tempList[i].isDirectory()) {
        // 这里就不递归了
      }
    }
  }

  public static void startTask() {
    getFiles();
    Timer timer = new Timer("Music Player");
    TimerTask task = new TimerTask() {
      public void run() {
        if (!playMusic) {
          if (player != null)
            player.stop();
          return;
        }
        if (musicList.size() == 0)
          return;
        if (player == null || player.isComplete()) {
          player = new PlayThread(musicPath + musicList.get(0));
          player.play();
          switch (type) {
            case Loop: {
              musicList.add(musicList.get(0));
              musicList.remove(0);
              break;
            }
            case LoopOne: {// do nothing
              break;
            }
            case Normal: {
              musicList.remove(0);
              break;
            }
            case Random: {
              int i = new Random().nextInt() % musicList.size();
              musicList.add(0, musicList.get(i));
              musicList.remove(i + 1);
              break;
            }
            default:
              break;
          }
        } else if (jump) {
          player.stop();
          jump = false;
        }
      }
    };
    timer.schedule(task, 0, 100);// 0.1s载入一次
  }

  public static void reload() {
    getFiles();
  }

  public static void jump() {
    jump = true;
  }

  public static void normal() {
    type = ModeType.Normal;
  }

  public static void loop() {
    type = ModeType.Loop;
  }

  public static void random() {
    type = ModeType.Random;
  }

  public static void loopOne() {
    type = ModeType.LoopOne;
  }

  public static void start() {
    playMusic = true;
  }

  public static void stop() {
    playMusic = false;
  }

  public static String getList() {
    String l = "";
    for (int i = 0; i < musicList.size(); i++) {
      l += "\n" + String.valueOf(i + 1) + " : " + musicList.get(i);
    }
    return l;
  }

  public static void play(int index) {
    index -= 1;
    if (index < 0 || index >= musicList.size())
      return;
    musicList.add(0, musicList.get(index));
    musicList.remove(index + 1);
  }

  private enum ModeType {
    Normal, Loop, Random, LoopOne
  }
}
