package commands;

import core.PluginCore;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class Money implements CommandExecutor {
    private PluginCore core;

    public Money(PluginCore core) {
        this.core = core;
    }

    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        commandSource.sendMessage(Text.of("/money <info|create|add|delete>"));

        return CommandResult.success();
    }
}