module Palet(Palet, newP, destinationP, netP) where

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet
newP city weight = Pal city weight

destinationP :: Palet -> String
destinationP (Pal name_city _) = name_city

netP :: Palet -> Int
netP (Pal _ weight) = weight