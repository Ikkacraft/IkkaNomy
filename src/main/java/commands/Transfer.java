package commands;

import core.PluginCore;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import java.util.UUID;

public class Transfer implements CommandExecutor {
    PluginCore core;

    public Transfer(PluginCore core) {
        this.core = core;
    }

    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        String cause = commandSource.toString();
        String worldName = cause.substring(cause.indexOf("l='")+3, cause.indexOf("',"));
        UUID world = core.getGame().getServer().getWorld(worldName).get().getUniqueId();
        commandSource.sendMessage(Text.of(world));
        return CommandResult.success();
    }
}
