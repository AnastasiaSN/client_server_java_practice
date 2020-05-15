import org.junit.Test;
import ua.edu.ukma.fido.classes.Cipher;

import static org.junit.Assert.assertTrue;

public class CipherTest {
    @Test
    public void testDeencode() {
        String sourceText = "test123";
        String cypheredText = Cipher.encode(sourceText);
        String decypheredText = Cipher.decode(cypheredText);
        assertTrue(sourceText.equals(decypheredText));
    }
}
