package mod_HarkenScythe.common;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;

public class EnchantmentHSSoulsteal extends Enchantment
{
    public EnchantmentHSSoulsteal(int var1, int var2)
    {
        super(var1, var2, EnumEnchantmentType.weapon);
        this.setName("Soulsteal");
    }

    public EnchantmentHSSoulsteal(int var1, int var2, EnumEnchantmentType var3)
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
        return 3;
    }

    /**
     * Returns the correct traslated name of the enchantment and the level in roman numbers.
     */
    public String getTranslatedName(int var1)
    {
        String var2 = "Soulsteal";
        return var2 + " " + StatCollector.translateToLocal("enchantment.level." + var1);
    }
}
