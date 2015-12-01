import random

def print_intro():
    print('''Congrats, you are the newest ruler of ancient Samaria, elected for a ten year term
of office. Your duties are to distribute food, direct farming, and buy and sell land
as needed to support your people. Watch out for rat infestations and the resultant
plague! Grain is the general currency, measured in bushels. The following will help
you in your decisions:

    * Each person needs at least 20 bushels of grain per year to survive.
    * Each person can farm at most 10 acres of land.
    * It takes 2 bushels of grain to farm an acre of land.
    * The market price for land fluctuates yearly.

Rule wisely and you will be showered with appreciation at the end of your term. Rule
poorly and you will be kicked out of office!''')
    return

def ask_to_buy_land(bushels, cost):
    '''Ask user how many bushels to spend buying land.'''
    acres_to_buy = input("How many acres will you buy? ")
    while acres_to_buy * cost > bushels:
        print "O great Hammurabi, we have but", bushels, "bushels of grain!"
        acres_to_buy = input("How many acres will you buy? ")
    return acres_to_buy

def ask_to_sell_land(acres):
    '''Ask use how much land they want to sell.'''
    acres_to_sell = input("How many acres will you sell? ")
    while acres_to_sell > acres:
        print "O great Hammurabi, we have but", acres, "acres of land!"
        acres_to_sell = input("How many acres will you sell? ")
    return acres_to_sell

def ask_to_feed(bushels):
    '''Ask user how many bushels they want to use for feeding.'''
    bushels_to_feed = input("How many bushels do you wish to feed your people? ")
    while (bushels_to_feed > bushels):
        print "O great Hammurabi, we have but", bushels, "bushels of grain!"
        bushels_to_feed = input("How many bushels will you use to feed the people? ")
    return bushels_to_feed

def ask_to_cultivate(acres, population, bushels):
    '''Ask user how much land they want to plant seed in.'''
    while True:
        acres_to_cultivate = input("How many acres do you wish to plant with seed? ")    

        if acres_to_cultivate > acres:
            print "O great Hammurabi, we have but", acres, "to plant."
            continue
        if acres_to_cultivate > (population * 10):
            print "O great Hammurabi, we have but", population, "people."
            continue
        if acres_to_cultivate > bushels:
            print "O great Hammurabi, we have but", bushels, "bushels."
            continue
        #only here if all conditions have been met
        return acres_to_cultivate

def isPlague():
    '''function that determines with 15% chance if there was a plague this turn'''
    random_num = random.randint(1,100)
    if random_num <= 15:
        return True
    else:
        return False

def numStarving(population, bushels):
    '''how many people starved this turn? each person needs 20 bushels'''
    starved = population - (bushels / 20)
    if starved > 0:
        return starved
    else:
        return 0

def numImmigrants(land, grainInStorage, population, numStarving):
    if numStarving > 0:
        return 0
    else:
        immigrants = (20 * land + grainInStorage) / (100 * population + 1)
        return immigrants

def getHarvest():
    random_num = random.randint(1,8)
    return random_num

def doRatsInfest():
    random_num = random.randint(1,3)
    random_num = float(random_num) / 10
    return random_num

def priceOfLand():
    price = random.randint(16,22)
    return price

def endProgress(total_starved, final_acre):
    print "\nYou made it through all 10 rounds, and your reign is over."
    print "You starved", total_starved, "people and ended with", final_acre, "acres."
    if total_starved > 50:
        print "Let's just say with that starvation rate, you kind of suck."
        return
    if final_acre < 1000:
        print "You didn't starve too many people, but you only ended with", final_acre, "acres. Ehh, you're just OK."
        return
    print "What a rockstar! You're a king for the ages!"
    return
    
def Hammurabi():
    # declare variable values
    starved = 0
    immigrants = 5
    population = 100
    harvest = 3000 # total bushels harvested
    bushels_per_acre = 3 # amount harvested for each acre planted
    rats_ate = 200 # bushels destroyed by rats
    bushels_in_storage = 2800
    acres_owned = 1000
    cost_per_acre = 19 # each acre costs this many bushels
    plague_deaths = 0

    #variables to keep track of progress for the end
    total_starved = 0

    #begin running main
    print_intro()

    #main loop
    for year in range(1,11):
        #tell player current status
        print("\n\nO great Hammurabi!")
        print("You are in year " + str(year) + " of your ten year rule.")
        print("In the previous year " + str(starved) + " people starved to death.")
        print("In the previous year " + str(immigrants) + " people entered the kingdom.")
        print("The population is now " + str(population) + ".")
        print("We harvested " + str(harvest) + " bushels at " + str(bushels_per_acre) + " bushels per acre.")
        print("Rats destroyed " + str(rats_ate) + " bushels, leaving " + str(bushels_in_storage) + " bushels in storage.")
        print("The city owns " + str(acres_owned) + " acres of land.")
        print("Land is currently worth " + str(cost_per_acre) + " bushels per acre.")
        print("There were " + str(plague_deaths) + " deaths from the plague.")

        #ask player input
        acre_change = ask_to_buy_land(bushels_in_storage, cost_per_acre)
        if acre_change <= 0:
            acre_change = - ask_to_sell_land(bushels_in_storage)
        acres_owned = acres_owned + acre_change
        bushels_in_storage = bushels_in_storage - acre_change * cost_per_acre

        bushels_feed = ask_to_feed(bushels_in_storage)
        bushels_in_storage = bushels_in_storage - bushels_feed

        acres_to_cultivate = ask_to_cultivate(acres_owned, population, bushels_in_storage)

        #we have all input, start running the turn
        #plague deaths
       
        if isPlague():
            plague_deaths = population / 2
        else:
            plague_deaths = 0
        population = population - plague_deaths
            
        #how many starved?
        starved = numStarving(population, bushels_feed)
        
        if (( float(starved) / population ) > .45):
            print "You have ben kicked out of office!!"
            print "You starved", starved, "people in one year you bastard!"
            print "Due to this extreme mismanagement, you have not only been impeached and "
            print "thrown out of office, but you have also been declared 'National Jackass'!!"
            return
        total_starved = total_starved + starved
        #what is new population?
        immigrants = numImmigrants(acres_owned, bushels_in_storage, population, starved)
        population = population - starved + immigrants

        #what is new number of bushels?
        bushels_per_acre = getHarvest()
        harvest = bushels_per_acre * acres_to_cultivate
        bushels_in_storage = bushels_in_storage + harvest - acres_to_cultivate

        #40% chance of a rat infestation
        if (random.randint(1,10) <= 4):
            rats_ate = int(round(doRatsInfest() * bushels_in_storage))
        else:
            rats_ate = 0
       
        bushels_in_storage = bushels_in_storage - rats_ate

        cost_per_acre = priceOfLand()

    #outside of main loop
    endProgress(total_starved, acres_owned)

    return

Hammurabi()

