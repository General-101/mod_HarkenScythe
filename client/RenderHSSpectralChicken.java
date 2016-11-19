package mod_HarkenScythe.client;

import mod_HarkenScythe.common.EntityHSSpectralChicken;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class RenderHSSpectralChicken extends RenderLiving
{
    public RenderHSSpectralChicken(ModelBase var1, float var2)
    {
        super(var1, var2);
    }

    public void renderChicken(EntityHSSpectralChicken var1, double var2, double var4, double var6, float var8, float var9)
    {
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    protected float getWingRotation(EntityHSSpectralChicken var1, float var2)
    {
        float var3 = var1.field_70888_h + (var1.field_70886_e - var1.field_70888_h) * var2;
        float var4 = var1.field_70884_g + (var1.destPos - var1.field_70884_g) * var2;
        return (MathHelper.sin(var3) + 1.0F) * var4;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLiving var1, float var2)
    {
        return this.getWingRotation((EntityHSSpectralChicken)var1, var2);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderChicken((EntityHSSpectralChicken)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderChicken((EntityHSSpectralChicken)var1, var2, var4, var6, var8, var9);
    }
}
