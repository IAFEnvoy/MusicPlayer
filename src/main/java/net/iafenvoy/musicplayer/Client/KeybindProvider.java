package net.iafenvoy.musicplayer.Client;

import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import net.iafenvoy.musicplayer.Configs.Configs;

public class KeybindProvider implements IKeybindProvider {
    @Override
    public void addKeysToMap(IKeybindManager manager) {
        manager.addKeybindToMap(Configs.General.open_menu_key.getKeybind());
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        // Not necessary
    }
}