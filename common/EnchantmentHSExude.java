package mod_HarkenScythe.common;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class EnchantmentHSExude extends Enchantment
{
    public EnchantmentHSExude(int var1, int var2)
    {
        super(var1, var2, EnumEnchantmentType.armor);
        this.setName("Exude");
    }

    public EnchantmentHSExude(int var1, int var2, EnumEnchantmentType var3)
    {
        super(var1, var2, var3);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int var1)
    {
        return 100;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int var1)
    {
        return 100;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 4;
    }

    /**
     * Returns the correct traslated name of the enchantment and the level in roman numbers.
     */
    public String getTranslatedName(int var1)
    {
        String var2 = "Exude";
        return var2 + " " + StatCollector.translateToLocal("enchantment.level." + var1);
    }

    public static int getTotalEnchantmentLvl(EntityPlayer var0)
    {
        int var1 = 0;

        for (int var2 = 0; var2 < 4; ++var2)
        {
            if (var0.inventory.armorItemInSlot(var2) != null)
            {
                ItemStack var3 = var0.inventory.armorItemInSlot(var2);
                var1 += EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSExudeAug.effectId, var3);
            }
        }

        return var1;
    }
}
