package com.scottkillen.mod.scottstweaks.tweaks.endermen;

import com.scottkillen.mod.koresample.common.util.event.ForgeEventListener;
import com.scottkillen.mod.scottstweaks.config.Settings;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import java.util.List;

public final class EnderDumper extends ForgeEventListener
{
    private static void dumpEnderman(EntityEnderman enderman, List<EntityItem> drops)
    {
        final Settings settings = Settings.INSTANCE;

        if (settings.doEndermenDrops())
        {
            final Block block = enderman.func_146080_bZ();
            if (block.getMaterial().equals(Material.air)) return;

            final int metadata = enderman.getCarryingData();
            final ItemStack itemStack = new ItemStack(block.equals(Blocks.grass) ? Blocks.dirt : block, 1, metadata);

            final EntityItem entityItem =
                    new EntityItem(enderman.worldObj, enderman.posX, enderman.posY, enderman.posZ, itemStack);
            drops.add(entityItem);
            enderman.func_146081_a(Blocks.air);
            enderman.setCarryingData(0);
        }
    }

    @SuppressWarnings("MethodMayBeStatic")
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        if (event.entity.worldObj.isRemote) return;

        if (event.entity instanceof EntityEnderman)
        {
            dumpEnderman((EntityEnderman) event.entity, event.drops);
        }
    }
}
