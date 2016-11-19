package mod_HarkenScythe.common;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntityEnchantmentTable;

public class TileEntityHSBloodAltar extends TileEntityEnchantmentTable implements IInventory
{
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    private boolean ItemBloodAltered = false;
    private boolean BlockBloodAltered = false;
    private boolean AugmentBloodAltered = false;
    private String field_94136_s;
    private ItemStack[] inventory = new ItemStack[1];

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.inventory.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int var1)
    {
        return this.inventory[var1];
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        this.inventory[var1] = var2;

        if (var2 != null && var2.stackSize > this.getInventoryStackLimit())
        {
            var2.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int var1, int var2)
    {
        ItemStack var3 = this.getStackInSlot(var1);

        if (var3 != null)
        {
            if (var3.stackSize <= var2)
            {
                this.setInventorySlotContents(var1, (ItemStack)null);
            }
            else
            {
                var3 = var3.splitStack(var2);

                if (var3.stackSize == 0)
                {
                    this.setInventorySlotContents(var1, (ItemStack)null);
                }
            }
        }

        return var3;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        ItemStack var2 = this.getStackInSlot(var1);

        if (var2 != null)
        {
            this.setInventorySlotContents(var1, (ItemStack)null);
        }

        return var2;
    }

    public boolean bloodAlterItem(int var1, int var2)
    {
        if (var1 == 6)
        {
            this.BlockBloodAltered = true;
        }

        if (var1 != 6)
        {
            this.ItemBloodAltered = true;
        }

        return true;
    }

    public boolean bloodAltarDrain(int var1)
    {
        this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
        int var2 = this.xCoord;
        int var3 = this.yCoord;
        int var4 = this.zCoord;
        BlockHSBloodAltar.bloodDrain(var1, var2, var3, var4, this.worldObj);
        return true;
    }

    public boolean bloodAlterAugment(int var1, int var2)
    {
        this.AugmentBloodAltered = true;
        return true;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && var1.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) < 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Inventory");

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.inventory.length)
            {
                this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
            }

            if (var1.hasKey("CustomName"))
            {
                this.field_94136_s = var1.getString("CustomName");
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.inventory.length; ++var3)
        {
            ItemStack var4 = this.inventory[var3];

            if (var4 != null)
            {
                NBTTagCompound var5 = new NBTTagCompound();
                var5.setByte("Slot", (byte)var3);
                var4.writeToNBT(var5);
                var2.appendTag(var5);
            }
        }

        var1.setTag("Inventory", var2);

        if (this.func_94135_b())
        {
            var1.setString("CustomName", this.field_94136_s);
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "TileEntityBloodAltar";
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();
        this.ItemBloodAltered = RecipeHSBloodAltar.BloodAltarUsed;
        this.BlockBloodAltered = RecipeHSBloodAltar.BloodAltarUsedBlock;
        this.AugmentBloodAltered = RecipeHSBloodAltar.BloodAltarUsedAugment;
        byte var1;
        boolean var2;
        boolean var3;
        boolean var4;
        byte var5;
        byte var6;
        byte var7;
        byte var8;
        byte var9;
        boolean var10;
        boolean var11;
        boolean var12;
        byte var13;
        byte var14;
        byte var15;
        byte var16;
        byte var17;
        boolean var18;
        byte var19;
        ByteArrayOutputStream var20;
        DataOutputStream var21;
        Packet250CustomPayload var22;
        EntityClientPlayerMP var23;

        if (this.ItemBloodAltered && this.inventory[0] != null)
        {
            this.inventory[0] = new ItemStack(RecipeHSBloodAltar.bloodAltarItemStackBuilder(), RecipeHSBloodAltar.BloodAltarUsedStackSize);

            if (this.side == Side.SERVER)
            {
                this.bloodAltarDrain(RecipeHSBloodAltar.BloodAltarUsedCost);
            }

            var1 = 2;
            var2 = false;
            var3 = false;
            var4 = false;
            var5 = 0;
            var6 = 0;
            var7 = 0;
            var8 = 0;
            var9 = 0;
            var10 = false;
            var11 = false;
            var12 = false;
            var13 = 0;
            var14 = 0;
            var15 = 0;
            var16 = 0;
            var17 = 0;
            var18 = false;
            var19 = 0;
            var20 = new ByteArrayOutputStream(8);
            var21 = new DataOutputStream(var20);

            try
            {
                var21.writeInt(var1);
                var21.writeBoolean(var2);
                var21.writeBoolean(var3);
                var21.writeBoolean(var4);
                var21.writeInt(var5);
                var21.writeInt(var6);
                var21.writeInt(var7);
                var21.writeInt(var8);
                var21.writeInt(var9);
                var21.writeBoolean(var10);
                var21.writeBoolean(var11);
                var21.writeBoolean(var12);
                var21.writeInt(var13);
                var21.writeInt(var14);
                var21.writeInt(var15);
                var21.writeInt(var16);
                var21.writeInt(var17);
                var21.writeBoolean(var18);
                var21.writeInt(var19);
            }
            catch (Exception var26)
            {
                var26.printStackTrace();
            }

            var22 = new Packet250CustomPayload();
            var22.channel = "HSPacket";
            var22.data = var20.toByteArray();
            var22.length = var20.size();

            if (this.side == Side.SERVER)
            {
                RecipeHSBloodAltar.BloodAltarUsed = false;
            }

            if (this.side == Side.CLIENT)
            {
                var23 = FMLClientHandler.instance().getClient().thePlayer;
                var23.sendQueue.addToSendQueue(var22);
            }

            this.ItemBloodAltered = false;
        }

        if (this.BlockBloodAltered && this.inventory[0] != null)
        {
            this.inventory[0] = new ItemStack(RecipeHSBloodAltar.bloodAltarBlockStackBuilder(), RecipeHSBloodAltar.BloodAltarUsedStackSize);

            if (this.side == Side.SERVER)
            {
                this.bloodAltarDrain(RecipeHSBloodAltar.BloodAltarUsedCost);
            }

            var1 = 2;
            var2 = false;
            var3 = false;
            var4 = false;
            var5 = 0;
            var6 = 0;
            var7 = 0;
            var8 = 0;
            var9 = 0;
            var10 = false;
            var11 = false;
            var12 = false;
            var13 = 0;
            var14 = 0;
            var15 = 0;
            var16 = 0;
            var17 = 0;
            var18 = false;
            var19 = 0;
            var20 = new ByteArrayOutputStream(8);
            var21 = new DataOutputStream(var20);

            try
            {
                var21.writeInt(var1);
                var21.writeBoolean(var2);
                var21.writeBoolean(var3);
                var21.writeBoolean(var4);
                var21.writeInt(var5);
                var21.writeInt(var6);
                var21.writeInt(var7);
                var21.writeInt(var8);
                var21.writeInt(var9);
                var21.writeBoolean(var10);
                var21.writeBoolean(var11);
                var21.writeBoolean(var12);
                var21.writeInt(var13);
                var21.writeInt(var14);
                var21.writeInt(var15);
                var21.writeInt(var16);
                var21.writeInt(var17);
                var21.writeBoolean(var18);
                var21.writeInt(var19);
            }
            catch (Exception var25)
            {
                var25.printStackTrace();
            }

            var22 = new Packet250CustomPayload();
            var22.channel = "HSPacket";
            var22.data = var20.toByteArray();
            var22.length = var20.size();

            if (this.side == Side.SERVER)
            {
                RecipeHSBloodAltar.BloodAltarUsedBlock = false;
            }

            if (this.side == Side.CLIENT)
            {
                var23 = FMLClientHandler.instance().getClient().thePlayer;
                var23.sendQueue.addToSendQueue(var22);
            }

            this.BlockBloodAltered = false;
        }

        if (this.AugmentBloodAltered && this.inventory[0] != null)
        {
            this.inventory[0] = RecipeHSBloodAltar.bloodAltarItemStackAugmentBuilder(this.inventory[0]);

            if (this.side == Side.SERVER)
            {
                this.bloodAltarDrain(RecipeHSBloodAltar.BloodAltarUsedCost);
            }

            var1 = 2;
            var2 = false;
            var3 = false;
            var4 = false;
            var5 = 0;
            var6 = 0;
            var7 = 0;
            var8 = 0;
            var9 = 0;
            var10 = false;
            var11 = false;
            var12 = false;
            var13 = 0;
            var14 = 0;
            var15 = 0;
            var16 = 0;
            var17 = 0;
            var18 = false;
            var19 = 0;
            var20 = new ByteArrayOutputStream(8);
            var21 = new DataOutputStream(var20);

            try
            {
                var21.writeInt(var1);
                var21.writeBoolean(var2);
                var21.writeBoolean(var3);
                var21.writeBoolean(var4);
                var21.writeInt(var5);
                var21.writeInt(var6);
                var21.writeInt(var7);
                var21.writeInt(var8);
                var21.writeInt(var9);
                var21.writeBoolean(var10);
                var21.writeBoolean(var11);
                var21.writeBoolean(var12);
                var21.writeInt(var13);
                var21.writeInt(var14);
                var21.writeInt(var15);
                var21.writeInt(var16);
                var21.writeInt(var17);
                var21.writeBoolean(var18);
                var21.writeInt(var19);
            }
            catch (Exception var24)
            {
                var24.printStackTrace();
            }

            var22 = new Packet250CustomPayload();
            var22.channel = "HSPacket";
            var22.data = var20.toByteArray();
            var22.length = var20.size();

            if (this.side == Side.SERVER)
            {
                RecipeHSBloodAltar.BloodAltarUsedAugment = false;
            }

            if (this.side == Side.CLIENT)
            {
                var23 = FMLClientHandler.instance().getClient().thePlayer;
                var23.sendQueue.addToSendQueue(var22);
            }

            this.AugmentBloodAltered = false;
        }
    }

    public String func_94133_a()
    {
        return this.func_94135_b() ? this.field_94136_s : "container.enchant";
    }

    public boolean func_94135_b()
    {
        return this.field_94136_s != null && this.field_94136_s.length() > 0;
    }

    public void func_94134_a(String var1)
    {
        this.field_94136_s = var1;
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    public boolean isInvNameLocalized()
    {
        return false;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isStackValidForSlot(int var1, ItemStack var2)
    {
        return false;
    }
}
