package mod_HarkenScythe.common;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;

public final class ServerTickHandlerHarkenScythe implements ITickHandler
{
    public void tickStart(EnumSet var1, Object ... var2) {}

    public void tickEnd(EnumSet var1, Object ... var2)
    {
        if (var1.equals(EnumSet.of(TickType.SERVER)))
        {
            this.onTickInGame();
        }
    }

    public EnumSet ticks()
    {
        return EnumSet.of(TickType.RENDER, TickType.SERVER);
    }

    public String getLabel()
    {
        return null;
    }

    public void onTickInGame() {}
}
