package net.robert.soulland.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.robert.soulland.helper.MathHelper;
import net.robert.soulland.item.ModItems;
import net.robert.soulland.item.custom.AlFurnaceFuel;
import net.robert.soulland.item.custom.pills.AbstractPillItem;
import net.robert.soulland.recipe.AlFurnaceRecipe;
import net.robert.soulland.screen.AlchemyFurnaceMenu;
import net.robert.soulland.util.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlchemyFurnaceBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6);

    public static final int PILL_RECIPE_SLOT = 0;
    public static final int INPUT_SLOT_1 = 1;
    public static final int INPUT_SLOT_2 = 2;
    public static final int INPUT_SLOT_3 = 3;
    public static final int FUEL_SLOT = 4;
    public static final int OUTPUT_SLOT = 5;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;
    private int fuelLeft = 0;
    private int fuelAdded = 0;

    public AlchemyFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALCHEMY_FURNACE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AlchemyFurnaceBlockEntity.this.progress;
                    case 1 -> AlchemyFurnaceBlockEntity.this.maxProgress;
                    case 2 -> AlchemyFurnaceBlockEntity.this.fuelLeft;
                    case 3 -> AlchemyFurnaceBlockEntity.this.fuelAdded;
                    default -> -1;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> AlchemyFurnaceBlockEntity.this.progress = pValue;
                    case 1 -> AlchemyFurnaceBlockEntity.this.maxProgress = pValue;
                    case 2 -> AlchemyFurnaceBlockEntity.this.fuelLeft = pValue;
                    case 3 -> AlchemyFurnaceBlockEntity.this.fuelAdded = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) return lazyItemHandler.cast();
            else return LazyOptional.empty();   // Avoid Hopper interactions.
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.soulland.alchemy_furnace");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AlchemyFurnaceMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("alchemy_furnace.progress", progress);
        pTag.putInt("alchemy_furnace.max_progress", maxProgress);
        pTag.putInt("alchemy_furnace.fuel_left", fuelLeft);
        pTag.putInt("alchemy_furnace.fuel_added", fuelAdded);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("alchemy_furnace.progress");
        maxProgress = pTag.getInt("alchemy_furnace.max_progress");
        fuelLeft = pTag.getInt("alchemy_furnace.fuel_left");
        fuelAdded = pTag.getInt("alchemy_furnace.fuel_added");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean hasRecipe = hasRecipe(), hasFuel = hasFuel(pLevel, pPos, pState);
        if (hasRecipe && hasFuel) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
        decreaseFuel();
//        System.out.printf("\rHas Recipe: %b.\tHas Fuel: %b", hasRecipe, hasFuel);
    }

    private void decreaseFuel() {
        if (fuelLeft > 0) fuelLeft --;
    }

    private void resetProgress() {
        progress = 0;
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private void craftItem() {
        Optional<AlFurnaceRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        this.itemHandler.extractItem(INPUT_SLOT_1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_2, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_3, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasFuel(Level pLevel, BlockPos pPos, BlockState pState) {
        ItemStack fuelStack = this.itemHandler.getStackInSlot(FUEL_SLOT);
        if (fuelLeft > 0) {
            return true;
        } else {
            if (fuelStack.isEmpty() || !(fuelStack.is(ModTags.Items.AL_FURNACE_FUEL)) || !hasRecipe()) {
                return false;
            } else {
                decreaseFuelInSlot(fuelStack);
                setChanged(pLevel, pPos, pState);
                return true;
            }
        }
    }

    private void decreaseFuelInSlot(ItemStack fuelStack) {
        this.itemHandler.extractItem(FUEL_SLOT, 1, false);
        fuelAdded = ((AlFurnaceFuel) fuelStack.getItem()).burnAbility;
        fuelLeft = fuelAdded;
    }

    private boolean hasRecipe() {
        Optional<AlFurnaceRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        maxProgress = MathHelper.getAFMaxProgress(((AbstractPillItem) result.getItem()).getLevel());
        return canInsertItemIntoOutputSlot(result);
        // TODO Add loot. Drop under any pickaxe.
        // TODO Add JEI compatibility
    }

    private Optional<AlFurnaceRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        return this.level.getRecipeManager().getRecipeFor(AlFurnaceRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack result) {
        return (this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(result.getItem()))
                && this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount() <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
}
