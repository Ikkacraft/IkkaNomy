package commands;

import core.PluginCore;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.UUID;

public class MoneyInfo implements CommandExecutor {
    private PluginCore core;

    public MoneyInfo(PluginCore core) {
        this.core = core;
    }

    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        String accountName;
        try {
            accountName = commandContext.getOne("accountName").get().toString();
        } catch (Exception e) {
            accountName = "";
        }
        String playerUUID = null;
        boolean flag_player = false;
        if(accountName.isEmpty()) {
            // Récupérer l'uuid du joueur
            Player player = (Player)commandSource;
            playerUUID = player.getUniqueId().toString();
            flag_player = true;
        } else playerUUID = accountName;
        // récupérer l'argent disponible sur le compte
        int balance = core.getAccountBalanceByUUID(playerUUID);
        if(balance != -1) {
            if(flag_player) {
                commandSource.sendMessage(Text.of("Vous possédez " + balance));
            } else commandSource.sendMessage(Text.of(accountName + " possède " + balance));
        } else
            commandSource.sendMessage(Text.of("Désolé, ce compte n'existe pas"));

        return CommandResult.success();
    }
}