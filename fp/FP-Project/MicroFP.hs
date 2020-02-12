-- Add your names and student numbers to the following file. Do not change anything else, since it is parsed.
-- Student 1: Denys Flederus (s1855034)
-- Student 2: Yernar Kumashev (s1702831)

{-# LANGUAGE TemplateHaskell #-}

module MicroFP where

import Control.Applicative
import PComb
import BasicParsers
-- import Test.QuickCheck
-- import Test.QuickCheck.All
import Debug.Trace

{- FP3.1
Program
Function
Expr
Cond -}
data Program =  Prog [Function] deriving Show

data Function = Func Expr [Expr] Expr deriving Show

data Expr =       Sub Expr Expr
                | Mult Expr Expr
                | Add Expr Expr
                | If Cond Expr Expr  -- first Expr for "if", second Expr for "else"
                | Parens Expr
                | FuncCall String [Expr]
                | Int Integer
                | ID String
                deriving Show

data Cond = Equals Expr Expr
            |Lower Expr Expr
            |Greater Expr Expr
            deriving Show

{- FP4.1
program
function
expr
factor
ordering
-}
program :: Parser Program
program = Prog <$> (sep function (char ';')) <* (char ';')

function :: Parser Function
function = Func <$> expr <*> (some ((ID <$> identifier) <|> (Int <$> integer) ))
            <* symbol ":=" <*> expr

expr :: Parser Expr
expr =  Sub <$> term <* symbol "-" <*> expr
        <|> Add <$> term <* symbol "+" <*> expr
        <|> term

term :: Parser Expr
term = Mult <$> factor <* symbol "*" <*> term
       <|> factor

factor :: Parser Expr
factor = FuncCall <$> identifier <*> parens (sep1 expr (char ','))
        <|> If <$ string "if" <*> (parens ordering) <* string "then" <*> (braces expr) <* string "else" <*> (braces expr)
        <|> Parens <$> (parens expr)
        <|> ID <$> identifier
        <|> Int <$> integer

ordering :: Parser Cond
ordering = Equals <$> expr <* symbol "==" <*> expr
        <|> Greater <$> expr <* symbol ">" <*> expr
        <|> Lower <$> expr <* symbol "<" <*> expr

{- FP3.3
pretty
-}
pretty :: Program -> String
pretty (Prog func) = concat $ prettyFunction <$> func

prettyFunction :: Function -> String
prettyFunction (Func name es e) = prettyExpr (name) ++ " " ++ (concat $ prettyExpr <$> es) ++ ":= " ++ (prettyExpr e) ++ ";"

prettyExpr :: Expr -> String
prettyExpr (Sub e1 e2)     = (prettyExpr e1) ++ "-" ++ (prettyExpr e2)
prettyExpr (Mult e1 e2)    = (prettyExpr e1) ++ "*" ++ (prettyExpr e2)
prettyExpr (Add e1 e2)     = (prettyExpr e1) ++ "+" ++  (prettyExpr e2)
prettyExpr (If cond e1 e2) = "if (" ++ (prettyCond cond) ++ ") then {" ++ (prettyExpr e1) ++ "} else {" ++ (prettyExpr e2) ++ "}"
prettyExpr (Parens e)      = "(" ++ (prettyExpr e) ++ ")"
prettyExpr (FuncCall str es)   = str ++ " " ++ "(" ++ (concat $ prettyExpr <$> es) ++ ")"
prettyExpr (Int i)         = (show i) -- ++ " "
prettyExpr (ID str)        = str ++ " "

prettyCond :: Cond -> String
prettyCond (Equals e1 e2) = (prettyExpr e1) ++ "==" ++ (prettyExpr e2)
prettyCond (Lower e1 e2) = (prettyExpr e1) ++ "<" ++ (prettyExpr e2)
prettyCond (Greater e1 e2) = (prettyExpr e1) ++ ">" ++ (prettyExpr e2)

{- FP3.4
eval
reduce
bind -}
eval :: Program -> [Expr] -> Integer
eval (Prog []) _ = 0
eval (Prog ((Func name ns e):fs)) vs = evalExpr (reduce e ns vs) -- + (eval (Prog fs) vs)

evalExpr :: Expr -> Integer
evalExpr (Sub e1 e2) = (evalExpr e1) - (evalExpr e2)
evalExpr (Mult e1 e2) = (evalExpr e1) * (evalExpr e2)
evalExpr (Add e1 e2) = (evalExpr e1) + (evalExpr e2)
-- evalExpr (If cond e1 e2) = (evalExpr e1) - (evalExpr e2)
evalExpr (Parens e) = evalExpr e
-- evalExpr (FuncCall)
evalExpr (Int i) = i
evalExpr (ID s) = error "cant evaluate strings"

reduce :: Expr -> [Expr] -> [Expr] -> Expr
reduce e [] [] = e
reduce e (n:ns) (v:vs) = (reduce (bind e n v) ns vs)

bind :: Expr -> Expr -> Expr -> Expr
bind (Sub e1 e2) ns vs = Sub (bind e1 ns vs) (bind e2 ns vs)
bind (Mult e1 e2) ns vs = Mult (bind e1 ns vs) (bind e2 ns vs)
bind (Add e1 e2) ns vs = Add (bind e1 ns vs) (bind e2 ns vs)
bind (If cond e1 e2) ns vs = If cond (bind e1 ns vs) (bind e2 ns vs)
bind (Parens e) ns vs = Parens (bind e ns vs)
bind (FuncCall s es) ns vs = FuncCall s (map (bind ns vs) es)
bind (Int i) _ _ = (Int i)
bind (ID id) (ID x) (v)     | x == id = v
                                   | otherwise = (ID id)

-- -- (If (Lower (ID "n") (Int 3)) (Int 1) (Add (FuncCall "fib" [Sub (ID "n") (Int 1)]) (FuncCall "fib" [Sub (ID "n") (Int 2)])))

-- double :: [Expr] -> Integer
-- double = (eval . compile) "double a := a*2;"
--
-- four :: [Expr] -> Integer
-- four = (eval . compile) "four a := (double (a))*2;"
--
-- fib :: [Expr] -> Integer
-- fib = (eval . compile) "fib n := if (n < 3) then { 1 } else { fib (n-1) + fib (n-2) };"

{- FP4.2
TODO: add comment. -}
compile :: String -> Program
compile xs = fst $ head res
        where res = parse program (Stream xs)


-- QuickCheck: all prop_* tests
-- return []
-- check = $quickCheckAll
