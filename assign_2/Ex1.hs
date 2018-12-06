-- import Data.List -- sorBy
-- import Data.Function -- compare

module Ex1 (
  ListBag(LB),
  wf,
  empty,
  singleton,
  fromList,
  toList,
  isEmpty,
  mul,
  sumBag
) where

data ListBag a = LB [(a, Int)]
  deriving (Show, Eq)

-- Well formated
find :: Eq a => a -> ListBag a -> Bool
find a (LB []) = False
find a (LB ((x,i):xs)) = if x == a then True else find a (LB xs)

wf :: Eq a => ListBag a -> Bool
wf (LB []) = True 
wf (LB ((x,i):xs)) = if (find x (LB xs)) then False else wf (LB xs)
-- wf (LB lb) = foldl (\isWf x -> False) True (sortBy (compare `on` fst) lb)

-- empty, that returns an empty ListBag
empty :: ListBag a
empty = LB []

-- singleton v, returning a ListBag containing just one occurrence of element v
singleton :: a -> ListBag a
singleton v = LB [(v, 1)]

-- fromList lst, returning a ListBag containing all and only the elements of lst, each with the right multiplicity
{- insertAux :: Eq a => a -> ListBag a -> [(a, Int)] -> ListBag a
insertAux j (LB []) r = LB (r ++ [(j,1)])
insertAux j (LB ((x,i):xs)) r = if x == j then LB (r ++ [(x,i+1)] ++ xs) else insertAux j (LB xs) (r ++ [(x,i)])

insert j (LB lb) = insertAux j (LB lb) [] -}

myCountOcc item [] = 0
myCountOcc item (x:xs) = if x == item then 1 + myCountOcc item xs else myCountOcc item xs

toTupleList [] = []
toTupleList (x:xs) = (x,1+myCountOcc x xs) : toTupleList (filter (/= x) xs)

fromList :: Eq a => [a] -> ListBag a
fromList lst = LB (toTupleList lst)      


-- isEmpty bag, returning True if and only if bag is empty
isEmpty :: ListBag a -> Bool
isEmpty (LB []) = True
isEmpty (LB lb) = False

-- mul v bag, returning the multiplicity of v in the ListBag bag if v is an element of bag, and 0 otherwise
mul :: Eq a => a -> ListBag a -> Int
mul v (LB []) = 0 
mul v (LB ((x,i):xs)) = if v == x then i else mul v (LB xs)

-- toList bag, that returns a list containing all the elements of the ListBag bag,
-- each one repeated a number of times equal to its multiplicity
toList :: ListBag a -> [a]
toList (LB []) = []
toList (LB ((x,i):xs)) = (replicate i x) ++ toList (LB xs)

-- sumBag bag bag', returning the ListBag obtained by adding all the elements of bag' to bag
sumBag :: Eq a => ListBag a -> ListBag a -> ListBag a
sumBag (LB a) (LB b) = fromList ((toList (LB a)) ++ (toList (LB b)))