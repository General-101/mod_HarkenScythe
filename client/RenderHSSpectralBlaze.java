package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralBlaze;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralBlaze extends RenderLiving
{
    private int field_77068_a;

    public RenderHSSpectralBlaze()
    {
        super(new ModelHSSpectralBlaze(), 0.5F);
        this.field_77068_a = ((ModelHSSpectralBlaze)this.mainModel).func_78104_a();
    }

    public void renderBlaze(EntityHSSpectralBlaze var1, double var2, double var4, double var6, float var8, float var9)
    {
        int var10 = ((ModelHSSpectralBlaze)this.mainModel).func_78104_a();

        if (var10 != this.field_77068_a)
        {
            this.field_77068_a = var10;
            this.mainModel = new ModelBlaze();
        }

        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderBlaze((EntityHSSpectralBlaze)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderBlaze((EntityHSSpectralBlaze)var1, var2, var4, var6, var8, var9);
    }
}
