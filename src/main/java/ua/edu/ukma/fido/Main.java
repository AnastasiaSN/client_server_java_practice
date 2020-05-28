package ua.edu.ukma.fido;

public class Main {
    static final int
            PRODUCT = 1,
            GROUP = 2;

    static final int
            CREATE = 4,
            READ = 8,
            UPDATE = 16,
            DELETE = 32;

    static final int
            PRODUCT_CREATE = PRODUCT ^ CREATE,
            PRODUCT_READ   = PRODUCT ^ READ,
            PRODUCT_UPDATE = PRODUCT ^ UPDATE,
            PRODUCT_DELETE = PRODUCT ^ DELETE;

    static final int
            GROUP_CREATE = GROUP ^ CREATE,
            GROUP_READ   = GROUP ^ READ,
            GROUP_UPDATE = GROUP ^ UPDATE,
            GROUP_DELETE = GROUP ^ DELETE;

    public static void main(String[] args) {
        int INCOMING_TYPE = GROUP;
        int INCOMING_ACTION = CREATE;

        int INCOMING_COMMAND_TYPE = INCOMING_TYPE ^ INCOMING_ACTION;

        boolean IS_PRODUCT = (INCOMING_COMMAND_TYPE & PRODUCT) == 1;

        int COMMAND = INCOMING_COMMAND_TYPE ^ (IS_PRODUCT ? PRODUCT : GROUP);

        switch (COMMAND) {
            case CREATE:
                System.out.println("CREATE " + (IS_PRODUCT ? "PRODUCT" : "GROUP"));
                break;

            case READ:
                System.out.println("READ " + (IS_PRODUCT ? "PRODUCT" : "GROUP"));
                break;

            case UPDATE:
                System.out.println("UPDATE " + (IS_PRODUCT ? "PRODUCT" : "GROUP"));
                break;

            case DELETE:
                System.out.println("DELETE " + (IS_PRODUCT ? "PRODUCT" : "GROUP"));
                break;
        }
    }
}
