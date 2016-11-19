package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntitySkull;

public class TileEntityHSSkull extends TileEntitySkull
{
    private int skullType;
    private int skullRotation;
    private String extraType = "";

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setByte("SkullType", (byte)(this.skullType & 255));
        var1.setByte("Rot", (byte)(this.skullRotation & 255));
        var1.setString("ExtraType", this.extraType);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.skullType = var1.getByte("SkullType");
        this.skullRotation = var1.getByte("Rot");

        if (var1.hasKey("ExtraType"))
        {
            this.extraType = var1.getString("ExtraType");
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 4, var1);
    }

    /**
     * Set the entity type for the skull
     */
    public void setSkullType(int var1, String var2)
    {
        this.skullType = var1;
        this.extraType = var2;
    }

    /**
     * Get the entity type for the skull
     */
    public int getSkullType()
    {
        return this.skullType;
    }

    /**
     * Set the skull's rotation
     */
    public void setSkullRotation(int var1)
    {
        this.skullRotation = var1;
    }

    @SideOnly(Side.CLIENT)
    public int func_82119_b()
    {
        return this.skullRotation;
    }

    /**
     * Get the extra data foor this skull, used as player username by player heads
     */
    public String getExtraType()
    {
        return this.extraType;
    }
}

