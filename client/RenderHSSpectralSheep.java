package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralSheep;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralSheep extends RenderLiving
{
    public RenderHSSpectralSheep(ModelBase var1, ModelBase var2, float var3)
    {
        super(var1, var3);
        this.setRenderPassModel(var2);
    }

    protected int setWoolColorAndRender(EntityHSSpectralSheep var1, int var2, float var3)
    {
        if (var2 == 0 && !var1.getSheared())
        {
            this.loadTexture("/mods/mod_HarkenScythe/textures/models/mob/spectral_sheep_fur.png");
            float var4 = 1.0F;
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
        return this.setWoolColorAndRender((EntityHSSpectralSheep)var1, var2, var3);
    }
}
