// Generated from C:/UT Modules/MOD08 - Programming Paradigms/pp/homework1\ListExp.g4 by ANTLR 4.7.2
package pp.s1855034.q1_4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ListExpParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, LETTER=12, DIGIT=13, WS=14;
	public static final int
		RULE_start = 0, RULE_listExp = 1;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "listExp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'::'", "'['", "','", "']'", "'('", "')'", "'{'", "'='", 
			"';'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"LETTER", "DIGIT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ListExp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ListExpParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class StartContext extends ParserRuleContext {
		public ListExpContext listExp() {
			return getRuleContext(ListExpContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ListExpParser.EOF, 0); }
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).exitStart(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(4);
			listExp(0);
			setState(5);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListExpContext extends ParserRuleContext {
		public ListExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listExp; }
	 
		public ListExpContext() { }
		public void copyFrom(ListExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParenContext extends ListExpContext {
		public ListExpContext listExp() {
			return getRuleContext(ListExpContext.class,0);
		}
		public ParenContext(ListExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).enterParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).exitParen(this);
		}
	}
	public static class LetterContext extends ListExpContext {
		public TerminalNode LETTER() { return getToken(ListExpParser.LETTER, 0); }
		public LetterContext(ListExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).enterLetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).exitLetter(this);
		}
	}
	public static class ConcatContext extends ListExpContext {
		public List<ListExpContext> listExp() {
			return getRuleContexts(ListExpContext.class);
		}
		public ListExpContext listExp(int i) {
			return getRuleContext(ListExpContext.class,i);
		}
		public ConcatContext(ListExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).enterConcat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).exitConcat(this);
		}
	}
	public static class ListContext extends ListExpContext {
		public List<ListExpContext> listExp() {
			return getRuleContexts(ListExpContext.class);
		}
		public ListExpContext listExp(int i) {
			return getRuleContext(ListExpContext.class,i);
		}
		public ListContext(ListExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).exitList(this);
		}
	}
	public static class ProgexpContext extends ListExpContext {
		public List<ListExpContext> listExp() {
			return getRuleContexts(ListExpContext.class);
		}
		public ListExpContext listExp(int i) {
			return getRuleContext(ListExpContext.class,i);
		}
		public List<TerminalNode> LETTER() { return getTokens(ListExpParser.LETTER); }
		public TerminalNode LETTER(int i) {
			return getToken(ListExpParser.LETTER, i);
		}
		public ProgexpContext(ListExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).enterProgexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).exitProgexp(this);
		}
	}
	public static class DigitContext extends ListExpContext {
		public TerminalNode DIGIT() { return getToken(ListExpParser.DIGIT, 0); }
		public DigitContext(ListExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).enterDigit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).exitDigit(this);
		}
	}
	public static class ConsContext extends ListExpContext {
		public List<ListExpContext> listExp() {
			return getRuleContexts(ListExpContext.class);
		}
		public ListExpContext listExp(int i) {
			return getRuleContext(ListExpContext.class,i);
		}
		public ConsContext(ListExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).enterCons(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ListExpListener ) ((ListExpListener)listener).exitCons(this);
		}
	}

	public final ListExpContext listExp() throws RecognitionException {
		return listExp(0);
	}

	private ListExpContext listExp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ListExpContext _localctx = new ListExpContext(_ctx, _parentState);
		ListExpContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_listExp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				{
				_localctx = new ListContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(8);
				match(T__2);
				setState(17);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__5) | (1L << T__7) | (1L << LETTER) | (1L << DIGIT))) != 0)) {
					{
					setState(9);
					listExp(0);
					setState(14);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__3) {
						{
						{
						setState(10);
						match(T__3);
						setState(11);
						listExp(0);
						}
						}
						setState(16);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(19);
				match(T__4);
				}
				break;
			case T__5:
				{
				_localctx = new ParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(20);
				match(T__5);
				setState(21);
				listExp(0);
				setState(22);
				match(T__6);
				}
				break;
			case T__7:
				{
				_localctx = new ProgexpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(24);
				match(T__7);
				setState(32);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(25);
						match(LETTER);
						setState(26);
						match(T__8);
						setState(27);
						listExp(0);
						setState(28);
						match(T__9);
						}
						} 
					}
					setState(34);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				}
				setState(35);
				listExp(0);
				setState(36);
				match(T__10);
				}
				break;
			case DIGIT:
				{
				_localctx = new DigitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(38);
				match(DIGIT);
				}
				break;
			case LETTER:
				{
				_localctx = new LetterContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(39);
				match(LETTER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(50);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(48);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new ConcatContext(new ListExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_listExp);
						setState(42);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(43);
						match(T__0);
						setState(44);
						listExp(8);
						}
						break;
					case 2:
						{
						_localctx = new ConsContext(new ListExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_listExp);
						setState(45);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(46);
						match(T__1);
						setState(47);
						listExp(6);
						}
						break;
					}
					} 
				}
				setState(52);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return listExp_sempred((ListExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean listExp_sempred(ListExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\208\4\2\t\2\4\3\t"+
		"\3\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\7\3\17\n\3\f\3\16\3\22\13\3\5\3\24"+
		"\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3!\n\3\f\3\16\3$\13"+
		"\3\3\3\3\3\3\3\3\3\3\3\5\3+\n\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\63\n\3\f\3"+
		"\16\3\66\13\3\3\3\2\3\4\4\2\4\2\2\2>\2\6\3\2\2\2\4*\3\2\2\2\6\7\5\4\3"+
		"\2\7\b\7\2\2\3\b\3\3\2\2\2\t\n\b\3\1\2\n\23\7\5\2\2\13\20\5\4\3\2\f\r"+
		"\7\6\2\2\r\17\5\4\3\2\16\f\3\2\2\2\17\22\3\2\2\2\20\16\3\2\2\2\20\21\3"+
		"\2\2\2\21\24\3\2\2\2\22\20\3\2\2\2\23\13\3\2\2\2\23\24\3\2\2\2\24\25\3"+
		"\2\2\2\25+\7\7\2\2\26\27\7\b\2\2\27\30\5\4\3\2\30\31\7\t\2\2\31+\3\2\2"+
		"\2\32\"\7\n\2\2\33\34\7\16\2\2\34\35\7\13\2\2\35\36\5\4\3\2\36\37\7\f"+
		"\2\2\37!\3\2\2\2 \33\3\2\2\2!$\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#%\3\2\2\2"+
		"$\"\3\2\2\2%&\5\4\3\2&\'\7\r\2\2\'+\3\2\2\2(+\7\17\2\2)+\7\16\2\2*\t\3"+
		"\2\2\2*\26\3\2\2\2*\32\3\2\2\2*(\3\2\2\2*)\3\2\2\2+\64\3\2\2\2,-\f\t\2"+
		"\2-.\7\3\2\2.\63\5\4\3\n/\60\f\b\2\2\60\61\7\4\2\2\61\63\5\4\3\b\62,\3"+
		"\2\2\2\62/\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\5\3\2"+
		"\2\2\66\64\3\2\2\2\b\20\23\"*\62\64";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}