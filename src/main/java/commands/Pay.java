package commands;

import accounts.Account;
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

public class Pay implements CommandExecutor {
    private PluginCore core;

    public Pay(PluginCore core) {
        this.core = core;
    }

    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        String account = commandContext.<String>getOne("account").get();
        int amount = commandContext.<Integer>getOne("amount").get();
        Player player = (Player) commandSource;

        if(amount > 0) {
            String accountUUID;
            try {
                Player test = core.getGame().getServer().getPlayer(account).get();
                accountUUID = player.getUniqueId().toString();
            } catch (Exception e) {
                accountUUID = account;
            }
            Account crediteur = core.getAccount(accountUUID);
            Account debiteur = core.getAccount(player.getUniqueId().toString());

            int debiteurBalance = debiteur.getBalance();
            if (debiteurBalance - amount >= 0) {
                debiteur.setBalance(debiteurBalance - amount);
                crediteur.setBalance(crediteur.getBalance() + amount);
                player.sendMessage(Text.of("Vous avez effectué un virement de " + amount + " pour " + account));
                WebService ws = new WebService(core);
                try {
                    ws.postTransaction(amount, debiteur.getAccountID(), crediteur.getAccountID(), "Virement");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else player.sendMessage(Text.of("Vous n'avez pas assez d'argent !"));
        } else player.sendMessage(Text.of("Montant invalide !"));
        return CommandResult.success();
    }
}
