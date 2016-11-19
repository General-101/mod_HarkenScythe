package mod_HarkenScythe.common;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemHSBook extends Item
{
    private Icon iconReady;
    private Icon iconNormal;
    private int specialNumber;
    private int counter;
    private int slow;
    Side side = FMLCommonHandler.instance().getEffectiveSide();

    public ItemHSBook(int var1, int var2)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.specialNumber = var2;
        this.setNoRepair();
        this.counter = 0;
        this.slow = 0;

        if (var2 == 2)
        {
            this.setMaxDamage(32);
        }

        if (var2 == 3)
        {
            this.maxStackSize = 5;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        if (this.specialNumber == 2)
        {
            this.iconReady = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "Open");
            this.iconNormal = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
        }

        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    public Icon getIcon(ItemStack var1, int var2, EntityPlayer var3, ItemStack var4, int var5)
    {
        if (this.specialNumber == 2)
        {
            if (var3.getItemInUse() == null)
            {
                return this.itemIcon;
            }
            else
            {
                int var6 = var1.getMaxItemUseDuration() - var5;
                return var6 > 0 ? this.iconReady : this.iconNormal;
            }
        }
        else
        {
            return this.itemIcon;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack var1)
    {
        return this.specialNumber != 3;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack var1)
    {
        return EnumRarity.epic;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4)
    {
        if (this.specialNumber == 0)
        {
            var3.add("A book containing religious texts");
            var3.add("and instructions for magical rituals");
        }

        if (this.specialNumber == 1)
        {
            var3.add("A book containing religious texts");
            var3.add("and instructions for sacrificial rituals");
        }

        if (this.specialNumber == 2)
        {
            var3.add("The Book of the Dead: Bound in human");
            var3.add("flesh and inked in blood, this ancient");
            var3.add("Samarian text contained bizarre");
            var3.add("burial rights, funeral incantations,");
            var3.add("and demon resurrection passages.");
        }

        if (this.specialNumber == 3)
        {
            var3.add("A Missing page of the Necronomicon");
            var3.add("writen in ancient Samarian text.");
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack var1)
    {
        return 20;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack var1)
    {
        return this.specialNumber == 2 ? EnumAction.block : EnumAction.none;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (this.specialNumber == 2)
        {
            int[] var4 = necronomiconCostCheck(var1, var3);
            int var5 = var4[0];
            int var6 = var4[1];

            if (var5 == 1)
            {
                if (!var3.capabilities.isCreativeMode)
                {
                    boolean var10 = false;
                    ++this.slow;
                    var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
                    int var11 = var1.getItemDamage();

                    if (var11 == 4)
                    {
                        var2.playSoundAtEntity(var3, "mob.endermen.stare", 5.0F, 1.0F);
                    }

                    if (var11 == 2 || var11 == 6 || var11 == 10 || var11 == 14 || var11 == 18 || var11 == 22 || var11 == 26 || var11 == 30)
                    {
                        this.amuletEffectText(var2, var3);
                    }

                    if (var11 == 4 || var11 == 8 || var11 == 12 || var11 == 16 || var11 == 20 || var11 == 24 || var11 == 28 || var11 == 30)
                    {
                        this.amuletEffectText2(var2, var3);
                    }

                    if (this.side == Side.CLIENT)
                    {
                        Minecraft var9 = FMLClientHandler.instance().getClient();

                        if (var9.isIntegratedServerRunning() && this.slow == (this.counter + 1) * 2)
                        {
                            var10 = true;
                        }
                    }
                    else if (this.slow == (this.counter + 1) * 1)
                    {
                        var10 = true;
                    }

                    if (var10)
                    {
                        ++this.counter;
                        ++this.counter;
                        var3.getCurrentEquippedItem().damageItem(2, var3);
                        var1.setItemDamage(this.counter);

                        if (var11 >= 30)
                        {
                            this.onPlayerStoppedUsing(var1, var2, var3, var11);
                            return var1;
                        }

                        return var1;
                    }

                    return var1;
                }

                if (var3.capabilities.isCreativeMode)
                {
                    ++this.counter;
                    var1.setItemDamage(this.counter);
                    int var7 = var1.getItemDamage();

                    if (var7 >= 30)
                    {
                        this.onPlayerStoppedUsing(var1, var2, var3, var7);
                        return var1;
                    }

                    var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));

                    if (var7 == 4 || var7 == 5)
                    {
                        var2.playSoundAtEntity(var3, "mob.endermen.stare", 5.0F, 1.0F);
                    }

                    if (var7 == 3 || var7 == 7 || var7 == 11 || var7 == 15 || var7 == 19 || var7 == 23 || var7 == 27 || var7 == 29)
                    {
                        this.amuletEffectText(var2, var3);
                    }

                    if (var7 == 5 || var7 == 9 || var7 == 13 || var7 == 17 || var7 == 17 || var7 == 21 || var7 == 25 || var7 == 29)
                    {
                        this.amuletEffectText2(var2, var3);
                    }

                    if (this.side == Side.CLIENT)
                    {
                        Minecraft var8 = FMLClientHandler.instance().getClient();

                        if (var8.isIntegratedServerRunning())
                        {
                            --this.counter;
                        }
                    }

                    ++this.counter;
                    var1.setItemDamage(this.counter);
                    return var1;
                }
            }
        }

        return var1;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        if (this.specialNumber == 2)
        {
            int var5 = 0;
            float var6 = (float)var4;

            if (var6 >= 29.0F)
            {
                for (int var7 = 0; var7 < var2.loadedEntityList.size(); ++var7)
                {
                    int[] var8 = necronomiconCostCheck(var1, var3);
                    int var9 = var8[0];
                    int var10 = var8[1];
                    Object var11 = var3;

                    if (this.side == Side.CLIENT)
                    {
                        var11 = (Entity)var2.getLoadedEntityList().get(var7);
                    }

                    if (this.side == Side.SERVER)
                    {
                        var11 = (Entity)var2.loadedEntityList.get(var7);
                    }

                    if (var11 instanceof EntityHSSoul && var3.getDistanceSqToEntity((Entity)var11) < 5.0D && var9 == 1)
                    {
                        EntityHSSoul var12 = (EntityHSSoul)var11;

                        if (EntityHSSoul.createSpectralPet(var12.fallenSoulName, var2, ((Entity)var11).posX, ((Entity)var11).posY, ((Entity)var11).posZ, itemRand, Boolean.valueOf(false)))
                        {
                            this.necronomiconActivate(var10, var3);
                            ++var5;
                            var3.worldObj.playSoundAtEntity(var3, "mob.zombie.unfect", 1.0F, 1.0F);
                            ((Entity)var11).setDead();
                        }
                    }

                    if (var11 instanceof EntityHSSoulLost && var3.getDistanceSqToEntity((Entity)var11) < 5.0D && var9 == 1)
                    {
                        EntityHSSoulLost var13 = (EntityHSSoulLost)var11;

                        if (EntityHSSoulLost.createSpectralPet(var2, ((Entity)var11).posX, ((Entity)var11).posY, ((Entity)var11).posZ, itemRand))
                        {
                            this.necronomiconActivate(var10, var3);
                            ++var5;
                            var3.worldObj.playSoundAtEntity(var3, "mob.zombie.unfect", 1.0F, 1.0F);
                            ((Entity)var11).setDead();
                        }
                    }
                }
            }

            this.counter = 0;
            this.slow = 0;
            var1.setItemDamage(0);

            if (var5 == 0)
            {
                var3.worldObj.playSoundAtEntity(var3, "mob.zombie.infect", 1.0F, 1.0F);
            }
        }
    }

    private void amuletEffectText(World var1, EntityPlayer var2)
    {
        var1.spawnParticle("enchantmenttable", var2.posX, var2.posY, var2.posZ + 0.4D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX + 0.4D, var2.posY, var2.posZ, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX, var2.posY, var2.posZ - 0.4D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX - 0.4D, var2.posY, var2.posZ, 0.0D, 0.0D, 0.0D);
    }

    private void amuletEffectText2(World var1, EntityPlayer var2)
    {
        var1.spawnParticle("enchantmenttable", var2.posX + 0.2D, var2.posY + 0.2D, var2.posZ + 0.2D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX + 0.2D, var2.posY + 0.2D, var2.posZ - 0.2D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX - 0.2D, var2.posY + 0.2D, var2.posZ + 0.2D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX - 0.2D, var2.posY + 0.2D, var2.posZ - 0.2D, 0.0D, 0.0D, 0.0D);
    }

    public static int[] necronomiconCostCheck(ItemStack var0, EntityPlayer var1)
    {
        int[] var2 = new int[] {0, 0, 0};
        byte var3 = 2;

        if (ItemHSKeeper.bloodkeeperCheck(var1, var3, false))
        {
            var2[0] = 1;
            var2[1] = var3;
            return var2;
        }
        else
        {
            return var2;
        }
    }

    public void necronomiconActivate(int var1, EntityPlayer var2)
    {
        if (var1 > 0)
        {
            boolean var3 = false;
            int var4;
            int var5;

            for (var4 = 0; var4 < var2.inventory.mainInventory.length; ++var4)
            {
                if (var2.inventory.mainInventory[var4] != null && "item.HSBloodkeeper".equals(var2.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                {
                    var5 = var2.inventory.mainInventory[var4].getMaxDamage() - var2.inventory.mainInventory[var4].getItemDamage();

                    if (var5 >= var1)
                    {
                        var2.inventory.mainInventory[var4].damageItem(var1, var2);
                        var3 = true;

                        if (var2.inventory.mainInventory[var4].getItemDamage() >= var2.inventory.mainInventory[var4].getMaxDamage())
                        {
                            var2.inventory.mainInventory[var4] = new ItemStack(mod_HarkenScythe.HSEssenceKeeper, 1);
                        }

                        break;
                    }
                }
            }

            if (!var3)
            {
                for (var4 = 0; var4 < var2.inventory.mainInventory.length; ++var4)
                {
                    if (var2.inventory.mainInventory[var4] != null && "item.HSBloodVessel".equals(var2.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var5 = var2.inventory.mainInventory[var4].getMaxDamage() - var2.inventory.mainInventory[var4].getItemDamage();

                        if (var5 >= var1)
                        {
                            var2.inventory.mainInventory[var4].damageItem(var1, var2);

                            if (var2.inventory.mainInventory[var4].getItemDamage() >= var2.inventory.mainInventory[var4].getMaxDamage())
                            {
                                var2.inventory.mainInventory[var4] = new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1);
                            }

                            break;
                        }
                    }
                }
            }
        }
    }
}
