package dev.mattrm.mc.gametools.settings;

import dev.mattrm.mc.gametools.util.ISB;
import dev.mattrm.mc.gametools.util.ItemStacks;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GameSettingsMenu implements InventoryProvider {
    public static void openMenu(Player player, boolean canEdit) {
        buildInventory(canEdit).open(player);
    }

    private static SmartInventory buildInventory(boolean canEdit) {
        return SmartInventory.builder()
            .provider(new GameSettingsMenu(canEdit))
            .id("gamemanager")
            .size(4, 9)
            .title("Game Settings")
            .build();
    }

    private static final int EXTRA_ROWS = 0;
    private static final SlotPos CLOSE_SLOT = SlotPos.of(0, 8);
    private static final SlotPos PREV_PAGE_SLOT = SlotPos.of(3 + EXTRA_ROWS, 0);
    private static final SlotPos NEXT_PAGE_SLOT = SlotPos.of(3 + EXTRA_ROWS, 8);
    private static final SlotPos DISPLAYS_SLOT = SlotPos.of(1, 1);
    private static final SlotPos SETTINGS_SLOT = SlotPos.of(2, 1);
    private static final int ITEMS_PER_PAGE = 7;

    private final boolean canEdit;
    private final List<IGameSetting<?>> settings;

    private GameSettingsMenu(boolean canEdit) {
        this.canEdit = canEdit;
        this.settings = GameSettingsService.getInstance().getAllSettings();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        // Close button
        ItemStack barrierStack = ISB.material(Material.BARRIER).name("Close").build();
        contents.set(CLOSE_SLOT, ClickableItem.of(barrierStack, event -> player.closeInventory()));

        // Pagination
        Pagination pagination = contents.pagination();
        pagination.setItems(this.settings.stream().map(setting -> ClickableItem.empty(setting.getDisplayItem())).toArray(ClickableItem[]::new));
        pagination.setItemsPerPage(ITEMS_PER_PAGE);

        this.updateSettingsItems(contents);
        this.updatePaginationControls(contents);
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        this.updatePaginationControls(contents);
    }

    private void updateSettingsItems(InventoryContents contents) {
        // Clear current
        contents.fillRow(1, null);
        contents.fillRow(2, null);

        // Header
        Pagination pagination = contents.pagination();
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, DISPLAYS_SLOT).allowOverride(true));
        int page = pagination.getPage();

        // Values
        SlotIterator iter = contents.newIterator(SlotIterator.Type.HORIZONTAL, SETTINGS_SLOT).allowOverride(true);
        this.settings.stream()
            .skip((long) page * ITEMS_PER_PAGE)
            .limit(ITEMS_PER_PAGE)
            .forEach(setting -> {
                iter.next();
                iter.set(ClickableItem.of(setting.getCurrentValueItem(), event -> {
                    if (!this.canEdit) {
                        return;
                    }

                    if (event.isRightClick()) {
                        setting.toggleValueBackwards();
                    } else {
                        setting.toggleValueForwards();
                    }

                    this.updateSettingsItems(contents);
                }));
            });
    }

    private void updatePaginationControls(InventoryContents contents) {
        Pagination pagination = contents.pagination();

        // Previous page
        if (!pagination.isFirst()) {
            ItemStack stack = ISB.material(Material.ARROW).name("Previous page").build();
            contents.set(PREV_PAGE_SLOT, ClickableItem.of(stack, (InventoryClickEvent event) -> {
                pagination.previous();
                this.updateSettingsItems(contents);
            }));
        } else {
            contents.set(PREV_PAGE_SLOT, null);
        }

        // Next page
        if (!pagination.isLast()) {
            ItemStack stack = ISB.material(Material.ARROW).name("Next page").build();
            contents.set(NEXT_PAGE_SLOT, ClickableItem.of(stack, (InventoryClickEvent event) -> {
                pagination.next();
                this.updateSettingsItems(contents);
            }));
        } else {
            contents.set(NEXT_PAGE_SLOT, null);
        }
    }
}
