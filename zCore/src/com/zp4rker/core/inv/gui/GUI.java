package com.zp4rker.core.inv.gui;

import com.zp4rker.core.inv.listener.InvClickListener;
import com.zp4rker.core.inv.listener.InvCloseListener;
import com.zp4rker.core.inv.listener.InvOpenListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map.Entry;

public class GUI {

    private String name;
    private List<HashMap<Map<String, Object>, Map<String, Object>>> items;
    private int size = 0;
    private String title;

    public GUI(String name, Collection<ItemStack> itemStacks, int size) {

        this.name = name;
        items = serializeItemStacks(itemStacks);
        this.size = size;
        this.title = name;

    }

    public GUI(String name, Collection<ItemStack> itemStacks, int size, String title) {

        this.name = name;
        items = serializeItemStacks(itemStacks);
        this.size = size;
        this.title = title;

    }

    private Map<String, Object> serializeItem(ItemStack itemStack) {

        itemStack.setItemMeta(null);

        return itemStack.serialize();

    }

    private Map<String, Object> serializeMeta(ItemStack itemStack) {

        return (itemStack.hasItemMeta() ? itemStack.getItemMeta().serialize() : null);

    }

    private HashMap<Map<String, Object>, Map<String, Object>> seralizeItemStack(ItemStack itemStack) {

        HashMap<Map<String, Object>, Map<String, Object>> map = new HashMap<>();

        map.put(serializeItem(itemStack), serializeMeta(itemStack));

        return map;

    }

    private ItemStack deserializeItemStack(HashMap<Map<String, Object>, Map<String, Object>> map) {

        Entry<Map<String, Object>, Map<String, Object>> entry = map.entrySet().iterator().next();

        ItemStack itemStack = ItemStack.deserialize(entry.getKey());

        if (entry.getValue() != null) {
            ItemMeta itemMeta = (ItemMeta) ConfigurationSerialization.deserializeObject(entry.getValue(),
                    ConfigurationSerialization.getClassByAlias("ItemMeta"));
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;

    }

    private List<HashMap<Map<String, Object>, Map<String, Object>>> serializeItemStacks(Collection<ItemStack> itemStacks) {

        List<HashMap<Map<String, Object>, Map<String, Object>>> list = new ArrayList<>();

        for (ItemStack itemStack : itemStacks) {

            list.add(seralizeItemStack(itemStack));

        }

        return list;

    }

    private List<ItemStack> deserializeItemStacks(List<HashMap<Map<String, Object>, Map<String, Object>>> items) {

        List<ItemStack> list = new ArrayList<>();

        for (HashMap<Map<String, Object>, Map<String, Object>> itemHash : items) {

            list.add(deserializeItemStack(itemHash));

        }

        return list;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public List<ItemStack> getContents() {

        return deserializeItemStacks(items);

    }

    public void setContents(Collection<ItemStack> itemStacks) {

        this.items = serializeItemStacks(itemStacks);

    }

    public int getSize() {

        return size;

    }

    public void setSize(int size) {

        this.size = size;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Inventory getInventory() {

        Inventory gui = Bukkit.createInventory(null, getSize(), getTitle());
        gui.setContents((ItemStack[]) getContents().toArray());

        return gui;

    }

    public void openGUI(Player player) {

        player.openInventory(getInventory());

    }

    public void register(GUIRunnable runnable) {

        InvClickListener.guis.put(this, runnable);

    }

    public void onOpen(GUIRunnable runnable) {

        InvOpenListener.guis.put(this, runnable);

    }

    public void onClose(GUIRunnable runnable) {

        InvCloseListener.guis.put(this, runnable);

    }

}