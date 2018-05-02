import Expressions.Procedure;
import Tokenizer.Token;
import Tokenizer.Tokenizer;
import Util.Scope;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Resources;
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
        testOnReturnValue(new String[][]{
                {"1","1"},
                {"1 + 2", "3"},
                {"2 * 3", "6"},
                {"2 * 3 * 4", "24"},
                {"2 + 3 + 4", "9"},
        });
    }

    @Test
    void astComplexTest() {
        testOnReturnValue(new String[][]{
                {"4 * 2 + 3","11"},
                {"2 + 3 * 4", "14"},
                {"2 + 3 * 5 + 1", "18"},
                {"2 * 3 * 4 + 2", "26"},
                {"2 + 3 + 4 * 3 * 2 + 1", "30"}
        });
    }

    @Test
    void precedenceBracketSimpleTest() {
        testOnReturnValue(new String[][]{
                {"1 * (2 + 3)","5"},
                {"(2 + 3) * 4", "20"},
                {"2 + (3 * 5) + 1", "18"},
                {"(2 * 3) * (4 + 2)", "36"},
        });
    }

    @Test
    void precedenceBracketComplexTest() {
        testOnReturnValue(new String[][]{
                {"(2 + 3) + (4 * 3 * (2 + 1))", "41"},
                {"(2 + 3)", "5"},
                {"(2)", "2"},
                {"((2))", "2"}
        });
    }

    private void testOnReturnValue(String[][] codes){
        for (String[] code : codes) {
            MoLang lang = new MoLang(code[0]);
            Scope scope = lang.executeAndGetScope();
            Object actual = scope.getReturnValue();
            assertEquals(code[1], actual.toString(), code[0] + " should be " + code[1] + " but was " + actual);
        }
    }

    @Test
    void identifierTest() {
        MoLang lang = new MoLang("a = 10");
        Scope scope = lang.executeAndGetScope();
        assertEquals(new Integer(10), scope.getReturnValue(), "Expression is 10");
        assertEquals(10, scope.getValue("a"), "Variable is 10");
    }

    @Test
    void identifierComplexTest() {
        MoLang lang = new MoLang("a = 10 * 5 + 3 * 2 * 1");
        Scope scope = lang.executeAndGetScope();
        assertEquals(new Integer(56), scope.getReturnValue(), "Expression is 56");
        assertEquals(56, scope.getValue("a"), "Variable is 56");
    }

    @Test
    void multilineTest() {
        MoLang lang = new MoLang("a = 1; b = 2; a = 4");
        Scope scope = lang.executeAndGetScope();
        assertEquals(4, scope.getValue("a"));
        assertEquals(2, scope.getValue("b"));
    }

    @Test
    void booleanTest() {
        MoLang lang = new MoLang("a = true; b = false;");
        Scope scope = lang.executeAndGetScope();
        assertEquals(true, scope.getValue("a"));
        assertEquals(false, scope.getValue("b"));
    }

    @Test
    void booleanComplexTest() {
        MoLang lang = new MoLang("a = true; b = false; c = a && b; d = false || a;");
        Scope scope = lang.executeAndGetScope();
        assertEquals(true, scope.getValue("a"));
        assertEquals(false, scope.getValue("b"));
        assertEquals(false, scope.getValue("c"));
        assertEquals(true, scope.getValue("d"));
    }

    @Test
    void comparisonIntegerTest() {
        testOnReturnValue(new String[][]{
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
        testOnReturnValue(new String[][]{
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
        MoLang lang = new MoLang("a = 0; if 3 > 2 { a = 20; }");
        assertEquals(20, lang.executeAndGetScope().getValue("a"));
    }

    @Test
    void ifSimpleTest2() {
        MoLang lang = new MoLang("a = 0; if 3 < 2 { a = 20; }");
        assertEquals(0, lang.executeAndGetScope().getValue("a"));
    }

    @Test
    void ifTest() {
        MoLang lang = new MoLang("a = 0; b = 0; c = 0; if 2 < 3 { a = 20; b = 21; } c = 90; if 2 > 3 { a = 10; b = 11; }");
        Scope scope = lang.executeAndGetScope();
        assertEquals(20, scope.getValue("a"));
        assertEquals(21, scope.getValue("b"));
        assertEquals(90, scope.getValue("c"));
    }

    @Test
    void whileTest() {
        MoLang lang = new MoLang("a = 0; while a < 10 { a = a + 3; }");
        assertEquals(12, lang.executeAndGetScope().getValue("a"));
    }

    @Test
    void multilineComplexTest() {
        MoLang lang = new MoLang("a = 10 * 30 + 3; b = a / 3; c = (10 + 3) * a;");
        Scope scope = lang.executeAndGetScope();
        assertEquals(303, scope.getValue("a"));
        assertEquals(101, scope.getValue("b"));
        assertEquals(13 * 303, scope.getValue("c"));
    }

    @Test
    void procedureTest() {
        MoLang lang = new MoLang("b = 0; a = { b = 2 / 2; }");
        assertEquals(Procedure.class, lang.executeAndGetScope().getValue("a").getClass());
    }

    @Test
    void scopeTest2() {
        MoLang lang = new MoLang("b = 0; if b == 0 { if b < 1 { b = 2; } }");
        assertEquals(2, lang.executeAndGetScope().getValue("b"));
    }

    @Test
    void plusPlusTest() {
        MoLang lang = new MoLang("b = 0; ++ b;");
        assertEquals(1, lang.executeAndGetScope().getValue("b"));
    }

    @Test
    void returnTest() {
        MoLang lang = new MoLang("return 3 * 2;");
        assertEquals(6, lang.executeAndGetScope().getReturnValue());
    }

    @Test
    void localTest() {
        MoLang lang = new MoLang("a = 5; { local a = 3; } return a;");
        assertEquals(5, lang.executeAndGetScope().getReturnValue());
    }

    @Test
    void lazyEvalTest() {
        testOnReturnValue(new String[][]{
                {"a = 5; b = { a = 3; } return a;", "5"},
                {"a = 5; { a = 3; } return a;", "3"}
        });
    }

    @Test
    void defineFunTest() {
        MoLang lang = new MoLang("a = (x,y){ 3; };");
        assertEquals("[x, y]", ((Procedure)lang.executeAndGetScope().getReturnValue()).getArgumentNames().toString());
    }

    @Test
    void defineFunTest2() {
        MoLang lang = new MoLang("a = (x){ 3; };");
        assertEquals("[x]", ((Procedure)lang.executeAndGetScope().getReturnValue()).getArgumentNames().toString());
    }

    @Test
    void runFunTest() {
        MoLang lang = new MoLang("a = (x,y){ x * y; }; a(2,3);");
        assertEquals("6", lang.executeAndGetScope().getReturnValue().toString());
    }

    @Test
    void runFunTest2() {
        MoLang lang = new MoLang("a = (x){ 3; }; a(1);");
        assertEquals("3", lang.executeAndGetScope().getReturnValue().toString());
    }

    @Test
    void runFunTest3() {
        MoLang lang = new MoLang("a = (){ 3; }; a();");
        assertEquals("3", lang.executeAndGetScope().getReturnValue().toString());
    }

    @Test
    void simpleExampleTest() throws IOException {
        String code = Resources.toString(Resources.getResource("simpleExample.molang"), StandardCharsets.UTF_8);

        MoLang lang = new MoLang(code);
        assertEquals("42", lang.executeAndGetScope().getReturnValue().toString());
    }

    @Test
    void complexExampleTest() throws IOException {
        String code = Resources.toString(Resources.getResource("complexExample.molang"), StandardCharsets.UTF_8);

        MoLang lang = new MoLang(code);
        assertEquals("55", lang.executeAndGetScope().getReturnValue().toString());
    }

    @Test
    void printTest() throws IOException {
        MoLang lang = new MoLang("print 5;");
        lang.execute();
    }

    @Test
    void anotherExampleTest() throws IOException {
        String code = Resources.toString(Resources.getResource("functionAsParameter.molang"), StandardCharsets.UTF_8);

        MoLang lang = new MoLang(code);
        assertEquals("15", lang.executeAndGetScope().getReturnValue().toString());
    }

    //Todo: This one will fail
    @Test
    void functionAsReturnTest() throws IOException {
        String code = Resources.toString(Resources.getResource("functionAsReturn.molang"), StandardCharsets.UTF_8);

        MoLang lang = new MoLang(code);
        assertEquals("-", lang.executeAndGetScope().getReturnValue().toString());
    }
}
