-- test -> Corre todos los tests

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

-------------------------------------------------------------------------Test Route------------------------------------------------------------------------
emptyRoute = newR []
routeWithEmptyCity = newR ["Roma", "", "Paris"] -- Lista con una ciudad vacía
routeWithNumbers = newR ["Roma", "Paris23", "Londres"] -- Ciudad con números
routeDuplicateConsecutive = newR ["Roma", "Roma", "Paris"] -- Duplicados consecutivos
routeDuplicateNotConsecutive = newR ["Roma", "Paris", "Roma"] -- Duplicados no consecutivos

routeSmall = newR ["Roma", "Paris"] -- Ruta válida pequeña
routeMedium = newR ["Roma","Paris","Londres"] -- Ruta válida mediana

------------------------------------------------------------------------Test Palet------------------------------------------------------------------------

paletEmptyCity = newP "" 5                  -- Destino vacío (debe fallar)
paletWithNumbers = newP "Paris23" 5         -- Destino con números (debe fallar)
paletWithSpecial = newP "Paris!" 5          -- Destino con caracteres especiales (debe fallar)
paletZeroWeight = newP "Roma" 0             -- Peso cero (debe fallar)
paletNegativeWeight = newP "Roma" (-5)      -- Peso negativo (debe fallar)
palet1 = newP "Roma" 5                      -- Palet válido
palet2 = newP "Paris" 5                     -- Otro palet válido
palet3 = newP "Londres" 3                   -- Otro palet válido
palet4 = newP "Paris" 10                    -- Otro palet válido
palet5 = newP "Paris" 15                    -- Otro palet válido
palet6 = newP "Roma" 1
palet7 = newP "Paris" 1
palet8 = newP "Londres" 1

------------------------------------------------------------------------Test Stack------------------------------------------------------------------------

stackInvalid = newS 0                                   -- Capacidad inválida (debe fallar)
stackNegative = newS (-3)                               -- Capacidad negativa (debe fallar)
stackSmall = newS 2                                     -- Pila con capacidad 2
stackFull = stackS (stackS stackSmall palet2) palet1    -- Pila llena (2 palets)
stackOne = stackS stackSmall palet1                     -- Pila con 1 palet

-----------------------------------------------------------------------Test Truck------------------------------------------------------------------------
-- Caso de camión con bahías inválidas (0 bahías)
truckInvalidBays = newT 0 2 routeSmall

-- Caso de camión con altura inválida (0 altura)
truckInvalidHeight = newT 2 0 routeSmall

-- Caso base: camión vacío con 2 bahías y altura 2
truckSmall = newT 2 2 routeSmall

-- Caso base: camión vacío con 1 bahía y altura 2
truckOneBay = newT 1 2 routeSmall

-- Caso de cargar un palet en un camión con 2 bahías
truckLoaded = loadT truckSmall palet1

-- Caso de cargar dos palets diferentes en un camión con 2 bahías
truckFull = loadT truckLoaded palet2

-- Caso de llenar completamente una bahía con 2 palets "Roma"
truckOneBayFull = loadT (loadT truckOneBay palet1) palet1

-- Caso de intentar sobrecargar una bahía ya llena
truckOneBayOverload = loadT truckOneBayFull palet1

-- Caso de cargar dos palets "Roma" en un camión con 2 bahías
truckTwoRoma = loadT truckLoaded palet1

-- Caso de cargar tres palets "Roma" distribuidos en varias bahías
truckThreeRoma = loadT truckTwoRoma palet1

-- Caso de desapilar todos los palets "Roma" de un camión con 3 palets
truckThreeRomaUnloaded = unloadT truckThreeRoma "Roma"

-- Caso base: camión vacío con 2 bahías y altura 3, usando ruta más larga
truckLarge = newT 2 3 routeMedium

-- Caso de cargar un palet "Londres" en un camión con 2 bahías
truckOneLondres = loadT truckLarge palet3

-- Caso de cargar dos palets ("Londres", "Paris") en un camión con 2 bahías
truckLondresParis = loadT truckOneLondres palet2

-- Caso de cargar tres palets ("Londres", "Paris", "Londres") en un camión
truckLondresParisLondres = loadT truckLondresParis palet3

