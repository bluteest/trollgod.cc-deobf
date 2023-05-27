//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.movement;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;

@ModuleManifest(label = "Sprint", category = Module.Category.MOVEMENT)
public class Sprint extends Module
{
    private final Setting<Mode> mode;
    
    public Sprint() {
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (Object)Mode.RAGE));
    }
    
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() == Sprint.mc.player) {
            switch ((Mode)this.mode.getValue()) {
                case RAGE: {
                    if ((Sprint.mc.gameSettings.keyBindForward.isKeyDown() || Sprint.mc.gameSettings.keyBindBack.isKeyDown() || Sprint.mc.gameSettings.keyBindLeft.isKeyDown() || Sprint.mc.gameSettings.keyBindRight.isKeyDown()) && !Sprint.mc.player.isSneaking() && !Sprint.mc.player.collidedHorizontally && Sprint.mc.player.getFoodStats().getFoodLevel() > 6.0f) {
                        Sprint.mc.player.setSprinting(true);
                        break;
                    }
                    break;
                }
                case LEGIT: {
                    if (Sprint.mc.gameSettings.keyBindForward.isKeyDown() && !Sprint.mc.player.isSneaking() && !Sprint.mc.player.isHandActive() && !Sprint.mc.player.collidedHorizontally && Sprint.mc.player.getFoodStats().getFoodLevel() > 6.0f && Sprint.mc.currentScreen == null) {
                        Sprint.mc.player.setSprinting(true);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public enum Mode
    {
        LEGIT, 
        RAGE;
    }
}
