package net.iafenvoy.musicplayer.Configs.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigColor;
import net.iafenvoy.musicplayer.MusicPlayer;
import net.minecraft.text.TranslatableText;

public class NativeColor extends ConfigColor {
  private static final String MOD_ID = MusicPlayer.MOD_ID;

  public NativeColor(String keyname, String defaultValue) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue,
        new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
  }

}
