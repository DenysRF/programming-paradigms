package block2.cc.antlr;

import block2.cc.*;
import block2.cc.Parser;
import org.antlr.v4.runtime.*;

import block2.cc.ll.Sentence;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class SentenceConverter 
		extends SentenceBaseListener implements Parser {
	/** Factory needed to create terminals of the {@link Sentence}
	 * grammar. See {@link block2.cc.ll.SentenceParser} for
	 * example usage. */
	private final SymbolFactory fact;

	public SentenceConverter() {
		this.fact = new SymbolFactory(Sentence.class);
	}

	private Stack<AST> parents;

    private static final NonTerm SENTENCE = new NonTerm("Sentence");
    private static final NonTerm SUBJECT = new NonTerm("Subject");
    private static final NonTerm OBJECT = new NonTerm("Object");
    private static final NonTerm MODIFIER = new NonTerm("Modifier");

    private static AST current;

    /**
     * Converts a lexer instance into an AST.
     *
     * @param lexer
     */
    @Override
    public AST parse(Lexer lexer) throws ParseException {
        parents = new Stack<>();
        current = new AST(SENTENCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SentenceParser parser = new SentenceParser(tokens);
        SentenceListener listener = new SentenceConverter();

        parser.addParseListener(listener);
        parser.sentence();

        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new ParseException();
        }
        return current;
    }

    @Override
    public void enterSentence(SentenceParser.SentenceContext ctx) {
        super.enterSentence(ctx);
        parents.push(current);
    }

    @Override
    public void enterSubject(SentenceParser.SubjectContext ctx) {
        super.enterSubject(ctx);
        AST ast = new AST(SUBJECT);
        current.addChild(ast);
        parents.push(current);
        current = ast;
    }

    @Override
    public void enterObject(SentenceParser.ObjectContext ctx) {
        super.enterObject(ctx);
        AST ast = new AST(OBJECT);
        current.addChild(ast);
        parents.push(current);
        current = ast;
    }

    @Override
    public void enterModifier(SentenceParser.ModifierContext ctx) {
        super.enterModifier(ctx);
        AST ast = new AST(MODIFIER);
        current.addChild(ast);
        parents.push(current);
        current = ast;
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
        current = parents.pop();
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
        int nodeType = node.getSymbol().getType();
        Term terminal = fact.getTerminal(nodeType);
        current.addChild(new AST(terminal, node.getSymbol()));
    }

}