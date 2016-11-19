package mod_HarkenScythe.common;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;

public class EnchantmentHSDecapitate extends Enchantment
{
    public EnchantmentHSDecapitate(int var1, int var2)
    {
        super(var1, var2, EnumEnchantmentType.all);
        this.setName("Decapitate");
    }

    public EnchantmentHSDecapitate(int var1, int var2, EnumEnchantmentType var3)
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
        String var2 = "Decapitate";
        return var2 + " " + StatCollector.translateToLocal("enchantment.level." + var1);
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment var1)
    {
        return super.canApplyTogether(var1) && var1.effectId != mod_HarkenScythe.HSAfterlifeAug.effectId;
    }
}
