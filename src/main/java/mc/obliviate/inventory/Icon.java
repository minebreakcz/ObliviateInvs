package mc.obliviate.inventory;

import mc.obliviate.inventory.action.DragAction;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import mc.obliviate.inventory.action.ClickAction;

import java.util.*;

public class Icon {

	private final ItemStack item;
	private ClickAction clickAction;
	private DragAction dragAction;

	public Icon(final ItemStack item) {
		this.item = item;
		this.dragAction = event -> {
		};
		this.clickAction = event -> {
		};
	}

	public Icon(final Material material) {
		this.clickAction = event -> {
		};
		this.dragAction = event -> {
		};
		this.item = new ItemStack(material);
	}

	public Icon setDamage(final short dmg) {
		item.setDurability(dmg);
		return this;
	}

	public Icon setDamage(final int dmg) {
		setDamage((short) dmg);
		return this;
	}

	public Icon setName(final BaseComponent name) {
		final ItemMeta meta = item.getItemMeta();
		if (meta == null) return this;
		meta.setDisplayName(ComponentSerializer.toString(name));
		item.setItemMeta(meta);
		return this;
	}

	public Icon setName(final String name) {
		final ItemMeta meta = item.getItemMeta();
		if (meta == null) return this;
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}

	public Icon setLore(final List<BaseComponent> lore) {
		final ItemMeta meta = item.getItemMeta();
		if (meta == null) return this;
		meta.setLore(Collections.singletonList(ComponentSerializer.toString(lore)));
		item.setItemMeta(meta);
		return this;
	}

	public Icon setLore(final BaseComponent... lore) {
		return setLore(new ArrayList<>(Arrays.asList(lore)));
	}

	public Icon appendLore(final List<BaseComponent> strings) {
		final ItemMeta meta = item.getItemMeta();
		if (meta == null) return this;
		List<String> lore = meta.getLore();
		if (lore != null) lore.add(ComponentSerializer.toString(strings));
		else lore = Collections.singletonList(ComponentSerializer.toString(strings));
		return setLore((BaseComponent) lore);
	}

	public Icon appendLore(final BaseComponent... strings) {
		return appendLore(new ArrayList<>(Arrays.asList(strings)));
	}

	public Icon insertLore(final int index, final BaseComponent... strings) {
		return insertLore(index, new ArrayList<BaseComponent>(Arrays.asList(strings)));
	}

	public Icon insertLore(final int index, final List<BaseComponent> strings) {
		final ItemMeta meta = item.getItemMeta();
		if (meta == null) return this;
		List<String> lore = meta.getLore();
		if (lore != null) lore.addAll(index, Collections.singleton(ComponentSerializer.toString(strings)));
		else lore = Collections.singletonList(ComponentSerializer.toString(strings));
		return setLore((BaseComponent) lore);
	}

	public Icon setAmount(final int amount) {
		item.setAmount(amount);
		return this;
	}

	public Icon hideFlags(final ItemFlag itemFlag) {
		final ItemMeta meta = item.getItemMeta();
		if (meta == null) return this;
		meta.addItemFlags(itemFlag);
		item.setItemMeta(meta);
		return this;
	}

	public Icon hideFlags() {
		hideFlags(ItemFlag.HIDE_ATTRIBUTES)
				.hideFlags(ItemFlag.HIDE_DESTROYS)
				.hideFlags(ItemFlag.HIDE_ENCHANTS)
				.hideFlags(ItemFlag.HIDE_PLACED_ON)
				.hideFlags(ItemFlag.HIDE_POTION_EFFECTS)
				.hideFlags(ItemFlag.HIDE_UNBREAKABLE);
		//hideFlags(DYE) not exists because 1.8 servers does not support.
		return this;
	}

	public Icon enchant(final Enchantment enchantment) {
		return enchant(enchantment, enchantment.getStartLevel());
	}

	public Icon enchant(final Map<Enchantment, Integer> enchantments) {
		for (Map.Entry<Enchantment, Integer> enchant : enchantments.entrySet()) {
			enchant(enchant.getKey(), enchant.getValue());
		}
		return this;
	}

	public Icon enchant(final Enchantment enchantment, final int value) {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();

			if (meta == null) return this;

			meta.addStoredEnchant(enchantment, value, true);

			item.setItemMeta(meta);
		} else {
			item.addUnsafeEnchantment(enchantment, value);
		}
		return this;
	}


	public ClickAction getClickAction() {
		return clickAction;
	}

	public Icon onClick(ClickAction clickAction) {
		this.clickAction = clickAction;
		return this;
	}

	public DragAction getDragAction() {
		return dragAction;

	}

	public Icon onDrag(DragAction dragAction) {
		this.dragAction = dragAction;
		return this;
	}

	public ItemStack getItem() {
		return item;
	}

}

