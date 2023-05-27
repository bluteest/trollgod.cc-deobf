//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth;

import net.minecraftforge.fml.common.*;
import net.b0at.api.event.*;
import me.hollow.realth.client.managers.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import java.nio.*;
import me.hollow.realth.api.util.*;
import java.io.*;
import org.apache.logging.log4j.*;

@Mod(name = "JordoHack", modid = "jordohack")
public class JordoHack
{
    public static final JordoHack INSTANCE;
    public static final Logger logger;
    private final EventManager<Event> eventManager;
    public static final String VERSION = "v2.0";
    public static FontManager fontManager;
    public static String name;
    private final File directory;
    public final ModuleManager moduleManager;
    public static PositionManager positionManager;
    private static CommandManager commandManager;
    private final ConfigManager configManager;
    public static RotationManager rotationManager;
    public static FriendManager friendManager;
    private final ColorManager colorManager;
    public final FileManager fileManager;
    public static InteractionManager interactionManager;
    public static InventoryManager inventoryManager;
    private final SafeManager safeManager;
    private final SpeedManager speedManager;
    public final HoleManager holeManager;
    private final PopManager popManager;
    private final TPSManager tpsManager;
    
    public JordoHack() {
        this.eventManager = (EventManager<Event>)new EventManager((Class)Event.class);
        this.directory = new File(Minecraft.getMinecraft().gameDir, "TrollGod");
        this.moduleManager = new ModuleManager();
        JordoHack.commandManager = new CommandManager();
        this.configManager = new ConfigManager();
        JordoHack.friendManager = new FriendManager();
        JordoHack.rotationManager = new RotationManager();
        this.holeManager = new HoleManager();
        this.colorManager = new ColorManager();
        this.fileManager = new FileManager();
        JordoHack.positionManager = new PositionManager();
        JordoHack.interactionManager = new InteractionManager();
        JordoHack.inventoryManager = new InventoryManager();
        this.safeManager = new SafeManager();
        this.speedManager = new SpeedManager();
        this.popManager = new PopManager();
        this.tpsManager = new TPSManager();
        JordoHack.fontManager = new FontManager();
    }
    
    public void setName(final String name) {
        if (name.equals("")) {
            return;
        }
        JordoHack.name = name;
    }
    
    public final String getName() {
        return JordoHack.name;
    }
    
    public void init() {
        this.moduleManager.init();
        JordoHack.commandManager.init();
        this.configManager.init();
        this.tpsManager.load();
        JordoHack.friendManager.setDirectory(new File(this.directory, "friends.json"));
        JordoHack.friendManager.init();
        this.popManager.load();
        this.safeManager.load();
        JordoHack.interactionManager.load();
        Display.setTitle("trollgod");
        setWindowIcon();
        JordoHack.logger.info("other did no fucky wuckies while loading *nuzzles* uwuuu - trollgod");
    }
    
    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (final InputStream inputStream = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/icons/icon16x.png");
                 final InputStream inputStream2 = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/icons/icon32x.png")) {
                final ByteBuffer[] byteBufferArray = { IconUtil.INSTANCE.readImageToBuffer(inputStream), IconUtil.INSTANCE.readImageToBuffer(inputStream2) };
                Display.setIcon(byteBufferArray);
            }
            catch (Exception exception) {
                JordoHack.logger.error("Couldn't set Windows Icon", (Throwable)exception);
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
        return JordoHack.friendManager;
    }
    
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    
    public InteractionManager getInteractionManager() {
        return JordoHack.interactionManager;
    }
    
    public final ColorManager getColorManager() {
        return this.colorManager;
    }
    
    public final FontManager getFontManager() {
        return JordoHack.fontManager;
    }
    
    public final RotationManager getRotationManager() {
        return JordoHack.rotationManager;
    }
    
    public final EventManager<Event> getEventManager() {
        return this.eventManager;
    }
    
    public CommandManager getCommandManager() {
        return JordoHack.commandManager;
    }
    
    public final ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    
    public PositionManager getPositionManager() {
        return JordoHack.positionManager;
    }
    
    public HoleManager getHoleManager() {
        return this.holeManager;
    }
    
    public InventoryManager getInventoryManager() {
        return JordoHack.inventoryManager;
    }
    
    public SpeedManager getSpeedManager() {
        return this.speedManager;
    }
    
    public final SafeManager getSafeManager() {
        return this.safeManager;
    }
    
    static {
        INSTANCE = new JordoHack();
        final JordoHack instance = JordoHack.INSTANCE;
        logger = LogManager.getLogger(JordoHack.name);
        JordoHack.fontManager = new FontManager();
        JordoHack.name = "TrollGod.CC";
    }
}
