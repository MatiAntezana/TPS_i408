module Route ( Route, newR, inOrderR, inRouteR ) where

import Data.Char (isAlpha)
import Data.List (elemIndex, nub)


-- Defino el tipo de dato Route, que es un constructor de datos que acepta una lista de Strings.
data Route = Rou[String] deriving (Eq, Show)


-- Esta función es responsable de crear una nueva ruta a partir de una lista de ciudades.
-- nub elimina los duplicados de la lista de ciudades.
newR :: [String] -> Route
newR list_cities | null list_cities = error "Error: La lista de ciudades no puede estar vacía."
                 | any null list_cities = error "Error: Ninguna ciudad en la lista puede estar vacía."
                 | any (not . all isAlpha) list_cities = error "Error: Los nombres de las ciudades en la lista solo pueden contener letras."
                 | length list_cities /= length (nub list_cities) = error "Error: La lista de ciudades no puede contener duplicados."
                 | otherwise = Rou list_cities


-- Esta función toma una ruta y dos ciudades y devuelve True si la primera ciudad aparece antes que la segunda en la ruta. 
-- Si city1 o city2 no se encuentran en la lista ciudades, entonces elemIndex devolvería Nothing. Como estamos usando el patrón de guarda, no se ejecutaría la primera guarda y pasaría directamente a la segunda guarda, devolviendo False.
-- elemIndex :: Eq a => a -> [a] -> Maybe Int
-- elemIndex devuelve Just idx de la primera ocurrencia del elemento en la lista o Nothing si el elemento no está en la lista.
inOrderR :: Route -> String -> String -> Bool

inOrderR (Rou list_cities) city1 city2 | null city1 || null city2 = error "Error: Ninguna ciudad puede estar vacía."
                                       | not (all isAlpha city1) || not (all isAlpha city2) = error "Error: Los nombres de las ciudades solo pueden contener letras." 
                                       | city1 == city2 = True -- Para que me permita poner una ciudad seguida de la otra en la pila cuando son iguales
                                       | Just idx1 <- elemIndex city1 list_cities, Just idx2 <- elemIndex city2 list_cities = idx1 < idx2
                                       | otherwise = False


-- Esta función toma una ruta y una ciudad y devuelve True si la ciudad se encuentra en la ruta.
-- elem :: (Foldable t, Eq a) => a -> t a -> Bool
-- Devuelve True si un elemento está en una lista.
inRouteR :: Route -> String -> Bool
inRouteR (Rou list_cities) city | null city = error "Error: La ciudad no puede estar vacía."
                                | not (all isAlpha city) = error "Error: El nombre de la ciudad solo puede contener letras."
                                | otherwise = city `elem` list_cities



-- Preguntas: ---------------------------------------------------------------------
-- Deberia chequear en inOrderR y inRouteR todo lo que chequeo en newR? O como (quiero creer) que los teste se hacen creando variables y para eso tengo que pasar primero por newR, y por lo tanto no es necesario.
-- Xq si lo testeo desde el gchi sin variables, deberia chequear todo.
