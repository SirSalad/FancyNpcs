package de.oliver.fancynpcs.commands;

import de.oliver.fancynpcs.api.Npc;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Subcommand {

    List<String> tabcompletion(@NotNull Player player, @NotNull String[] args);

    boolean run(@NotNull Player player, @Nullable Npc npc, @NotNull String[] args);

}
