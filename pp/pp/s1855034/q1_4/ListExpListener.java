// Generated from C:/UT Modules/MOD08 - Programming Paradigms/pp/homework1\ListExp.g4 by ANTLR 4.7.2
package pp.s1855034.q1_4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ListExpParser}.
 */
public interface ListExpListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ListExpParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(ListExpParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ListExpParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(ListExpParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code paren}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void enterParen(ListExpParser.ParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code paren}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void exitParen(ListExpParser.ParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code letter}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void enterLetter(ListExpParser.LetterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code letter}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void exitLetter(ListExpParser.LetterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code concat}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void enterConcat(ListExpParser.ConcatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code concat}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void exitConcat(ListExpParser.ConcatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code list}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void enterList(ListExpParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code list}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void exitList(ListExpParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code progexp}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void enterProgexp(ListExpParser.ProgexpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code progexp}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void exitProgexp(ListExpParser.ProgexpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code digit}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void enterDigit(ListExpParser.DigitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code digit}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void exitDigit(ListExpParser.DigitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cons}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void enterCons(ListExpParser.ConsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cons}
	 * labeled alternative in {@link ListExpParser#listExp}.
	 * @param ctx the parse tree
	 */
	void exitCons(ListExpParser.ConsContext ctx);
}