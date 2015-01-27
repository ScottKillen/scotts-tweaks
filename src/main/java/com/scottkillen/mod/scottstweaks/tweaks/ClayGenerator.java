package com.scottkillen.mod.scottstweaks.tweaks;

import com.scottkillen.mod.scottstweaks.config.Settings;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
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
            generateClayVein(world, random, x, y, z);
        }
    }

    @SuppressWarnings("NumericCastThatLosesPrecision")
    private static void generateClayVein(World world, Random random, int x, int y, int z)
    {
        final Settings settings = Settings.INSTANCE;

        final float shape = random.nextFloat() * (float) Math.PI;

        final int clayVeinSizeMin = settings.clayVeinSizeMin();
        final int numberOfBlocks = random.nextInt(settings.clayVeinSizeMax() - clayVeinSizeMin) + clayVeinSizeMin;

        final double minX = x + 8 + MathHelper.sin(shape) * numberOfBlocks / 8.0F;
        final double maxX = x + 8 - MathHelper.sin(shape) * numberOfBlocks / 8.0F;
        final double minZ = z + 8 + MathHelper.cos(shape) * numberOfBlocks / 8.0F;
        final double maxZ = z + 8 - MathHelper.cos(shape) * numberOfBlocks / 8.0F;
        final double minY = y + random.nextInt(3) - 2;
        final double maxY = y + random.nextInt(3) - 2;

        for (int i = 0; i <= numberOfBlocks; ++i)
        {
            placeClayBlockInCloud(world, random, numberOfBlocks, minX, maxX, minY, maxY, minZ, maxZ, i);
        }
    }

    @SuppressWarnings({ "MethodWithMultipleLoops", "OverlyNestedMethod" })
    private static void placeClayBlockInCloud(World world, Random random, int numberOfBlocks, double minXBorder,
                                              double maxXBorder, double minYBorder, double maxYBorder,
                                              double minZBorder, double maxZBorder, int iteration)
    {
        final double minXLimit = minXBorder + (maxXBorder - minXBorder) * iteration / numberOfBlocks;
        final double minYLimit = minYBorder + (maxYBorder - minYBorder) * iteration / numberOfBlocks;
        final double minZLimit = minZBorder + (maxZBorder - minZBorder) * iteration / numberOfBlocks;
        final double radius = random.nextDouble() * numberOfBlocks / 16.0D;
        final double maxXZLimit = (MathHelper.sin(iteration * (float) Math.PI / numberOfBlocks) + 1.0F) * radius + 1.0D;
        final double maxYLimit = (MathHelper.sin(iteration * (float) Math.PI / numberOfBlocks) + 1.0F) * radius + 1.0D;
        final int minX = MathHelper.floor_double(minXLimit - maxXZLimit / 2.0D);
        final int minY = MathHelper.floor_double(minYLimit - maxYLimit / 2.0D);
        final int minZ = MathHelper.floor_double(minZLimit - maxXZLimit / 2.0D);
        final int maxX = MathHelper.floor_double(minXLimit + maxXZLimit / 2.0D);
        final int maxY = MathHelper.floor_double(minYLimit + maxYLimit / 2.0D);
        final int maxZ = MathHelper.floor_double(minZLimit + maxXZLimit / 2.0D);

        for (int x = minX; x <= maxX; ++x)
        {
            final double xVector = (x + 0.5D - minXLimit) / (maxXZLimit / 2.0D);

            if (xVector * xVector < 1.0D) for (int y = minY; y <= maxY; ++y)
            {
                final double yVector = (y + 0.5D - minYLimit) / (maxYLimit / 2.0D);

                if (xVector * xVector + yVector * yVector < 1.0D) for (int z = minZ; z <= maxZ; ++z)
                {
                    final double zVector = (z + 0.5D - minZLimit) / (maxXZLimit / 2.0D);

                    if (xVector * xVector + yVector * yVector + zVector * zVector < 1.0D)
                        placeClayBlock(world, x, y, z);
                }
            }
        }
    }

    private static void placeClayBlock(World world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).isReplaceableOreGen(world, x, y, z, Blocks.stone))
            world.setBlock(x, y, z, Blocks.clay, 0, 2);
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
