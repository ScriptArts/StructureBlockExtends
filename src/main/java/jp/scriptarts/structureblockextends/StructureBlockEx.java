package jp.scriptarts.structureblockextends;

import net.minecraft.block.*;

import javax.annotation.Nullable;

import net.minecraft.block.StructureBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class StructureBlockEx extends StructureBlock {
    public static final EnumProperty<StructureMode> MODE = BlockStateProperties.STRUCTURE_BLOCK_MODE;

    public StructureBlockEx(AbstractBlock.Properties properties) {
        super(properties);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new StructureBlockExTileEntity();
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof StructureBlockExTileEntity) {
            return ((StructureBlockExTileEntity)tileentity).usedBy(player) ? ActionResultType.func_233537_a_(worldIn.isRemote) : ActionResultType.PASS;
        } else {
            return ActionResultType.PASS;
        }
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            if (placer != null) {
                TileEntity tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof StructureBlockExTileEntity) {
                    ((StructureBlockExTileEntity)tileentity).createdBy(placer);
                }
            }

        }
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(MODE, StructureMode.DATA);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof StructureBlockExTileEntity) {
                StructureBlockExTileEntity structureblocktileentity = (StructureBlockExTileEntity)tileentity;
                boolean flag = worldIn.isBlockPowered(pos);
                boolean flag1 = structureblocktileentity.isPowered();
                if (flag && !flag1) {
                    structureblocktileentity.setPowered(true);
                    this.trigger(structureblocktileentity);
                } else if (!flag && flag1) {
                    structureblocktileentity.setPowered(false);
                }

            }
        }
    }

    private void trigger(StructureBlockExTileEntity structureIn) {
        switch(structureIn.getMode()) {
            case SAVE:
                structureIn.save(false);
                break;
            case LOAD:
                structureIn.load(false);
                break;
            case CORNER:
                structureIn.unloadStructure();
            case DATA:
        }

    }
}
