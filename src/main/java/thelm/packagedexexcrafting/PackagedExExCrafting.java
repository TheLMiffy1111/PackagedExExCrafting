package thelm.packagedexexcrafting;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import thelm.packagedexexcrafting.client.event.ClientEventHandler;
import thelm.packagedexexcrafting.event.CommonEventHandler;

@Mod(PackagedExExCrafting.MOD_ID)
public class PackagedExExCrafting {

	public static final String MOD_ID = "packagedexexcrafting";

	public PackagedExExCrafting() {
		CommonEventHandler.getInstance().onConstruct();
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
			ClientEventHandler.getInstance().onConstruct();
		});
	}
}
