package net.barnacle.blubbertiles.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallOrFloorPlantBlock extends Block implements BonemealableBlock {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    protected static final VoxelShape FLOOR_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    protected static final VoxelShape WALL_NORTH_SHAPE = Block.box(2.0D, 2.0D, 8.0D, 14.0D, 14.0D, 16.0D);
    protected static final VoxelShape WALL_SOUTH_SHAPE = Block.box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 8.0D);
    protected static final VoxelShape WALL_WEST_SHAPE = Block.box(8.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D);
    protected static final VoxelShape WALL_EAST_SHAPE = Block.box(0.0D, 2.0D, 2.0D, 8.0D, 14.0D, 14.0D);

    public WallOrFloorPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);

        return switch (direction) {
            case UP -> FLOOR_SHAPE;
            case NORTH -> WALL_NORTH_SHAPE;
            case SOUTH -> WALL_SOUTH_SHAPE;
            case WEST -> WALL_WEST_SHAPE;
            case EAST -> WALL_EAST_SHAPE;
            default -> FLOOR_SHAPE;
        };
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos attachedPos = pos.relative(facing.getOpposite());
        BlockState attachedState = level.getBlockState(attachedPos);

        if (facing == Direction.UP) {
            return attachedState.is(Blocks.GRASS_BLOCK) || attachedState.is(Blocks.MYCELIUM) || attachedState.is(Blocks.DIRT) || attachedState.is(Blocks.ROOTED_DIRT) || attachedState.is(Blocks.COARSE_DIRT) || attachedState.is(Blocks.MOSS_BLOCK) || attachedState.is(Blocks.PALE_MOSS_BLOCK) || attachedState.is(Blocks.PODZOL);
        } else if (facing.getAxis().isHorizontal()) {
            return attachedState.isFaceSturdy(level, attachedPos, facing);
        }

        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();

        if (clickedFace == Direction.DOWN) {
            return null;
        }

        BlockState state = this.defaultBlockState().setValue(FACING, clickedFace);

        if (state.canSurvive(context.getLevel(), context.getClickedPos())) {
            return state;
        }

        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {

        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(levelReader, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos blockPos, BlockState state) {
        popResource(level, blockPos, new ItemStack(this));
    }
}