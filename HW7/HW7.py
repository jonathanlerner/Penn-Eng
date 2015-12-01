'''This is Jonathan Lerner's HW7 on recursion'''

def sameAB(string):
    if string == '':
        return True
    if string[0] == 'a' and string[-1] == 'b':
        return sameAB(string[1:-1])
    return False

def binary_search(lst, val):
    length = len(lst)
    if length == 1:
        if lst[0] == val:
            return True
        else:
            return False
    midpoint_index = int((length-1)/2.0) #for odd length is the midpoint, for even length is the smaller of the two candidates
    if length % 2 == 0:
        '''even list length'''
        if val > lst[midpoint_index]:
            return binary_search(lst[midpoint_index+1 :],val)
        else:
            return binary_search(lst[:midpoint_index+1],val)
    else:
        '''odd list length'''
        if lst[midpoint_index] == val:
            return True
        elif val > lst[midpoint_index]:
            return binary_search(lst[midpoint_index+1 :],val)
        else:
            return binary_search(lst[:midpoint_index],val)

def flatten(lst):
    if lst == []:
        return []
    else:
        return lst[0] + flatten(lst[1:])

def initials(lst):
    return [name.split()[0][0]+'.'+name.split()[1][0]+'.' for name in lst]

def meamers(filename):
    split_lines = [line.rstrip().split(',') for line in open(filename).readlines()]
    return sum([1 for x in range(0,len(split_lines)) if split_lines[x][1] == 'MEAM'])

def most_frequent_alphabet(freq):
    return [k for k in freq.keys() if freq[k] == max(freq.values())][-1]
