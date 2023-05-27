//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.misc;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraft.init.*;
import me.hollow.realth.api.util.*;
import me.hollow.realth.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

@ModuleManifest(label = "PvPInfo", category = Category.MISC)
public class PvPInfo extends Module
{
    private final Setting<Integer> pvpInfoY;
    private final Setting<Integer> pvpInfoX;
    public Setting<Float> breakRange;
    public Setting<Float> placeRange;
    
    public PvPInfo() {
        this.pvpInfoY = (Setting<Integer>)this.register(new Setting("Y", (Object)60, (Object)0, (Object)500));
        this.pvpInfoX = (Setting<Integer>)this.register(new Setting("X", (Object)60, (Object)2, (Object)500));
        this.breakRange = (Setting<Float>)this.register(new Setting("Break Range", (Object)5.0f, (Object)1.0f, (Object)6.0f));
        this.placeRange = (Setting<Float>)this.register(new Setting("Place Range", (Object)5.0f, (Object)1.0f, (Object)6.0f));
    }
    
    @Override
    public void onRender2D() {
        final String[] array = { "HTR", "PLR", String.valueOf(ItemUtil.getItemCount(Items.TOTEM_OF_UNDYING)), "PING " + ((PvPInfo.mc.getConnection() != null && PvPInfo.mc.getConnection().getPlayerInfo(PvPInfo.mc.player.getUniqueID()) != null) ? Integer.valueOf(PvPInfo.mc.getConnection().getPlayerInfo(PvPInfo.mc.player.getUniqueID()).getResponseTime()) : "-1"), "LBY" };
        final EntityPlayer player = CombatUtil.getTarget((float)((Float)this.breakRange.getValue()).intValue());
        int offset = 0;
        for (int i = 0; i < 5; ++i) {
            final String s = array[i];
            JordoHack.fontManager.drawString(s, (float)(int)this.pvpInfoX.getValue(), (float)((int)this.pvpInfoY.getValue() + offset), this.getColor(s, player));
            offset += 9;
        }
    }
    
    private int getColor(final String s, final EntityPlayer entityPlayer) {
        final int green = 65280;
        final int red = 16711680;
        switch (s) {
            case "HTR": {
                if (entityPlayer != null && PvPInfo.mc.player.getDistance((Entity)entityPlayer) < (float)this.breakRange.getValue()) {
                    return 65280;
                }
                return 16711680;
            }
            case "PLR": {
                if (entityPlayer != null && PvPInfo.mc.player.getDistance((Entity)entityPlayer) < (float)this.placeRange.getValue()) {
                    return 65280;
                }
                return 16711680;
            }
            case "LBY": {
                if (entityPlayer != null) {
                    final int holeInfo = CombatUtil.isInHoleInt(entityPlayer);
                    if (holeInfo == 0) {
                        return 16711680;
                    }
                    if (holeInfo == 1) {
                        return 65280;
                    }
                    if (holeInfo == 2) {
                        return 16727040;
                    }
                }
                return 16711680;
            }
            default: {
                if (s.startsWith("PING")) {
                    final int num = Integer.parseInt(s.substring(5));
                    if (num >= 100) {
                        return 16711680;
                    }
                    return 65280;
                }
                else {
                    final int num = Integer.parseInt(s);
                    if (num > 0) {
                        return 65280;
                    }
                    return 16711680;
                }
                break;
            }
        }
    }
}
