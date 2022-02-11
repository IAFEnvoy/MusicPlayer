package net.iafenvoy.musicplayer.Client;

import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.iafenvoy.musicplayer.Configs.GuiConfig;
import net.minecraft.client.MinecraftClient;

public class KeyBindHandler implements IHotkeyCallback {

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof GuiConfig)
            client.currentScreen.onClose(); // actually has no effect
        else
            client.openScreen(new GuiConfig());
        return true;
    }
}
