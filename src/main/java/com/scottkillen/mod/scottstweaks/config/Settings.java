package com.scottkillen.mod.scottstweaks.config;

import com.google.common.base.Objects;
import com.scottkillen.mod.koresample.config.ConfigSyncable;
import com.scottkillen.mod.scottstweaks.TheMod;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;

public enum Settings implements ConfigSyncable
{
    INSTANCE;
    public static final String CONFIG_VERSION = "1";
    private static final String CATEGORY_FEATHER_DROP =
            String.format("%s%sfeather_drop", Configuration.CATEGORY_GENERAL, Configuration.CATEGORY_SPLITTER);

    private static final String CATEGORY_PLANTING =
            String.format("%s%splanting", Configuration.CATEGORY_GENERAL, Configuration.CATEGORY_SPLITTER);


    private int chickFeatherQuantity = 1;
    private int henFeatherQuantity = 1;
    private int chickFeatherRarity = 50000;
    private int henFeatherRarity = 25000;
    private boolean doChicksDropFeathers = true;
    private boolean doHensDropFeathers = true;
    private boolean doPlantGrowable = true;

    private static int get(Configuration config, String settingName, int defaultValue, int minumumValue,
                           int maximumValue)
    {
        return config.getInt(settingName, CATEGORY_FEATHER_DROP, defaultValue, minumumValue, maximumValue,
                getLocalizedComment(settingName));
    }

    private static boolean get(Configuration config, String settingName, String category, boolean defaultValue)
    {
        return config.getBoolean(settingName, category, defaultValue, getLocalizedComment(settingName));
    }

    private static String getLocalizedComment(String settingName)
    {
        return StatCollector.translateToLocal("config." + TheMod.INSTANCE.modID() + ':' + settingName);
    }

    @Override
    public void convertOldConfig(Configuration oldConfig)
    {
        // Handle old config versions (none yet)

        syncConfig(TheMod.INSTANCE.configuration());
    }

    @Override
    public void syncConfig(Configuration config)
    {
        chickFeatherQuantity = get(config, "chickFeatherQuantity", chickFeatherQuantity, 1, 10);
        chickFeatherRarity = get(config, "chickFeatherRarity", chickFeatherRarity, 6000, Integer.MAX_VALUE);
        doChicksDropFeathers = get(config, "doChicksDropFeathers", CATEGORY_FEATHER_DROP, doChicksDropFeathers);
        doHensDropFeathers = get(config, "doHensDropFeathers", CATEGORY_FEATHER_DROP, doHensDropFeathers);
        henFeatherQuantity = get(config, "henFeatherQuantity", henFeatherQuantity, 1, 10);
        henFeatherRarity = get(config, "henFeatherRarity", henFeatherRarity, 6000, Integer.MAX_VALUE);

        doPlantGrowable = get(config, "doPlantGrowable", CATEGORY_PLANTING, doPlantGrowable);
    }

    public int chickFeatherQuantity() { return chickFeatherQuantity; }

    public int chickFeatherRarity() { return chickFeatherRarity; }

    public boolean doChicksDropFeathers() { return doChicksDropFeathers; }

    public boolean doHensDropFeathers() { return doHensDropFeathers; }

    public boolean doPlantGrowable() { return doPlantGrowable;}

    public int henFeatherQuantity() { return henFeatherQuantity; }

    public int henFeatherRarity() { return henFeatherRarity; }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("chickFeatherQuantity", chickFeatherQuantity)
                .add("henFeatherQuantity", henFeatherQuantity).add("chickFeatherRarity", chickFeatherRarity)
                .add("henFeatherRarity", henFeatherRarity).add("doChicksDropFeathers", doChicksDropFeathers)
                .add("doHensDropFeathers", doHensDropFeathers).add("doPlantGrowable", doPlantGrowable).toString();
    }
}
