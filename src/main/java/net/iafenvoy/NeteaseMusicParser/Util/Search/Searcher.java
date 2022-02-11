package net.iafenvoy.NeteaseMusicParser.Util.Search;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.iafenvoy.NeteaseMusicParser.BaseUrls;
import net.iafenvoy.NeteaseMusicParser.Util.HttpUtil;

public class Searcher {
  public static List<SearchResult> search(String keyword, int offset, int limit, int type) {
    List<SearchResult> result = new ArrayList<>();
    String url = String.format(BaseUrls.searchUrl, keyword.replace(" ", "%20"), offset, limit, type);
    String json = HttpUtil.get(url);
    JsonArray data = new JsonParser().parse(json).getAsJsonObject().get("result").getAsJsonObject().get("songs")
        .getAsJsonArray();
    for (JsonElement jsonElement : data) {
      JsonObject object = jsonElement.getAsJsonObject();
      String name = object.get("name").getAsString();
      int id = object.get("id").getAsInt();
      String artistName = object.get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
      String albumName = object.get("album").getAsJsonObject().get("name").getAsString();
      SearchResult resultSong = new SearchResult(id, name, artistName, albumName);
      result.add(resultSong);
    }
    return result;
  }
}
