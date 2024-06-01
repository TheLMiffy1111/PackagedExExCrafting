package thelm.packagedexexcrafting.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import thelm.packagedauto.block.BaseBlock;
import thelm.packagedauto.block.entity.BaseBlockEntity;
import thelm.packagedexexcrafting.block.entity.EpicCrafterBlockEntity;

public class EpicCrafterBlock extends BaseBlock {

	public static final EpicCrafterBlock INSTANCE = new EpicCrafterBlock();
	public static final Item ITEM_INSTANCE = new BlockItem(INSTANCE, new Item.Properties());

	public EpicCrafterBlock() {
		super(BlockBehaviour.Properties.of().strength(15F, 25F).mapColor(MapColor.METAL).sound(SoundType.METAL));
	}

	@Override
	public EpicCrafterBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return EpicCrafterBlockEntity.TYPE_INSTANCE.create(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return BaseBlockEntity::tick;
	}
}
