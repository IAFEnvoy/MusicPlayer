package net.iafenvoy.musicplayer.Configs.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import net.iafenvoy.musicplayer.MusicPlayer;
import net.minecraft.text.TranslatableText;

public class NativeHotkey extends ConfigHotkey {
  private static final String MOD_ID = MusicPlayer.MOD_ID;

  public NativeHotkey(String keyname, String defaultStorageString) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultStorageString,
        KeybindSettings.DEFAULT, new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
  }

}
