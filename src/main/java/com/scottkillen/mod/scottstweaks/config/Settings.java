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

    private int chickFeatherQuantity = 1;
    private int henFeatherQuantity = 1;
    private int chickFeatherRarity = 50000;
    private int henFeatherRarity = 25000;
    private boolean doChicksDropFeathers = true;
    private boolean doHensDropFeathers = true;

    private static int get(Configuration config, String settingName, String category, int defaultValue,
                           int minumumValue, int maximumValue)
    {
        return config.getInt(settingName, category, defaultValue, minumumValue, maximumValue,
                getLocalizedComment(settingName));
    }

    private static boolean get(Configuration config, String settingName, String category, boolean defaultValue)
    {
        return config.getBoolean(settingName, category, defaultValue, getLocalizedComment(settingName));
    }

    private static String getLocalizedComment(String settingName)
    {
        return StatCollector.translateToLocal("config." + TheMod.MOD_ID + ':' + settingName);
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
        final String featherDropCategory =
                String.format("%s%sfeather_drop", Configuration.CATEGORY_GENERAL, Configuration.CATEGORY_SPLITTER);
        chickFeatherQuantity = get(config, "chickFeatherQuantity", featherDropCategory, chickFeatherQuantity, 1, 10);
        chickFeatherRarity =
                get(config, "chickFeatherRarity", featherDropCategory, chickFeatherRarity, 6000, Integer.MAX_VALUE);
        doChicksDropFeathers = get(config, "doChicksDropFeathers", featherDropCategory, doChicksDropFeathers);
        doHensDropFeathers = get(config, "doHensDropFeathers", featherDropCategory, doHensDropFeathers);
        henFeatherQuantity = get(config, "henFeatherQuantity", featherDropCategory, henFeatherQuantity, 1, 10);
        henFeatherRarity =
                get(config, "henFeatherRarity", featherDropCategory, henFeatherRarity, 6000, Integer.MAX_VALUE);
    }

    public int chickFeatherQuantity() { return chickFeatherQuantity; }

    public int chickFeatherRarity() { return chickFeatherRarity; }

    public boolean doChicksDropFeathers() { return doChicksDropFeathers; }

    public boolean doHensDropFeathers() { return doHensDropFeathers; }

    public int henFeatherQuantity() { return henFeatherQuantity; }

    public int henFeatherRarity() { return henFeatherRarity; }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("chickFeatherQuantity", chickFeatherQuantity)
                .add("henFeatherQuantity", henFeatherQuantity).add("chickFeatherRarity", chickFeatherRarity)
                .add("henFeatherRarity", henFeatherRarity).add("doChicksDropFeathers", doChicksDropFeathers)
                .add("doHensDropFeathers", doHensDropFeathers).toString();
    }
}
