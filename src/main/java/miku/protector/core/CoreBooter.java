package miku.protector.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.MixinService;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("protector")
public class CoreBooter implements IFMLLoadingPlugin {
    public CoreBooter(){
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.protector.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        MixinService.getService().getTransformerProvider().addTransformerExclusion("com.shinoow.btg.common.rituals.AzathothInvocationRitual");
        MixinService.getService().getTransformerProvider().addTransformerExclusion("com.shinoow.btg.common.util.PlayerKillUtil");
        MixinService.getService().getTransformerProvider().addTransformerExclusion("com.brandon3055.draconicevolution.lib.ExplosionHelper");
        MixinService.getService().getTransformerProvider().addTransformerExclusion("net.mcreator.stemweapon.procedure.ProcedureGoodbyeworldRedstoneOn");
        MixinService.getService().getTransformerProvider().addTransformerExclusion("com.brandon3055.draconicevolution.entity.ProcessChaosImplosion");
        MixinService.getService().getTransformerProvider().addTransformerExclusion("com.brandon3055.draconicevolution.entity.ProcessChaosImplosion.ChaosImplosionTrace");
        MixinService.getService().getTransformerProvider().addTransformerExclusion("com.github.alexthe666.iceandfire.entity.EntityGorgon");
        MixinService.getService().getTransformerProvider().addTransformerExclusion("com.github.alexthe666.iceandfire.item.ItemGorgonHead");
        return new String[]{
                "miku.protector.core.Transformer.ByTheGodsTransformer",
                "miku.protector.core.Transformer.DragonTransformer",
                "miku.protector.core.Transformer.StemWeaponTransformer",
                "miku.protector.core.Transformer.IceAndFireTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "miku.protector.core.Container";
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
