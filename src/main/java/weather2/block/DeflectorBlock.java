package weather2.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import weather2.WeatherBlocks;
import weather2.blockentity.DeflectorBlockEntity;

public class DeflectorBlock extends BaseEntityBlock {

    public static final MapCodec<DeflectorBlock> CODEC = simpleCodec(DeflectorBlock::new);

    public DeflectorBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new DeflectorBlockEntity(p_153215_, p_153216_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return createTickerHelper(p_153214_, WeatherBlocks.BLOCK_ENTITY_DEFLECTOR.get(), DeflectorBlockEntity::tickHelper);
    }

    @Override
    public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        //System.out.println("removed deflector block");
        if (level.getBlockEntity(pos) instanceof DeflectorBlockEntity deflectorBlockEntity) {
            deflectorBlockEntity.blockBroken();
        }
        super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
    }
}
