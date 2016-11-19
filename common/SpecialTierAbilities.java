package mod_HarkenScythe.common;

import net.minecraft.entity.player.EntityPlayer;

public class SpecialTierAbilities
{
    private static String[] livingmetalEquipmentArmorCheck = new String[14];
    private static String[] biomassEquipmentArmorCheck;

    public static void sTALivingmetal(EntityPlayer var0, int var1)
    {
        int var2;
        int var3;
        int var4;

        for (var2 = 0; var2 < var0.inventory.mainInventory.length; ++var2)
        {
            if (var0.inventory.mainInventory[var2] != null)
            {
                for (var3 = 0; var3 < livingmetalEquipmentArmorCheck.length; ++var3)
                {
                    if (livingmetalEquipmentArmorCheck[var3].equals(var0.inventory.mainInventory[var2].getItem().getUnlocalizedName()))
                    {
                        var4 = var0.inventory.mainInventory[var2].getItemDamage();

                        if (var4 > var1)
                        {
                            var0.inventory.mainInventory[var2].damageItem(-var1, var0);
                        }
                        else
                        {
                            var0.inventory.mainInventory[var2].damageItem(-var4, var0);
                        }
                    }
                }
            }
        }

        for (var2 = 0; var2 < 4; ++var2)
        {
            if (var0.inventory.armorItemInSlot(var2) != null)
            {
                for (var3 = 7; var3 <= 10; ++var3)
                {
                    if (livingmetalEquipmentArmorCheck[var3].equals(var0.inventory.armorItemInSlot(var2).getItem().getUnlocalizedName()))
                    {
                        var4 = var0.inventory.armorItemInSlot(var2).getItemDamage();

                        if (var4 > var1)
                        {
                            var0.inventory.armorItemInSlot(var2).damageItem(-var1, var0);
                        }
                        else
                        {
                            var0.inventory.armorItemInSlot(var2).damageItem(-var4, var0);
                        }
                    }
                }
            }
        }
    }

    public static void sTABiomass(EntityPlayer var0, int var1)
    {
        int var2;
        int var3;
        int var4;

        for (var2 = 0; var2 < var0.inventory.mainInventory.length; ++var2)
        {
            if (var0.inventory.mainInventory[var2] != null)
            {
                for (var3 = 0; var3 < biomassEquipmentArmorCheck.length; ++var3)
                {
                    if (biomassEquipmentArmorCheck[var3].equals(var0.inventory.mainInventory[var2].getItem().getUnlocalizedName()))
                    {
                        var4 = var0.inventory.mainInventory[var2].getItemDamage();

                        if (var4 > var1)
                        {
                            var0.inventory.mainInventory[var2].damageItem(-var1, var0);
                        }
                        else
                        {
                            var0.inventory.mainInventory[var2].damageItem(-var4, var0);
                        }
                    }
                }
            }
        }

        for (var2 = 0; var2 < 4; ++var2)
        {
            if (var0.inventory.armorItemInSlot(var2) != null)
            {
                for (var3 = 7; var3 <= 10; ++var3)
                {
                    if (biomassEquipmentArmorCheck[var3].equals(var0.inventory.armorItemInSlot(var2).getItem().getUnlocalizedName()))
                    {
                        var4 = var0.inventory.armorItemInSlot(var2).getItemDamage();

                        if (var4 > var1)
                        {
                            var0.inventory.armorItemInSlot(var2).damageItem(-var1, var0);
                        }
                        else
                        {
                            var0.inventory.armorItemInSlot(var2).damageItem(-var4, var0);
                        }
                    }
                }
            }
        }
    }

    static
    {
        livingmetalEquipmentArmorCheck[0] = "item.HSScytheLivingmetal";
        livingmetalEquipmentArmorCheck[1] = "item.HSGlaiveLivingmetal";
        livingmetalEquipmentArmorCheck[2] = "item.HSSwordLivingmetal";
        livingmetalEquipmentArmorCheck[3] = "item.HSSpadeLivingmetal";
        livingmetalEquipmentArmorCheck[4] = "item.HSPickaxeLivingmetal";
        livingmetalEquipmentArmorCheck[5] = "item.HSAxeLivingmetal";
        livingmetalEquipmentArmorCheck[6] = "item.HSHoeLivingmetal";
        livingmetalEquipmentArmorCheck[7] = "item.HSHelmetLivingmetal";
        livingmetalEquipmentArmorCheck[8] = "item.HSChestplateLivingmetal";
        livingmetalEquipmentArmorCheck[9] = "item.HSLeggingsLivingmetal";
        livingmetalEquipmentArmorCheck[10] = "item.HSBootsLivingmetal";
        livingmetalEquipmentArmorCheck[11] = "item.ASShieldLivingmetal";
        livingmetalEquipmentArmorCheck[12] = "item.ASShieldLivingmetalGilded";
        livingmetalEquipmentArmorCheck[13] = "item.HSGaintSwordLivingmetal";
        biomassEquipmentArmorCheck = new String[14];
        biomassEquipmentArmorCheck[0] = "item.HSScytheBiomass";
        biomassEquipmentArmorCheck[1] = "item.HSGlaiveBiomass";
        biomassEquipmentArmorCheck[2] = "item.HSSwordBiomass";
        biomassEquipmentArmorCheck[3] = "item.HSSpadeBiomass";
        biomassEquipmentArmorCheck[4] = "item.HSPickaxeBiomass";
        biomassEquipmentArmorCheck[5] = "item.HSAxeBiomass";
        biomassEquipmentArmorCheck[6] = "item.HSHoeBiomass";
        biomassEquipmentArmorCheck[7] = "item.HSHelmetBiomass";
        biomassEquipmentArmorCheck[8] = "item.HSChestplateBiomass";
        biomassEquipmentArmorCheck[9] = "item.HSLeggingsBiomass";
        biomassEquipmentArmorCheck[10] = "item.HSBootsBiomass";
        biomassEquipmentArmorCheck[11] = "item.ASShieldBiomass";
        biomassEquipmentArmorCheck[12] = "item.ASShieldBiomassGilded";
        biomassEquipmentArmorCheck[13] = "item.HSGaintSwordBiomass";
    }
}
