package net.iafenvoy.NeteaseMusicParser.Util.Search;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonParser;

import net.iafenvoy.NeteaseMusicParser.BaseUrls;
import net.iafenvoy.NeteaseMusicParser.Util.HttpUtil;

public class SearchResult {
  private int songId;
  private String songName;
  private String artistName;
  private String albumName;

  public SearchResult(int songId, String songName, String artistName, String albumName) {
    this.songId = songId;
    this.songName = songName;
    this.artistName = artistName;
    this.albumName = albumName;
  }

  public int getSongId() {
    return songId;
  }

  public String getSongName() {
    return songName;
  }

  public String getArtistName() {
    return artistName;
  }

  public String getAlbumName() {
    return albumName;
  }

  public String getSongUrl() {
    return String.format(BaseUrls.songUrl, songId);
  }

  public String getLRCUrl() {
    return String.format(BaseUrls.lyricUrl, songId);
  }

  public InputStream getSongStream() {
    try {
      return new URL(getSongUrl()).openStream();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<String> getLRC() {
    try {
      List<String> lrc = new ArrayList<>();
      String data = new JsonParser().parse(HttpUtil.get(getLRCUrl())).getAsJsonObject().get("lyric").getAsString();
      String[] lines = data.split("\n");
      for (String line : lines)
        lrc.add(line);
      return lrc;
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  public InputStream saveSong(String path) {
    try {
      URL url = new URL(getSongUrl());
      InputStream inStream = url.openStream();
      FileOutputStream fs = new FileOutputStream(path + songName + "-" + artistName + ".mp3");
      byte[] buffer = new byte[1024];
      int byteread = 0;
      while ((byteread = inStream.read(buffer)) != -1)
        fs.write(buffer, 0, byteread);
      fs.close();
      return new FileInputStream(path + songName + "-" + artistName + ".mp3");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public InputStream saveLRC(String path) {
    if (getLRC().size() == 0)
      return null;
    try {
      FileOutputStream fs = new FileOutputStream(path + songName + "-" + artistName + ".lrc");
      for (String line : getLRC())
        fs.write((line + "\n").getBytes());
      fs.close();
      return new FileInputStream(path + songName + "-" + artistName + ".lrc");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
