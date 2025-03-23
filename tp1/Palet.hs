module Palet(Palet, newP, destinationP, netP) where
import Data.Char (isAlpha)

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet
newP city weight | null city = error "Error: el destino no puede estar vacío."
                 | not (all isAlpha city) = error "Error: el destino solo puede contener letras."
                 | weight <= 0    = error "Error: el peso debe ser mayor a 0."
                 | otherwise = Pal city weight

destinationP :: Palet -> String
destinationP (Pal nameCity _) = nameCity

netP :: Palet -> Int
netP (Pal _ weight) = weight

