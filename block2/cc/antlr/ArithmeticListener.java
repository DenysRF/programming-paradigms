// Generated from C:/UT Modules/MOD08 - Programming Paradigms/pp/block2/cc/antlr\Arithmetic.g4 by ANTLR 4.7.2
package block2.cc.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ArithmeticParser}.
 */
public interface ArithmeticListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code parentheses}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParentheses(ArithmeticParser.ParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentheses}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParentheses(ArithmeticParser.ParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code neg}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNeg(ArithmeticParser.NegContext ctx);
	/**
	 * Exit a parse tree produced by the {@code neg}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNeg(ArithmeticParser.NegContext ctx);
	/**
	 * Enter a parse tree produced by the {@code number}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumber(ArithmeticParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code number}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumber(ArithmeticParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code minus}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMinus(ArithmeticParser.MinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code minus}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMinus(ArithmeticParser.MinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mult}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMult(ArithmeticParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMult(ArithmeticParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exp}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExp(ArithmeticParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exp}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExp(ArithmeticParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plus}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlus(ArithmeticParser.PlusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plus}
	 * labeled alternative in {@link ArithmeticParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlus(ArithmeticParser.PlusContext ctx);
}