import Tokenizer.Token;
import Tokenizer.TokenType;
import Tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenizerTest {
    @Test
    public void simpleTest() {
        Tokenizer t = new Tokenizer();

        List<Token> tokens = t.tokenize("10 + 20 + 3 + 4");

        assertEquals(TokenType.NUMLITERAL, tokens.get(0).getType());
        assertEquals(TokenType.PLUS, tokens.get(1).getType());
    }
}
