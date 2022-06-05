package atonkish.reinfbarrel.block.entity;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import atonkish.reinfcore.screen.ReinforcedStorageScreenHandler;
import atonkish.reinfcore.util.ReinforcingMaterial;

public class ReinforcedBarrelBlockEntity extends BarrelBlockEntity {
    private final ViewerCountManager stateManager;
    private final ReinforcingMaterial cachedMaterial;

    public ReinforcedBarrelBlockEntity(ReinforcingMaterial material, BlockPos pos, BlockState state) {
        super(pos, state);
        ((BlockEntityInterface) this).setType(ModBlockEntityType.REINFORCED_BARREL_MAP.get(material));
        this.setInvStackList(DefaultedList.ofSize(material.getSize(), ItemStack.EMPTY));
        this.stateManager = new ViewerCountManager() {
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                ReinforcedBarrelBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_BARREL_OPEN);
                ReinforcedBarrelBlockEntity.setOpen(world, pos, state, true);
            }

            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                ReinforcedBarrelBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_BARREL_CLOSE);
                ReinforcedBarrelBlockEntity.setOpen(world, pos, state, false);
            }

            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount,
                    int newViewerCount) {
            }

            protected boolean isPlayerViewing(PlayerEntity player) {
                if (player.currentScreenHandler instanceof ReinforcedStorageScreenHandler) {
                    Inventory inventory = ((ReinforcedStorageScreenHandler) player.currentScreenHandler).getInventory();
                    return inventory == ReinforcedBarrelBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
        this.cachedMaterial = material;
    }

    public int size() {
        return this.cachedMaterial.getSize();
    }

    protected Text getContainerName() {
        String namespace = BlockEntityType.getId(this.getType()).getNamespace();
        return Text.translatable("container." + namespace + "." + this.cachedMaterial.getName() + "Barrel");
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return ReinforcedStorageScreenHandler.createSingleBlockScreen(this.cachedMaterial, syncId, playerInventory,
                this);
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    private static void setOpen(World world, BlockPos pos, BlockState state, boolean open) {
        world.setBlockState(pos, state.with(BarrelBlock.OPEN, open), Block.NOTIFY_ALL);
    }

    private static void playSound(World world, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = ((Direction) state.get(BarrelBlock.FACING)).getVector();
        double d = (double) pos.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
        double e = (double) pos.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
        double f = (double) pos.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
        world.playSound((PlayerEntity) null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F,
                world.random.nextFloat() * 0.1F + 0.9F);
    }
}
