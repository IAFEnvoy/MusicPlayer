package net.iafenvoy.musicplayer.Configs;

import java.io.File;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.util.JsonUtils;
import net.iafenvoy.musicplayer.Configs.NativeConfigType.NativeBoolean;
import net.iafenvoy.musicplayer.Configs.NativeConfigType.NativeColor;
import net.iafenvoy.musicplayer.Configs.NativeConfigType.NativeDouble;
import net.iafenvoy.musicplayer.Configs.NativeConfigType.NativeHotkey;
import net.iafenvoy.musicplayer.Configs.NativeConfigType.NativeInt;

public class Configs implements IConfigHandler {
  private static final String FILE_PATH = "./config/ornaments.json";
  private static final File CONFIG_DIR = new File("./config");

  public static class General {
    public static final ConfigHotkey open_menu_key = new NativeHotkey("open_menu_key", "F10");
    public static final ConfigColor text_color = new NativeColor("text_color", "0xFFFFFF");
    public static final ConfigColor text_background_color = new NativeColor("text_background_color", "0x000000");
    public static final ConfigInteger locate_x = new NativeInt("locate_x", 0, Integer.MIN_VALUE, Integer.MAX_VALUE,
        false);
    public static final ConfigInteger locate_y = new NativeInt("locate_y", 0, Integer.MIN_VALUE, Integer.MAX_VALUE,
        false);
    public static final ConfigDouble scale = new NativeDouble("scale", 1, 0, 4, false);
    public static final ConfigBoolean use_text_background = new NativeBoolean("use_text_background", false);
    public static final ConfigBoolean use_font_shadow = new NativeBoolean("use_font_shadow", false);
    public static final ConfigDouble volumn = new NativeDouble("volumn", 50, 0, 100, false);

    public static void init() {
      Category.GENERAL.add(open_menu_key);
      Category.GENERAL.add(volumn);
      Category.GENERAL.add(text_color);
      Category.GENERAL.add(text_background_color);
      Category.GENERAL.add(locate_x);
      Category.GENERAL.add(locate_y);
      Category.GENERAL.add(scale);
    }
  }

  public static void Init() {
    General.init();
  }

  @Override
  public void load() {
    loadFile();
  }

  public static void loadFile() {
    File settingFile = new File(FILE_PATH);
    if (settingFile.isFile() && settingFile.exists()) {
      JsonElement jsonElement = JsonUtils.parseJsonFile(settingFile);
      if (jsonElement instanceof JsonObject) {

        for (Category category : Category.values())
          ConfigUtils.readConfigBase((JsonObject) jsonElement, category.name(), category.getConfigs());
      }
    }
  }

  @Override
  public void save() {
    if ((CONFIG_DIR.exists() && CONFIG_DIR.isDirectory()) || CONFIG_DIR.mkdirs()) {
      JsonObject configRoot = new JsonObject();
      for (Category category : Category.values())
        ConfigUtils.writeConfigBase(configRoot, category.name(), category.getConfigs());
      JsonUtils.writeJsonToFile(configRoot, new File(FILE_PATH));
    }
  }

}
