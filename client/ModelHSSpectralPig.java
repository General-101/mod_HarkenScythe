package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHSSpectralPig extends ModelHSSpectralQuadruped
{
    public ModelHSSpectralPig()
    {
        this(0.0F);
    }

    public ModelHSSpectralPig(float var1)
    {
        super(6, var1);
        this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, var1);
        this.field_78145_g = 4.0F;
    }
}
