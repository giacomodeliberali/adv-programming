def ciaoWord(word):
    char_list = list(word)
    for (i,c) in enumerate(char_list):
        char_list[i] = c.lower()
    
    char_list.sort()

    return ''.join(char_list)

def addCiaoDict(ciaoDict, word):
    
    ciao_key = ciaoWord(word)

    if(ciao_key in ciaoDict):
        ciaoDict[ciao_key].add(word)
    else:
        ciaoDict[ciao_key] = set([word])

def createDict(filePath):
    file = open(filePath, "r")
    lines = file.readlines()

    ciaoDict = {}
    for w in lines:
        w = w.replace("\t", "").replace("\n", "").strip()
        addCiaoDict(ciaoDict, w)

    return ciaoDict

    
def replaceAnagrams(ciaoDict, line):
    words = line.split(" ")
    result = []
    for w in words:
        ciao_key = ciaoWord(w)
        if ciao_key in ciaoDict:
            result.append(list(ciaoDict[ciao_key])[0])
        else:
            result.append(w)

    return " ".join(result)
