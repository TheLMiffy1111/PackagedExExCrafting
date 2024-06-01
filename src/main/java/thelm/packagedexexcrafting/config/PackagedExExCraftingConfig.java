package thelm.packagedexexcrafting.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import thelm.packagedexexcrafting.block.entity.EpicCrafterBlockEntity;

public class PackagedExExCraftingConfig {

	private PackagedExExCraftingConfig() {}

	private static ForgeConfigSpec serverSpec;

	public static ForgeConfigSpec.IntValue epicCrafterEnergyCapacity;
	public static ForgeConfigSpec.IntValue epicCrafterEnergyReq;
	public static ForgeConfigSpec.IntValue epicCrafterEnergyUsage;
	public static ForgeConfigSpec.BooleanValue epicCrafterDrawMEEnergy;

	public static void registerConfig() {
		buildConfig();
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverSpec);
	}

	private static void buildConfig() {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		builder.push("epic_crafter");
		builder.comment("How much FE the Epic Package Crafter should hold.");
		epicCrafterEnergyCapacity = builder.defineInRange("energy_capacity", 10000, 0, Integer.MAX_VALUE);
		builder.comment("How much total FE the Epic Package Crafter should use per operation.");
		epicCrafterEnergyReq = builder.defineInRange("energy_req", 10000, 0, Integer.MAX_VALUE);
		builder.comment("How much FE/t maximum the Epic Package Crafter can use.");
		epicCrafterEnergyUsage = builder.defineInRange("energy_usage", 1000, 0, Integer.MAX_VALUE);
		builder.comment("Should the Epic Package Crafter draw energy from ME systems.");
		epicCrafterDrawMEEnergy = builder.define("draw_me_energy", true);
		builder.pop();

		serverSpec = builder.build();
	}

	public static void reloadServerConfig() {
		EpicCrafterBlockEntity.energyCapacity = epicCrafterEnergyCapacity.get();
		EpicCrafterBlockEntity.energyReq = epicCrafterEnergyReq.get();
		EpicCrafterBlockEntity.energyUsage = epicCrafterEnergyUsage.get();
		EpicCrafterBlockEntity.drawMEEnergy = epicCrafterDrawMEEnergy.get();
	}
}
