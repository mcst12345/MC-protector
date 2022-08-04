package miku.protector.mixins;

import com.anotherstar.network.LoliDeadPacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = LoliDeadPacket.MessageHandler.class)
public class MixinLoliDeadPacket {

    /**
     * @author mcst12345
     * @reason Protect
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public IMessage onMessage(LoliDeadPacket message, MessageContext ctx){
        return null;
    }
}
