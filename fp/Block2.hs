module Block2 where

import FPPrac.Trees
import Data.List
import Data.Char
import Test.QuickCheck
import Data.Maybe

import Foreign.Marshal.Unsafe

-- Ex 1
data Tree1a = Leaf1a Int
            | Node1a Int Tree1a Tree1a
            deriving (Show, Eq)

pp1a :: Tree1a -> RoseTree
pp1a (Leaf1a x) = RoseNode (show x) []
pp1a (Node1a x t1 t2) = RoseNode (show x) [pp1a t1, pp1a t2]

tree1a :: Tree1a
tree1a = Node1a 1
            (Node1a 2
                (Leaf1a 3) (Node1a 3
                    (Node1a 4
                        (Leaf1a 5) (Leaf1a 5))
                    (Leaf1a 4)))
            (Leaf1a 2)

data Tree1b = Leaf1b (Int, Int)
            | Node1b (Int, Int) Tree1b Tree1b
            deriving (Show, Eq)

pp1b :: Tree1b -> RoseTree
pp1b (Leaf1b x) = RoseNode (show x) []
pp1b (Node1b x t1 t2) = RoseNode (show x) [pp1b t1, pp1b t2]

tree1b :: Tree1b
tree1b = Node1b (1, 1)
            (Node1b (2, 2)
                (Leaf1b (3, 3)) (Leaf1b (3, 3)))
            (Leaf1b (2, 2))

data Tree1c = Leaf1c Int
            | Node1c Tree1c Tree1c
            deriving (Show, Eq)

pp1c :: Tree1c -> RoseTree
pp1c (Leaf1c x) = RoseNode (show x) []
pp1c (Node1c t1 t2) = RoseNode (show "") [pp1c t1, pp1c t2]

tree1c :: Tree1c
tree1c = Node1c --1
            (Node1c --2
                (Leaf1c 3) (Leaf1c 3))
            (Node1c --2
                (Node1c
                    (Leaf1c 4) (Leaf1c 4))
                (Leaf1c 3))

data Tree1d = Leaf1d (Int, Int)
            | Node1d [Tree1d]
            deriving (Show, Eq)

pp1d :: Tree1d -> RoseTree
pp1d (Leaf1d (x, y)) = RoseNode (show (x, y)) []
pp1d (Node1d ts) = RoseNode (show "") (map pp1d ts)

tree1d :: Tree1d
tree1d = Node1d --1
            [Node1d --2
                [Leaf1d (1, 3), Leaf1d (1, 3)],
            Node1d --2
                [Leaf1d (1, 3),
                    Node1d [Leaf1d (1, 4), Leaf1d (1, 4),
                        Node1d [Leaf1d (1, 5)],
                    Leaf1d (1, 4)],
                Leaf1d (1, 3)],
            Leaf1d (1, 2)]

class PP a where
    pp :: a -> RoseTree

instance PP Tree1a where
    pp = pp1a

instance PP Tree1b where
    pp = pp1b

instance PP Tree1c where
    pp = pp1c

instance PP Tree1d where
    pp = pp1d

-- Ex 2
treeAdd :: Int -> Tree1a -> Tree1a
treeAdd n = mapTree (+n)
-- treeAdd n (Leaf1a x) = Leaf1a (x+n)
-- treeAdd n (Node1a x t1 t2) = Node1a (x+n) (treeAdd n t1) (treeAdd n t2)

treeSquare :: Tree1a -> Tree1a
treeSquare = mapTree (^2)
-- treeSquare (Leaf1a x) = Leaf1a (x^2)
-- treeSquare (Node1a x t1 t2) = Node1a(x^2) (treeSquare t1) (treeSquare t2)

mapTree :: (Int -> Int) -> Tree1a -> Tree1a
mapTree f (Leaf1a x) = Leaf1a (f x)
mapTree f (Node1a x t1 t2) = Node1a (f x) (mapTree f t1) (mapTree f t2)

addNode :: Tree1b -> Tree1a
addNode (Leaf1b (x, y)) = Leaf1a (x + y)
addNode (Node1b (x, y) t1 t2) = Node1a (x + y) (addNode t1) (addNode t2)

