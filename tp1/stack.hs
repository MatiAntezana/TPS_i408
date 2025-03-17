module Stack(Stack, newS, freeCellsS, stackS, netS, holdsS, popS) where

import Route
import Palet


-- Defino el tipo de dato Stack, que es un constructor de datos que acepta una lista de Palets y un entero.
-- La lista de Palets representa los palets apilados en la pila, y el entero representa la cantidad máxima de palets que se pueden apilar.
data Stack = Sta[Palet] Int deriving (Eq, Show)


-- Esta función crea una pila con una capacidad específica. La capacidad se pasa como parámetro, y la pila comienza vacía.
newS :: Int -> Stack
newS max_palets | max_palets<= 0 = error "Error: La capacidad de la pila debe ser positiva."
                | otherwise = Sta [] max_palets  


-- Devuelve la cantidad de celdas libres en la pila, que será la capacidad restante de la pila.
freeCellsS :: Stack -> Int
freeCellsS (Sta palets max_palets) = max_palets - length palets


-- Apila un palet en la pila. 
-- La verificación la hacemos en el camion
stackS :: Stack -> Palet -> Stack
stackS (Sta palets max_palets) palet = Sta ([palet]++ palets) max_palets


-- Devuelve el peso neto de los palets apilados, sumando los pesos de todos los palets en la pila.
-- map :: (a -> b) -> [a] -> [b]
-- Map es una función que aplica una función a cada elemento de una lista y devuelve una nueva lista con los resultados.
-- map netP palets aplica netP a cada Palet de la lista palets, generando una lista de pesos.
-- sum suma todos los elementos de la lista de pesos.
netS :: Stack -> Int

netS (Sta palets _) = sum (map netP palets)


-- Esta función debe verificar si un Palet se puede apilar en una Stack, cumpliendo dos condiciones:
-- 1. El peso neto de los palets apilados más el peso del palet a apilar no supera los 10 toneladas.
-- 2. El palet a apilar debe tener un destino anterior o igual al último palet apilado.
-- \p -> inOrderR ruta (destinationP palet) (destinationP p): Función anónima que verifica si el nuevo palet tiene un destino antes o igual que p en ruta.

holdsS :: Stack -> Palet -> Route -> Bool
holdsS (Sta palets max_capacity) palet ruta | (netS (Sta palets max_capacity) + netP palet) > max_capacity = False -- "Error: El peso total de los palets excede las 10 toneladas."
                                          | not (all (\p -> inOrderR ruta (destinationP palet) (destinationP p)) palets) = False --"Error: El destino del nuevo palet debe ser anterior o igual al último palet apilado."
                                          | otherwise = True  -- Si todo está correcto, se puede apilar el palet

-- Esta función elimina todos los palets con un destino específico de la pila.
-- filter :: (a -> Bool) -> [a] -> [a]
-- Filter es una función que toma una función booleana y una lista, y devuelve una nueva lista con los elementos que cumplen la condición.
-- destinationP p /= destination_city: Función anónima que verifica si el destino de un palet es diferente al destino de la ciudad.
popS :: Stack -> String -> Stack
popS (Sta palets max_palets) destination_city | null palets = error "Error: La pila está vacía, no se puede realizar la operación."
                                              | otherwise = Sta (filter (\p -> destinationP p /= destination_city) palets) max_palets
