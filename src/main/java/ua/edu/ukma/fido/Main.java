package ua.edu.ukma.fido;

import lombok.SneakyThrows;
import ua.edu.ukma.fido.utils.CommandTypeEncoder;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        int INCOMING_TYPE = CommandTypeEncoder.PRODUCT;
        int INCOMING_ACTION = CommandTypeEncoder.CREATE;

        int INCOMING_COMMAND_TYPE = INCOMING_TYPE ^ INCOMING_ACTION;

        CommandTypeEncoder commandType = new CommandTypeEncoder(INCOMING_COMMAND_TYPE);

        System.out.println("This is " + (commandType.isProduct() ? " product" : "group"));
        System.out.println("Command code: " + commandType.getCommandTypeCode());
        System.out.println("Command: " + commandType.getCommandType());
    }
}
