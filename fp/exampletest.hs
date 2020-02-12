import Data.List
import Data.Char
import Data.Maybe
import Data.Either
import System.IO
import Control.Applicative
import Data.Monoid

-- Q1
lkp :: Eq k => k -> [(k, v)] -> Maybe v
lkp _ []                         = Nothing
lkp k ((l,v):xs) | k == l        = Just v
                    | otherwise  = lkp k xs

lfts :: [Either a b] -> [a]
lfts xs = [ l | (Left l) <- xs ]

jsts :: [Maybe a] -> Maybe [a]
jsts []     = pure []
jsts (x:xs) = (:) <$> x <*> jsts xs

frst :: [Maybe a] -> Maybe a
frst xs = foldl (\x y -> if (isJust x) then x else y) Nothing xs

rng :: IO [Int]
rng = (\x y -> [read x, read y])
    <$> (putStrLn "First value?" *> getLine)
    <*> (putStrLn "Second value?" *> getLine)

-- Q2
splitMatrix :: [[a]] -> ([[a]], [[a]], [[a]], [[a]])
splitMatrix xss = (nw, ne, sw, se)
    where
        (v1, v2) = splitAt n xss
        (nw, ne) = unzip $ map (splitAt n) v1
        (sw, se) = unzip $ map (splitAt n) v2
        n = length xss `div` 2

data Range a = Range a a deriving (Eq, Show)

instance (Ord a, Bounded a) => Monoid (Range a) where
    mempty = Range minBound maxBound
    (Range a b) `mappend` (Range x y) = Range (min a x) (max b y)

data QuadTree a = QNode Int (Range a)   (QuadTree a)
                                        (QuadTree a)
                                        (QuadTree a)
                                        (QuadTree a)
                | QLeaf Int a

getRange :: QuadTree a -> Range a
getRange (QNode _ r _ _ _ _) = r
-- getRange (QLeaf n _) = n

makeQuadTree :: (Bounded a, Ord a) => [[a]] -> QuadTree a
makeQuadTree [[x]] = QLeaf 1 x
makeQuadTree xss = QNode n r ne' nw' se' sw'
    where
        (ne, nw, se, sw) = splitMatrix xss
        ne' = makeQuadTree ne
        nw' = makeQuadTree nw
        se' = makeQuadTree se
        sw' = makeQuadTree sw
        r = getRange ne' <>
            getRange nw' <>
            getRange se' <>
            getRange sw'
        n = length (xss) `div` 2

compress :: QuadTree Int -> QuadTree Int
compress (QLeaf n x) = QLeaf n x
compress (QNode n r@(Range l h) nw ne sw se)
    | l == h    = QLeaf (2*n) l
    | otherwise = QNode n r nw' ne' sw' se'
    where
        (nw', ne', sw', se') = (compress nw, compress ne,
                                compress sw, compress se)

-- Q3
data A z x =  B {y::x}

instance Functor (A z) where
    fmap f = B . f . y

-- Q4
r :: [Int]
r = 1 : zipWith (+) r (repeat 1)
--r = [1..]

-- Q5
instance Functor ((,,) a b) where
    fmap f (a, b, c) = (a, b, f c)

-- fmap (f . g) (a, b, c) == (fmap f . fmap g) (a, b, c)

-- fmap (f . g) (a, b, c) = (a, b, (f.g) c)
--     = (a, b, f(g c)) = fmap f (a, b, g c)
--     = fmap f (fmap g (a, b, c)) = (fmap f . fmap g) (a, b, c)

instance (Monoid a, Monoid b) => Applicative ((,,) a b) where
    pure c = (mempty, mempty, c)
    (a, b, f) <*> (x, y, z) = (a <> x, b <> y, f z)

-- pure f <*> pure x = pure (f x)
-- = (mempty, mempty, f) <*> (mempty, mempty, x)
-- = (mempty <> mempty, mempty <> mempty, f <> x)
-- = (mempty, mempty, f x)
-- = pure (f x)

x = (*) <$> ("a", [1], 10) <*> ("b", [2, 3], 20)

--------------------------------------------------------------------------------
add35 :: Int -> Int
add35 n = sum $ [ x | x <- [1..(n-1)], x `mod` 3 == 0 || x `mod` 5 == 0 ]

perfect :: Int -> [Int]
perfect m = [ x | x <- [1..m-1], isPerfect x]

isPerfect :: Int -> Bool
isPerfect m = (sum [ x | x <- [1..m-1], x `mod` m == 0] ) == m

-- -- 1 : 2 : 3 : []
-- myMap :: (a -> b) -> [a] -> [b]
-- myMap f []      = []
-- myMap f xs  = foldl (:) (\f x -> )f xs []
