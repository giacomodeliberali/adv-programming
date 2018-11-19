data Expr a = Const a | Sum (Expr a) (Expr a) | Mul (Expr a) (Expr a) deriving Show

-- fmap (*2) (Sum (Const 10) (Const 2))
instance Functor Expr where
    fmap f (Const a) = Const(f a)
    fmap f (Sum a b) = Sum (fmap f a) (fmap f b)
    fmap f (Mul a b) = Mul (fmap f a) (fmap f b)