mapTree1b :: ((Int, Int) -> Int) -> Tree1b -> Tree1a
mapTree1b f (Leaf1b x) = Leaf1a (f x)
mapTree1b f (Node1b x t1 t2) = Node1a (f x) (mapTree1b f t1) (mapTree1b f t2)
-- Demonstrate with:    showRoseTree $ pp $ mapTree1b (\(x, y) -> x*y) tree1b

-- Ex 3
binMirror1a :: Tree1a -> Tree1a
binMirror1a (Leaf1a x) = Leaf1a x
binMirror1a (Node1a x t1 t2) = Node1a x (binMirror1a t2) (binMirror1a t1)

class BinMirror a where
    binMirror :: a -> a

instance BinMirror Tree1a where
    binMirror = binMirror1a

binMirror1d :: Tree1d -> Tree1d
binMirror1d (Leaf1d (x, y)) = Leaf1d (y, x)
binMirror1d (Node1d ts) = Node1d (map binMirror1d $ reverse ts)

instance BinMirror Tree1d where
    binMirror = binMirror1d

-- Ex 4
data TreeInt = LeafInt
            | NodeInt Int TreeInt TreeInt
            deriving (Show, Eq)

treeInt :: TreeInt
treeInt = NodeInt 5
            (NodeInt 3
                (NodeInt 2
                    (NodeInt 1
                        (NodeInt 1
                            (LeafInt) (LeafInt))
                        (LeafInt))
                    (LeafInt))
                (NodeInt 4
                    (LeafInt) (LeafInt)))
            (NodeInt 8
                (NodeInt 6
                    (NodeInt 6
                        (LeafInt) (LeafInt))
                    (NodeInt 7
                        (LeafInt) (LeafInt)))
                (NodeInt 9
                    (LeafInt) (LeafInt)))

ppInt :: TreeInt -> RoseTree
ppInt LeafInt = RoseNode (show "") []
ppInt (NodeInt x t1 t2) = RoseNode (show x) [(ppInt t1), (ppInt t2)]

instance PP TreeInt where
    pp = ppInt

insertTree :: Int -> TreeInt -> TreeInt
insertTree n LeafInt = NodeInt n LeafInt LeafInt
insertTree n (NodeInt x t1 t2)
            | (n <= x) = NodeInt x (insertTree n t1) t2
            | otherwise = NodeInt x (t1) (insertTree n t2)

makeTreeRec :: [Int] -> TreeInt
makeTreeRec [] = LeafInt
makeTreeRec [x] = NodeInt x LeafInt LeafInt
makeTreeRec (x:xs) = (insertTree x (makeTreeRec xs))

makeTreeFold :: [Int] -> TreeInt
makeTreeFold = foldr insertTree LeafInt

makeList :: TreeInt -> [Int]
makeList LeafInt = []
makeList (NodeInt x t1 t2) = (makeList t1) ++ [x] ++ (makeList t2)

sortList :: [Int] -> [Int]
sortList = makeList . makeTreeFold

instance Arbitrary TreeInt where
    arbitrary = sized arbTreeInt

arbTreeInt :: Int -> Gen TreeInt
arbTreeInt 0 = pure LeafInt
arbTreeInt n = frequency
    [   (1, pure LeafInt),
        (2, NodeInt <$> arbitrary
                    <*> (arbTreeInt (n `div` 2))
                    <*> (arbTreeInt (n `div` 2)))]

genTreeInt :: Int -> TreeInt
genTreeInt = unsafeLocalState . generate . arbTreeInt

prop_sortList :: [Int] -> Bool
prop_sortList xs = sort xs == sortList xs

sortTree :: TreeInt -> TreeInt
sortTree = makeTreeFold . makeList

prop_sortTree :: TreeInt -> Bool
prop_sortTree t = sort (makeList t) == (makeList $ sortTree t)

