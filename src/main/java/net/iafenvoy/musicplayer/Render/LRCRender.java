package net.iafenvoy.musicplayer.Render;

import java.util.ArrayList;
import java.util.List;

import fi.dy.masa.malilib.config.HudAlignment;
import fi.dy.masa.malilib.interfaces.IRenderer;
import fi.dy.masa.malilib.render.RenderUtils;
import net.iafenvoy.musicplayer.Configs.Configs;
import net.iafenvoy.musicplayer.Music.MusicManager;
import net.minecraft.client.util.math.MatrixStack;

public class LRCRender implements IRenderer {
  public LRCRender() {

  }

  @Override
  public void onRenderGameOverlayPost(float partialTicks, MatrixStack matrixStack) {
    List<String> lines = new ArrayList<>();
    if (MusicManager.player != null)
      lines.add(MusicManager.player.text);

    RenderUtils.renderText(Configs.General.locate_x.getIntegerValue(), Configs.General.locate_y.getIntegerValue(),
        Configs.General.scale.getDoubleValue(), Configs.General.text_color.getIntegerValue(),
        Configs.General.text_background_color.getIntegerValue(), HudAlignment.CENTER,
        Configs.General.use_text_background.getBooleanValue(), Configs.General.use_font_shadow.getBooleanValue(), lines,
        matrixStack);
  }
}
