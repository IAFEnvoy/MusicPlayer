package net.iafenvoy.musicplayer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.iafenvoy.musicplayer.Client.KeyBindHandler;
import net.iafenvoy.musicplayer.Client.KeybindProvider;
import net.iafenvoy.musicplayer.Configs.Configs;
import net.iafenvoy.musicplayer.Music.MusicManager;
import net.iafenvoy.musicplayer.Music.Download.Search;
import net.iafenvoy.musicplayer.Render.InitHandler;
import net.minecraft.text.Text;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;

@Environment(EnvType.CLIENT)
public class MusicPlayer implements ClientModInitializer {
  private static Logger logger = LogManager.getLogger();
  public static final String MOD_ID = "musicplayer";

  @Override
  public void onInitializeClient() {
    if (!FabricLoader.getInstance().isModLoaded("malilib")) {
      logger.log(Level.FATAL, "Malilib is not loaded, MusicPlayer will not work.");
      return;
    }
    InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());

    ConfigManager.getInstance().registerConfigHandler(MOD_ID, new Configs());
    Configs.loadFile();
    InputEventHandler.getKeybindManager().registerKeybindProvider(new KeybindProvider());
    Configs.General.open_menu_key.getKeybind().setCallback(new KeyBindHandler());
    Configs.Init();

    MusicManager.startTask();

    ClientCommandManager.DISPATCHER
        .register(ClientCommandManager.literal("music")
            .then(ClientCommandManager.literal("reload")
                .executes(MusicPlayer::ReloadMusic))
            .then(ClientCommandManager.literal("jump")
                .executes(MusicPlayer::JumpMusic))
            .then(ClientCommandManager.literal("loop")
                .executes(MusicPlayer::LoopMusic))
            .then(ClientCommandManager.literal("normal")
                .executes(MusicPlayer::NormalMusic))
            .then(ClientCommandManager.literal("loopone")
                .executes(MusicPlayer::LoopOneMusic))
            .then(ClientCommandManager.literal("random")
                .executes(MusicPlayer::RandomMusic))
            .then(ClientCommandManager.literal("list")
                .executes(MusicPlayer::ListMusic))
            .then(ClientCommandManager.literal("stop")
                .executes(MusicPlayer::StopMusic))
            .then(ClientCommandManager.literal("start")
                .executes(MusicPlayer::StartMusic))
            .then(ClientCommandManager.literal("nextpage").executes(commandContext -> {
              Search.NextPage();
              return 0;
            }))
            .then(ClientCommandManager.literal("previouspage").executes(commandContext -> {
              Search.PreviousPage();
              return 0;
            }))
            .then(ClientCommandManager.literal("download")
                .then(ClientCommandManager.argument("index", StringArgumentType.string())
                    .executes(commandContext -> {
                      String index = StringArgumentType.getString(commandContext, "index");
                      try {
                        Search.download(Integer.parseInt(index));
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                      return 0;
                    })))
            .then(ClientCommandManager.literal("search")
                .then(ClientCommandManager.argument("keyword", StringArgumentType.greedyString())
                    .executes(MusicPlayer::SearchMusic)))
            .then(ClientCommandManager.literal("play")
                .then(ClientCommandManager.argument("index", StringArgumentType.word())
                    .executes(MusicPlayer::PlayMusic))));
  }

  public static int ReloadMusic(CommandContext<FabricClientCommandSource> context) {
    MusicManager.reload();
    return 0;
  }

  public static int JumpMusic(CommandContext<FabricClientCommandSource> context) {
    MusicManager.jump();
    return 0;
  }

  public static int LoopMusic(CommandContext<FabricClientCommandSource> context) {
    MusicManager.loop();
    return 0;
  }

  public static int NormalMusic(CommandContext<FabricClientCommandSource> context) {
    MusicManager.normal();
    return 0;
  }

  public static int RandomMusic(CommandContext<FabricClientCommandSource> context) {
    MusicManager.random();
    return 0;
  }

  public static int LoopOneMusic(CommandContext<FabricClientCommandSource> context) {
    MusicManager.loopOne();
    return 0;
  }

  public static int StartMusic(CommandContext<FabricClientCommandSource> context) {
    MusicManager.start();
    return 0;
  }

  public static int StopMusic(CommandContext<FabricClientCommandSource> context) {
    MusicManager.stop();
    return 0;
  }

  public static int PlayMusic(CommandContext<FabricClientCommandSource> context) {
    try {
      int index = Integer.parseInt(StringArgumentType.getString(context, "index"));
      MusicManager.play(index);
    } catch (NumberFormatException e) {
      context.getSource().sendError(Text.of("Invalid index"));
      return 1;
    }
    return 0;
  }

  public static int ListMusic(CommandContext<FabricClientCommandSource> context) {
    context.getSource().getPlayer().sendMessage(Text.of(MusicManager.getList()), false);
    return 0;
  }

  public static int SearchMusic(CommandContext<FabricClientCommandSource> context) {
    Search.searchMusic(StringArgumentType.getString(context, "keyword"));
    return 0;
  }
}
