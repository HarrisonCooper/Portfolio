-- Enigma Machine
-- By Harrison Paul Cooper
-- 150159601
-- 27/10/16

module Assignment2 where
    
    import AssignmentHelp
    import Cipher
    import Data.Maybe
    import Data.List

    {-
      Algebraic type enigma is either a Simple enigma which takes
      a list of list of chars and an int, or a Steckered Enigma
      which takes the same as Simple enigma + a list of steckered
      values
    -}
    data Enigma = SimpleEnigma
                    {rotors :: [String],
                     offsets :: [Int]}
                  |SteckeredEnigma
                    {rotors ::[String], 
                     offsets :: [Int], 
                     steckerTuple :: [(Char,Char)]}

    steckerValues :: [(Char,Char)]
    steckerValues = [('A','B'),('C','D'),('E','F'),('G','H'),('I','J'),('K','L'),('M','N'),('O','P'),('Q','R'),('S','T')]

    {-
      Determines if enigma given is Simple or Steckered.
      If Simple, it takes the letter, adjusts the offsets and plugs 
      the values into rotor which returns the ciphered letter.
      If Steckered, it takes a letter; if that letter has a corresponding steckered
      letter, they are swapped. The offsets are adjusted, and 
      I pass the steckereLetter into the rotor to get a 
      ciphered letter. The final step is to see if this letter
      has a corresponding steckered letter, which is returned.
    -}
    enigmaEncode :: Char -> Enigma -> Char
    enigmaEncode letter (SimpleEnigma rotors offsets) =
      let adjustedOffsets = advancingRotor(offsets)
      in rotor letter rotors adjustedOffsets
    enigmaEncode letter (SteckeredEnigma rotors offsets steckerTuple) =
      let steckeredLetter = steckerboard letter steckerTuple
          adjustedOffsets = advancingRotor(offsets)
          rotoredLetter   = rotor steckeredLetter rotors adjustedOffsets
      in steckerboard rotoredLetter steckerTuple

    {-
      Determines if enigma given is Simple or Steckered.
      If Simple, it takes the message, if its empty, an empty message is
      returned. It takes the head of the message and passes
      it to enigmaEncode, where its offsets are adjsted and
      a ciphered character returned. The tail of the message is
      passes back through enigmaEncodeMessage, however, we
      pass back the adjusted rotor values, so that ever 
      new letter (key press) advances the rotors by 1.
      If Steckered Enigma, it takes the message, sends the head to enigmaEncode and
      puts the tail back into the function, but with the rotors
      offsets adjusted so that they increasae on each 'key press'
    -}
    enigmaEncodeMessage :: String -> Enigma -> String
    enigmaEncodeMessage [] _  = []
    enigmaEncodeMessage (h:t) (SimpleEnigma rotors offsets) =
      enigmaEncode h (SimpleEnigma rotors offsets) : enigmaEncodeMessage t (SimpleEnigma rotors (advancingRotor(offsets)))
    enigmaEncodeMessage (h:t) (SteckeredEnigma rotors offsets steckerTuple) =
      enigmaEncode h (SteckeredEnigma rotors offsets steckerTuple) : enigmaEncodeMessage t (SteckeredEnigma rotors (advancingRotor(offsets)) steckerTuple) 

    -- Takes the letter, the list of rotors to use, and a list of 
    -- adjusted offsets to use and goes through the algorithm
    -- of creating the ciphered letter.
    -- Firstly the letter is encoded in rr and the result encoded
    -- with rm and that result encoded in rl. Then that result is
    -- swapped in the reflector and the result decoded in rl,
    -- then that result decoded in rm then that result decoded in 
    -- rr. This result is then returned.
    rotor :: Char -> [String] -> [Int] -> Char
    rotor letter (rl:rm:rr:d) (ol:om:or:s) =
      let encode1 = encode letter rr or
          encode2 = encode encode1 rm om
          encode3 = encode encode2 rl ol
          reflected = reflector encode3
          decode1 = reverseEncode reflected rl ol
          decode2 = reverseEncode decode1 rm om
     in reverseEncode decode2 rr or
    
    -- Works by seeing what the given offset values are and
    -- adjustes them accordingly to the rules set.
    advancingRotor :: [Int] -> [Int]
    advancingRotor [] = []
    advancingRotor (a:b:c:d)
      | b<25 && c==25 = [a,b+1,0]
      | a<25 && b==25 && c==25 = [a+1, 0, 0]
      | a==25 && b==25 && c==25 = [0,0,0]
      | otherwise = [a,b,c+1]

    -- Reflector takes a letter, finds the position of that
    -- letter within the reflections list and works on the
    -- index. If the index is divisible by 2, you add 1 to
    -- the index and return that letter; otherwise, 
    -- subtract 1 from the index and return that letter.
    reflector :: Char -> Char
    reflector letter 
      | charPos `mod` 2 == 0 = reflections!!(charPos+1)
      | otherwise            = reflections!!(charPos-1)
      where reflections = "AYBRCUDHEQFSGLIPJXKNMOTZVW"
            charPos     = fromJust(elemIndex letter reflections)

    -- Takes a letter and filters the given list of tuples to
    -- see if the letter is in the list. If it itn't, the 
    -- orignial letter is returned. If the letter is in the
    -- list of tuples, the corresponding steckered letter is
    -- returned. 
    steckerboard :: Char -> [(Char, Char)] -> Char
    steckerboard letter [] = letter
    steckerboard letter tuple
      | filteredTuples == []               = letter
      | fst(head filteredTuples) == letter = snd(head filteredTuples)
      | snd(head filteredTuples) == letter = fst(head filteredTuples)
      | otherwise                          = letter
      where filteredTuples = filter(\(a,b)-> a==letter || b==letter) tuple

    -- This is the cipher phill gives in the spec.
    testCipher :: String
    testCipher = "RWIVTYRESXBFOGKUHQBAISE"

    -- This is the cipher phill gives in the spec.
    testMessage :: String
    testMessage = "WETTERVORHERSAGEBISKAYA"

    -- This is the whole crib in a list.
    menuList :: [(Int, String, String)]
    menuList = cribCreator (testMessage, testCipher)

    -- A function I created to take the first element from a triple
    extractFirst :: (a,b,c) -> a
    extractFirst (a,_,_) = a

    -- A function I created to take the second element from a triple
    extractSecond :: (a,b,c) -> b
    extractSecond (_,b,_) = b

    -- A function I created to take the third element from a triple
    extractThird :: (a,b,c) -> c
    extractThird (_,_,c) = c

    -- Crib takes a message and a cipher
    type Crib = (String, String)
    type Menu = [Int]

    --crib takes a message and a cipher and returns a tripple with the
    --corresponding cipher and plain char at that position
    cribCreator :: Crib -> [(Int, String, String)]
    cribCreator (message, cipher) =
      let positions :: [Int]
          positions = [0..(length message)-1]
      in cribTripple positions message cipher

    -- This creates the crib in the correct order.
    cribTripple :: [Int] -> String -> String -> [(Int, String, String)]
    cribTripple [] _ _ = []
    cribTripple _ [] _ = []
    cribTripple _ _ [] = []                                 
    cribTripple (positionHead:positionTail) (messageHead:messageTail) (cipherHead:cipherTail) =
      (positionHead, [messageHead], [cipherHead]):cribTripple positionTail messageTail cipherTail

    -- This function takes a position and the list of crib triples and 
    -- recursively goes through the list until the first elemnt of the
    -- triple is equal to the position given. It then returns the corresponding
    -- cipher character, which is the third element in the triple.
    correspondingCipherChar :: Int -> [(Int, String, String)] -> String
    correspondingCipherChar _ [] = []
    correspondingCipherChar position (trippleHead:trippleTail)
      | position==extractFirst trippleHead = extractThird trippleHead
      | otherwise = correspondingCipherChar position trippleTail

    -- This function takes the cipher character and the list of 
    -- crib triples and filters the list by seeing if the
    -- second elemnt in the triple is equal to the char given. If
    -- true, it reuturns the list of integers of positions.
    posOfMatchingPlainChars :: String -> [(Int, String, String)] -> [Int]
    posOfMatchingPlainChars cipherChar (trippleHead:trippleTail) = 
      let listOfTripples = filter(\(_,a,_)->a==cipherChar) (trippleHead:trippleTail)
      in extractPosFromTripple listOfTripples

    extractPosFromTripple :: [(Int, String, String)] -> [Int]
    extractPosFromTripple [] = []
    extractPosFromTripple (x:xs) = extractFirst x : extractPosFromTripple xs

    -- Currently menu takes a position and returns the positions of the 
    -- plain chars corresponding to the cipher chat at that index.
    -- It doesn't work recursively yet but in terminal you can keep
    -- re-running it with the result it reuturns to get a menu.
    menu :: Int -> String -> String -> Menu
    menu pos message cipher =
      do
        posOfMatchingPlainChars cipherChar menuList
      where
        menuList = cribCreator (message, cipher)
        cipherChar = correspondingCipherChar pos menuList

    data Tree a = Empty | Leaf a | Node a (Tree a) (Tree a)

    treeInserter :: Ord a => a -> (Tree a) -> Tree a
    treeInserter y Empty = Leaf y -- inserting into an empty tree, produces a leaf
    treeInserter y (Leaf x) -- inserting into a lead produces a node
      | x==y = (Leaf x) -- already there, leave as is
      | y<x = (Node x (Leaf y) Empty) -- y in the left sub tree
      | otherwise = (Node x Empty (Leaf y)) -- in the right sub tree
    treeInserter y tree
      | x==y = (Node x left_sub_tree right_sub_tree)
      | y<x  = (Node x
                     (treeInserter y left_sub_tree)
                     right_sub_tree)
      | otherwise = (Node x
                          left_sub_tree
                          (treeInserter y right_sub_tree))
      where (Node x left_sub_tree right_sub_tree) = tree



      
