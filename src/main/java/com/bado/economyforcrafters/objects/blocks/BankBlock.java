package com.bado.economyforcrafters.objects.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraft.block.Block;

import java.util.stream.Stream;
public class BankBlock extends Block{


    /**
     * Block properties
     */
    public BankBlock() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(10,10)
                .sound(SoundType.STONE)
                .harvestTool(ToolType.PICKAXE));

        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }
    
    /**
     * Orientation Handling
     */
    
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    
    private static final VoxelShape SHAPE_S = Stream.of(
    		Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
    		Block.makeCuboidShape(0, 4, 10, 16, 14, 11),
    		Block.makeCuboidShape(7, 1, 11, 9, 10, 13),
    		Block.makeCuboidShape(1, 1, 2, 3, 2, 5),
    		Block.makeCuboidShape(5, 1, 1, 15, 2, 5)
    		).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    
    private static final VoxelShape SHAPE_W=Stream.of(
    		Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
    		Block.makeCuboidShape(5, 4, 0, 6, 14, 16),
    		Block.makeCuboidShape(3, 1, 7, 5, 10, 9),
    		Block.makeCuboidShape(11, 1, 1, 14, 2, 3),
    		Block.makeCuboidShape(11, 1, 5, 15, 2, 15)
    		).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    
    private static final VoxelShape SHAPE_N=Stream.of(
    		Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
    		Block.makeCuboidShape(0, 4, 5, 16, 14, 6),
    		Block.makeCuboidShape(7, 1, 3, 9, 10, 5),
    		Block.makeCuboidShape(13, 1, 11, 15, 2, 14),
    		Block.makeCuboidShape(1, 1, 11, 11, 2, 15)
    		).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    
    private static final VoxelShape SHAPE_E=Stream.of(
    		Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
    		Block.makeCuboidShape(10, 4, 0, 11, 14, 16),
    		Block.makeCuboidShape(11, 1, 7, 13, 10, 9),
    		Block.makeCuboidShape(2, 1, 13, 5, 2, 15),
    		Block.makeCuboidShape(1, 1, 1, 5, 2, 11)
    		).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(FACING)){
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context){
        return this.getDefaultState().with(FACING,context.getPlacementHorizontalFacing());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
