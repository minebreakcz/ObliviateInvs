package mc.obliviate.inventory;

import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.NBTTagList;
import net.minecraft.server.v1_16_R2.NBTTagString;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Nbt {

    private static void setNbt_String_(@NotNull NBTTagCompound nbt, @NotNull String key, String value) {
        int index = key.indexOf('.');
        if (index == -1) {
            nbt.setString(key, value);
            return;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        NBTTagCompound child = nbt.getCompound(k);
        if (child == null)
            child = new NBTTagCompound();

        setNbt_String_(child, subKey, value);
        nbt.set(k, child);
    }

    private static void setNbt_StringArray_(@NotNull NBTTagCompound nbt, @NotNull String key, @NotNull String[] value) {
        int index = key.indexOf('.');
        if (index == -1) {
            NBTTagList idsTag = new NBTTagList();
            for (String v : value)
                idsTag.add(NBTTagString.a(v));

            nbt.set(key, idsTag);
            return;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        NBTTagCompound child = nbt.getCompound(k);
        if (child == null)
            child = new NBTTagCompound();

        setNbt_StringArray_(child, subKey, value);
        nbt.set(k, child);
    }

    public static ItemStack setNbt_String(@NotNull ItemStack is, @NotNull String key, String value) {
        net.minecraft.server.v1_16_R2.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound == null)
                compound = new NBTTagCompound();
            {
                setNbt_String_(compound, key, value);
            }
            nms.setTag(compound);
        }
        return CraftItemStack.asBukkitCopy(nms);
    }

    public static ItemStack setNbt_StringArray(@NotNull ItemStack is, @NotNull String key, @NotNull String[] value) {
        net.minecraft.server.v1_16_R2.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound == null)
                compound = new NBTTagCompound();
            {
                setNbt_StringArray_(compound, key, value);
            }
            nms.setTag(compound);
        }
        return CraftItemStack.asBukkitCopy(nms);
    }

    private static String getNbt_String_(@NotNull NBTTagCompound nbt, @NotNull String key, String default_) {
        int index = key.indexOf('.');
        if (index == -1)
            return nbt.getString(key);

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        if (nbt.hasKey(k))
            return getNbt_String_(nbt.getCompound(k), subKey, default_);
        else
            return default_;
    }

    public static boolean hasKey(@NotNull ItemStack is, @NotNull String key) {
        net.minecraft.server.v1_16_R2.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound != null)
                return compound.hasKey(key);
        }

        return false;
    }

    public static String getNbt_String(@NotNull ItemStack is, @NotNull String key, String default_) {
        net.minecraft.server.v1_16_R2.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound != null)
                return getNbt_String_(compound, key, default_);
        }

        return default_;
    }

    public static String getNbt_String(@NotNull ItemStack is, @NotNull String key) {
        return getNbt_String(is, key, null);
    }

}
