package mod_HarkenScythe.common;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import java.util.Random;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class TradeHandler implements cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler
{
    public void manipulateTradesForVillager(EntityVillager var1, MerchantRecipeList var2, Random var3)
    {
        var2.add(new MerchantRecipe(new ItemStack(Item.emerald, 30), new ItemStack(mod_HarkenScythe.HSNecronomiconPage, 1)));
    }
}
