// Generated from C:/UT Modules/MOD08 - Programming Paradigms/pp/block3/cc/tabular\Latex.g4 by ANTLR 4.7.2
package block3.cc.tabular;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LatexParser}.
 */
public interface LatexListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LatexParser#tabular}.
	 * @param ctx the parse tree
	 */
	void enterTabular(LatexParser.TabularContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatexParser#tabular}.
	 * @param ctx the parse tree
	 */
	void exitTabular(LatexParser.TabularContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatexParser#row}.
	 * @param ctx the parse tree
	 */
	void enterRow(LatexParser.RowContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatexParser#row}.
	 * @param ctx the parse tree
	 */
	void exitRow(LatexParser.RowContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatexParser#cell}.
	 * @param ctx the parse tree
	 */
	void enterCell(LatexParser.CellContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatexParser#cell}.
	 * @param ctx the parse tree
	 */
	void exitCell(LatexParser.CellContext ctx);
}