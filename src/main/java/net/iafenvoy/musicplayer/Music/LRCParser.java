package net.iafenvoy.musicplayer.Music;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.impl.Pair;

public class LRCParser {
  private List<Pair<Float, String>> data = new ArrayList<>();
  private boolean available = false;
  private String songName;

  public LRCParser(String filePath) {
    songName = filePath.replace(".lrc", "").replace("./music/", "");
    try {
      BufferedReader input = new BufferedReader(new FileReader(filePath));
      String line = "";
      while ((line = input.readLine()) != null) {
        if (line.contains("offset"))
          continue;
        String[] parts = line.split("]");
        if (parts.length == 2) {
          parts[0] = parts[0].replace(".", ":").replace("[", "");
          String[] time = parts[0].split(":");
          if (time.length == 3) {
            float time_s = Float.parseFloat(time[0]) * 60 + Float.parseFloat(time[1]) + Float.parseFloat(time[2]) / 1000;
            data.add(Pair.of(time_s, parts[1]));
          }
        }
      }
      input.close();
      available = true;
    } catch (IOException e) {
      available = false;
      e.printStackTrace();
    }
  }

  public String getText(float time) {
    if (!available)
      return songName;
    for (int i = data.size() - 1; i >= 0; i--)
      if (time >= data.get(i).first)
        return data.get(i).second;
    return songName;
  }
}
