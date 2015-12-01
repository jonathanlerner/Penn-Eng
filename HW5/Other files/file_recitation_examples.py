
"please download the graphene,txt file"


def readFromFile(filename):
    '''Reads a file and stores data in list'''
    #Reads a file and stores each line in a list and returns list
    fileList=[]
    fo = open(filename, 'r')
    for line in fo:
        fileList.append(line)
    fo.close()
    return fileList       
    
def writeToFile(filename,fileList):
    ''' write data in a list to file'''
    #Show them ways to write from a file.
    fo = open(filename, 'w')
    for eachVariable in fileList:
        eachLine=eachVariable +'\n'
        fo.write(eachLine)
    fo.close()

def appendToFile(filename, word):
    fo = open(filename, 'a')
    fo.write(word)
    fo.close()


def isFile(string):
    ''' It tells whether the entered file exists or not.'''
    try:
        open(string)
        return True
    except  IOError:
        return False


def writeDictToFile(dictionary,fileName) :
    '''Create a file to record the frequency of each word'''
    fileList = []
    f= open(fileName,'w')
    for word in dictionary.keys():
        wordString= word+' : '+str(dictionary[word])
        wordString+='\n'
        fileList.append(wordString)     
    f.writelines(fileList)
    f.close()
    


def frequencyCount(fileName):
    ''' It gets the individual word frequency in the file.'''
    wordFreq={}
    f= open(fileName)
    line=f.readline()
    while line:
        words=line.split()
        for word in words:
            word=word.lower()
            wordFreq[word]= wordFreq.get(word,0)+1
        line=f.readline()
    f.close()
    return wordFreq

def main():
    "use this commands that are commented out for the recitation"
    "please download the graphene,txt file"
    print isFile("Graphene.txt")
    #print isFile("noGraphene.txt")
    #name=readFromFile("Graphene.txt")
    #writeToFile("name.txt",name)
    #appendToFile("name.txt", "Am in love with the coco")
    #count=frequencyCount("Graphene.txt")
    #writeDictToFile(count,"game.txt")
    
if __name__=='__main__':
    main()
