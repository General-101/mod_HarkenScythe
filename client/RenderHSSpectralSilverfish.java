package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralSilverfish extends RenderLiving
{
    public RenderHSSpectralSilverfish()
    {
        super(new ModelHSSpectralSilverfish(), 0.3F);
    }

    protected float getSilverfishDeathRotation(EntityHSSpectralSilverfish var1)
    {
        return 180.0F;
    }

    public void renderSilverfish(EntityHSSpectralSilverfish var1, double var2, double var4, double var6, float var8, float var9)
    {
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    protected int shouldSilverfishRenderPass(EntityHSSpectralSilverfish var1, int var2, float var3)
    {
        return -1;
    }

    protected float getDeathMaxRotation(EntityLiving var1)
    {
        return this.getSilverfishDeathRotation((EntityHSSpectralSilverfish)var1);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.shouldSilverfishRenderPass((EntityHSSpectralSilverfish)var1, var2, var3);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderSilverfish((EntityHSSpectralSilverfish)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderSilverfish((EntityHSSpectralSilverfish)var1, var2, var4, var6, var8, var9);
    }
}
