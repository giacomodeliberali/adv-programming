data Expr a = Const a | Sum (Expr a) (Expr a) | Mul (Expr a) (Expr a) deriving Show

-- foldl (+) 0 (Sum (Const 10) (Const 2))
instance Foldable Expr where
    foldl f acc (Const a) = f acc a
    foldl f acc (Sum a b) = let nextAcc = foldl f acc a 
                            in foldl f nextAcc b
                            