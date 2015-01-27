package com.scottkillen.mod.scottstweaks.tweaks;

import com.scottkillen.mod.scottstweaks.config.Settings;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import java.util.Random;

public class ClayGenerator implements IWorldGenerator
{
    private static void generateClay(World world, Random random, int x, int z)
    {
        final Settings settings = Settings.INSTANCE;
        final int clayVeinQuantity = settings.clayVeinQuantity();
        for (int i = 0; i < clayVeinQuantity; i++)
        {
            final int claySpawnYMin = settings.claySpawnYMin();
            final int y = random.nextInt(settings.claySpawnYMax() - claySpawnYMin) + claySpawnYMin;

            newMinable(random).generate(world, random, x, y, z);
        }
    }

    private static WorldGenMinable newMinable(Random random)
    {
        final Settings settings = Settings.INSTANCE;
        final int clayVeinSizeMin = settings.clayVeinSizeMin();
        final int numberOfBlocks = random.nextInt(settings.clayVeinSizeMax() - clayVeinSizeMin) + clayVeinSizeMin;

        return new WorldGenMinable(Blocks.clay, numberOfBlocks);
    }

    public void install()
    {
        GameRegistry.registerWorldGenerator(this, 10);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
                         IChunkProvider chunkProvider)
    {
        if (world.provider.dimensionId == 0)
        {
            generateClay(world, random, chunkX << 4, chunkZ << 4);
        }
    }
}
