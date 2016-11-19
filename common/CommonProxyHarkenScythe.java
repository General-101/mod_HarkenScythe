package mod_HarkenScythe.common;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;

public class CommonProxyHarkenScythe
{
    public void registerRenderThings()
    {
        TickRegistry.registerTickHandler(new ServerTickHandlerHarkenScythe(), Side.SERVER);
    }

    public int addArmor(String var1)
    {
        return 0;
    }

    public World getClientWorld()
    {
        return null;
    }

    public void registerRenderInformation() {}
}
