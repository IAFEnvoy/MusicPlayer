package net.iafenvoy.NeteaseMusicParser.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpUtil {
  private static String cookie = "";

  public static String get(String url) {
    if (cookie == "")
      setCookie(url);
    StringBuffer data = new StringBuffer();
    try {
      // 通过url获得连接
      URL u = new URL(url);
      HttpURLConnection re = (HttpURLConnection) u.openConnection();
      re.setRequestMethod("POST");
      re.setRequestProperty("Cookie", cookie);
      re.connect();
      // 读取返回的数据
      BufferedReader in = new BufferedReader(new InputStreamReader(re.getInputStream(), "UTF-8"));
      String inputline = null;
      while ((inputline = in.readLine()) != null)
        data.append(inputline);
      in.close();
      return data.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static void setCookie(String url) {
    try {
      // 通过url获得连接
      URL u = new URL(url);
      HttpURLConnection re = (HttpURLConnection) u.openConnection();
      re.setRequestMethod("POST");
      re.connect();
      Map<String, List<String>> respone_headers = re.getHeaderFields();
      for (String key : respone_headers.keySet()) {
        if (key != null && key.equals("Set-Cookie")) {
          List<String> string = respone_headers.get(key);
          for (String string2 : string)
            if (string2.startsWith("NMTID="))
              cookie = string2;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
