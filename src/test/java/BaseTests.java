import Expressions.RightValue;
import Tokenizer.Token;
import Tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTests {
    @Test
    public void simpleTest() {
        Tokenizer t = new Tokenizer();

        List<Token> tokens = t.tokenize("10 + 20 + 3 + 4");

        assertEquals("Numliteral", tokens.get(0).getType().getExpressionName());
        assertEquals("10", tokens.get(0).getValue());

        assertEquals("Plus", tokens.get(1).getType().getExpressionName());

        assertEquals("20", tokens.get(2).getValue());
    }

    @Test
    public void astSimpleTest() {
        executeTests(new String[][]{
                {"1","1"},
                {"1 + 2", "3"},
                {"2 * 3", "6"},
                {"2 * 3 * 4", "24"},
                {"2 + 3 + 4", "9"},
        });
    }

    @Test
    public void astComplexTest() {
        executeTests(new String[][]{
                {"1 * 2 + 3","5"},
                {"2 + 3 * 4", "14"},
                {"2 + 3 * 5 + 1", "18"},
                {"2 * 3 * 4 + 2", "26"},
                {"2 + 3 + 4 * 3 * 2 + 1", "30"}
        });
    }

    private void executeTests(String[][] codes){
        for(int i = 0; i < codes.length; i++){
            Molang lang = new Molang(codes[i][0]);
            RightValue<Integer> v = (RightValue<Integer>)lang.getAst();
            assertEquals(new Integer(codes[i][1]), v.evaluate());
        }
    }

    @Test
    public void identifierTest() {
        Molang lang = new Molang("a = 10");
        RightValue<Integer> v = (RightValue<Integer>)lang.getAst();
        assertEquals(new Integer(10), v.evaluate(), "Expression is 10");
        assertEquals(10, lang.getContext().getIdentifier("a").evaluate(), "Variable is 10");
    }

    @Test
    public void identifierComplexTest() {
        Molang lang = new Molang("a = 10 * 5 + 3 * 2 * 1");
        RightValue<Integer> v = (RightValue<Integer>)lang.getAst();
        assertEquals(new Integer(56), v.evaluate(), "Expression is 56");
        assertEquals(56, lang.getContext().getIdentifier("a").evaluate(), "Variable is 56");
    }
}
