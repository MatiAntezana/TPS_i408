--module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where
module Truck (Truck, newT, freeCellsT, loadT) where

import Palet
import Stack
import Route
import Text.XHtml (height)

-- Defino el tipo de dato Truck, que es un constructor de datos que acepta una lista de Stacks y una Ruta.
-- La lista de Stacks representa las bahías del camión, y la Ruta representa el recorrido que debe seguir el camión.
data Truck = Tru [Stack] Route deriving (Eq, Show)


-- La función newT debe crear un camión con:
-- Una cantidad de bahías (nro_bahias), que serán las pilas (Stack).
-- Una altura máxima por bahía (altura), que representa la cantidad máxima de palets que puede apilarse en cada una.
-- Una ruta (Route) que el camión debe seguir.
-- replicate :: Int -> a -> [a]
-- Replicate es una función que toma un entero y un elemento, y devuelve una lista con el elemento repetido la cantidad de veces del entero.
module Truck ( Truck, newT, freeCellsT, netT ) where

import Stack
import Route
import Palet

data Truck = Tru [Stack] Route deriving (Eq, Show)

build_list_stack :: Truck -> Int -> Int -> Truck
build_list_stack (Tru list_stack route_) nro_bahias size | length list_stack == nro_bahias = Tru list_stack route_
                                                           | otherwise = build_list_stack (Tru (list_stack ++ [newS size])route_) nro_bahias size
newT :: Int -> Int -> Route -> Truck
newT nro_bahias altura ruta | nro_bahias <= 0 = error "Error: El camión debe tener al menos una bahía."
                            | altura <= 0 = error "Error: La altura de las bahías debe ser positiva."
                            | otherwise = Tru (replicate nro_bahias (newS altura)) ruta


-- La función debe responder cuántas celdas libres hay en todo el camión, sumando las celdas libres de cada bahía (Stack).
-- map: aplica una función a cada elemento de una lista y devuelve una nueva lista con los resultados. 
freeCellsT :: Truck -> Int
freeCellsT (Tru stacks _) = sum (map freeCellsS stacks)


-- La función loadT debe cargar un palet en el camión, es decir, ubicarlo en alguna de sus bahías (Stack) respetando las restricciones.
loadT :: Truck -> Palet -> Truck



-- unloadT :: Truck -> string -> Truck

-- (esta no la vi todavia)
netT :: Truck -> Int
netT (Tru [] _) = 0 -- Caso borde de que no haya ningun stack
netT (Tru (stack_:list_stack) route_) | length list_stack == 0 = netS stack_
                                      | otherwise = netS stack_ + netT (Tru list_stack route_)
