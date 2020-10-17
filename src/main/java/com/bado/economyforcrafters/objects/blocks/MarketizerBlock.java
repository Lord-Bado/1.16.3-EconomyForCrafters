package com.bado.economyforcrafters.objects.blocks;

import java.util.stream.Stream;

import com.bado.economyforcrafters.init.EfcTileEntityTypes;
import com.bado.economyforcrafters.objects.tileentity.MarketizerTE;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class MarketizerBlock extends Block {

	/**
	 * Block properties
	 */
	public MarketizerBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(10, 10).sound(SoundType.STONE)
				.harvestTool(ToolType.PICKAXE));

		this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
	}

	/**
	 * Orientation Handling
	 */
	private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	private static final VoxelShape SHAPE_N = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 9, 16),
			Block.makeCuboidShape(0, 9, 3, 16, 16, 16), Block.makeCuboidShape(0, 14, 0, 16, 16, 3),
			Block.makeCuboidShape(0, 16, 0, 1, 32, 16), Block.makeCuboidShape(15, 16, 0, 16, 32, 16),
			Block.makeCuboidShape(1, 16, 14, 15, 32, 16), Block.makeCuboidShape(1, 31, 0, 15, 32, 14),
			Block.makeCuboidShape(6, 16, 7, 10, 18, 11), Block.makeCuboidShape(2, 18, 3, 14, 29, 15),
			Block.makeCuboidShape(2, 18, 2, 3, 29, 3), Block.makeCuboidShape(13, 18, 2, 14, 29, 3),
			Block.makeCuboidShape(3, 18, 2, 13, 19, 3), Block.makeCuboidShape(3, 28, 2, 13, 29, 3),
			Block.makeCuboidShape(5, 16, 0, 13, 17, 3), Block.makeCuboidShape(2, 16, 0, 4, 17, 3)

	).reduce((v1, v2) -> {
		return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
	}).get();

	private static final VoxelShape SHAPE_E = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 9, 16),
			Block.makeCuboidShape(3, 9, 0, 16, 16, 16), Block.makeCuboidShape(0, 14, 0, 3, 16, 16),
			Block.makeCuboidShape(0, 16, 15, 16, 32, 16), Block.makeCuboidShape(0, 16, 0, 16, 32, 1),
			Block.makeCuboidShape(14, 16, 1, 16, 32, 15), Block.makeCuboidShape(0, 31, 1, 14, 32, 15),
			Block.makeCuboidShape(7, 16, 6, 11, 18, 10), Block.makeCuboidShape(3, 18, 2, 15, 29, 14),
			Block.makeCuboidShape(2, 18, 13, 3, 29, 14), Block.makeCuboidShape(2, 18, 2, 3, 29, 3),
			Block.makeCuboidShape(2, 18, 3, 3, 19, 13), Block.makeCuboidShape(2, 28, 3, 3, 29, 13),
			Block.makeCuboidShape(0, 16, 3, 3, 17, 11), Block.makeCuboidShape(0, 16, 12, 3, 17, 14))
			.reduce((v1, v2) -> {
				return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
			}).get();

	private static final VoxelShape SHAPE_W = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 9, 16),
			Block.makeCuboidShape(0, 9, 0, 13, 16, 16), Block.makeCuboidShape(13, 14, 0, 16, 16, 16),
			Block.makeCuboidShape(0, 16, 0, 16, 32, 1), Block.makeCuboidShape(0, 16, 15, 16, 32, 16),
			Block.makeCuboidShape(0, 16, 1, 2, 32, 15), Block.makeCuboidShape(2, 31, 1, 16, 32, 15),
			Block.makeCuboidShape(5, 16, 6, 9, 18, 10), Block.makeCuboidShape(1, 18, 2, 13, 29, 14),
			Block.makeCuboidShape(13, 18, 2, 14, 29, 3), Block.makeCuboidShape(13, 18, 13, 14, 29, 14),
			Block.makeCuboidShape(13, 18, 3, 14, 19, 13), Block.makeCuboidShape(13, 28, 3, 14, 29, 13),
			Block.makeCuboidShape(13, 16, 5, 16, 17, 13), Block.makeCuboidShape(13, 16, 2, 16, 17, 4))
			.reduce((v1, v2) -> {
				return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
			}).get();

	private static final VoxelShape SHAPE_S = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 9, 16),
			Block.makeCuboidShape(0, 9, 0, 16, 16, 13), Block.makeCuboidShape(0, 14, 13, 16, 16, 16),
			Block.makeCuboidShape(15, 16, 0, 16, 32, 16), Block.makeCuboidShape(0, 16, 0, 1, 32, 16),
			Block.makeCuboidShape(1, 16, 0, 15, 32, 2), Block.makeCuboidShape(1, 31, 2, 15, 32, 16),
			Block.makeCuboidShape(6, 16, 5, 10, 18, 9), Block.makeCuboidShape(2, 18, 1, 14, 29, 13),
			Block.makeCuboidShape(13, 18, 13, 14, 29, 14), Block.makeCuboidShape(2, 18, 13, 3, 29, 14),
			Block.makeCuboidShape(3, 18, 13, 13, 19, 14), Block.makeCuboidShape(3, 28, 13, 13, 29, 14),
			Block.makeCuboidShape(3, 16, 13, 11, 17, 16), Block.makeCuboidShape(12, 16, 13, 14, 17, 16))
			.reduce((v1, v2) -> {
				return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
			}).get();

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(FACING)) {
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
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	/**
	 * Tiling handler/designator for the block
	 */

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return EfcTileEntityTypes.MARKETIZER.get().create();
	}

	/**
	 * Action handler
	 */

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult result) {
		if (!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof MarketizerTE) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (MarketizerTE) tile, pos);
				return ActionResultType.SUCCESS;
			}
		} else return ActionResultType.FAIL;
		return ActionResultType.FAIL;

	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof MarketizerTE) {
				InventoryHelper.dropItems(worldIn, pos, ((MarketizerTE) te).getItems());
				te.remove();
			}
		}
	}
	

}