-- Caso de cargar cuatro palets variados ("Londres", "Paris", "Londres", "Roma")
truckFourMixed = loadT truckLondresParisLondres palet1

-- Caso de desapilar solo los palets "Roma" de un camión con múltiples destinos
truckFourMixedUnloadedRoma = unloadT truckFourMixed "Roma"

-- Caso de cargar un palet "Paris" como base en un camión con 2 bahías
truckOneParis = loadT truckSmall palet2

-- Caso de cargar dos palets ("Paris", "Roma") en un camión con 2 bahías
truckParisRoma = loadT truckOneParis palet1

-- Caso de cargar tres palets ("Paris", "Roma", "Roma") para probar orden de destinos
truckParisTwoRoma = loadT truckParisRoma palet1

-- Caso de cargar palets para tener [Roma, Paris, Londres] en una bahía y [Paris, Londres] en otra
truckMixedStacks = loadT (loadT (loadT (loadT (loadT truckLarge palet8) palet7) palet6) palet8) palet7

-- Caso de descargar "Paris" de truckMixedStacks, dejando [Roma, Londres] y [Londres]
truckMixedStacksUnloadedParis = unloadT truckMixedStacks "Paris"

-- Caso de cargar "Londres" encima de "Londres"
truckMixedStacksLoadLondres = loadT truckMixedStacksUnloadedParis palet8

-- Caso de descargar "Londres" de truckMixedStacksLoadLondres, dejando [Roma, Paris, Londres] y []
truckMixedStacksUnLoadLondres = unloadT truckMixedStacksLoadLondres "Londres"
-------------------------------------------------------------------------------------------------------------------------------------------

testNewR :: [Bool]
testNewR = [
    testF emptyRoute,                           -- Caso de ruta vacía debe fallar
    testF routeWithEmptyCity,                   -- Caso de ciudad vacía en la lista debe fallar
    testF routeWithNumbers,                     -- Caso de ciudad con números debe fallar
    testF routeDuplicateConsecutive,            -- Caso de duplicados consecutivos debe fallar
    not (testF routeSmall),                     -- Caso de ruta válida no debe fallar
    not (testF routeDuplicateNotConsecutive)    -- Caso de duplicados no consecutivos es válido
    ]


testInOrderR :: [Bool]
testInOrderR = [
    inOrderR routeSmall "Roma" "Paris",             -- Caso positivo: Roma antes que Paris
    inOrderR routeSmall "Roma" "Roma",              -- Caso positivo: misma ciudad
    inOrderR routeMedium "Roma" "Londres",          -- Caso positivo: Roma antes que Londres
    inOrderR routeMedium "Paris" "Londres",         -- Caso positivo: Paris antes que Londres
    not (inOrderR routeSmall "Paris" "Roma"),       -- Caso negativo: Paris después de Roma
    not (inOrderR routeSmall "Roma" "Londres"),     -- Caso negativo: Londres no está en routeSmall         (ver con mati)
    not (inOrderR routeSmall "" "Paris"),           -- Caso negativo: ciudad vacía como primera
    not (inOrderR routeSmall "Roma" ""),            -- Caso negativo: ciudad vacía como segunda
    not (inOrderR routeSmall "Roma23" "Paris"),     -- Caso negativo: ciudad con números como primera
    not (inOrderR routeSmall "Roma" "Paris23"),     -- Caso negativo: ciudad con números como segunda
    not (inOrderR routeMedium "Londres" "Roma")     -- Caso negativo: Londres después de Roma

    ]


testInRouteR :: [Bool]
testInRouteR = [
    inRouteR routeSmall "Roma",               -- Caso positivo: Roma está en la ruta
    inRouteR routeSmall "Paris",              -- Caso positivo: Paris está en la ruta
    not (inRouteR routeSmall "Londres"),      -- Caso negativo: Londres no está en la ruta
    not (inRouteR routeSmall ""),             -- Caso negativo: ciudad vacía
    not (inRouteR routeSmall "Paris23")       -- Caso negativo: ciudad con números
    ]


