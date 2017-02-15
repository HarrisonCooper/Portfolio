-- Substitution Cipher
-- By Harrison Paul Cooper
-- 150159601
-- 20/10/16

module Assignment where

  import AssignmentHelp
  import Data.List
  import Data.Maybe
  import Data.Tuple
  import Data.Char
  
  -- Defining a data structure of type [Char] to hold the alphbet
  alphabet :: [Char]
  alphabet = ['A'..'Z'] 
 
  -- Defining a data structure of type [Char] to hold the cipher
  cipher :: [Char]
  cipher = "EKMFLGDQVZNTOWYHXUSPAIBRCJ"
  
  -- I take the  cipher and sort it into alphabetical order.
  -- I then compare the sorted cipher to the alphabet
  -- If they're equal then this shows that the cipher contains each letter once and that it is in capitals. 
  -- Thus it is validated.
  validateCipher :: [Char] -> [Char] -> Bool
  validateCipher alphabet testCipher 
    | alphabet == (mergesort (<) testCipher) = True
    | otherwise = False
    
  -- To offset the cipher, I take the orignial cipher and an integer
  -- I cycle the cipher to form an infinite list of the cipher recurring
  -- I drop the corresponding number of elements from the front of the cipher
  -- And finally return the first 26 characters from the infinite list
  offset :: [Char] -> Int -> [Char]
  offset cipher n =
    let cycleCipher = cycle cipher
        v = 26-n
        intermediateCipher = drop v cycleCipher
    in take 26 intermediateCipher
    
  -- To get the ciphered character, I find the position of the letter in the alphabet
  -- Then return the element which is indexed in the same position in the offsetted cipher.
  encode :: Char -> [Char] -> Int -> Char
  encode character cipher n =
    let offsettedCipher = offset cipher n
        nPosition = alphaPos character
    in offsettedCipher!!nPosition
      
  -- I recursively take the head of the string and pass it to encode where its
  -- corresponding cipher is returned; then the tail is inputed back into 
  -- encodeMessage. This will cipher every character, in turn.
  -- I also have a base case stating that if the given string is empty
  -- the function will return an empty list.
  encodeMessage :: String -> [Char] -> Int -> String
  encodeMessage [] _ _ = [] 
  encodeMessage (h:t) cipher offset =
    (encode h cipher offset): encodeMessage t cipher offset
    
  -- The offset is the same as in encode, returning the offsetted cipher
  -- I find the index of the given character in the offsetted cipher
  -- The returned index takes type Maybe Int, therefore I use fromJust to convert to Int
  -- I then take this Int and return the corresponding letter in the alphabet with the same index.
  reverseEncode :: Char -> [Char] -> Int -> Char  
  reverseEncode character cipher n =
    let offsettedCipher = offset cipher n
        nPosition = character `elemIndex` offsettedCipher
        nnPosition = fromJust nPosition
    in alphabet!!nnPosition
    
  -- Same as encodeMessage, however the head is input into reverseEncode
  -- and the tail back into reverseEncodeMessage
  reverseEncodeMessage :: String -> [Char] -> Int -> String
  reverseEncodeMessage [] _ _ = []
  reverseEncodeMessage (h:t) cipher offset =
    (reverseEncode h cipher offset): reverseEncodeMessage t cipher offset
    
  -- You input a message (of type String) and it returns a tuple of type [(Char,Int)]
  -- If an empty message is supplied, an empty tuple is returned
  -- I store the length of the message in messageLength
  -- I sort the message into alphabetical order to get all the same elements together e.g. "ABCAB" -> "AABBC"
  -- I then group the sorted message into its same elements. 
  -- A mapping function takes the length of each grouping, and uses the function 'percent' to to work out the percentage frequency of each unique element in the message. 
  -- This returns a tuple with the letter and its corresponding frequency percent.
  letterStats :: String -> [(Char,Int)]
  letterStats [] = []
  letterStats message =
    let messageLength = length message 
        sortedMessage = mergesort (<) message
        groupedLetters = group sortedMessage
    in map (\x -> (head x, (percent (length x) messageLength))) $ groupedLetters
 
  -- partialDecodeStart takes a single letter and the list of tuples and returns a letter
  -- I split the list of tuples into a head tuple e.g. ('H','R') and the tail e.g. [('E','U'), ('P', 'O')]
  -- I then compare to see if the letter is the same as the first element in the head tuple
  -- If true I take the second element of the head tuple and make it lowercase.
  -- If they arn't the same, I put the letter back into the function along with the tail tuples
  -- This recursively itterates through until each letter is mapped to its cipher
  partialDecodeStart :: Char -> [(Char, Char)] -> Char
  partialDecodeStart x (h:t) 
    | (x == fst h) = toLower (snd h)
    | (x /= fst h) = partialDecodeStart x t
    | otherwise = x
    
  -- partialDecode takes a message, the list of tuples and returns a message
  -- If an empty message or tupple is given, an empty list is returned
  -- I split the message into its head and tail components and pass the head into partialDecodeStart
  -- I then take the tail and pass it back into the function to be worked on recursively until each element is translated.
  partialDecode :: [Char] -> [(Char, Char)] -> [Char]
  partialDecode [] _  = [] 
  partialDecode _ [] = []
  partialDecode (x:xs) (h:t) =
    (partialDecodeStart x (h:t)):(partialDecode xs (h:t))
    
    
    
