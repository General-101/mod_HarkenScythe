package mod_HarkenScythe.common;

import cpw.mods.fml.common.ICraftingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingHandler implements ICraftingHandler
{
    public void onCrafting(EntityPlayer var1, ItemStack var2, IInventory var3)
    {
        if (var2.itemID == Item.appleGold.itemID)
        {
            var1.addStat(mod_HarkenScythe.Tutorial, 1);
        }
    }

    public void onSmelting(EntityPlayer var1, ItemStack var2)
    {
        if (var2.itemID == Item.ingotGold.itemID)
        {
            var1.addStat(mod_HarkenScythe.Tutorial, 1);
        }
    }
}
