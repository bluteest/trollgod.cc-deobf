package me.hollow.realth;

import java.io.File;
import java.nio.ByteBuffer;

import me.hollow.realth.api.util.IconUtil;
import me.hollow.realth.client.managers.*;
import net.b0at.api.event.Event;
import net.b0at.api.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import scala.tools.reflect.quasiquotes.Holes;

import java.io.InputStream;

@Mod(name="JordoHack", modid="jordohack")
public class JordoHack {
    public static final JordoHack INSTANCE = new JordoHack();
    public static final Logger logger = LogManager.getLogger(JordoHack.INSTANCE.name);
    private final EventManager<Event> eventManager = new EventManager<Event>(Event.class);
    public static final String VERSION = "v2.0";
    public static FontManager fontManager = new FontManager();
    public static String name = "TrollGod.CC";
    private final File directory;
    public final ModuleManager moduleManager;
    public static PositionManager positionManager;
    private static CommandManager commandManager;
    private final ConfigManager configManager;
    public static RotationManager rotationManager;
    public static TotemPopManager totemPopManager;
    public static FriendManager friendManager;
    private final ColorManager colorManager;
    public static PlayerManager playerManager;
    public final FileManager fileManager;
    public static  InteractionManager interactionManager;
    public static InventoryManager inventoryManager;
    private final SafeManager safeManager;
    private final SpeedManager speedManager;
    public final HoleManager holeManager;
    private final PopManager popManager;
    private final TPSManager tpsManager;

    public JordoHack() {
        this.directory = new File(Minecraft.getMinecraft().gameDir, "TrollGod");
        this.moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        this.configManager = new ConfigManager();
        friendManager = new FriendManager();
        rotationManager = new RotationManager();
        totemPopManager = new TotemPopManager();
        playerManager = new PlayerManager();
        holeManager = new HoleManager();
        this.colorManager = new ColorManager();
        this.fileManager = new FileManager();
        positionManager = new PositionManager();
        interactionManager = new InteractionManager();
        inventoryManager = new InventoryManager();
        this.safeManager = new SafeManager();
        this.speedManager = new SpeedManager();
        this.popManager = new PopManager();
        this.tpsManager = new TPSManager();
        fontManager = new FontManager();
    }

    public void setName(String name) {
        if (name.equals("")) {
            return;
        }
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }

    public void init() {
        this.moduleManager.init();
        commandManager.init();
        this.configManager.init();
        this.tpsManager.load();
        friendManager.setDirectory(new File(this.directory, "friends.json"));
        friendManager.init();
        this.popManager.load();
        this.safeManager.load();
        interactionManager.load();
        Display.setTitle("trollgod");
        setWindowIcon();
        logger.info("other did no fucky wuckies while loading *nuzzles* uwuuu - trollgod");
    }

    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (InputStream inputStream = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/icons/icon16x.png");
                 InputStream inputStream2 = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/icons/icon32x.png");){
                ByteBuffer[] byteBufferArray = new ByteBuffer[]{IconUtil.INSTANCE.readImageToBuffer(inputStream), IconUtil.INSTANCE.readImageToBuffer(inputStream2)};
                Display.setIcon(byteBufferArray);
            }
            catch (Exception exception) {
                logger.error("Couldn't set Windows Icon", exception);
            }
        }
    }

    public TPSManager getTpsManager() {
        return this.tpsManager;
    }

    public final PopManager getPopManager() {
        return this.popManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }
    public TotemPopManager getTotemPopManager(){
        return totemPopManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    public InteractionManager getInteractionManager() {
        return interactionManager;
    }

    public final ColorManager getColorManager() {
        return this.colorManager;
    }
    public final PlayerManager getPlayerManager(){
        return playerManager;
    }
    public final FontManager getFontManager() {
        return fontManager;
    }
    public final RotationManager getRotationManager() {
        return rotationManager;
    }

    public final EventManager<Event> getEventManager() {
        return this.eventManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public final ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public PositionManager getPositionManager(){
        return positionManager;
    }

    public HoleManager getHoleManager() {
        return holeManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public SpeedManager getSpeedManager() {
        return this.speedManager;
    }

    public final SafeManager getSafeManager() {
        return this.safeManager;
    }
}
