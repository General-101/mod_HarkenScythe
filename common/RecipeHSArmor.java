package mod_HarkenScythe.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class RecipeHSArmor
{
    public static void setupArmorRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSHelmetSoulweave, 1), new Object[] {"XXX", "X X", 'X', mod_HarkenScythe.HSSoulweaveClothBlock});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSPlateSoulweave, 1), new Object[] {"X X", "XXX", "XXX", 'X', mod_HarkenScythe.HSSoulweaveClothBlock});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSLegsSoulweave, 1), new Object[] {"XXX", "X X", "X X", 'X', mod_HarkenScythe.HSSoulweaveClothBlock});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSBootsSoulweave, 1), new Object[] {"X X", "X X", 'X', mod_HarkenScythe.HSSoulweaveClothBlock});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSHelmetBloodweave, 1), new Object[] {"XXX", "X X", 'X', mod_HarkenScythe.HSBloodweaveClothBlock});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSPlateBloodweave, 1), new Object[] {"X X", "XXX", "XXX", 'X', mod_HarkenScythe.HSBloodweaveClothBlock});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSLegsBloodweave, 1), new Object[] {"XXX", "X X", "X X", 'X', mod_HarkenScythe.HSBloodweaveClothBlock});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSBootsBloodweave, 1), new Object[] {"X X", "X X", 'X', mod_HarkenScythe.HSBloodweaveClothBlock});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSHelmetLivingmetal, 1), new Object[] {"XXX", "X X", 'X', mod_HarkenScythe.HSIngotLivingmetal});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSPlateLivingmetal, 1), new Object[] {"X X", "XXX", "XXX", 'X', mod_HarkenScythe.HSIngotLivingmetal});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSLegsLivingmetal, 1), new Object[] {"XXX", "X X", "X X", 'X', mod_HarkenScythe.HSIngotLivingmetal});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSBootsLivingmetal, 1), new Object[] {"X X", "X X", 'X', mod_HarkenScythe.HSIngotLivingmetal});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSHelmetBiomass, 1), new Object[] {"XXX", "X X", 'X', mod_HarkenScythe.HSBiomass});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSPlateBiomass, 1), new Object[] {"X X", "XXX", "XXX", 'X', mod_HarkenScythe.HSBiomass});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSLegsBiomass, 1), new Object[] {"XXX", "X X", "X X", 'X', mod_HarkenScythe.HSBiomass});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSBootsBiomass, 1), new Object[] {"X X", "X X", 'X', mod_HarkenScythe.HSBiomass});
    }
}
