package webService;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.PluginCore;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WebService {
    private PluginCore core;
    private static String ikkauser = "http://d6308d6c.ngrok.io/";
    private static String ikkanomy = "http://82562d01.ngrok.io/";


    public WebService(PluginCore core) {
        this.core = core;
    }

    public void postAccount(String uuid, int role_id, String pseudo) throws IOException {
        postAccount(uuid, role_id, pseudo, 200);
    }

    public void postAccount(String uuid, int role_id, String pseudo, int balance) throws IOException {
        String body = "{\"uuid\": \"" + uuid + "\",\"role_id\": " + String.valueOf(role_id) +
                ",\"pseudo\": \"" + pseudo + "\",\"account_description\": \"" + uuid +
                "\",\"account_balance\": " + String.valueOf(balance) + "}";
        put(body, new URL(ikkauser + "users"), "POST");
    }

    public List<User> getAccounts() throws IOException {
        URL url = new URL(ikkauser + "users");

        ObjectMapper mapper = new ObjectMapper();
        String output = getBody(url);
        List<User> navigation = mapper.readValue(
                output,
                mapper.getTypeFactory().constructCollectionType(
                        List.class, User.class));
        return navigation;

    }

    public List<wsAccount> getAllAccounts() throws IOException {
        String urlString = ikkanomy + "accounts";
        URL url = new URL(urlString);
        ObjectMapper mapper = new ObjectMapper();
        String output = getBody(url);
        List<wsAccount> navigation = mapper.readValue(
                output,
                mapper.getTypeFactory().constructCollectionType(
                        List.class, wsAccount.class));
        return navigation;
    }

    public void putConnect(String playerUUID) throws IOException {
        String body = "{uuid : " + playerUUID + "}";
        put(body, new URL(ikkauser + "connect/minecraft"));
    }

    public void postTransaction(int amount, int accountDebitor, int accountCreditor, String description) throws IOException {
        String body ="{\"amount\" :" + String.valueOf(amount);
        if(accountCreditor != -1)
            body += ", \"account_creditor\" :" +String.valueOf(accountCreditor);
        if(accountDebitor != -1)
            body += ", \"account_debtor\": " + String.valueOf(accountDebitor);
        body += ", \"description\" :\"" + description + "\"}";
        put(body, new URL(ikkanomy + "transactions"), "POST");
    }

    private void put(String body, URL url, String key) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
        String encoding = "MmNiMjEyYzctZTE5Mi00ZWMzLTg2YmMtMDVlOGVmM2EyODZmOmYxMGUxYjkzYWYxYjQwZTA5YTI4YTAyMTMwMzE4Nzhl";

        //add request header
        con.setRequestMethod(key);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty( "Content-type", "application/json");
        con.setRequestProperty( "Accept", "*/*" );
        con.setRequestProperty("Authorization", "Basic " + encoding);

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(body);
        wr.flush();
        wr.close();
    }

    private void put(String body, URL url) throws IOException {
        put(body, url, "PUT");
    }


    private String getBody(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        String encoding = "MmNiMjEyYzctZTE5Mi00ZWMzLTg2YmMtMDVlOGVmM2EyODZmOmYxMGUxYjkzYWYxYjQwZTA5YTI4YTAyMTMwMzE4Nzhl";
        con.setRequestProperty("Authorization", "Basic " + encoding);
        con.setRequestProperty("Accept", "*/*");
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        DataInputStream in = new DataInputStream(con.getInputStream());

        String inputLine;
        String output = "";
        while ((inputLine = in.readLine()) != null)
            output += inputLine;
        in.close();
        return output;
    }
}
