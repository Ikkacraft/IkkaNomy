package webService;

import java.util.UUID;

public class User {
    private UUID uuid;
    private int account_id;
    private int role_id;
    private String pseudo;
    private int flag_connection;
    private String token;

    public User() {

    }

    public User(UUID uuid, int account_id, int role_id, String pseudo, int flag_connection, String token) {
        this.uuid = uuid;
        this.account_id = account_id;
        this.role_id = role_id;
        this.pseudo = pseudo;
        this.flag_connection = flag_connection;
        this.token = token;

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getFlag_connection() {
        return flag_connection;
    }

    public void setFlag_connection(int flag_connection) {
        this.flag_connection = flag_connection;
    }
}
