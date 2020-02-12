// Generated from C:/UT Modules/MOD08 - Programming Paradigms/pp/block3/cc/antlr\TypeCalc.g4 by ANTLR 4.7.2
package block3.cc.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TypeCalcParser}.
 */
public interface TypeCalcListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code str}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterStr(TypeCalcParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code str}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitStr(TypeCalcParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(TypeCalcParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(TypeCalcParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bool}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBool(TypeCalcParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBool(TypeCalcParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code num}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNum(TypeCalcParser.NumContext ctx);
	/**
	 * Exit a parse tree produced by the {@code num}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNum(TypeCalcParser.NumContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equals}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEquals(TypeCalcParser.EqualsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equals}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEquals(TypeCalcParser.EqualsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code hat}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterHat(TypeCalcParser.HatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code hat}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitHat(TypeCalcParser.HatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plus}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlus(TypeCalcParser.PlusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plus}
	 * labeled alternative in {@link TypeCalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlus(TypeCalcParser.PlusContext ctx);
}