testNewP :: [Bool]
testNewP = [
    testF paletEmptyCity,          -- Caso negativo: destino vacío debe 
    testF paletWithNumbers,        -- Caso negativo: destino con números
    testF paletWithSpecial,        -- Caso negativo: destino con caracteres especiales
    testF paletZeroWeight,         -- Caso negativo: peso cero debe fallar
    testF paletNegativeWeight,     -- Caso negativo: peso negativo debe fallar
    not (testF palet1),            -- Caso positivo 
    not (testF palet2)             -- Caso positivo
    ]


testDestinationP :: [Bool]
testDestinationP = [
    destinationP palet1 == "Roma",   -- Debe devolver el destino correcto
    destinationP palet2 == "Paris"   -- Debe devolver el destino correcto
    ]


testNetP :: [Bool]
testNetP = [
    netP palet1 == 5,    -- Debe devolver el peso correcto
    netP palet3 == 3    -- Debe devolver el peso correcto
    ]


testNewS :: [Bool]
testNewS = [
    testF stackInvalid,       -- Caso de capacidad cero debe fallar
    testF stackNegative,      -- Caso de capacidad negativa debe fallar
    not (testF stackSmall)    -- Caso de capacidad válida no debe fallar
    ]


testFreeCellsS :: [Bool]
testFreeCellsS = [
    freeCellsS stackSmall == 2,   -- Pila vacía tiene toda la capacidad libre
    freeCellsS stackOne == 1,     -- Pila con 1 palet tiene 1 celda libre
    freeCellsS stackFull == 0     -- Pila llena no tiene celdas libres
    ]


testStackS :: [Bool]
testStackS = [
    freeCellsS (stackS stackSmall palet1) == 1,  -- Apilar en pila vacía funciona
    freeCellsS stackFull == 0                    -- Pila llena no acepta más (ver holdsS)
    ]


testNetS :: [Bool]
testNetS = [
    netS stackSmall == 0,     -- Pila vacía tiene peso 0
    netS stackOne == 5,       -- Pila con 1 palet tiene peso 5
    netS stackFull == 10      -- Pila con 2 palets tiene peso 10
    ]


testHoldsS :: [Bool]
testHoldsS = [
    holdsS stackSmall palet1 routeSmall,                   -- Caso válido: espacio, peso y orden OK
    not (holdsS stackOne palet2 routeSmall),               -- Caso inválido: Roma abajo, Paris no se puede apilar (Paris > Roma)
    not (holdsS stackFull palet3 routeMedium),             -- Caso inválido: sin espacio
    not (holdsS stackOne palet4 routeSmall),               -- Caso inválido: peso excede 10 (5 + 10 > 10)
    holdsS stackSmall palet2 routeSmall,                   -- Caso válido: pila vacía, apilar Paris OK
    holdsS (stackS stackSmall palet2) palet1 routeSmall,   -- Caso válido: Paris abajo, Roma arriba
    not (holdsS stackOne palet3 routeMedium)               -- Caso inválido: Londres no está en routeSmall
    ]


testPopS :: [Bool]
testPopS = [
    netS (popS stackSmall "Roma") == 0,          -- Caso válido: desapilar en pila vacía no cambia nada
    netS (popS stackOne "Paris") == 5,           -- Caso válido: desapilar destino no presente no cambia
    netS (popS stackOne "Roma") == 0,            -- Caso válido: desapilar destino presente elimina el palet
    netS (popS stackFull "Roma") == 5,           -- Caso válido: desapilar destino presente elimina el palet si se encuentra en la parte superior de la pila
    netS (popS stackFull "Paris") == 10          -- Caso inválido: desapilar destino presente elimina el palet si se encuentra en la parte superior de la pila
    ]


testNewT :: [Bool]
testNewT = [
    testF truckInvalidBays,    -- Caso de bahías inválidas debe fallar
    testF truckInvalidHeight,  -- Caso de altura inválida debe fallar
    not (testF truckSmall),    -- Caso válido (2x2) no debe fallar
    not (testF truckOneBay),   -- Caso válido (1x2) no debe fallar
    not (testF truckLarge)     -- Caso válido (2x3) no debe fallar
    ]


