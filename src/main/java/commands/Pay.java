package commands;

import core.PluginCore;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class Pay implements CommandExecutor {
    private PluginCore core;

    public Pay(PluginCore core) {
        this.core = core;
    }

    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        String cause = commandSource.toString();
        String account = commandContext.<String>getOne("account").get();
        int amount = commandContext.<Integer>getOne("amount").get();

        core.broadcastText("Vous avez effectué un virement de " + amount + " pour " + account);
        return CommandResult.success();
    }
}