-- Ex 5
subtreeAt :: TreeInt -> Int -> Maybe TreeInt
subtreeAt LeafInt _ = Nothing
subtreeAt t@(NodeInt x t1 t2) n
        | n == x = Just t
        | n <= x = subtreeAt t1 n
        | otherwise = subtreeAt t2 n

-- Ex 6
cutOffAt :: Int -> Tree1a -> Tree1a
cutOffAt n t = f t n 0
    where
        f :: Tree1a -> Int -> Int -> Tree1a
        f (Leaf1a x) _ _ = Leaf1a x
        f (Node1a x t1 t2) n m
            | n <= m = Leaf1a x
            | otherwise = Node1a x (f t1 n (m+1)) (f t2 n (m+1))

data BinTree a = Leaf
                | Node a (BinTree a) (BinTree a)
                deriving (Show, Eq)

binTree :: Num a => BinTree a
binTree = Node 1
            (Node 2
                (Node 3
                    (Leaf)
                    (Node 4
                        (Leaf)
                        (Leaf)))
                (Node 3
                    (Leaf)
                    (Leaf)))
            (Node 2
                (Leaf) (Leaf))

instance Show a => PP (BinTree a) where
    pp Leaf = RoseNode [] []
    pp (Node a t1 t2) = RoseNode (show a) [pp t1, pp t2]

instance BinMirror (BinTree a) where
    binMirror Leaf = Leaf
    binMirror (Node a t1 t2) = Node a (binMirror t2) (binMirror t1)

-- fmap :: Functor f => (a -> b) -> f a -> f b
instance Functor BinTree where
    fmap f Leaf = Leaf
    fmap f (Node a t1 t2) = Node (f a) (f <$> t1) (f <$> t2)

data MyList a = Nil
            | Cons a (MyList a)
            deriving (Show, Eq)

mylst = Cons 1 $ Cons 2 $ Cons 3 $ Nil

instance Functor MyList where
    fmap f Nil = Nil
    fmap f (Cons a l) = Cons (f a) (f <$> l)

fromList :: [a] -> MyList a
fromList [] = Nil
fromList (x:xs) = Cons x (fromList xs)

-- First Functor Law:   fmap id = id
prop_functor1 :: Real a => [a] -> Bool
prop_functor1 xs = let l = fromList xs in fmap id l == id l

-- Second Functor Law:  fmap (f . g) = fmap f . fmap g
prop_functor2 :: Real a => [a] -> Fun a a -> Fun a a -> Bool
prop_functor2 xs (Fn f) (Fn g) = let l = fromList xs in fmap (f . g) l == (fmap f . fmap g) l

-- Ex 9
-- name, age, sex, pos
data Person = Person {  name :: String,
                        age :: Int,
                        sex :: String,
                        res :: String
                    } deriving Show

type Database = [Person]

db :: Database
db = [p1, p2, p3]

p1 :: Person
p1 = Person "Denys" 22 "Male" "Barneveld"
p2 :: Person
p2 = Person "Myrthe" 20 "Female" "Barneveld"
p3 :: Person
p3 = Person "Sjoerd" 21 "Male" "Veenendaal"

plus :: Int -> [Person] -> [Person]
plus _ [] = []
plus n (x:xs) = x {age = ((age x) + n)} : plus n xs

names :: [Person] -> [String]
names = map name

-- Ex 10
getInt :: IO Integer
getInt = fmap read getLine

