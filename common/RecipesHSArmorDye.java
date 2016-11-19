package mod_HarkenScythe.common;

import java.util.ArrayList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipesHSArmorDye implements IRecipe
{
    private static int[] customSpectralRecipesInput = new int[12];
    private static int[] customSpectralRecipesOutput;

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting var1, World var2)
    {
        ItemStack var3 = null;
        ArrayList var4 = new ArrayList();

        for (int var5 = 0; var5 < var1.getSizeInventory(); ++var5)
        {
            ItemStack var6 = var1.getStackInSlot(var5);

            if (var6 != null)
            {
                if (var6.getItem() instanceof ItemArmor)
                {
                    ItemArmor var7 = (ItemArmor)var6.getItem();

                    if (!this.getCraftingSpectralDye(var7, var3) || var3 != null)
                    {
                        return false;
                    }

                    var3 = var6;
                }
                else
                {
                    if (var6.itemID != mod_HarkenScythe.HSDyeSpectral.itemID)
                    {
                        return false;
                    }

                    var4.add(var6);
                }
            }
        }

        return var3 != null && !var4.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting var1)
    {
        ItemStack var2 = null;
        int[] var3 = new int[3];
        boolean var4 = false;
        boolean var5 = false;
        ItemArmor var6 = null;

        for (int var7 = 0; var7 < var1.getSizeInventory(); ++var7)
        {
            ItemStack var12 = var1.getStackInSlot(var7);

            if (var12 != null)
            {
                if (var12.getItem() instanceof ItemArmor)
                {
                    var6 = (ItemArmor)var12.getItem();

                    if (!this.getCraftingSpectralDye(var6, var2) || var2 != null)
                    {
                        return null;
                    }

                    var2 = var12.copy();
                    var2.stackSize = 1;
                }
                else if (var12.itemID != mod_HarkenScythe.HSDyeSpectral.itemID)
                {
                    return null;
                }
            }
        }

        if (var6 == null)
        {
            return null;
        }
        else
        {
            for (int var14 = 0; var14 < customSpectralRecipesInput.length; ++var14)
            {
                if (var6.itemID == customSpectralRecipesInput[var14])
                {
                    var2.itemID = customSpectralRecipesOutput[var14];
                    var2 = new ItemStack(var2.getItem(), 1, var2.getItemDamage());
                    return var2;
                }
            }

            return var2;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 10;
    }

    public ItemStack getRecipeOutput()
    {
        return null;
    }

    public boolean getCraftingSpectralDye(ItemArmor var1, ItemStack var2)
    {
        int var3 = 0;

        for (int var4 = 0; var4 < customSpectralRecipesInput.length; ++var4)
        {
            if (var1.itemID == customSpectralRecipesInput[var4])
            {
                ++var3;
            }
        }

        if (var3 > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    static
    {
        customSpectralRecipesInput[0] = Item.helmetLeather.itemID;
        customSpectralRecipesInput[1] = Item.plateLeather.itemID;
        customSpectralRecipesInput[2] = Item.legsLeather.itemID;
        customSpectralRecipesInput[3] = Item.bootsLeather.itemID;
        customSpectralRecipesInput[4] = mod_HarkenScythe.HSHelmetSoulweave.itemID;
        customSpectralRecipesInput[5] = mod_HarkenScythe.HSPlateSoulweave.itemID;
        customSpectralRecipesInput[6] = mod_HarkenScythe.HSLegsSoulweave.itemID;
        customSpectralRecipesInput[7] = mod_HarkenScythe.HSBootsSoulweave.itemID;
        customSpectralRecipesInput[8] = mod_HarkenScythe.HSHelmetBloodweave.itemID;
        customSpectralRecipesInput[9] = mod_HarkenScythe.HSPlateBloodweave.itemID;
        customSpectralRecipesInput[10] = mod_HarkenScythe.HSLegsBloodweave.itemID;
        customSpectralRecipesInput[11] = mod_HarkenScythe.HSBootsBloodweave.itemID;
        customSpectralRecipesOutput = new int[12];
        customSpectralRecipesOutput[0] = mod_HarkenScythe.HSHelmetSpectralLeather.itemID;
        customSpectralRecipesOutput[1] = mod_HarkenScythe.HSPlateSpectralLeather.itemID;
        customSpectralRecipesOutput[2] = mod_HarkenScythe.HSLegsSpectralLeather.itemID;
        customSpectralRecipesOutput[3] = mod_HarkenScythe.HSBootsSpectralLeather.itemID;
        customSpectralRecipesOutput[4] = mod_HarkenScythe.HSHelmetSpectralSoulweave.itemID;
        customSpectralRecipesOutput[5] = mod_HarkenScythe.HSPlateSpectralSoulweave.itemID;
        customSpectralRecipesOutput[6] = mod_HarkenScythe.HSLegsSpectralSoulweave.itemID;
        customSpectralRecipesOutput[7] = mod_HarkenScythe.HSBootsSpectralSoulweave.itemID;
        customSpectralRecipesOutput[8] = mod_HarkenScythe.HSHelmetSpectralBloodweave.itemID;
        customSpectralRecipesOutput[9] = mod_HarkenScythe.HSPlateSpectralBloodweave.itemID;
        customSpectralRecipesOutput[10] = mod_HarkenScythe.HSLegsSpectralBloodweave.itemID;
        customSpectralRecipesOutput[11] = mod_HarkenScythe.HSBootsSpectralBloodweave.itemID;
    }
}
