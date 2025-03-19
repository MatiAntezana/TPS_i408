import Control.Exception
import System.IO.Unsafe

import Route
import Palet
import Stack
import Truck
import Control.Applicative (Alternative(empty))

testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()

data Test = Tes()

-- Test route
empty_route = newR []

route_duplicate_consecutive :: Route
route_duplicate_consecutive = newR ["Roma","Roma","Paris"]

route_duplicate_no_consecutive = newR ["Roma","Paris","Roma"]

route_small = newR ["Roma","Paris"]
truck_with_space = newT 10 5 route_duplicate_no_consecutive

-- Test Palet

palet_without_city = newP "" 2
palet_city_with_number = newP "Salta23" 4
palet_with_weight_zero = newP "Roma" 0
palet1 = newP "Roma" 5
palet2 = newP "Paris" 5

-- Test Stack

stack_capacity_zero = newS 0

stack_small = newS 3

stack_fail_pop = popS stack_small "Roma"

-- Test Truck

new_truck = newT 1 2 route_small

new_truck1 = loadT new_truck palet1
new_truck2 = loadT new_truck1 palet1
new_truck3 = loadT new_truck2 palet1

test :: [Bool]
test = [
    testF(empty_route), -- Caso de Ruta vacia
    testF(route_duplicate_consecutive), -- Caso de ciudad duplicadas seguidas
    route_duplicate_no_consecutive == newR ["Roma","Paris","Roma"], --Si es aceptado
    testF(inRouteR route_small ""), -- Caso de buscar una ciudad vacia
    testF(inRouteR route_small "Paris23"), -- Caso de buscar una ciudad con números
    inRouteR route_small "Paris" == True, -- Encuentra bien la ciudad
    testF(palet_without_city), -- Caso de crear palet sin ciudad
    testF(palet_city_with_number), -- Caso de crear palet con una ciudad con número
    testF(palet_with_weight_zero), -- Caso de crear un palet con peso = 0
    testF(stack_capacity_zero), -- Caso de crear una pila con capacidad 0
    popS stack_small "Roma" == stack_small, -- Volverá la misma pila si es que no hay elementos para sacar
    testF(loadT new_truck2 palet1)  -- Volverá el mismo Truck al no poder agregar el palet
    ]