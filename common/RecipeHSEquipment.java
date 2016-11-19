package mod_HarkenScythe.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeHSEquipment
{
    public static void setupEquipmentRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSSwordLivingmetal, 1), new Object[] {" X ", " X ", " * ", 'X', mod_HarkenScythe.HSIngotLivingmetal, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSSpadeLivingmetal, 1), new Object[] {" X ", " * ", " * ", 'X', mod_HarkenScythe.HSIngotLivingmetal, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSPickaxeLivingmetal, 1), new Object[] {"XXX", " * ", " * ", 'X', mod_HarkenScythe.HSIngotLivingmetal, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSAxeLivingmetal, 1), new Object[] {"XX ", "X* ", " * ", 'X', mod_HarkenScythe.HSIngotLivingmetal, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSHoeLivingmetal, 1), new Object[] {"XX ", " * ", " * ", 'X', mod_HarkenScythe.HSIngotLivingmetal, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSSwordBiomass, 1), new Object[] {" X ", " X ", " * ", 'X', mod_HarkenScythe.HSBiomass, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSSpadeBiomass, 1), new Object[] {" X ", " * ", " * ", 'X', mod_HarkenScythe.HSBiomass, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSPickaxeBiomass, 1), new Object[] {"XXX", " * ", " * ", 'X', mod_HarkenScythe.HSBiomass, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSAxeBiomass, 1), new Object[] {"XX ", "X* ", " * ", 'X', mod_HarkenScythe.HSBiomass, '*', Item.stick});
        GameRegistry.addRecipe(new ItemStack(mod_HarkenScythe.HSHoeBiomass, 1), new Object[] {"XX ", " * ", " * ", 'X', mod_HarkenScythe.HSBiomass, '*', Item.stick});
    }
}
