package mod_HarkenScythe.common;

import cpw.mods.fml.common.IPickupNotifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class PickupHandler implements IPickupNotifier
{
    public void notifyPickup(EntityItem var1, EntityPlayer var2)
    {
        if (var1.getEntityItem().itemID == Item.appleGold.itemID)
        {
            var2.addStat(mod_HarkenScythe.Tutorial, 1);
        }
    }
}
