import Expressions.Procedure;
import Tokenizer.Token;
import Tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseTests {
    @Test
    void tokenizerTest() {
        Tokenizer t = new Tokenizer();

        List<Token> tokens = t.tokenize("10 + 20 + 3 + 4");

        assertEquals("Numliteral", tokens.get(0).getType().getExpressionName());
        assertEquals("10", tokens.get(0).getValue());

        assertEquals("Plus", tokens.get(1).getType().getExpressionName());

        assertEquals("20", tokens.get(2).getValue());
    }

    @Test
    void astSimpleTest() {
        executeTests(new String[][]{
                {"1","1"},
                {"1 + 2", "3"},
                {"2 * 3", "6"},
                {"2 * 3 * 4", "24"},
                {"2 + 3 + 4", "9"},
        });
    }

    @Test
    void astComplexTest() {
        executeTests(new String[][]{
                {"4 * 2 + 3","11"},
                {"2 + 3 * 4", "14"},
                {"2 + 3 * 5 + 1", "18"},
                {"2 * 3 * 4 + 2", "26"},
                {"2 + 3 + 4 * 3 * 2 + 1", "30"}
        });
    }

    @Test
    void precedenceBracketSimpleTest() {
        executeTests(new String[][]{
                {"1 * (2 + 3)","5"},
                {"(2 + 3) * 4", "20"},
                {"2 + (3 * 5) + 1", "18"},
                {"(2 * 3) * (4 + 2)", "36"},
        });
    }

    @Test
    void precedenceBracketComplexTest() {
        executeTests(new String[][]{
                {"(2 + 3) + (4 * 3 * (2 + 1))", "41"},
                {"(2 + 3)", "5"},
                {"(2)", "2"},
                {"((2))", "2"}
        });
    }

    private void executeTests(String[][] codes){
        for (String[] code : codes) {
            Molang lang = new Molang(code[0]);
            lang.exec();
            Object actual = lang.getResult();
            assertEquals(code[1], actual.toString(), code[0] + " should be " + code[1] + " but was " + actual);
        }
    }

    @Test
    void identifierTest() {
        Molang lang = new Molang("a = 10");
        lang.exec();
        assertEquals(new Integer(10), lang.getResult(), "Expression is 10");
        assertEquals(10, lang.getScope().getValue("a"), "Variable is 10");
    }

    @Test
    void identifierComplexTest() {
        Molang lang = new Molang("a = 10 * 5 + 3 * 2 * 1");
        lang.exec();
        assertEquals(new Integer(56), lang.getResult(), "Expression is 56");
        assertEquals(56, lang.getScope().getValue("a"), "Variable is 56");
    }

    @Test
    void multilineTest() {
        Molang lang = new Molang("a = 1; b = 2; a = 4");
        lang.exec();
        assertEquals(4, lang.getScope().getValue("a"));
        assertEquals(2, lang.getScope().getValue("b"));
    }

    @Test
    void booleanTest() {
        Molang lang = new Molang("a = true; b = false;");
        lang.exec();
        assertEquals(true, lang.getScope().getValue("a"));
        assertEquals(false, lang.getScope().getValue("b"));
    }

    @Test
    void booleanComplexTest() {
        Molang lang = new Molang("a = true; b = false; c = a && b; d = false || a;");
        lang.exec();
        assertEquals(true, lang.getScope().getValue("a"));
        assertEquals(false, lang.getScope().getValue("b"));
        assertEquals(false, lang.getScope().getValue("c"));
        assertEquals(true, lang.getScope().getValue("d"));
    }

    @Test
    void comparisonIntegerTest() {
        executeTests(new String[][]{
                {"10 > 11", "false"},
                {"11 > 10", "true"},
                {"10 < 11", "true"},
                {"11 < 10", "false"},
                {"10 == 11", "false"},
                {"10 == 10", "true"}
        });
    }

    @Test
    void comparisonBooleanTest() {
        executeTests(new String[][]{
                {"false && false", "false"},
                {"false && true", "false"},
                {"true || false", "true"},
                {"false || false", "false"},
                {"true == true", "true"},
                {"false == false", "true"}
        });
    }

    @Test
    void ifSimpleTest() {
        Molang lang = new Molang("a = 0; if 3 > 2 { a = 20; }");
        lang.exec();
        assertEquals(20, lang.getScope().getValue("a"));
    }

    @Test
    void ifSimpleTest2() {
        Molang lang = new Molang("a = 0; if 3 < 2 { a = 20; }");
        lang.exec();
        assertEquals(0, lang.getScope().getValue("a"));
    }

    @Test
    void ifTest() {
        Molang lang = new Molang("a = 0; b = 0; c = 0; if 2 < 3 { a = 20; b = 21; } c = 90; if 2 > 3 { a = 10; b = 11; }");
        lang.exec();
        assertEquals(20, lang.getScope().getValue("a"));
        assertEquals(21, lang.getScope().getValue("b"));
        assertEquals(90, lang.getScope().getValue("c"));
    }

    @Test
    void whileTest() {
        Molang lang = new Molang("a = 0; while a < 10 { a = a + 3; }");
        lang.exec();
        assertEquals(12, lang.getScope().getValue("a"));
    }

    @Test
    void multilineComplexTest() {
        Molang lang = new Molang("a = 10 * 30 + 3; b = a / 3; c = (10 + 3) * a;");
        lang.exec();
        assertEquals(303, lang.getScope().getValue("a"));
        assertEquals(101, lang.getScope().getValue("b"));
        assertEquals(13 * 303, lang.getScope().getValue("c"));
    }

    @Test
    void procedureTest() {
        Molang lang = new Molang("b = 0; a = { b = 2 / 2; }");
        lang.exec();
        assertEquals(Procedure.class, lang.getScope().getValue("a").getClass());
        //assertEquals(1, lang.getScope().getValue("b"));
    }

    @Test
    void scopeTest2() {
        Molang lang = new Molang("b = 0; if b == 0 { if b < 1 { b = 2; } }");
        lang.exec();
        assertEquals(2, lang.getScope().getValue("b"));
    }

    @Test
    void plusPlusTest() {
        Molang lang = new Molang("b = 0; ++ b;");
        lang.exec();
        assertEquals(1, lang.getScope().getValue("b"));
    }

    @Test
    void returnTest() {
        Molang lang = new Molang("return 3 * 2;");
        lang.exec();
        assertEquals(6, lang.getScope().getReturnValue());
    }

    @Test
    void localTest() {
        Molang lang = new Molang("a = 5; { local a = 3; } return a;");
        lang.exec();
        assertEquals(5, lang.getScope().getReturnValue());
    }

    @Test
    void lazyEvalTest() {
        executeTests(new String[][]{
                {"a = 5; b = { a = 3; } return a;", "5"},
                {"a = 5; { a = 3; } return a;", "3"}
        });
    }
}
