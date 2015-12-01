'''Jonathan Lerner's numberTheory.py'''
import math

'''below are helper functions'''
def isInt(x):
    '''helper function, checks if x is an integer or not. returns boolean'''
    return x == int(x)

def numInput():
    '''helper function, asks for input, checks if a number is within the range of acceptable input, in this case an integer between 1 and 10000, inclusive. if not says why not. returns the number for valid number, returns -1 if -1 was entered'''
    while True:
        n = input("Please enter a number between 1 and 10,000, inclusive: ")
        if isInt(n):
            if n == -1:
                return -1
            if 1 <= n and n <= 10000:
                return n
            else:
                print "Make sure your number is between 1 and 10,000, inclusive! Try again."

        else:
            print "Make sure your number is an integer! Try again."
        
def sumFactors(n):
    '''helper function, returns the sum of factors of a particular inputted number, including the number itself'''
    factorSum = 0
    '''run through every potential factor up to half the number itself. there are no factors greater than half the number'''
    for x in range(1, n/2 + 1):
        if n % x == 0:
            factorSum += x #increment factorSum each time
    '''add one more for the number itself'''
    return factorSum + n

def QuadIntSolns(a,b,c):
    '''helper function, returns whether the quadradic equation with coefficients a, b, and c has a positive integer solution'''
    '''reminder: the two solution to quadratic equation are (-b +/- sqrt(b^2 - 4ac))/2a'''
    soln1 = (-b + math.sqrt(math.pow(b,2) - 4 * a * c))/(2 * a)
    soln2 = (-b - math.sqrt(math.pow(b,2) - 4 * a * c))/(2 * a)
    '''for each of the two solutions to the quadratic equation, test that it is a positive integer'''
    return ((isInt(soln1) and soln1 >= 0) or (isInt(soln2) and soln2 >= 0))


'''below are the primary functions'''
def isPrime(x):
    '''function that returns whether or not the given number x is prime. Returns boolean'''
    '''we can simply test if the sum of the factors is the number plus the factor 1'''
    if (sumFactors(x) == x + 1):
        return True
    else:
        return False

def isComposite(x):
    '''function that returns whether or not the given number x is composite. Returns boolean'''
    '''a composite is a number that simply is not prime'''
    return not isPrime(x)

def isPerfect(x):
    '''function that returns whether or not the given number x is perfect. Returns boolean'''
    '''note that we need to remove x from the sum of x's factors'''
    return (sumFactors(x) - x == x)

def isAbundant(x):
    '''function that returns whether or not the given number x is abundant. Returns boolean'''
    '''tests that the sum of the factors, removing the number itself, greater than the number'''
    return (sumFactors(x) - x > x)

def isTriangular(x):
    '''function that returns whether or not the given number x is triangular. Returns boolean'''
    '''a triangular number x can be expressed by the equation n(n+1)/2 = x for some integer n. rearrange to get the below input to QuadIntSolns'''
    return QuadIntSolns(1, 1, -2 * x)

def isPentagonal(x):
    '''function that returns whether or not the given number x is pentagonal. Returns boolean'''
    '''a pentagonal number x can be expressed by the equation (3n - 1)(3n - 1 + 1)/2 = 3x for some integer n. rearrange to get the below input to QuadIntSolns'''
    return QuadIntSolns(9, -3, -6 * x)

def isHexagonal(x):
    '''function that returns whether or not the given number x is Hexagonal. Returns boolean'''
    '''a hexagonal number x can be expressed by the equation 2n^2 - n = x for some n. rearrange to get the below input to QuadIntSolns'''
    return QuadIntSolns(2, -1, -x)



def main():
    '''the main function'''
    
    while True:
        n = numInput()
        if n == -1:
            break
        '''valid number here'''
        s = str(n) + " is"
        if not isPrime(n):
            s += " not"
        s += " prime, is"
        if not isComposite(n):
            s += " not"
        s += " composite, is"
        if not isPerfect(n):
            s += " not"
        s += " perfect, is"
        if not isAbundant(n):
            s += " not"
        s += " abundant, is"
        if not isTriangular(n):
            s += " not"
        s += " triangular, is"
        if not isPentagonal(n):
            s += " not"
        s += " pentagonal, and is"
        if not isHexagonal(n):
            s += " not"
        s += " hexagonal.\n"

        print s
        

if __name__ == "__main__":
    main()
