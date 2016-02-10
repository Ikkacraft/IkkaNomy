package core;

import accounts.Account;
import commands.*;
import events.PlayerConnect;
import org.slf4j.Logger;
import com.google.inject.Inject;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
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
    private HashMap<UUID, Account> accounts = new HashMap<UUID, Account>();

    @Listener
    // Lorsque le serveur minecraft est lancé, on initialise le plugin
    public void onServerStart(GameStartedServerEvent event) {
        this.getLogger().info("IkkaNomy a recu l'instruction start");

        /* Récupération des comptes */
        accounts.put(UUID.fromString("2cb212c7-e192-4ec3-86bc-05e8ef3a286f"), new Account(100));

        // Evènement OpenChest qui permet l'achievement coffre caché
        EventListener<ClientConnectionEvent.Join> listenerOpenChest = new PlayerConnect(this);
        Sponge.getEventManager().registerListener(this, ClientConnectionEvent.Join.class, listenerOpenChest);

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
                .executor(new Money(this))
                .build();

        CommandSpec moneyDelete = CommandSpec.builder()
                .permission("ikkanomy.money.delete")
                .description(Text.of("Delete an account"))
                .arguments(
                        GenericArguments.string(Text.of("accountName"))
                )
                .executor(new Money(this))
                .build();

        CommandSpec moneyAdd = CommandSpec.builder()
                .permission("ikkanomy.money.add")
                .description(Text.of("Add money to an account"))
                .arguments(
                        GenericArguments.string(Text.of("accountName")),
                        GenericArguments.integer(Text.of("amount"))
                )
                .executor(new Money(this))
                .build();

        CommandSpec money = CommandSpec.builder()
                .description(Text.of("Get money of our account"))
                .permission("ikkanomy.command.money")
                .arguments(
                        GenericArguments.string(Text.of("account"))
                )
                .child(moneyCreate, "create")
                .child(moneyDelete, "delete")
                .child(moneyAdd, "add")
                .executor(new Money(this))
                .build();
        Sponge.getCommandManager().register(this, money, "money");
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
        Account account = accounts.get(UUID.fromString(accountName));
        if(!(account == null)) {
            return account.getBalance();
        }
        else return -1;
    }
}