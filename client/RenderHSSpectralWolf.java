package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralWolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralWolf extends RenderLiving
{
    public RenderHSSpectralWolf(ModelBase var1, ModelBase var2, float var3)
    {
        super(var1, var3);
        this.setRenderPassModel(var2);
    }

    protected float getTailRotation(EntityHSSpectralWolf var1, float var2)
    {
        return var1.getTailRotation();
    }

    protected int func_82447_a(EntityHSSpectralWolf var1, int var2, float var3)
    {
        float var4;

        if (var2 == 0 && var1.getWolfShaking())
        {
            var4 = var1.getBrightness(var3) * var1.getShadingWhileShaking(var3);
            this.loadTexture(var1.getTexture());
            GL11.glColor3f(var4, var4, var4);
            return 1;
        }
        else if (var2 == 1 && var1.isTamed())
        {
            this.loadTexture("/mob/wolf_collar.png");
            var4 = 1.0F;
            int var5 = var1.getCollarColor();
            GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.func_82447_a((EntityHSSpectralWolf)var1, var2, var3);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLiving var1, float var2)
    {
        return this.getTailRotation((EntityHSSpectralWolf)var1, var2);
    }
}
