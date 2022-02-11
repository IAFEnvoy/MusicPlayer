package net.iafenvoy.musicplayer.Configs.NativeConfigType;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import net.iafenvoy.musicplayer.MusicPlayer;
import net.minecraft.text.TranslatableText;

public class NativeOptionList extends ConfigOptionList {
  private static final String MOD_ID = MusicPlayer.MOD_ID;

  public NativeOptionList(String keyname, IConfigOptionListEntry defaultValue) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue,
        new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
  }

}
