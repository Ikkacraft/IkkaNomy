package commands;

import core.PluginCore;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import java.util.UUID;

public class Money implements CommandExecutor {
    private PluginCore core;

    public Money(PluginCore core) {
        this.core = core;
    }

    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        // Récupérer l'uuid du joueur
        String cause = commandSource.toString();
        String playerName = getPlayerFromCause(cause);
        UUID playerUUID = core.getGame().getServer().getPlayer(playerName).get().getUniqueId();

        // récupérer l'argent disponible sur le compte
        int balance = core.getAccountBalanceByUUID(playerUUID.toString());
        if(balance != -1)
            core.getGame().getServer().getPlayer(playerUUID).get().sendMessage(Text.of("Vous possédez " + balance));
        else
            core.getGame().getServer().getPlayer(playerUUID).get().sendMessage(Text.of("Désolé, ce compte n'existe pas"));

        core.broadcastText(commandContext.toString());
        core.broadcastText(commandSource.toString());
        return CommandResult.success();
    }

    // Récupère le nom du joueur grâce à la cause de l'évènement générée par Sponge
    public String getPlayerFromCause(String cause) {
        int pos1 = cause.indexOf("['");
        int pos2 = cause.indexOf("'/");
        return cause.substring(pos1 + 2, pos2);
    }
}