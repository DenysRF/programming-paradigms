// Generated from C:/UT Modules/MOD08 - Programming Paradigms/pp/block6/cc/pascal\FuncPascal6.g4 by ANTLR 4.7.2
package block6.cc.pascal;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FuncPascal6Parser}.
 */
public interface FuncPascal6Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(FuncPascal6Parser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(FuncPascal6Parser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void enterFuncDecl(FuncPascal6Parser.FuncDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void exitFuncDecl(FuncPascal6Parser.FuncDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(FuncPascal6Parser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(FuncPascal6Parser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(FuncPascal6Parser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(FuncPascal6Parser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTrueExpr(FuncPascal6Parser.TrueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTrueExpr(FuncPascal6Parser.TrueExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompExpr(FuncPascal6Parser.CompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompExpr(FuncPascal6Parser.CompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrfExpr(FuncPascal6Parser.PrfExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrfExpr(FuncPascal6Parser.PrfExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCallExpr(FuncPascal6Parser.CallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCallExpr(FuncPascal6Parser.CallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFalseExpr(FuncPascal6Parser.FalseExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFalseExpr(FuncPascal6Parser.FalseExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(FuncPascal6Parser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(FuncPascal6Parser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(FuncPascal6Parser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(FuncPascal6Parser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumExpr(FuncPascal6Parser.NumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumExpr(FuncPascal6Parser.NumExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlusExpr(FuncPascal6Parser.PlusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlusExpr(FuncPascal6Parser.PlusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(FuncPascal6Parser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(FuncPascal6Parser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(FuncPascal6Parser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(FuncPascal6Parser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(FuncPascal6Parser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(FuncPascal6Parser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(FuncPascal6Parser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(FuncPascal6Parser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(FuncPascal6Parser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(FuncPascal6Parser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(FuncPascal6Parser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(FuncPascal6Parser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssStat(FuncPascal6Parser.AssStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssStat(FuncPascal6Parser.AssStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void enterIfStat(FuncPascal6Parser.IfStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void exitIfStat(FuncPascal6Parser.IfStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void enterWhileStat(FuncPascal6Parser.WhileStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void exitWhileStat(FuncPascal6Parser.WhileStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlockStat(FuncPascal6Parser.BlockStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlockStat(FuncPascal6Parser.BlockStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void enterInStat(FuncPascal6Parser.InStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void exitInStat(FuncPascal6Parser.InStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code outStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void enterOutStat(FuncPascal6Parser.OutStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code outStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 */
	void exitOutStat(FuncPascal6Parser.OutStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link FuncPascal6Parser#target}.
	 * @param ctx the parse tree
	 */
	void enterIdTarget(FuncPascal6Parser.IdTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link FuncPascal6Parser#target}.
	 * @param ctx the parse tree
	 */
	void exitIdTarget(FuncPascal6Parser.IdTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#prfOp}.
	 * @param ctx the parse tree
	 */
	void enterPrfOp(FuncPascal6Parser.PrfOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#prfOp}.
	 * @param ctx the parse tree
	 */
	void exitPrfOp(FuncPascal6Parser.PrfOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#multOp}.
	 * @param ctx the parse tree
	 */
	void enterMultOp(FuncPascal6Parser.MultOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#multOp}.
	 * @param ctx the parse tree
	 */
	void exitMultOp(FuncPascal6Parser.MultOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#plusOp}.
	 * @param ctx the parse tree
	 */
	void enterPlusOp(FuncPascal6Parser.PlusOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#plusOp}.
	 * @param ctx the parse tree
	 */
	void exitPlusOp(FuncPascal6Parser.PlusOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#boolOp}.
	 * @param ctx the parse tree
	 */
	void enterBoolOp(FuncPascal6Parser.BoolOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#boolOp}.
	 * @param ctx the parse tree
	 */
	void exitBoolOp(FuncPascal6Parser.BoolOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuncPascal6Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(FuncPascal6Parser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuncPascal6Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(FuncPascal6Parser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link FuncPascal6Parser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(FuncPascal6Parser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link FuncPascal6Parser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(FuncPascal6Parser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link FuncPascal6Parser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(FuncPascal6Parser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link FuncPascal6Parser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(FuncPascal6Parser.BoolTypeContext ctx);
}