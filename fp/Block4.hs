module Block4 where

import FP_Core

import GHC.Generics
import FPPrac.Trees



instance CodeGen Expr where
    codeGen = codeGenE

instance CodeGen Stmnt where
    codeGen = codeGenS
