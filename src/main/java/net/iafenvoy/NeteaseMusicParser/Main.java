package net.iafenvoy.NeteaseMusicParser;

import java.util.List;
import java.util.Scanner;

import net.iafenvoy.NeteaseMusicParser.Util.Search.SearchResult;
import net.iafenvoy.NeteaseMusicParser.Util.Search.Searcher;
import net.iafenvoy.NeteaseMusicParser.Util.Type.SearchType;

public class Main {
  public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);
    System.out.println("请输入歌曲名称：");
    String songName = scan.nextLine();
    int offset = 0;
    while (true) {
      List<SearchResult> result = Searcher.search(songName, offset, 10, SearchType.Song);
      for (int i = 0; i < 10; i++)
        System.out.println(
            String.valueOf(i) + " : " + result.get(i).getSongName() + " - " + result.get(i).getArtistName());
      System.out.println("输入序号以下载，输入next/pervious换页，输入exit退出");
      String input = scan.nextLine();
      if (input.equals("exit")) {
        break;
      } else if (input.equals("next")) {
        offset += 10;
      } else if (input.equals("pervious")) {
        offset = offset == 0 ? offset : offset - 10;
      } else {
        int index = Integer.parseInt(input);
        if (index >= 0 && index < result.size()) {
          System.out.println(
              "正在下载 " + result.get(index).getSongName() + " - " + result.get(index).getArtistName() + ".mp3");
          result.get(index)
              .saveSong("./");
          System.out.println(
              "正在下载 " + result.get(index).getSongName() + " - " + result.get(index).getArtistName() + ".lrc");
          result.get(index)
              .saveLRC("./");
          break;
        }
      }
    }
    scan.close();
  }
}
