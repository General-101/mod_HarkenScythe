package mod_HarkenScythe.common;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentHSSoulAttuned extends Enchantment
{
    public EnchantmentHSSoulAttuned(int var1, int var2)
    {
        super(var1, var2, EnumEnchantmentType.all);
        this.setName("Soul Attuned");
    }

    public EnchantmentHSSoulAttuned(int var1, int var2, EnumEnchantmentType var3)
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
        return 1;
    }

    /**
     * Returns the correct traslated name of the enchantment and the level in roman numbers.
     */
    public String getTranslatedName(int var1)
    {
        String var2 = "Soul Attuned";
        return var2;
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment var1)
    {
        return super.canApplyTogether(var1) && var1.effectId != mod_HarkenScythe.HSBloodAttunedAug.effectId;
    }
}
