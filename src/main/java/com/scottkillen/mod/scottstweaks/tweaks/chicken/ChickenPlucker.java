package com.scottkillen.mod.scottstweaks.tweaks.chicken;

import com.scottkillen.mod.koresample.ForgeEventListener;
import com.scottkillen.mod.scottstweaks.config.Settings;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public final class ChickenPlucker extends ForgeEventListener
{
    @SuppressWarnings("MethodMayBeStatic")
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event)
    {
        if (event.entity.worldObj.isRemote) return;

        if (event.entity instanceof EntityChicken)
        {
            pluckChicken((EntityChicken) event.entity);
        }
    }

    private static void pluckChicken(EntityChicken chicken)
    {
        final Settings settings = Settings.INSTANCE;
        final boolean doFeatherDrop =
                chicken.isChild() ? settings.doChicksDropFeathers() : settings.doHensDropFeathers();
        final int dropRarity = chicken.isChild() ? settings.chickFeatherRarity() : settings.henFeatherRarity();

        if (doFeatherDrop && dropRarity > 0 && chicken.getRNG().nextInt(dropRarity) == 0)
        {
            final int dropQuantity =
                    chicken.isChild() ? settings.chickFeatherQuantity() : settings.henFeatherQuantity();
            chicken.dropItem(Items.feather, dropQuantity);
        }
    }
}
