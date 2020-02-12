{-# LANGUAGE FlexibleInstances, DeriveGeneric, DeriveAnyClass #-}

module FP_Core where

import GHC.Generics
import FPPrac.Trees

{-
Extension of CoreIntro.hs:
- instructions as program *in* the processor,
- stack now is list of fixed length,i
- clock added
- program counter + stack pointer added,
- instruction EndProg added,
- update operaton (<~) added,
-}

-- ========================================================================

type Stack  = [Int]

type Variable = Int

data Op     = Add | Mul | Sub
            deriving (Show, Generic, ToRoseTree)


data Instr  = PushConst Int
            | Calc Op
            | EndProg
            | PushAddr Int
            | Store Int
            | PushPC
            | EndRep
            deriving (Show, Generic, ToRoseTree)


data Tick = Tick

data Expr = Const Int                   -- for constants
          | BinExpr Op Expr Expr        -- for ``binary expressions''
          | Var Variable                -- for variable, refers to address
            deriving (Show, Generic, ToRoseTree)

data Stmnt = Assign Variable Expr
           | Repeat Expr [Stmnt]
            deriving (Show, Generic, ToRoseTree)

-- ========================================================================
-- Processor functions

xs <~ (i,a) = take i xs ++ [a] ++ drop (i+1) xs
                -- Put value a on position i in list xs

alu op = case op of
                Add -> (+)
                Mul -> (*)
                Sub -> (-)


core :: [Instr] -> (Int,Int,[Int],Stack) -> Tick -> (Int,Int,[Int],Stack)

core instrs (pc,sp,heap,stack) tick =  case instrs!!pc of

        PushConst n -> (pc+1, sp+1, heap, stack <~ (sp,n))

        Calc op     -> (pc+1, sp-1, heap, stack <~ (sp-2,v))
            where
                v = alu op (stack!!(sp-2)) (stack!!(sp-1))

        EndProg     -> (-1, sp, heap, stack)

        PushAddr n  -> (pc+1, sp+1, heap, stack <~ (sp,heap!!n))

        Store n     -> (pc+1, sp-1, heap <~ (n,stack!!(sp-1)), stack)

        PushPC      -> (pc+1,sp+1,heap,stack <~ (sp,pc))

        EndRep      | n == 0    -> (pc+1,sp-2,heap,stack)
                    | otherwise -> (stack!!(sp-2)+2,sp,heap,stack <~ (sp-1, n-1))
                        where n = stack!!(sp-1)
-- ========================================================================

-- example Program for expression: (((2*10) + (3*(4+11))) * (12+5))

-- Tree of this expression of type Expr (result of parsing):
expr = BinExpr Mul
          (BinExpr Add
              (BinExpr Mul
                  (Const 2)
                  (Const 10))
              (BinExpr Mul
                  (Const 3)
                  (BinExpr Add
                      (Const 4)
                      (Const 11))))
          (BinExpr Add
              (Const 12)
              (Const 5))

-- The program that results in the value of the expression (1105):
prog = [ PushConst 2
       , PushConst 10
       , Calc Mul
       , PushConst 3
       , PushConst 4
       , PushConst 11
       , Calc Add
       , Calc Mul
       , Calc Add
       , PushConst 12
       , PushConst 5
       , Calc Add
       , Calc Mul
       , EndProg
       ]

-- Testing
clock      = repeat Tick
emptyStack = replicate 8 0
emptyHeap  = replicate 8 0

test       = putStr
           $ unlines
           $ map show
           $ takeWhile (\(pc,_,_,_) -> pc /= -1)
           $ scanl (core prog) (0,0,emptyHeap,emptyStack) clock

test1      = putStr
           $ unlines
           $ map show
           $ takeWhile (\(pc,_,_,_) -> pc /= -1)
           $ scanl (core $ (repeatProg)) (0,0,emptyHeap,emptyStack) clock

stmnt1 = Assign 7 (BinExpr Add
                        (Const 1)
                        (Const 2)
                    )

expr1 = (BinExpr Add (Const 1) (Const 2))

repeatProg = codeGenRep (Repeat (Const 10) statement)
statement = [(Assign 0 (BinExpr Add (Var 0) (Var 1))), (Assign 1 (BinExpr Add (Var 1) (Const 1)))]

codeGenE :: Expr -> [Instr]
codeGenE e = (helper e) ++ [EndProg]

helper :: Expr -> [Instr]
helper (Const x) = [(PushConst x)]
helper (Var x) = [(PushAddr x)]
helper (BinExpr o e1 e2) = (helper e1) ++ (helper e2) ++ [(Calc o)]

codeGenS :: Stmnt -> [Instr]
codeGenS (Assign x y) = helper y ++ [Store x]

class CodeGen a where
    codeGen :: a -> [Instr]

instance CodeGen Expr where
    codeGen = codeGenE

instance CodeGen Stmnt where
    codeGen = codeGenS

codeGenRep :: Stmnt -> [Instr]
codeGenRep (Repeat (Const y) x) = [PushPC, (PushConst y)] ++ (concat (map codeGen x)) ++ [EndRep, EndProg]
-- codeGenRep (Repeat expr x) = codeGenRep(Repeat (codeGen expr) x)

repHelper :: [Stmnt] -> [Instr]
repHelper [] = []
repHelper (x:xs) = (codeGen x) ++ repHelper xs
