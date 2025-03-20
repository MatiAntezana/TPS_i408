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

route_medium = newR ["Roma","Paris","Caba"]
truck_with_space = newT 10 5 route_duplicate_no_consecutive

-- Test Palet

palet_without_city = newP "" 2
palet_city_with_number = newP "Salta23" 4
palet_with_weight_zero = newP "Roma" 0
palet1 = newP "Roma" 5
palet2 = newP "Paris" 5
palet3 = newP "Caba" 3

-- Test Stack

stack_capacity_zero = newS 0

stack_small = newS 3

stack_fail_pop = popS stack_small "Roma"

-- Test Truck

new_truck_case_1 = newT 1 2 route_small

new_truck_c12 = loadT new_truck_case_1 palet1

new_truck_c13 = loadT new_truck_c12 palet1
new_truck_c14 = loadT new_truck_c13 palet1

-- Caso de desapilar todos los palets que cumplan las condiciones
new_truck_case_2 = newT 2 2 route_small
new_truck_c21 = loadT new_truck_case_2 palet1
new_truck_c22 = loadT new_truck_c21 palet1
new_truck_c23 = loadT new_truck_c22 palet1
new_truck_c24 = loadT new_truck_c22 palet1

new_truck_c25 = loadT new_truck_c22 palet1

new_truck_c26 = unloadT new_truck_c25 "Roma"


-- Caso de desapilar un palet que no esta en la primer pila 
new_truck_case3 = newT 2 3 route_medium

new_truck_c31 = loadT new_truck_case3 palet3
new_truck_c32 = loadT new_truck_c31 palet2
new_truck_c33 = loadT new_truck_c32 palet3
new_truck_c34 = loadT new_truck_c33 palet1

new_truck_c35 = unloadT new_truck_c34 "Roma"

truck_empty = newT 2 2 route_small

new_truck_case4 = newT 2 2 route_small
new_truck_c41 = loadT new_truck_case4 palet2
new_truck_c42 = loadT new_truck_c41 palet1
new_truck_c43 = loadT new_truck_c42 palet1


testRoute :: [Bool]
testRoute = [
    testF(empty_route), -- Caso de Ruta vacia
    testF(route_duplicate_consecutive), -- Caso de ciudad duplicadas seguidas
    testF(inRouteR route_small ""), -- Caso de buscar una ciudad vacia
    testF(inRouteR route_small "Paris23"), -- Caso de buscar una ciudad con números
    testF(inOrderR route_small "" ""), -- No se le puede pasar dos ciudades vacias
    testF(inOrderR route_small "Paris2" "Caba"), -- No se le puede pasar una ciudad con números
    inRouteR route_small "Paris" == True, -- Encuentra bien la ciudad
    not (testF(route_duplicate_no_consecutive)) --Si es aceptado
    ]

testPalet :: [Bool]
testPalet = [
    testF(palet_without_city), -- Caso de crear palet sin ciudad
    testF(palet_city_with_number), -- Caso de crear palet con una ciudad con número
    testF(palet_with_weight_zero) -- Caso de crear un palet con peso = 0
    ]

testStack :: [Bool]
testStack = [
    testF(newS 0), -- No tiene que ser 0 la capcidad de la pila
    testF(newS (-23)), -- No tiene que ser negativo la capacidad
    testF(stack_capacity_zero), -- Caso de crear una pila con capacidad 0
    netS(popS stack_small "Roma") == netS(stack_small) -- Volverá la misma pila si es que no hay elementos para sacar (comparo peso neto para verificar)
    ]

testTruckloadT :: [Bool]
testTruckloadT = [
    testF(loadT new_truck_case_2 palet3), -- Dará excepción cuando la ciudad destinto del palet no esta en la ruta
    testF(loadT new_truck_c14 palet1),  -- Dará excepción cuando no se pueda cargar más palets'
    testF(loadT new_truck_c43 palet2) -- Dará excepción al no haber una pila que cumpla con las condiciones para apilar
    ]

testTruckunloadT :: [Bool]
testTruckunloadT = [
    testF(loadT new_truck_case_2 palet3), -- Dará excepción cuando la ciudad destinto del palet no esta en la ruta
    netT(new_truck_c26) == 0, -- Vacia todas las ciudades
    netT(new_truck_c35) == netT(new_truck_c33)
    ]

test :: [Bool]
test = testRoute ++ testPalet ++ testStack ++ testTruckloadT ++ testTruckunloadT
