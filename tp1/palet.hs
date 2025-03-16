module Palet(Palet, newP, destinationP, netP) where
import Data.Char (isAlpha)

-- Este tipo de dato define a un Palet, como un valor que consta de dos componentes:
-- 1. Un String que representa el nombre de la ciudad de destino del palet.
-- 2. Un entero que representa el peso neto del palet.
-- La estructura Pal es un constructor de datos que acepta un String (destino) y un Int (peso).
data Palet = Pal String Int deriving (Eq, Show)


-- Esta función es responsable de crear un nuevo palet. Toma dos parámteros:
-- 1. Un String que representa el nombre de la ciudad de destino del palet.
-- 2. Un entero que representa el peso en toneladas del palet.
-- El tipo de retorno es un Palet, lo que significa que esta función devolverá un palet con el destino y peso especificados.
newP :: String -> Int -> Palet
newP city weight | null city = error "Error: el destino no puede estar vacío."
                 | not (all isAlpha city) = error "Error: el destino solo puede contener letras."
                 | weight <= 0    = error "Error: el peso debe ser mayor a 0."
                 | otherwise    = Pal city weight


-- Esta función toma un palet y devuelve el nombre de la ciudad de destino del palet. 
destinationP :: Palet -> String
destinationP (Pal name_city _) = name_city


-- Esta función toma un palet y devuelve su peso en toneladas.
netP :: Palet -> Int
netP (Pal _ weight) = weight



-- Preguntas: ---------------------------------------------------------------------
-- Misma pregunta que hice en route.hs. 