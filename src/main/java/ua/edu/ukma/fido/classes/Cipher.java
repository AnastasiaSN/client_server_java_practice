package ua.edu.ukma.fido.classes;

import lombok.Builder;
import lombok.Data;
import ua.edu.ukma.fido.entity.Message;
import ua.edu.ukma.fido.entity.Packet;

@Data
@Builder(toBuilder = true)
public class Cipher {
    private static String deencode(String string) {
        char xorKey = 'P';

        String outputString = "";

        int len = string.length();

        for (int i = 0; i < len; i++)
            outputString = outputString + (char) (string.charAt(i) ^ xorKey);

        return outputString;
    }

    public static String encode(final String string) {
        return deencode(string);
    }

    public static String decode(final String string) {
        return deencode(string);
    }
}
