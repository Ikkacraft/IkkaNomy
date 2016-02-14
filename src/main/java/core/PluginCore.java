package core;

import accounts.Account;
import commands.*;
import events.PlayerConnect;
import org.slf4j.Logger;
import com.google.inject.Inject;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.UUID;

@Plugin(
        id = "IkkaNomy",
        name = "IkkaNomy",
        version = "1.0"
)
// La class PluginCore permet d'initialiser le plugin mais permet aussi la gestion des différents évènements
public class PluginCore {
    @Inject
    private Game game;
    private Logger logger;
    private HashMap<String, Account> accounts = new HashMap<String, Account>();

    @Listener
    // Lorsque le serveur minecraft est lancé, on initialise le plugin
    public void onServerStart(GameStartedServerEvent event) {
        this.getLogger().info("IkkaNomy a recu l'instruction start");

        /* Récupération des comptes */
        accounts.put("2cb212c7-e192-4ec3-86bc-05e8ef3a286f", new Account(100));
        accounts.put("vilia", new Account(100));

        // Evènement PlayerConnect qui permet de la detection d'une nouvelle connexion au serveur
        EventListener<ClientConnectionEvent.Join> listenerOpenChest = new PlayerConnect(this);
        Sponge.getEventManager().registerListener(this, ClientConnectionEvent.Join.class, listenerOpenChest);

        commandRegister();
    }

    @Inject
    public PluginCore(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Game getGame() {
        return this.game;
    }

    // Permet d'envoyer un message à tous les joueurs du serveur
    public void broadcastText(String text) {
        getGame().getServer().getBroadcastChannel().send(Text.of(text));
    }

    public int getAccountBalanceByUUID(String accountName) {
        Account account = accounts.get(accountName);
        if (!(account == null)) {
            return account.getBalance();
        } else return -1;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(String accountName) {
        return accounts.get(accountName);
    }

    private void commandRegister() {
        /* Enregistrement des commndes */
        CommandSpec pay = CommandSpec.builder()
                .description(Text.of("Pay someone"))
                .permission("ikkanomy.command.pay")
                .arguments(
                        GenericArguments.string(Text.of("account")),
                        GenericArguments.integer(Text.of("amount"))
                )
                .executor(new Pay(this))
                .build();
        Sponge.getCommandManager().register(this, pay, "pay");

        CommandSpec moneyCreate = CommandSpec.builder()
                .permission("ikkanomy.money.create")
                .description(Text.of("Create an account"))
                .arguments(
                        GenericArguments.string(Text.of("accountName")),
                        GenericArguments.integer(Text.of("accountBalance"))
                )
                .executor(new MoneyCreate(this))
                .build();

        CommandSpec moneyDelete = CommandSpec.builder()
                .permission("ikkanomy.money.delete")
                .description(Text.of("Delete an account"))
                .arguments(
                        GenericArguments.string(Text.of("accountName"))
                )
                .executor(new MoneyDelete(this))
                .build();

        CommandSpec moneyAdd = CommandSpec.builder()
                .permission("ikkanomy.money.add")
                .description(Text.of("Add money to an account"))
                .arguments(
                        GenericArguments.string(Text.of("accountName")),
                        GenericArguments.integer(Text.of("amount"))
                )
                .executor(new MoneyAdd(this))
                .build();

        CommandSpec moneyInfo = CommandSpec.builder()
                .permission("ikkanomy.money.info")
                .description(Text.of("Get amount of an account"))
                .arguments(
                        GenericArguments.optionalWeak(GenericArguments.string(Text.of("accountName")))
                )
                .executor(new MoneyInfo(this))
                .build();

        CommandSpec money = CommandSpec.builder()
                .description(Text.of("Get money of our account"))
                .permission("ikkanomy.command.money")
                .child(moneyCreate, "create")
                .child(moneyDelete, "delete")
                .child(moneyAdd, "add")
                .child(moneyInfo, "info")
                .executor(new Money(this))
                .build();
        Sponge.getCommandManager().register(this, money, "money");
    }
}