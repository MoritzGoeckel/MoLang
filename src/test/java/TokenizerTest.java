import Expressions.RightValue;
import Tokenizer.Token;
import Tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenizerTest {
    @Test
    public void simpleTest() {
        Tokenizer t = new Tokenizer();

        List<Token> tokens = t.tokenize("10 + 20 + 3 + 4");

        assertEquals("Numliteral", tokens.get(0).getType().getExpressionName());
        assertEquals("Plus", tokens.get(1).getType().getExpressionName());
    }

    @Test
    public void astSimpleTest() {
        Molang lang = new Molang("10 + 20 + 3 + 4");
        RightValue<Integer> v = (RightValue<Integer>)lang.getAst();

        assertEquals(new Integer(37), v.evaluate());
    }

    @Test
    public void astSuperComplexTest() {
        Molang lang = new Molang("10 + 20 + 3 * 4");
        RightValue<Integer> v = (RightValue<Integer>)lang.getAst();

        assertEquals(new Integer(42), v.evaluate());
    }

    @Test
    public void astComplexTest() {
        Molang lang = new Molang("2 + 3 * 4");
        RightValue<Integer> v = (RightValue<Integer>)lang.getAst();

        assertEquals(new Integer(14), v.evaluate());
    }

    @Test
    public void astOtherComplexTest() {
        Molang lang = new Molang("2 * 3 + 4");
        RightValue<Integer> v = (RightValue<Integer>)lang.getAst();

        assertEquals(new Integer(10), v.evaluate());
    }
}
