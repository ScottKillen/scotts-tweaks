package com.scottkillen.mod.koresample;

import cpw.mods.fml.common.eventhandler.EventBus;

public abstract class ForgeEventListener
{
    public void listen(EventBus eventBus)
    {
        eventBus.register(this);
    }
}
