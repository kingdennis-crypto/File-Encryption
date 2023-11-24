package org.zenith.Commands;

import org.zenith.Models.FunctionDescription;
import org.zenith.Models.KeyProperties;
import org.zenith.Handlers.ConfigurationHandler;
import org.zenith.Handlers.KeyHandler;

import java.util.Arrays;
import java.util.List;

public class KeyCommands extends Command {
    private final KeyHandler keyHandler;

    public KeyCommands() {
        super("key", "This command is to handle the key management");

        ConfigurationHandler configurationHandler = ConfigurationHandler.getInstance();
        keyHandler = new KeyHandler(configurationHandler.getProperty("KEY_PATH"));

        super.addFunction("create", new FunctionDescription("create [key]", "Create a key", this::createKey));
        super.addFunction("delete", new FunctionDescription("delete [key]", "Deletes a key", this::deleteKey));
        super.addFunction("get", new FunctionDescription("get [key]", "Gets a key", this::getKey));
        super.addFunction("list", new FunctionDescription("list", "Lists all generated keys", this::listKeys));
        super.addFunction("select", new FunctionDescription("select [key]", "Selects the key to use with encryption and decryption", this::selectKey));
    }

    private void createKey(String[] data) {
        try {
            if (data.length > 2) {
                keyHandler.createKey(data[2]);
            } else {
                System.out.println("Missing key name. Usage: key create [keyName]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteKey(String[] data) {
        try {
            if (data.length > 2) {
                keyHandler.deleteKey(data[2]);
            } else {
                System.out.println("Missing key name. Usage: key delete [keyName]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getKey(String[] data) {
        try {
            if (data.length > 2) {
                KeyProperties properties = keyHandler.getKey(data[2]);
                System.out.println("Secret key: " + Arrays.toString(properties.getSecretKey().getEncoded()));
            } else {
                System.out.println("Missing key name. Usage: key get [keyName]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void selectKey(String[] data) {
        try {
            System.out.println(Arrays.toString(data));
            if (data.length > 2) {
                keyHandler.selectKeys(data[2]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void listKeys(String[] data) {
        try {
            List<String> keys = keyHandler.listKeys();
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < keys.size(); i++) {
                stringBuilder
                        .append(String.format("%d. %s", i + 1, keys.get(i)))
                        .append(System.lineSeparator());
            }

            System.out.println(stringBuilder);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