testFreeCellsT :: [Bool]
testFreeCellsT = [
    freeCellsT truckSmall == 4,         -- Camión vacío: 2 bahías * 2 altura
    freeCellsT truckLoaded == 3,        -- Camión con 1 palet: 4 - 1 = 3
    freeCellsT truckFull == 2,          -- Camión con 2 palets: 4 - 2 = 2
    freeCellsT truckOneBay == 2,        -- Camión vacío (1 bahía): 1 * 2 = 2
    freeCellsT truckOneBayFull == 0,    -- Camión lleno (1 bahía, 2 palets)
    freeCellsT truckThreeRoma == 1      -- Camión con 3 palets: 4 - 3 = 1
    ]


testNetT :: [Bool]
testNetT = [
    netT truckSmall == 0,           -- Camión vacío tiene peso 0
    netT truckLoaded == 5,          -- Camión con 1 palet tiene peso 5
    netT truckFull == 10,           -- Camión con 2 palets ("Roma", "Paris")
    netT truckOneBayFull == 10,     -- Camión con 2 palets "Roma" tiene peso 10
    netT truckThreeRoma == 15,      -- Camión con 3 palets "Roma" tiene peso 15
    netT truckFourMixed == 16       -- Camión con 4 palets (3+5+3+5)
    ]


testLoadT :: [Bool]
testLoadT = [
    testF (loadT truckSmall palet3),          -- Caso de palet no en ruta debe fallar
    testF (loadT truckOneBayOverload palet1), -- Caso de camión lleno debe fallar
    testF (loadT truckParisTwoRoma palet2),   -- Caso de destino desordenado debe fallar
    testF (loadT truckOneBay palet5),         -- Sobrecarga de peso (15 > 10) debe fallar
    netT (loadT truckLoaded palet2) == 10,    -- Carga en otra bahía funciona
    not (testF (loadT truckLoaded palet2)),   -- Carga válida no debe fallar
    netT truckThreeRoma == 15                 -- Carga varias bahías (3 palets "Roma")
    ]


testUnloadT :: [Bool]
testUnloadT = [
    testF (unloadT truckSmall "Londres"),     -- Caso de ciudad no en ruta debe fallar
    netT (unloadT truckSmall "Roma") == 0,    -- Descarga en camión vacío no cambia
    netT (unloadT truckLoaded "Roma") == 0,   -- Descarga elimina todos los "Roma"
    netT (unloadT truckFull "Paris") == 5,    -- Descarga completa de "Paris"
    netT truckThreeRomaUnloaded == 0,         -- Descarga todos los "Roma"
    netT truckFourMixedUnloadedRoma == netT truckLondresParisLondres  -- Descarga "Roma" deja el resto
    ]


-- Load: [Roma, Paris, Londres] [Paris, Londres]
-- Unload: [Roma, Paris, Londres] [Londres]
testLoadUnloadCombined :: [Bool]
testLoadUnloadCombined = [
    netT truckMixedStacks == 5,                                 -- Verifica carga inicial: 5 palets (1+1+1+1+1)
    freeCellsT truckMixedStacks == 1,                           -- Verifica celdas libres tras carga: 6 - 5 = 1
    netT truckMixedStacksUnloadedParis == 4,                    -- Verifica descarga de "Paris": 3 palets restantes (1+1+1+1)
    freeCellsT truckMixedStacksUnloadedParis == 2,              -- Verifica celdas libres tras descarga: 6 - 4 = 2
    netT truckMixedStacksLoadLondres == 5,                       -- Verifica que se puede cargar "Londres" encima de "Londres"
    netT truckMixedStacksUnLoadLondres == 3                     -- Verifica que se puede descargar "Londres"

    ]


test :: [Bool]


--test = testNewR
--test = testInOrderR
--test = testInRouteR
-- test = testNewP
--test = testDestinationP
-- test = testNetP
-- test = testNewS
-- test = testFreeCellsS
-- test = testStackS
-- test = testNetS
-- test = testHoldsS
-- test = testPopS
-- test = testNewT
-- test = testFreeCellsT
-- test = testNetT 
-- test = testLoadT 
-- test = testUnloadT
-- test = testLoadUnloadCombined 

test = testNewR ++ testInOrderR ++ testInRouteR ++ testNewP ++ testDestinationP ++ testNetP ++ testNewS ++ testFreeCellsS ++ testStackS ++ testNetS ++ testHoldsS ++ testPopS ++ testNewT ++ testFreeCellsT ++ testNetT ++ testLoadT ++ testUnloadT ++ testLoadUnloadCombined
