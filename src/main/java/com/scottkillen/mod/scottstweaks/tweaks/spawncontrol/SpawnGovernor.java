package com.scottkillen.mod.scottstweaks.tweaks.spawncontrol;

import com.scottkillen.mod.koresample.common.util.event.ForgeEventListener;
import com.scottkillen.mod.scottstweaks.config.Settings;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;

public final class SpawnGovernor extends ForgeEventListener
{
    @SuppressWarnings("MethodMayBeStatic")
    @SubscribeEvent
    public void onCheckSpawn(CheckSpawn event)
    {
        if (event.entity.worldObj.isRemote) return;

        if (event.entity instanceof EntityBat || event.entity instanceof EntitySquid)
        {
            event.setResult(limitSpawn(event.entity));
        }
    }

    private Result limitSpawn(Entity entity)
    {
        final Settings settings = Settings.INSTANCE;
        final int successChance =
                entity instanceof EntityBat ? settings.batSpawnPercent() : settings.squidSpawnPercent();
        if (successChance == 0) return Result.DENY;
        if (successChance == 100) return Result.DEFAULT;

        return entity.worldObj.rand.nextInt(100) < successChance ? Result.DEFAULT : Result.DENY;
    }
}
