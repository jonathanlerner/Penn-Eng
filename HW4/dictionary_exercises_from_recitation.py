### Some Practice questions for disctionaries ###
### in each function, lines starting with # before the return is the one to run in IDLE
### run createDict() or build your own dictionary first

## make sure to clarify the difference between list and dictionary.
## I have seen students get confused about when to use which.

def createDict():
    '''create a dictionary'''
    shopping = {}

    # Add three key-value tuples to the dictionary
    shopping["apple"] = 2
    shopping["pear"] = 4
    shopping["orange"] = 7
    # dic = createDict()
    return shopping
    

def getValue(dic, key, messageNotFound = None):
    '''get values of key in dic, if key is not in dic, return messageNotFound'''
    '''
    dict.get(key, default=None)
    Parameters
    key -- This is the Key to be searched in the dictionary.
    default -- This is the Value to be returned in case key does not exist.
    '''
    # getValue(dic, 'pear')
    # getValue(dic, 'grape')
    # getValue(dic, 'grape',"not found")
    return dic.get(key, messageNotFound)


def isIn(dic, key):
    '''return whether key is in dic'''
    # isIn(dic, 'pear')
    # isIn(dic, 'grape')
    return key in dic

def getLength(dic):
    '''return number of key-value pairs'''
    # getLength(dic)
    return len(dic)

def getKeysList(dic):
    '''return a list containing all keys in dic'''
    # getKeysList(dic)
    return dic.keys()

def getValuesList(dic):
    '''return a list containing all values in dic'''
    # getValuesList(dic)
    return dic.values()

def getSortedKeys(dic):
    '''return a sortes list of keys in dic'''
    # getSortedKeys(dic)
    return sorted(dic.keys())


def printItems1(dic):
    '''print all key-value pairs using items method'''
    dicItems = dic.items()

    # Loop and display tuple items
    for item in dicItems:
        print("Fruit:", item[0])
        print("Quantity:", item[1])
        print("")
    # printItems1(dic)
    
def loopOverDictionary(dic):
    # Loop over dictionary directly.
    # ...This only accesses keys.
    for element in dic:
        print(element)    
    # loopOverDictionary(dic)

def deleteKey(dic, key):
    '''remove the key-value pair with given key from dict'''
    '''removing not existing key will result in error'''
    del dic[key]
    # deleteKey(dic, "apple")
    # print(dic)
    return dic

def updata(dic1, dic2):
    '''change the first dictionary to have new values from the second dictionary.
    This modifies also existing values.'''
    dic1.update(dic2)
    
    # dic1 = {"apple": 2, "pear": 4, "orange": 7}
    # dic2 = {"apple": 200, "watermelon": 1}
    # updata(dic1, dic2)
    # print(dic1)
    # print(dic2)
    'notice the value of "apple" will be changed to 200, dic2 is the same as before'
    return dic1

def copyDict(dic):
    new = dic.copy()
    # new = copyDict(dic)
    # new["apple"] = 1000
    # print(dic)
    # print(new)
    'notice: modifying new does NOT modify the original dic'
    return new

def createDicFromKeys(listOfKeys, value):
    dic = dict.fromkeys(listOfKeys, value)
    # listOfKeys = ["apple", "pear", "orange"]
    # value = 5
    # dic = createDicFromKeys(listOfKeys, value)
    # print(dic)
    return dic

def createDicFromTuples(listOfTuples):
    '''create a dictionary from a list of tuples
    The tuples are pairs: they each have two elements, a key and a value.'''
    # listOfTuples = [("apple", 2), ("pear", 4), ("orange",7)]
    # dic = createDicFromTuples(listOfTuples)
    # print(dic)
    dic = dict(listOfTuples)
    return dic



