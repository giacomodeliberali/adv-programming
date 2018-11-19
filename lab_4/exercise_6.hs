data Expr a = Const a | Sum (Expr a) (Expr a) | Mul (Expr a) (Expr a)

-- Sum (Const 10) (Mul (Const 3) (Const 5))
instance (Show a) => Show (Expr a) where
    show (Const a) = show a
    show (Sum a b) = "(" ++ (show a) ++ " + " ++ (show b) ++ ")" 
    show (Mul a b) = "(" ++ (show a) ++ " * " ++ (show b) ++ ")"