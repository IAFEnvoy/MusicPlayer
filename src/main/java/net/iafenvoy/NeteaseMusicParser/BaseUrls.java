package net.iafenvoy.NeteaseMusicParser;

public class BaseUrls {
  public static String searchUrl="http://music.163.com/api/search/pc?s=%s&offset=%d&limit=%d&type=%d";//搜索
  public static String songUrl="http://music.163.com/song/media/outer/url?id=%d.mp3";//%s is song id
  public static String albumUrl="http://music.163.com/api/album/%d?ext=true";//%s is album id
  public static String lyricUrl="http://music.163.com/api/song/media?id=%d";//%s is song id
}
