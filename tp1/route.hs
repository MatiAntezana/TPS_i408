module Route ( Route, newR, inOrderR, inRouteR ) where

import Data.Char (isAlpha)
import Data.List (elemIndex, nub)

data Route = Rou[String] deriving (Eq, Show)


-- Esta función toma una lista de ciudades y devuelve True si hay ciudades consecutivas repetidas.
searchConsecutiveDuplicates :: Eq a => [a] -> Bool
searchConsecutiveDuplicates (x:y:xs) = x == y || searchConsecutiveDuplicates (y:xs)
searchConsecutiveDuplicates _ = False


newR :: [String] -> Route
newR listCities  | null listCities = error "Error: La lista de ciudades no puede estar vacía."
                 | any null listCities = error "Error: Ninguna ciudad en la lista puede estar vacía."
                 | (not . all (all isAlpha)) listCities = error "Error: Los nombres de las ciudades en la lista solo pueden contener letras."
                 | searchConsecutiveDuplicates listCities = error "Error: La lista no puede contener ciudades repetidas de forma consecutiva."
                 | otherwise = Rou listCities

inOrderR :: Route -> String -> String -> Bool
inOrderR (Rou listCities) city1 city2  | null city1 || null city2 = False
                                       | not (all isAlpha city1) || not (all isAlpha city2) = False
                                       | Just idx1 <- elemIndex city1 listCities, Just idx2 <- elemIndex city2 listCities = idx1 <= idx2
                                       | otherwise = False
                            
inRouteR :: Route -> String -> Bool
inRouteR (Rou listCities) city  | null city = False
                                | not (all isAlpha city) = False
                                | otherwise = city `elem` listCities