-- Ex 11
data BinTree' a b = Leaf' b
                | Node' a (BinTree' a b) (BinTree' a b)
                deriving (Show, Eq)

-- Ex 12
data Value = Const Int
            | Id String
            deriving Show

lr :: [Char] -> Char -> ([Char], [Char])
lr xs o = f xs o 0 []
    where
        f [] _ _ ls = (ls, [])
        f (x:xs) o p ls
            | x == '(' = f xs o (p+1) (ls ++ [x])
            | x == ')' = f xs o (p-1) (ls ++ [x])
            | p > 0 = f xs o p (ls ++ [x])
            | x == o = (ls, xs)
            | otherwise = f xs o p (ls ++ [x])

parseExpr :: [Char] -> (BinTree' Char Value)
parseExpr xs
            | snd r /= [] = Node' '+' t1 t2
            | otherwise = parseTerm xs
            where
                r = lr xs '+'
                t1 = parseTerm $ fst r
                t2 = parseExpr $ snd r

parseTerm :: [Char] -> (BinTree' Char Value)
parseTerm xs
            | snd r /= [] = Node' '*' t1 t2
            | otherwise = parseFactor xs
            where
                r = lr xs '*'
                t1 = parseFactor $ fst r
                t2 = parseTerm $ snd r

parseFactor :: [Char] -> (BinTree' Char Value)
parseFactor ('(':xs) = parseExpr $ init xs
parseFactor (x:xs)
            | isDigit x = Leaf' (Const (read [x]))
            | isLetter x = Leaf' (Id [x])

-- Ex 13
data Token = Number Int
           | Identifier String
           | OpenP
           | CloseP
           | Add
           | Mult
           deriving (Show, Eq)

tokenizer :: String -> [Token]
tokenizer [] = []
tokenizer ('(':xs) = OpenP : tokenizer xs
tokenizer (')':xs) = CloseP : tokenizer xs
tokenizer ('+':xs) = Add : tokenizer xs
tokenizer ('*':xs) = Mult : tokenizer xs
tokenizer (' ':xs) = tokenizer xs
tokenizer (x:xs)
        | isDigit x = Number (read (x : takeWhile isDigit xs)) : tokenizer (dropWhile isDigit xs)
        | isLetter x = Identifier (x : takeWhile isAlphaNum xs) : tokenizer (dropWhile isAlphaNum xs)

-- Ex 14
lrT :: [Token] -> Token -> ([Token], [Token])
lrT xs o = f xs o 0 []
    where
        f [] _ _ ls = (ls, [])
        f (x:xs) o p ls
            | x == OpenP = f xs o (p+1) (ls ++ [x])
            | x == CloseP = f xs o (p-1) (ls ++ [x])
            | p > 0 = f xs o p (ls ++ [x])
            | x == o = (ls, xs)
            | otherwise = f xs o p (ls ++ [x])

parseExpr' :: [Token] -> BinTree' Token Token
parseExpr' xs
        | snd r /= [] = Node' Add t1 t2
        | otherwise = parseTerm' xs
        where
            r = lrT xs Add
            t1 = parseTerm' $ fst r
            t2 = parseExpr' $ snd r

parseTerm' :: [Token] -> BinTree' Token Token
parseTerm' xs
        | snd r /= [] = Node' Mult t1 t2
        | otherwise = parseFactor' xs
        where
            r = lrT xs Mult
            t1 = parseFactor' $ fst r
            t2 = parseTerm' $ snd r

parseFactor' :: [Token] -> BinTree' Token Token
parseFactor' (OpenP:xs) = parseExpr' $ init xs
parseFactor' ((Number n):xs) = Leaf' (Number n)
parseFactor' ((Identifier s):xs) = Leaf' (Identifier s)

-- Ex 15
-- second argument is a lookup table of tuples for identifiers
eval :: [Char] -> [([Char], Int)] -> Int
eval = evalTree . parseExpr' . tokenizer

evalTree :: BinTree' Token Token -> [([Char], Int)] -> Int
evalTree (Node' Add t1 t2) fs = (evalTree t1 fs) + (evalTree t2 fs)
evalTree (Node' Mult t1 t2) fs = (evalTree t1 fs) * (evalTree t2 fs)
evalTree (Leaf' (Identifier xs)) (f:fs)
                        | xs == fst f = snd f
                        | otherwise = evalTree (Leaf' (Identifier xs)) fs
evalTree (Leaf' (Number n)) fs = n

prop_eval1 = eval "(2+3)*9" [] == 29
prop_eval2 = eval "r2d2+1+1" [("r2d2", 4)] == 6
prop_eval3 = eval "((5+(3*(2+3))*3)+2)" [] == 52
prop_eval4 = eval "(the * answer) + (to + (the + universe) * is)" [("the", 2), ("answer", 5), ("to", 14), ("universe", 4), ("is", 3)] == 42
