package miku.protector.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.MixinService;

import javax.annotation.Nullable;
import java.util.Map;

public class CoreBooter implements IFMLLoadingPlugin {
    public CoreBooter(){
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.protector.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        MixinService.getService().getTransformerProvider().addTransformerExclusion("com.shinoow.btg.common.rituals.AzathothInvocationRitual");
        return new String[]{"miku.protector.core.Transformer.ByTheGodsTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
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
