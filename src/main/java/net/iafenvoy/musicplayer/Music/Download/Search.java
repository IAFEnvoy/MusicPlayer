package net.iafenvoy.musicplayer.Music.Download;

import java.util.ArrayList;
import java.util.List;

import net.iafenvoy.NeteaseMusicParser.Util.Search.SearchResult;
import net.iafenvoy.NeteaseMusicParser.Util.Search.Searcher;
import net.iafenvoy.NeteaseMusicParser.Util.Type.SearchType;
import net.iafenvoy.musicplayer.Music.MusicManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Search {
  public static List<SearchResult> result = new ArrayList<>();
  private static int offset = 0;
  private static String key1 = "";

  private static MinecraftClient mc = null;

  public static void searchMusic(String key) {
    if (mc == null)
      mc = MinecraftClient.getInstance();
    key1 = key;
    Thread thread = new Thread() {
      public void run() {
        result = Searcher.search(key, offset, offset + 10, SearchType.Song);
        for (int i = 0; i < result.size(); i++)
          mc.player.sendMessage(
              Text.of(
                  String.valueOf(i + 1) + " : " + result.get(i).getSongName() + "-" + result.get(i).getArtistName()),
              false);
      }
    };
    thread.setName("Searcher");
    thread.start();
  }

  public static void NextPage() {
    if (result.size() == 0 || key1 == "")
      return;
    offset += 10;
    searchMusic(key1);
  }

  public static void PreviousPage() {
    if (offset == 0 || key1 == "")
      return;
    offset -= 10;
    searchMusic(key1);
  }

  public static void download(int index) {
    index -= 1;
    if (index < 0 || index >= result.size())
      return;
    SearchResult sr = result.get(index);
    sr.saveSong(MusicManager.musicPath);
    sr.saveLRC(MusicManager.musicPath);
    FFmpeg.mp3_2_wav(MusicManager.musicPath + sr.getSongName() + "-" + sr.getArtistName() + ".mp3");
  }

}
