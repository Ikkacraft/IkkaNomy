package commands;

import core.PluginCore;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import webService.WebService;

import java.io.IOException;

public class MoneyAdd implements CommandExecutor {
    private PluginCore core;

    public MoneyAdd(PluginCore core) {
        this.core = core;
    }

    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        String accountName = commandContext.getOne("accountName").get().toString();
        int montant;
        try {
            montant = Integer.parseInt(commandContext.getOne("amount").get().toString());
        } catch (Exception e) {
            montant = 0;
        }

        if(!accountName.isEmpty() && montant != 0) {
            Player player = core.getGame().getServer().getPlayer(accountName).get();
            String uuid;
            if(player != null) {
                uuid = player.getUniqueId().toString();
            } else uuid = accountName;
            core.getAccount(uuid).add(montant);
            Player playerSource = (Player)commandSource;
            String message = accountName + " a reçu " + String.valueOf(montant) + ".";
            playerSource.sendMessage(Text.of(message));
            WebService ws = new WebService(core);
            try {
                if(montant < 0)
                    ws.postTransaction(montant*(-1), core.getAccount(uuid).getAccountID(), -1, "removed by administrator");
                else
                    ws.postTransaction(montant, -1, core.getAccount(uuid).getAccountID(), "given by administrator");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Player player = (Player)commandSource;
            player.sendMessage(Text.of("Le compte demandé n'existe pas"));
        }



        return CommandResult.success();
    }
}