package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralPig;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralPig extends RenderLiving
{
    public RenderHSSpectralPig(ModelBase var1, ModelBase var2, float var3)
    {
        super(var1, var3);
        this.setRenderPassModel(var2);
    }

    protected int renderSaddledPig(EntityHSSpectralPig var1, int var2, float var3)
    {
        if (var2 == 0 && var1.getSaddled())
        {
            this.loadTexture("/mob/saddle.png");
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public void renderLivingPig(EntityHSSpectralPig var1, double var2, double var4, double var6, float var8, float var9)
    {
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.renderSaddledPig((EntityHSSpectralPig)var1, var2, var3);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderLivingPig((EntityHSSpectralPig)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderLivingPig((EntityHSSpectralPig)var1, var2, var4, var6, var8, var9);
    }
}
