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

-- import Data.List -- sorBy
-- import Data.Function -- compare

data ListBag a = LB [(a, Int)]
  deriving (Show, Eq)

-- ################

-- Return True if an element is present in the ListBag
findElem :: Eq a => a -> ListBag a -> Bool
findElem a (LB []) = False
findElem a (LB ((x,i):xs)) = if x == a then True else findElem a (LB xs)

-- Return True if the ListBag is well formed
wf :: Eq a => ListBag a -> Bool
wf (LB []) = True 
wf (LB ((x,i):xs)) = if (findElem x (LB xs)) then False else wf (LB xs)

-- ################

-- Returns an empty ListBag
empty :: ListBag a
empty = LB []

-- ################

-- Return a ListBag containing just one occurrence of element v
singleton :: a -> ListBag a
singleton v = LB [(v, 1)]

-- ################

-- Return the number of times the item appears in the List
myCountOcc item [] = 0
myCountOcc item (x:xs) = if x == item then 1 + myCountOcc item xs else myCountOcc item xs

-- Return a tuple list of type (element, multiplicity)
toTupleList [] = []
toTupleList (x:xs) = (x,1+myCountOcc x xs) : toTupleList (filter (/= x) xs)

-- Return a ListBag containing all and only the elements of lst, each with the right multiplicity
fromList :: Eq a => [a] -> ListBag a
fromList lst = LB (toTupleList lst)    

-- ################

-- Return True if and only if bag is empty
isEmpty :: ListBag a -> Bool
isEmpty (LB []) = True
isEmpty (LB lb) = False

-- ################

-- Return the multiplicity of v in the ListBag bag if v is an element of bag, and 0 otherwise
mul :: Eq a => a -> ListBag a -> Int
mul v (LB []) = 0 
mul v (LB ((x,i):xs)) = if v == x then i else mul v (LB xs)

-- ################

-- Returns a list containing all the elements of the ListBag bag,
-- each one repeated a number of times equal to its multiplicity
toList :: ListBag a -> [a]
toList (LB []) = []
toList (LB ((x,i):xs)) = (replicate i x) ++ toList (LB xs)

-- ################

-- Return the ListBag obtained by adding all the elements of bag' to bag
sumBag :: Eq a => ListBag a -> ListBag a -> ListBag a
sumBag (LB a) (LB b) = fromList ((toList (LB a)) ++ (toList (LB b)))

-- ################

-- foldl (+) 0 (fromList [1,2,3])
instance Foldable ListBag where
  foldr f acc (LB lb) = foldr f acc (toList (LB lb))

-- ################
                          

--instance Functor ListBag where
fmap f (LB lb) = fromList (map f (toList (LB lb))) -- how to infer Eq type fot ListBag??

-- We can not implement the Functor fmap since the fromList method
-- that is used inside requires a Eq a => ListBag a and not a generic one.