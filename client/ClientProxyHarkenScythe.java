package mod_HarkenScythe.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import mod_HarkenScythe.common.CommonProxyHarkenScythe;
import mod_HarkenScythe.common.EntityHSHarbinger;
import mod_HarkenScythe.common.EntityHSSlime;
import mod_HarkenScythe.common.EntityHSSoul;
import mod_HarkenScythe.common.EntityHSSoulLost;
import mod_HarkenScythe.common.EntityHSSpectralBat;
import mod_HarkenScythe.common.EntityHSSpectralBlaze;
import mod_HarkenScythe.common.EntityHSSpectralCaveSpider;
import mod_HarkenScythe.common.EntityHSSpectralChicken;
import mod_HarkenScythe.common.EntityHSSpectralCow;
import mod_HarkenScythe.common.EntityHSSpectralCreeper;
import mod_HarkenScythe.common.EntityHSSpectralEnderman;
import mod_HarkenScythe.common.EntityHSSpectralGhast;
import mod_HarkenScythe.common.EntityHSSpectralMiner;
import mod_HarkenScythe.common.EntityHSSpectralMinerEvil;
import mod_HarkenScythe.common.EntityHSSpectralOcelot;
import mod_HarkenScythe.common.EntityHSSpectralPig;
import mod_HarkenScythe.common.EntityHSSpectralPigZombie;
import mod_HarkenScythe.common.EntityHSSpectralSheep;
import mod_HarkenScythe.common.EntityHSSpectralSilverfish;
import mod_HarkenScythe.common.EntityHSSpectralSkeleton;
import mod_HarkenScythe.common.EntityHSSpectralSkeletonWither;
import mod_HarkenScythe.common.EntityHSSpectralSpider;
import mod_HarkenScythe.common.EntityHSSpectralSquid;
import mod_HarkenScythe.common.EntityHSSpectralVillager;
import mod_HarkenScythe.common.EntityHSSpectralWitch;
import mod_HarkenScythe.common.EntityHSSpectralWolf;
import mod_HarkenScythe.common.EntityHSSpectralZombie;
import mod_HarkenScythe.common.TileEntityHSBloodAltar;
import mod_HarkenScythe.common.TileEntityHSSkull;
import mod_HarkenScythe.common.TileEntityHSSoulAltar;
import mod_HarkenScythe.common.mod_HarkenScythe;
import net.minecraft.client.model.ModelSlime;

public class ClientProxyHarkenScythe extends CommonProxyHarkenScythe
{
    public void registerRenderThings()
    {
        TickRegistry.registerTickHandler(new ClientTickHandlerHarkenScythe(), Side.CLIENT);
    }

    public int addArmor(String var1)
    {
        return RenderingRegistry.addNewArmourRendererPrefix(var1);
    }

    public void registerRenderInformation()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHSSoulAltar.class, new RenderHSSoulAltar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHSBloodAltar.class, new RenderHSBloodAltar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHSSkull.class, new RenderHSSkull());
        RenderingRegistry.registerBlockHandler(mod_HarkenScythe.HSCrucibleRenderID, new RenderHSCrucible());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSoul.class, new RenderHSSoul());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSoulLost.class, new RenderHSSoulLost());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSlime.class, new RenderHSSlime(new ModelSlime(1), new ModelSlime(0), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSHarbinger.class, new RenderHSHarbinger());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralMinerEvil.class, new RenderHSSpectralMiner(new ModelHSSpectralMiner(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralChicken.class, new RenderHSSpectralChicken(new ModelHSSpectralChicken(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralCow.class, new RenderHSSpectralCow(new ModelHSSpectralCow(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralSheep.class, new RenderHSSpectralSheep(new ModelHSSpectralSheep2(), new ModelHSSpectralSheep1(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralBat.class, new RenderHSSpectralBat());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralPig.class, new RenderHSSpectralPig(new ModelHSSpectralPig(), new ModelHSSpectralPig(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralWolf.class, new RenderHSSpectralWolf(new ModelHSSpectralWolf(), new ModelHSSpectralWolf(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralOcelot.class, new RenderHSSpectralOcelot(new ModelHSSpectralOcelot(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralSquid.class, new RenderHSSpectralSquid(new ModelHSSpectralSquid(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralVillager.class, new RenderHSSpectralVillager());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralCreeper.class, new RenderHSSpectralCreeper());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralZombie.class, new RenderHSSpectralZombie());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralPigZombie.class, new RenderHSSpectralBiped(new ModelHSSpectralZombie(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralSkeleton.class, new RenderHSSpectralSkeleton());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralSkeletonWither.class, new RenderHSSpectralSkeletonWither());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralWitch.class, new RenderHSSpectralWitch());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralSpider.class, new RenderHSSpectralSpider());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralCaveSpider.class, new RenderHSSpectralCaveSpider());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralSilverfish.class, new RenderHSSpectralSilverfish());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralEnderman.class, new RenderHSSpectralEnderman());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralBlaze.class, new RenderHSSpectralBlaze());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralGhast.class, new RenderHSSpectralGhast());
        RenderingRegistry.registerEntityRenderingHandler(EntityHSSpectralMiner.class, new RenderHSSpectralMiner(new ModelHSSpectralMiner(), 0.3F));
    }
}
