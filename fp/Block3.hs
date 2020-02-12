module Block3 where

import Data.List
import Data.Functor
import Data.Either
import Test.QuickCheck

import Text.ParserCombinators.Parsec
import Text.ParserCombinators.Parsec.Language
import qualified Text.ParserCombinators.Parsec.Token as Token

fromLeft' :: Either l r -> l
fromLeft' (Left x) = x

fromRight' :: Either l r -> r
fromRight' (Right x) = x

parser :: Parser a -> String -> a
parser p xs | isLeft res = error $ show $ fromLeft' res
          | otherwise  = fromRight' res
  where res = parse p "" xs

data Expr = Const Integer       -- num
          | Var String          -- identifier
          | Mult Expr Expr      -- *
          | Add Expr Expr       -- +
          | If Cond Expr Expr   -- if then else
          | Dec Expr            -- -1
          | FunCall Expr        -- f(x)
          deriving Show

data Cond = Equals Expr Expr    -- x1 == x2
          deriving Show

languageDef =
    emptyDef { Token.reservedNames   = [ "if", "then", "else", "dec", "function" ]
             , Token.reservedOpNames = [ "+", "*", "(", ")", "==", "="]
            }

lexer = Token.makeTokenParser languageDef

identifier :: Parser String
identifier = Token.identifier lexer

integer :: Parser Integer
integer = Token.integer lexer

parens:: Parser a -> Parser a
parens = Token.parens lexer

symbol :: String -> Parser String
symbol = Token.symbol lexer

reserved :: String -> Parser ()
reserved = Token.reserved lexer

-- <dec> | <term> ('+' <term>)* | <if>
parseExpr :: Parser Expr
parseExpr = parseDec
            <|> parseTerm `chainl1` add
            <|> parseIf
    where
        add = (\_ -> Add) <$> symbol "+"

-- <factor> ('*' <factor>)*
parseTerm :: Parser Expr
parseTerm = parseFactor `chainl1` mult
    where
        mult = (\_ -> Mult) <$> symbol "*"

-- num | identifier | '(' <expr> ')'
parseFactor :: Parser Expr
parseFactor = Const <$> integer
            <|> try(FunCall <$> (identifier *> parens parseExpr))
            <|> Var <$> identifier
            <|> parens parseExpr

-- <expr> '==' <expr>
parseCondition :: Parser Cond
parseCondition = Equals <$> parseExpr <* symbol "==" <*> parseExpr

-- 'if' <condition> 'then' <expr> 'else' <expr>
parseIf :: Parser Expr
parseIf = If <$ reserved "if" <*> parseCondition
            <* reserved "then" <*> parseExpr
            <* reserved "else" <*> parseExpr

-- 'dec' <expr>
parseDec :: Parser Expr
parseDec = Dec <$ reserved "dec" <*> parseExpr

data FunDef = Fun Expr Expr Expr    -- function f(x) = expr
            deriving Show

-- 'function' identifier identifier '=' <expr>
parseFunction :: Parser FunDef
parseFunction = Fun <$ reserved "function"
                        <*> (Var <$> identifier)
                        <*> (Var <$> identifier)
                        <* symbol "="
                        <*> parseExpr

parserFun :: String -> FunDef
parserFun = parser parseFunction

evalFun :: FunDef -> Integer -> Integer
evalFun f p = eval f f p
    where
        eval _ (Fun _ _ (Const i)) _ = i
        eval _ (Fun _ _ (Var _)) v = v
        eval r (Fun f x (Mult a0 a1)) p = (eval r (Fun f x a0) p) * (eval r (Fun f x a1) p)
        eval r (Fun f x (Add a0 a1)) p = (eval r (Fun f x a0) p) + (eval r (Fun f x a1) p)
        eval r (Fun f x (If (Equals a0 a1) t e)) p
            | eval r (Fun f x a0) p == eval r (Fun f x a1) p = eval r (Fun f x t) p
            | otherwise = eval r (Fun f x e) p
        eval r (Fun f x (Dec a)) p = (eval r (Fun f x a) p) - 1
        eval r (Fun f x (FunCall c)) p = evalFun r (eval r (Fun f x c) p)

fib :: Integer -> Integer
fib = (evalFun . parserFun)
    "function fib x = if x == 0 then 1 else (if x == 1 then 1 else fib(dec x)+fib(dec dec x))"

-- Checks if in the evaluation (xˆ2 + 2*x + 1) == (x+1)ˆ2
fn = (evalFun . parserFun) "function f x = x*x + 2*x + 1"
fn' = (evalFun . parserFun) "function f x = (x+1) * (x+1)"
prop_fn n = n >= 0 ==> fn n == fn' n

factorial :: Integer -> Integer
factorial = (evalFun . parserFun) "function factorial x = if x == 0 then 1 else factorial(dec x) * x"
prop_factorial n = n >= 0 ==> factorial n == product [1..n]
