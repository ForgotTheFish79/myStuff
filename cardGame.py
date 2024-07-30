import gc
import time
from tkinter import *

from PIL import ImageTk, Image
from tkinter import filedialog, messagebox
import random

root = Tk()
root.title("Title")
root.iconbitmap('images/pokerCard.ico')
root.title("Poker")
#root.geometry("1200x900")
root.configure(bg="#0a6522")
root.attributes("-fullscreen", True)

preFlopPowerDict = {'AA': 0, 'AKs': 2, 'AQs': 2, 'AJs': 3, 'ATs': 5, 'A9s': 8, 'A8s': 10, 'A7s': 13, 'A6s': 14,
                    'A5s': 12, 'A4s': 14, 'A3s': 14, 'A2s': 17,
                    'AKo': 5, 'KK': 1, 'KQs': 3, 'KJs': 3, 'KTs': 6, 'K9s': 10, 'K8s': 16, 'K7s': 19, 'K6s': 24,
                    'K5s': 25, 'K4s': 25, 'K3s': 26, 'K2s': 26,
                    'AQo': 8, 'KQo': 9, 'QQ': 1, 'QJs': 5, 'QTs': 6, 'Q9s': 10, 'Q8s': 19, 'Q7s': 26, 'Q6s': 28,
                    'Q5s': 29, 'Q4s': 29, 'Q3s': 30, 'Q2s': 31,
                    'AJo': 12, 'KJo': 14, 'QJo': 15, 'JJ': 2, 'JTs': 6, 'J9s': 11, 'J8s': 17, 'J7s': 27, 'J6s': 33,
                    'J5s': 35, 'J4s': 37, 'J3s': 37, 'J2s': 38,
                    'ATo': 18, 'KTo': 20, 'QTo': 22, 'JTo': 21, 'TT': 4, 'T9s': 10, 'T8s': 16, 'T7s': 25, 'T6s': 31,
                    'T5s': 40, 'T4s': 40, 'T3s': 41, 'T2s': 41,
                    'A9o': 32, 'K9o': 35, 'Q9o': 36, 'J9o': 34, 'T9o': 31, '99': 7, '98s': 17, '97s': 24, '96s': 29,
                    '95s': 38, '94s': 47, '93s': 47, '92s': 42,
                    'A8o': 39, 'K8o': 50, 'Q8o': 53, 'J8o': 48, 'T8o': 43, '98o': 42, '88': 9, '87s': 21, '86s': 27,
                    '85s': 33, '84s': 40, '83s': 53, '82s': 54,
                    'A7o': 45, 'K7o': 57, 'Q7o': 66, 'J7o': 64, 'T7o': 59, '97o': 55, '87o': 52, '77': 12, '76s': 25,
                    '75s': 28, '74s': 37, '73s': 45, '72s': 56,
                    'A6o': 51, 'K6o': 57, 'Q6o': 71, 'J6o': 80, 'T6o': 74, '96o': 68, '86o': 61, '76o': 57, '66': 16,
                    '65s': 27, '64s': 29, '63s': 38, '62s': 49,
                    'A5o': 44, 'K5o': 63, 'Q5o': 75, 'J5o': 82, 'T5o': 89, '95o': 83, '85o': 73, '75o': 65, '65o': 58,
                    '55': 20, '54s': 28, '53s': 32, '52s': 39,
                    'A4o': 46, 'K4o': 67, 'Q4o': 76, 'J4o': 85, 'T4o': 90, '94o': 95, '84o': 88, '74o': 78, '64o': 70,
                    '54o': 62, '44': 23, '43s': 36, '42s': 41,
                    'A3o': 49, 'K3o': 67, 'Q3o': 77, 'J3o': 86, 'T3o': 92, '93o': 96, '83o': 98, '73o': 93, '63o': 81,
                    '53o': 72, '43o': 76, '33': 23, '32s': 46,
                    'A2o': 54, 'K2o': 69, 'Q2o': 79, 'J2o': 87, 'T2o': 94, '92o': 97, '82o': 99, '72o': 100, '62o': 95,
                    '52o': 84, '42o': 86, '32o': 91, '22': 24
                    }


class Card:
    def __init__(self, value='-1', suit=""):

        cf = suit.casefold()
        if (cf == "diamonds" or cf == "spades" or cf == "hearts" or cf == "clubs"):
            self.suit = suit
        elif cf == "":
            pass
        else:
            raise Exception("Suit not in format")
        value = str(value)

        if value == "2" or value == "3" or value == "4" or value == "5" or value == "6" or value == "7" or value == "8" or value == "9":
            self.value = value
            self.power = int(value)
        elif value == "T" or value == "J" or value == "Q" or value == 'K' or value == "A" or value == "AL":
            self.value = value
            if (value == "T"):
                self.power = 10
            elif (value == "J"):
                self.power = 11
            elif (value == "Q"):
                self.power = 12
            elif (value == "K"):
                self.power = 13
            elif (value == "A"):
                self.power = 14
            elif (value == "AL"):
                self.power = 1
        elif value == '-1':
            self.randomCard()
        else:
            print(value)
            raise Exception("Value isnt in format")

    def randomCard(self):
        rn1 = random.randint(0, 3)
        rn2 = random.randint(2, 14)
        if rn1 == 0:
            self.suit = "diamonds"
        elif rn1 == 1:
            self.suit = "spades"
        elif rn1 == 2:
            self.suit = "hearts"
        elif rn1 == 3:
            self.suit = "clubs"
        else:
            raise Exception("Random is messed up")
        if rn2 == 2:
            self.value = '2'
            self.power = 2
        elif rn2 == 3:
            self.value = '3'
            self.power = 3
        elif rn2 == 4:
            self.value = '4'
            self.power = 4
        elif rn2 == 5:
            self.value = '5'
            self.power = 5
        elif rn2 == 6:
            self.value = '6'
            self.power = 6
        elif rn2 == 7:
            self.value = '7'
            self.power = 7
        elif rn2 == 8:
            self.value = '8'
            self.power = 8
        elif rn2 == 9:
            self.value = '9'
            self.power = 9
        elif rn2 == 10:
            self.value = "T"
            self.power = 10
        elif rn2 == 11:
            self.value = "J"
            self.power = 11
        elif rn2 == 12:
            self.value = "Q"
            self.power = 12
        elif rn2 == 13:
            self.value = "K"
            self.power = 13
        elif rn2 == 14:
            self.value = "A"
            self.power = 14

    def __repr__(self):
        return str(self.value) + " of " + str(self.suit)

    def __eq__(self, other):
        return self.power == other.power and self.suit==other.suit

    def __lt__(self, other):
        return self.power < other.power

    def __le__(self, other):
        return self.power <= other.power

    def __ge__(self, other):
        return self.power >= other.power

    def __ne__(self, other):
        return self.power != other.power

    def __gt__(self, other):
        return self.power > other.power


#jOD = Card("J","Diamonds")

class Deck:
    def __init__(self):
        self.deck = []
        suits = ["Hearts", "Diamonds", "Clubs", "Spades"]
        values = ["A", "2", "3", "4", "5", "6", '7', '8', '9', "T", "J", "Q", "K"]

        for s in suits:
            for v in values:
                card = Card(v, s)
                self.deck.append(card)

    def shuffle(self):
        self.deck = sorted(self.deck, key=lambda x: random.random())

    def draw(self, n: int) -> list:
        l = []
        #self.shuffle()
        for i in range(n):
            l.append(self.deck[0])
            self.deck.remove(self.deck[0])
        return l

    def drawOne(self) -> Card:
        card = self.deck[0]
        self.deck.remove(card)
        return card

    def remove(self, card):
        self.deck.remove(card)

    def __repr__(self):
        return str(self.deck)


class Bot:

    def __init__(self, botNumber: int, card1, card2, chips=4000):
        self.chips = chips
        if botNumber == 1:
            self.xCoord = 500
            self.yCoord = 900
        elif botNumber == 2:
            self.xCoord = 100
            self.yCoord = 800
        elif botNumber == 3:
            self.xCoord = 100
            self.yCoord = 500
        elif botNumber == 4:
            self.xCoord = 100
            self.yCoord = 200
        elif botNumber == 5:
            self.xCoord = 500
            self.yCoord = 150
        elif botNumber == 6:
            self.xCoord = 900
            self.yCoord = 150
        elif botNumber == 7:
            self.xCoord = 1300
            self.yCoord = 200
        elif botNumber == 8:
            self.xCoord = 1300
            self.yCoord = 500
        else:
            raise ValueError
        self.cardList = [card1, card2]
        self.cardPic1 = grabCardImage(card1)
        self.cardPic2 = grabCardImage(card2)
        self.botNumber = botNumber
        #self.cardPic1 = pic1
        #self.cardPic2 = pic2
        self.card1 = card1
        self.card2 = card2

        self.state=False
        self.selfBet=0
        #this is whether the bot folding before preflop(True) vs folding after,False
        i = random.randint(1, 3)

        if (i == 1):  # tight
            self.threshold = 20
        elif i == 2:  #normal
            self.threshold = 35
        else:  #loose
            self.threshold = 45


    def hideCard(self):
        if self.folded == False:
            self.card1Pic = showCardBack()
            self.card2Pic = showCardBack()
            self.pic1 = (Label(root, image=self.card1Pic))
            self.pic1.place(x=self.xCoord, y=self.yCoord, anchor=S)
            self.pic2 = Label(root, image=self.card2Pic)
            self.pic2.place(x=self.xCoord + 100, y=self.yCoord, anchor=S)
        else:
            self.card1Pic = showCardBackFolded(self.state)
            self.card2Pic = showCardBackFolded(self.state)
            self.pic1 = (Label(root, image=self.card1Pic))
            self.pic1.place(x=self.xCoord, y=self.yCoord, anchor=S)
            self.pic2 = Label(root, image=self.card2Pic)
            self.pic2.place(x=self.xCoord + 100, y=self.yCoord, anchor=S)
        #botLabel = Label(root, text="Bot " + str(self.botNumber), font=("Helvetica", 20))
        #botLabel.place(x=self.xCoord + 50, y=self.yCoord + 50, anchor=S)
        #botLabel = Label(root, text="Bot " + str(self.botNumber), font=("Helvetica", 20))
        #botLabel.place(x=self.xCoord + 50, y=self.yCoord + 50, anchor=S)

    def fold(self):
            self.folded = True

            if len(self.cardList) ==2:
                self.state=True
            else:
                self.state=False
    def preCheck(self):

        if (self.preFlopHand() > self.threshold): # if the hand is bad, fold
            self.fold()

        else:
            #pot update
            global potVal
            global  bet



            potVal+=bet #add whatever the bet is
            self.selfBet+=bet # calls the preflop
            self.chips-=bet
            self.folded = False
            if self.selfBet==bet and self.preFlopHand()<15: #raise the bet if the preflop hand is excellent
                self.chips-=50
                potVal+=50
                self.selfBet+=50
                bet+=50
                print(self)

            potLabelUpdate()
               # print(bet)





    def paint(self):
        try:
            self.botLabel.destroy()
        except:
            pass

        if self.folded:
            self.cardPic1=showCardBackFolded(self.state)
            self.cardPic2=showCardBackFolded(self.state)
        self.pic1 = (Label(root, image=self.cardPic1))
        self.pic1.place(x=self.xCoord, y=self.yCoord, anchor=S)
        self.pic2 = Label(root, image=self.cardPic2)
        self.pic2.place(x=self.xCoord + 100, y=self.yCoord, anchor=S)
        self.botLabel = Label(root, text="Bot " + str(self.botNumber)+" chips:"+str(self.chips), font=("Helvetica", 16))
        self.botLabel.place(x=self.xCoord + 50, y=self.yCoord + 50, anchor=S)

    def reset(self):  #probably not necessary since the bots are updated every time.
        self.folded = False

    def preFlopHand(self) -> int:
        result = ""

        if (self.card1.power > self.card2.power):
            result += self.card1.value
            result += self.card2.value
            if self.card1.suit == self.card2.suit:
                result += "s"
            else:
                result += "o"
        elif self.card1.power == self.card2.power:
            result += self.card1.value
            result += self.card2.value
        else:
            result += self.card2.value
            result += self.card1.value
            if self.card1.suit == self.card2.suit:
                result += "s"
            else:
                result += "o"
        return preFlopPowerDict.get(result)

    def revealCard(self):
        if (self.folded == False):
            self.card1Pic = grabCardImage(self.card1)
            self.card2Pic = grabCardImage(self.card2)
            #botLabel = Label(root, text="Bot " + str(self.botNumber), font=("Helvetica", 20))
            #botLabel.place(x=self.xCoord + 50, y=self.yCoord + 50, anchor=S)
        else:
            self.card1Pic = showCardBackFolded(self.state)
            self.card2Pic = showCardBackFolded(self.state)
        self.pic1 = (Label(root, image=self.card1Pic))
        self.pic1.place(x=self.xCoord, y=self.yCoord, anchor=S)
        self.pic2 = Label(root, image=self.card2Pic)
        self.pic2.place(x=self.xCoord + 100, y=self.yCoord, anchor=S)
            #botLabel = Label(root, text="Bot " + str(self.botNumber), font=("Helvetica", 20))
            #botLabel.place(x=self.xCoord + 50, y=self.yCoord + 50, anchor=S)

    def updateList(self, card):
        self.cardList.append(card)
        self.state=False
    def newRoundReset(self, card1, card2):
        global potVal
        self.cardList = [card1, card2]
        self.card1 = card1
        self.card2 = card2
        self.cardPic1=grabCardImage(card1)
        self.cardPic2=grabCardImage(card2)
        self.state=True
        if self.chips<50:
            self.chips+=2000
            messagebox.showinfo("Bot Action",'bot '+str(self.botNumber)+"has bought back in for 2000 chips ")
        try:
            self.botLabel.destroy()
        except:
            pass
        if blindStage==self.botNumber or blindStage==self.botNumber+1:#don't insta fold if big blind or small blind preeflop
            self.folded=False
            if blindStage==self.botNumber:
                self.selfBet=50 # big blind put in 50
                self.chips-=50
            else:
                self.preCheck()
                self.selfbet=25 #small blind put in 25, if decent, put in another 25
                self.chips-=25
                if not self.folded:
                    self.chips-=25
                    potVal+=25

        else:
            self.preCheck()

        self.paint()
    def destroy(self):
        try:
            self.botLabel.destroy()
        except:
            pass
    def __repr__(self):
        return "Bot "+str(self.botNumber)

def stFlushCheck(deck) -> int:  #0 means none of above, 1 means straight flush, 2 means royal flush
    flushDict = {"Hearts": 0, "Clubs": 0, "Spades": 0, "Diamonds": 0}
    if (len(deck) < 5):
        return 0
    for card in deck:
        flushDict[card.suit] = flushDict[card.suit] + 1
    for k in flushDict.keys():
        if (flushDict[k] >= 5):
            l = []
            for c in deck:
                if c.suit == k:
                    l.append(c)
            st = straightCheck(l)
            if (st):
                copy = l.copy()
                i = 0
                while i < len(copy):
                    card = copy[i]
                    if card.power < 10:
                        copy.remove(card)
                        #print(copy)
                        i -= 1
                    if (len(copy) < 5):
                        return 1
                    i += 1
                return 2
    return 0


def flushCheck(deck) -> bool:
    flushDict = {"Hearts": 0, "Clubs": 0, "Spades": 0, "Diamonds": 0}
    for card in deck:
        flushDict[card.suit] = flushDict[card.suit] + 1
    for k in flushDict.keys():
        if (flushDict[k] >= 5):
            return True
    return False


def multCheck(deck) -> list:
    multDict = {"2": 0, "3": 0, "4": 0, "5": 0, "6": 0, "7": 0, "8": 0, "9": 0, "T": 0, "J": 0, "Q": 0, "K": 0, "A": 0}
    sortedSect = sorted(deck, reverse=True)
    for card in deck:
        multDict[card.value] = multDict[card.value] + 1
    highest = 0
    key = ""

    for k in multDict.keys():
        if (multDict[k] >= highest):
            highest = multDict[k]  # highest is the msot of a multiple
            key = k
    if (highest == 4):
        highest2=0
        key2=""
        for k in multDict.keys():
            if (k == key):
                continue
            else:
                if (multDict[k] >= highest2):
                    highest2 = multDict[k]
                    key2 = k
        return [8, key,key2]  #"4 of a kind (" + key + "s)"
    elif (highest == 3):

        highest2 = 0
        key2 = ""
        for k in multDict.keys():
            if (k == key):
                continue
            else:
                if (multDict[k] >= highest2):
                    highest2 = multDict[k]
                    key2 = k
        if (highest2 >= 2):
            return [7, key, key2]  #"Full House with trips: " + key + " and pair of " + key2 + "s"
        filler1 = ''
        pos = 0
        filler2 = ''
        for card in sortedSect:
            if (pos == 0 and card.value != key):
                filler1 = card.value
                pos += 1
            elif (pos == 1 and card.value != key):
                filler2 = card.value
                pos += 1
        return [4, key, filler1, filler2]  #"trips of " + key + "s"
    elif (highest == 2):

        pos = 0
        filler1 = ""
        filler2 = ""
        filler3 = ""
        for card in sortedSect:
            if (pos == 0 and card.value != key):
                filler1 = card.value
                pos += 1
            elif (pos == 1 and card.value != key):
                filler2 = card.value
                pos += 1
            elif (pos == 2 and card.value != key):
                filler3 = card.value
                pos += 1

        highest2 = 0
        key2 = ""
        for k in multDict.keys():
            if (k == key):
                continue
            else:
                if (multDict[k] >= highest2):
                    highest2 = multDict[k]
                    key2 = k

        if (highest2 >= 2):
            for k in multDict.keys():
                if (k == key or k == key2):
                    continue
                else:
                    if (multDict[k] > 0):
                        filler1 = k
            #key will always be larger in value compared to key2
            return [3, key, key2, filler1]  #"Two Pair with a pair of " + key + "s and pair of " + key2 + "s"
        return [2, key, filler1, filler2, filler3]  #"a pair of " + key + "s"
    else:
        sortedSect = sorted(deck, reverse=True)
        pos = 0;
        filler1 = ""
        filler2 = ""
        filler3 = ""
        filler4 = ""
        for card in sortedSect:
            if (pos == 0 and card.value != key):
                filler1 = card.value
                pos += 1
            elif (pos == 1 and card.value != key):
                filler2 = card.value
                pos += 1
            elif (pos == 2 and card.value != key):
                filler3 = card.value
                pos += 1
            elif (pos == 3 and card.value != key):
                filler4 = card.value
                pos += 1

        return [1, key, filler1, filler2, filler3, filler4]  #"high card " + key+"s"
    #return key+" : "+str(highest)


def straightCheck(sortedSect) -> bool:
    if (len(sortedSect) < 5):
        return False
    var = 4  # how manycards needed for a straight
    pos = 0  # position in the sorted deck
    if (sortedSect[len(sortedSect) - 1].power == 14 and sortedSect[0].power == 2):

        #print("ACE and 2 DETECTED")
        sortedSect.insert(0, Card("AL", "spades"))  # place holder suit

        #print(sortedSect)
        curr = sortedSect[0]
        while (var > 0 and pos < len(sortedSect) - 1):
            next = sortedSect[pos + 1]
            #print("next "+str(next.power)+" curr "+str(curr.power))
            if (next.power == curr.power + 1):
                var -= 1
                #print('cont' +str(curr.power))
            elif (next.power == curr.power):
                #print("EQL")
                var += 0

            else:

                var = 4
            curr = next
            #print(var)
            pos += 1
        del sortedSect[0]
        if (var == 0):
            #print("val 0")
            return True
        return False

    #print(sortedSect)
    curr = sortedSect[0]
    while (var > 0 and pos < len(sortedSect) - 1):

        next = sortedSect[pos + 1]
        #print(sortedSect)
        #print("next "+str(next.power)+" curr "+str(curr.power))
        if (next.power == curr.power + 1):
            #print('cont' +str(curr.power))
            var -= 1
        elif (next.power == curr.power):
            var += 0
        else:

            var = 4
        curr = next
        pos += 1
    if (var == 0):
        #print("STRAIGHT")
        return True
    return False

    #print(sortedSect)
    curr = sortedSect[0]
    while (var > 0 and pos < len(sortedSect) - 1):
        next = sortedSect[pos + 1]
        #print(sortedSect)
        #print("next "+str(next.power)+" curr "+str(curr.power))
        if (next.power == curr.power + 1):
            #print('cont' +str(curr.power))
            var -= 1
        elif (next.power == curr.power):
            var += 0
        else:
            var = 4
        curr = next
        pos += 1
    if (var == 0):
        #print("STRAIGHT")
        return True
    return False


def findStHigh(sortedDeck) -> str:
    #given that the sortedDeck contains a straight. 12345 67 lowest high could be is position 5
    #unless an A and 2 are involved bug to be fixed
    #print(sortedDeck)

    #TODO 789TJKA gives K as high when it should be J

    noDupe = []
    [noDupe.append(x) for x in sortedDeck if x not in noDupe]
    noDupe = sorted(noDupe)
    if len(noDupe) == 5:  # if there are 3 duplicates and a straight, the high has to be at position 4
        if noDupe[4].power == 14 and noDupe[0].power == 2:  # if ace is the duplicate in A2345 only, return 5
            return noDupe[3].value
        return noDupe[4].value
    elif len(noDupe) == 6:  # if there is one duplicate and a straight, the high is at position 5 or 4, or 3 if 2345XA
        if noDupe[5].power == 14 and noDupe[
            0].power == 2:  #if ace and 2 exists, the straight is either 23456A or A2345X
            if noDupe[4].power == 6:
                return noDupe[4].value
            else:
                return noDupe[3].value
        else:  # if either A or 2 doesn't exist, normal case
            if noDupe[5].power == noDupe[4].power + 1:
                return noDupe[5].value
            else:
                return noDupe[4].value
    else:  #there are no duplicates:

        if noDupe[6].power == 14 and noDupe[0].power == 2:  #2345XXA,23456xA,234567A,2XXXXXA are the possibilities
            if noDupe[1].power != 3:  #must be 2XXXXXA unless 2XTJQKA
                if noDupe[5].power == 13:
                    return noDupe[6].value
                return noDupe[5].value
            elif noDupe[
                4].power == 6:  # if there is a 6,it must be 23456 or 34567 since A2345 is overriden by the larger straight
                if noDupe[5].power == 7:  # if 7 and six exist, its 34567
                    return noDupe[5].value
                else:  # if there is no7 , its 23456
                    return noDupe[4].value
            else:  # if there is no six, its gotta be A2345 right?
                return noDupe[3].value
        else:  #SSSSSXX.XSSSSSX.XXSSSSS are the three possibilities
            if noDupe[6].power == noDupe[5].power + 1:
                if noDupe[5].power == noDupe[4].power + 1:  #confirms XXSSSSS
                    return noDupe[6].value
                else:  #confirms SSSSSXX where the XX happen to connect
                    return noDupe[4].value
            elif noDupe[5].power == noDupe[4].power + 1:  #XXSSSSS is eliminated as 6 is not connected
                return noDupe[
                    5].value  #confirms XSSSSSX since XSSS cannot form a straight without the last two connected cards
            else:  #SSSSSXX is isolated as onyl 5 cards remain
                return noDupe[4].value

    """
    deckEnd = len(sortedDeck) - 1
    if (sortedDeck[deckEnd].power != sortedDeck[deckEnd - 1].power + 1):  # if the last card is not invol

        deckEnd -= 1
        print(sortedDeck[deckEnd - 1].power)
        if (sortedDeck[deckEnd].power != sortedDeck[deckEnd - 1].power + 1):

            return sortedDeck[deckEnd - 1].value
        else:

            if (sortedDeck[0].power != 2 and sortedDeck[len(sortedDeck) - 1].power != 14):
                return sortedDeck[deckEnd].value
            else:
                #current bug is here

                return sortedDeck[3].value
    else:
        deckEnd -= 1

        if (sortedDeck[deckEnd].power != sortedDeck[deckEnd - 1].power + 1):
            if (sortedDeck[0].power != 2 or sortedDeck[len(sortedDeck) - 1].power != 14):
                return sortedDeck[deckEnd - 1].value
            else:
                return sortedDeck[3].value

        else:

            if (sortedDeck[0].power != 2 or sortedDeck[len(sortedDeck) - 1].power != 14 or sortedDeck[
                3].power != 5):  # if ace and 2 are involved and 3 in a row at the end
                return sortedDeck[deckEnd + 1].value
            else:
                if (sortedDeck[deckEnd].power == sortedDeck[deckEnd - 1].power):
                    return sortedDeck[len(sortedDeck) - 1].value
                else:
                    return sortedDeck[3].value
        """


def allHandCheck(deck) -> list:  #str:
    flushDict = {"Hearts": 0, "Clubs": 0, "Spades": 0, "Diamonds": 0}
    for card in deck:
        flushDict[card.suit] = flushDict[card.suit] + 1
    for k in flushDict.keys():
        if flushDict[k] >= 5:
            # if there is a flush
            l = []
            for c in deck:
                if c.suit == k:
                    l.append(c)
                # make a new list to do straight check in.
            sortedSect = sorted(l)
            st = straightCheck(sortedSect)
            #print(" l is ")

            highestCard = sortedSect[len(sortedSect) - 1]

            # if it is a straight, st will be true which means that there is a straight flush
            if (st):

                copy = l.copy()
                i = 0
                while i < len(copy):
                    card = copy[i]
                    if card.power < 10:
                        copy.remove(card)
                        #print(copy)
                        i -= 1
                    if len(copy) < 5:
                        return [9, findStHigh(sortedSect)]  #"Straight Flush with "+findStHigh(sortedSect)+" high"
                    i += 1
                return [10]  #"Royal Flush"
            key = highestCard.value
            pos = 0;
            sortedSect = sorted(l, reverse=True)
            filler1 = ""
            filler2 = ""
            filler3 = ""
            filler4 = ""
            for card in sortedSect:
                if (pos == 0 and card.value != key):
                    filler1 = card.value
                    pos += 1
                elif (pos == 1 and card.value != key):
                    filler2 = card.value
                    pos += 1
                elif (pos == 2 and card.value != key):
                    filler3 = card.value
                    pos += 1
                elif (pos == 3 and card.value != key):
                    filler4 = card.value
                    pos += 1

            return [6, highestCard.value, filler1, filler2, filler3, filler4]  #"Flush with "+highestCard.value+" high"
    sortedSect = sorted(deck)
    straightness = straightCheck(sortedSect)
    if (straightness):
        return [5, findStHigh(sortedSect)]  #" Straight with "+findStHigh(sortedSect)+" high"
    return multCheck(deck)


# must only use results from the allHandCheck
def handResult(first: list, second: list) -> int:
    if (first[0] > second[0]): #stronger set
        #return "Player 1 win"
        return 1
    elif (second[0] > first[0]):
        return 2  #"Player 2 wins"
    else:

        if(second[0]==10 and first[0]==10): #equal set, diff kicker/power
            return 0
        copyFirst = first.copy()
        #print(copyFirst)

        copySecond = second.copy()

        for i in range(len(second)-1): #[4,f,f,f,f] we need 4 iteratiosn

            element1 = copyFirst[i+1]
            element2 = copySecond[i+1]
            #if element1=='' or element2=='':

                #print("-------------")
                #print(copyFirst)
                #print(copySecond)
            if element1==''or element2=='':
                print(copyFirst)
                print(copySecond)
                print(element1)
                print(element2)
                if element1=='' and element2=='':
                    return 0
            if (element1 == "J"):
                element1 = 11
            elif (element1 == "Q"):
                element1 = 12
            elif (element1 == "K"):
                element1 = 13
            elif (element1 == "A"):
                element1 = 14
            elif (element1 == "T"):
                element1 = 10
            else:
                element1 = int(element1)
            if (element2 == "J"):
                element2 = 11
            elif (element2 == "Q"):
                element2 = 12
            elif (element2 == "K"):
                element2 = 13
            elif (element2 == "A"):
                element2 = 14
            elif (element2 == "T"):
                element2 = 10
            else:
                element2 = int(element2)

            if (element1 > element2):
                return 1  #"Player 1 win";
            elif (element1 < element2):
                return 2  #"Player 2 wins";
        return 0  #"tie, split pot"


def interpretHand(hand: list) -> str:  # not any list, only use for hands returned from handEvaluator

    handPower = hand[0]
    if handPower == 10:
        return "Royal Flush"
    elif handPower == 9:
        return "Straight Flush with " + str(hand[1]) + " high"
    elif handPower == 8:
        return "Quad " + str(hand[1]) + "s with kicker: "+str(hand[2])
    elif handPower == 7:
        return "a Full House with triple " + str(hand[1]) + " and a pair of " + str(hand[2])
    elif handPower == 6:
        return " a Flush with " + str(hand[1]) + "-High and kickers :" + str(hand[2]) + "," + str(hand[3]) + "," + str(
            hand[4]) + "," + str(hand[5])
    elif handPower == 5:
        return "a Straight with " + str(hand[1]) + " high"
    elif handPower == 4:
        result = "three of " + str(hand[1]) + "s"
        if str(hand[2]) != '':
            result += ", with kickers :" + str(hand[2]) + ','
        if str(hand[3]) != '':
            result += str(hand[3])
        return result  #"three of " + str(hand[1]) + "s, with kickers :" + str(hand[2])+","+str(hand[3])
    elif handPower == 3:
        result = "two pairs: " + str(hand[1]) + 's and ' + str(hand[2]) + "s"
        if str(hand[3]) != '':
            result += ", with kickers :" + str(hand[3])

        return result
        #return "two pairs: " + str(hand[1]) + 's and ' + str(hand[2]) + "s, with kickers :" + str(hand[3])
    elif handPower == 2:
        result = "a pair of " + str(hand[1]) + "s"
        if str(hand[2]) != '':
            result += ", with kickers :" + str(hand[2]) + ','
        if str(hand[3]) != '':
            result += str(hand[3]) + ','
        if str(hand[4]) != '':
            result += str(hand[4])
        return result  #"a pair of " + str(hand[1]) + "s, with kickers :" + str(hand[2]) + "," + str(hand[3]) + "," + str( hand[4])
    else:  #high card
        result = "High Card " + str(hand[1]) + " with kickers :" + str(hand[2])
        if str(hand[3]) != '':
            result += ","+str(hand[3]) + ','
        if str(hand[4]) != '':
            result += str(hand[4]) + ','
        if str(hand[5]) != '':
            result += str(hand[5])

        return result  #"High Card " + str(hand[1]) + " with kickers :" + str(hand[2]) + "," + str(hand[3]) + "," + str(
        # hand[4]) + "," + str(hand[5])


def makeSureQuit():
    response = messagebox.askyesno("Quit", "Are you sure you want to quit?")
    if (response):
        root.quit()


def grabCardImage(card: Card):
    name = ""
    if (card.value == "T"):
        name = "10"
    elif card.value == "J":
        name = "jack"
    elif card.value == "Q":
        name = "queen"
    elif card.value == "K":
        name = "king"
    elif card.value == "A":
        name = "ace"
    else:
        name = str(card.value)
    imageURL = "images/PNG-cards-1.3/" + name + "_of_" + str(card.suit) + ".png"
    image = Image.open(imageURL)
    image = image.resize((80, 120))
    image = ImageTk.PhotoImage(image)
    return image


def showCardBack():
    imageURL = "images/cardback.png"
    image = Image.open(imageURL)
    image = image.resize((80, 120))
    image = ImageTk.PhotoImage(image)
    return image


def showCardBackFolded(preflop:bool):
    if preflop:
        imageURL = "images/GreenPic.png"
    else:
        imageURL = "images/GreyPic.png"
    image = Image.open(imageURL)
    image = image.resize((80, 120))
    image = ImageTk.PhotoImage(image)
    return image

def potLabelUpdate():
    global potLabel
    try:
        potLabel.destroy()
    except:
        pass
    potLabel = (Label(root, text = "Pot: "+str(potVal),font = ("Helvetica",16)))
    potLabel.place(x=750, y=550, anchor=S)

def playerCall():
    global bet
    global playerBet
    global potVal
    global playerChips
    global playerLabel
    x = bet-playerBet
    playerBet+=x
    playerChips-=x
    potVal+=x
    try:
        playerLabel.destroy()
    except:
        pass
    potLabelUpdate()
    playerLabel = Label(root, text="Player"+"Chips: "+str(playerChips), font=("Helvetica", 16))
    playerLabel.place(x=950, y=950, anchor=S)
    top.destroy()

def playerFold():
    global pFold
    pFold=True
    global cardLabel
    global cardLabel2
    global me1
    global me2
    me1=showCardBackFolded(False)
    me2=showCardBackFolded(False)
    cardLabel = (Label(root, image=me1))
    cardLabel2= (Label(root, image=me2))
    cardLabel.place(x=900, y=900, anchor=S)
    cardLabel2.place(x=1000, y=900, anchor=S)
    top.destroy()
    #while stage!=5:
        #print("num")
        #revealBoard()
        #time.sleep(1)
        #print(stage)

def confirmBet():
    global top
    global entryBox
    global potVal
    global bet
    global prLabel
    global playerBet
    global playerChips

    try:
        prLabel.destroy()
    except:
        pass
    newBet=entryBox.get()
    entryBox.delete(0, END)
    try:
        x=(int(newBet))
        if blindStage==1:
            x-=25
        else:
            x-=50
        if(x<25):
            prLabel =Label(top,text="Value is too small")
            prLabel.pack()
        elif(x>playerChips):
            prLabel =Label(top,text="Can't bet more than 100 percent of your money")
            prLabel.pack()
        else:
            potVal+=x
            bet+=x

            playerChips-=x
            playerBet+=x
            prLabel =Label(top,text=newBet)
            prLabel.pack()
            playerLabel = Label(root, text="Player"+"Chips: "+str(playerChips), font=("Helvetica", 16))
            playerLabel.place(x=950, y=950, anchor=S)
            for bot in botList:
                if not bot.folded:
                    decision =botDecision(bot,2500)
                    if decision:
                        owed=bet-bot.selfBet
                        if bot.chips-owed<0:
                            bot.selfBet+=bot.chips
                            potVal+=bot.chips
                            bot.chips=0
                        else:
                            bot.selfBet+=owed
                            bot.chips-=owed
                            potVal+=owed
                    else:
                        bot.fold()

                    bot.paint()
                    bot.hideCard()
                else:
                    pass
            potLabelUpdate()
            playerCall()

    except:
        prLabel =Label(top,text="RETRY NOT A NUMBER")
        prLabel.pack()



def playerAction():
    global top
    global entryBox
    top = Toplevel()
    top.iconbitmap('images/pokerCard.ico')
    top.geometry("400x400")
    Button(top, text="Close", command=top.destroy).pack()
    Label(top,text=bet).pack()
    Label(top,text=playerBet).pack()
    Button(top, text="Call",command = playerCall).pack()
    Button(top, text="Fold",command = playerFold).pack()

    entryBox = Entry(top,textvariable = "Quantity", font=('calibre',10,'normal'))
    entryBox.pack()
    Button(top, text="Confirm",command = confirmBet).pack()

def game():
    global me1
    global me2
    global pFold
    pFold=False
    global stage
    stage = 0
    global flop1Pic
    global flop2Pic
    global flop3Pic
    global turnPic
    global riverPic
    global gameDeck
    global myCard1
    global myCard2
    global bot1
    global bot2
    global bot3
    global bot4
    global bot5
    global bot6
    global bot7
    global bot8
    global playerChips
    global numOfOpponents
    global playerBet
    numOfOpponents=8
    playerBet=0
    global boardList
    global botList
    global myHand
    global playerLabel
    global cardLabel
    global cardLabel2
    global textWinner

    global bet
    bet=50
    global potVal
    potVal=75
    global potLabel
    try:
        textWinner.destroy()

    except:
        pass
    try:
        playerLabel.destroy()
    except:
        pass
    try:
        potLabel.destroy()
    except:
        pass
    blindUpdate(blindStage)
    if blindStage==1:
        playerChips-=25
        playerBet=25
    elif blindStage==0:
        playerBet=50
        playerChips-=50

    boardList = []
    gameDeck = Deck()
    gameDeck.shuffle()
    myCard1 = gameDeck.drawOne()
    myCard2 = gameDeck.drawOne()
    myHand = [myCard1, myCard2]

    opCard1 = gameDeck.drawOne()
    opCard2 = gameDeck.drawOne()

    opCard3 = gameDeck.drawOne()
    opCard4 = gameDeck.drawOne()

    opCard5 = gameDeck.drawOne()
    opCard6 = gameDeck.drawOne()
    opCard7 = gameDeck.drawOne()
    opCard8 = gameDeck.drawOne()
    opCard9 = gameDeck.drawOne()
    opCard10 = gameDeck.drawOne()
    opCard11 = gameDeck.drawOne()
    opCard12 = gameDeck.drawOne()
    opCard13 = gameDeck.drawOne()
    opCard14 = gameDeck.drawOne()
    opCard15 = gameDeck.drawOne()
    opCard16 = gameDeck.drawOne()

    #flop1=gameDeck.drawOne()
    #flop2=gameDeck.drawOne()
    #flop3=gameDeck.drawOne()
    #turn = gameDeck.drawOne()
    #river=gameDeck.drawOne()

    me1 = grabCardImage(myCard1)
    me2 = grabCardImage(myCard2)
    bot1.newRoundReset(opCard1, opCard2)
    bot2.newRoundReset(opCard3, opCard4)
    bot3.newRoundReset(opCard5, opCard6)
    bot4.newRoundReset(opCard7, opCard8)
    bot5.newRoundReset(opCard9, opCard10)
    bot6.newRoundReset(opCard11, opCard12)
    bot7.newRoundReset(opCard13, opCard14)
    bot8.newRoundReset(opCard15, opCard16)

    #for bot in botList:
    #print(bot.preFlopHand())
    #print(botList)
    flop1Pic = showCardBack()  #grabCardImage(flop1)
    flop2Pic = showCardBack()  #grabCardImage(flop2)
    flop3Pic = showCardBack()  #grabCardImage(flop3)
    turnPic = showCardBack()  #grabCardImage(turn)
    riverPic = showCardBack()  #grabCardImage(river)
    hideCard()
    #print(str(myCard)+str(myCard2))

    cardLabel = (Label(root, image=me1))
    cardLabel.place(x=900, y=900, anchor=S)
    cardLabel2 = (Label(root, image=me2))
    cardLabel2.place(x=1000, y=900, anchor=S)
    playerLabel = Label(root, text="Player"+"Chips: "+str(playerChips), font=("Helvetica", 16))
    playerLabel.place(x=950, y=950, anchor=S)

    #opp1 = (Label(root, image=o1))
    #opp1.place(x=500,y=900,anchor=S)
    #opp2 = Label(root, image=o2)
    #opp2.place(x=600,y=900,anchor=S)
    #bot1Label = Label(root,text="Bot 1",font=("Helvetica", 20))
    #bot1Label.place(x=550,y=950,anchor=S)
    potLabelUpdate()

    potLabel.place(x=750, y=550, anchor=S)
    """
    opp3 = Label(root, image=o3)
    opp3.place(x=500,y=150,anchor=S)
    opp4 = Label(root, image=o4)
    opp4.place(x=600,y=150,anchor=S)
    bot2Label = Label(root,text="Bot 2",font=("Helvetica", 20))
    bot2Label.place(x=550,y=200,anchor=S)

    opp5 = Label(root, image=o5)
    opp5.place(x=900,y=150,anchor=S)
    opp6 = Label(root, image=o6)
    opp6.place(x=1000,y=150,anchor=S)
    bot3Label = Label(root,text="Bot 3",font=("Helvetica", 20))
    bot3Label.place(x=950,y=200,anchor=S)

    opp7 = Label(root, image=o7)
    opp7.place(x=100,y=200,anchor=S)
    opp8 = Label(root, image=o8)
    opp8.place(x=200,y=200,anchor=S)
    bot4Label = Label(root,text="Bot 4",font=("Helvetica", 20))
    bot4Label.place(x=150,y=250,anchor=S)

    opp9 = Label(root, image=o9)
    opp9.place(x=1300,y=200,anchor=S)
    opp10 = Label(root, image=o10)
    opp10.place(x=1400,y=200,anchor=S)
    bot5Label = Label(root,text="Bot 5",font=("Helvetica", 20))
    bot5Label.place(x=1350,y=250,anchor=S)

    opp11 = Label(root, image=o11)
    opp11.place(x=100,y=500,anchor=S)
    opp12 = Label(root, image=o12)
    opp12.place(x=200,y=500,anchor=S)
    bot6Label = Label(root,text="Bot 6",font=("Helvetica", 20))
    bot6Label.place(x=150,y=550,anchor=S)

    opp13 = Label(root, image=o13)
    opp13.place(x=1300,y=500,anchor=S)
    opp14 = Label(root, image=o14)
    opp14.place(x=1400,y=500,anchor=S)
    bot7Label = Label(root,text="Bot 7",font=("Helvetica", 20))
    bot7Label.place(x=1350,y=550,anchor=S)

    opp15 = Label(root, image=o15)
    opp15.place(x=100,y=800,anchor=S)
    opp16 = Label(root, image=o16)
    opp16.place(x=200,y=800,anchor=S)
    bot8Label = Label(root,text="Bot 8",font=("Helvetica", 20))
    bot8Label.place(x=150,y=850,anchor=S)
    """
    flopCard1 = Label(root, image=flop1Pic)
    flopCard1.place(x=500, y=350)
    flopCard2 = Label(root, image=flop2Pic)
    flopCard2.place(x=600, y=350)
    flopCard3 = Label(root, image=flop3Pic)
    flopCard3.place(x=700, y=350)
    turnCard = Label(root, image=turnPic)
    turnCard.place(x=800, y=350)
    riverCard = Label(root, image=riverPic)
    riverCard.place(x=900, y=350)
    for bot in botList:

        if not bot.folded and bet>bot.selfBet:
                decision =botDecision(bot,2500)
                if decision:
                    owed=bet-bot.selfBet
                    if bot.chips-owed<0:
                        bot.selfBet+=bot.chips
                        potVal+=bot.chips
                        bot.chips=0
                    else:
                        bot.selfBet+=owed
                        bot.chips-=owed
                        potVal+=owed
                else:
                    bot.fold()
                bot.paint()
                bot.hideCard()
        else:
            pass
    #callCheck()
    #print("Table bet")
    #print(bet)
def revealBoard():
    global stage
    global flop1Pic
    global gameDeck
    global flop2Pic
    global flop3Pic
    global turnPic
    global riverPic
    global textWinner
    global boardList

    global potVal
    global bet
    if playerBet<bet and not pFold and playerChips!=0:
        messagebox.showwarning("Bet not matched","Bet not matched, either call, raise or fold with the player button")
        return
    for bot in botList:
        if bot.folded:
            continue
        else:
            bot.paint()
            bot.hideCard()
    if (stage <= 0):  #reset board and board

        stage += 1
    elif stage == 1:  #the flop
        card1 = gameDeck.drawOne()
        card2 = gameDeck.drawOne()
        card3 = gameDeck.drawOne()
        boardList.append(card1)
        boardList.append(card2)
        boardList.append(card3)
        flop1Pic = grabCardImage(card1)
        flop2Pic = grabCardImage(card2)
        flop3Pic = grabCardImage(card3)

        flopCard1 = Label(root, image=flop1Pic)
        flopCard1.place(x=500, y=350)
        flopCard2 = Label(root, image=flop2Pic)
        flopCard2.place(x=600, y=350)
        flopCard3 = Label(root, image=flop3Pic)
        flopCard3.place(x=700, y=350)
        updateBot(card1)
        updateBot(card2)
        updateBot(card3)
        stage += 1
    elif stage == 2:  # the turn
        card1 = gameDeck.drawOne()
        boardList.append(card1)
        turnPic = grabCardImage(card1)
        turnCard = Label(root, image=turnPic)
        turnCard.place(x=800, y=350)
        stage += 1
        updateBot(card1)
    elif stage == 3:  # the river
        card1 = gameDeck.drawOne()
        boardList.append(card1)
        riverPic = grabCardImage(card1)
        riverCard = Label(root, image=riverPic)
        riverCard.place(x=900, y=350)
        stage += 1
        updateBot(card1)
    elif stage == 4:
        revealCard()
        stage +=1
    elif stage==5:
        stage=0
        #potVal=0
        game()

    if stage>1 and stage<5:#stage is already added, post flop, turn and river included but before showdown
        for bot in botList:
            if bot.folded==True:
                continue
            else:
                decision=botDecision(bot,2500)
                #print(str(bot.botNumber)+" "+str(decision))
                if decision==False and bot.selfBet<bet: #fold, if true but bet is the same then check
                    bot.fold()
                    bot.paint()
                elif decision==True and bot.selfBet==bet:#raise
                    rDecision = raiseDecision (bot)
                    print(str(bot.botNumber)+" "+str(rDecision))
                    if rDecision:# the bot raises
                        potVal+=100
                        bot.chips-=100
                        bet+=100
                        bot.selfBet+=100
                        bot.botLabel = Label(root, text="Bot " + str(bot.botNumber)+" chips:"+str(bot.chips), font=("Helvetica", 16))
                        bot.botLabel.place(x=bot.xCoord + 50, y=bot.yCoord + 50, anchor=S)

                        for tempBot in botList:
                            if tempBot.folded==True:
                                continue
                            if tempBot == bot:
                                continue
                            elif tempBot.selfBet!=bet:
                                decision2=botDecision(tempBot,2500)
                                if decision2 == False:
                                    tempBot.fold()
                                    tempBot.paint()
                                else:#calls the raise
                                    potVal+=100
                                    tempBot.chips-=100
                                    tempBot.selfBet+=100


                elif decision==True and bot.selfBet<=bet: # call the raise
                    x=bet-bot.selfBet
                    potVal+=x
                    bot.chips-=x
                    bot.selfBet+=x
                    bot.botLabel = Label(root, text="Bot " + str(bot.botNumber)+" chips:"+str(bot.chips), font=("Helvetica", 16))
                    bot.botLabel.place(x=bot.xCoord + 50, y=bot.yCoord + 50, anchor=S)
                else:#check
                    pass
    potLabelUpdate()
    if stage !=5:
        for bot in botList:
            if bot.folded:
                continue
            else:
                bot.paint()
                bot.hideCard()

    overAllWinner()


def hideCard():

    for bot in botList:
        if bot.folded==False:
            bot.hideCard()

def raiseDecision(bot):
    botHole=[bot.card1,bot.card2]
    copy=bot.cardList.copy()
    copy.remove(bot.card1)
    copy.remove(bot.card2)
    numOfOpponents=0
    for bot in botList:
        if bot.folded==False:
            numOfOpponents+=1

    list=mcTrial(botHole,copy,numOfOpponents,1000)
    x=(list["W"]+list["T"]/2)/1000 #x is the est win rate for the bot's hand
    #print(x)
    #print(1/(numOfOpponents+1))
    if x< 1/(numOfOpponents+1)+0.1:
        return False#check/fold
    else:
        return True
def botDecision(bot,volume:int):
    botHole=[bot.card1,bot.card2]
    copy=bot.cardList.copy()
    copy.remove(bot.card1)
    copy.remove(bot.card2)
    numOfOpponents=0
    for bot in botList:
        if bot.folded==False:
            numOfOpponents+=1

    list=mcTrial(botHole,copy,numOfOpponents,volume)
    x=(list["W"]+list["T"]/2)/volume #x is the est win rate for the bot's hand
    #print(x)
    #print(1/(numOfOpponents+1))
    if x< 1/(numOfOpponents+1)-0.05:
        return False#check/fold
    else:
        return True #call/raise
def botInit():
    global bot1
    global bot2
    global bot3
    global bot4
    global bot5
    global bot6
    global bot7
    global bot8
    global botList

    tempCard = Card("A", "Spades")
    bot1 = Bot(1, tempCard, tempCard)
    bot2 = Bot(2, tempCard, tempCard)
    bot3 = Bot(3, tempCard, tempCard)
    bot4 = Bot(4, tempCard, tempCard)
    bot5 = Bot(5, tempCard, tempCard)
    bot6 = Bot(6, tempCard, tempCard)
    bot7 = Bot(7, tempCard, tempCard)
    bot8 = Bot(8, tempCard, tempCard)
    botList = [bot1, bot2, bot3, bot4, bot5, bot6, bot7, bot8]
def callCheck():
    global bet
    global botList
    global potVal
    for bot in botList:

        if bot.folded or bot.selfBet==bet:
            continue
        else:
            if bot.selfBet<bet:
                #print(bot.botNumber)
                #print(bot.selfBet)

                decision = botDecision(bot,5000)
                if decision:
                    x=bet-bot.selfBet
                    bot.selfBet+=x
                    bot.chips-=x
                    potVal+=x

                else:

                    bot.fold()
                    bot.paint()
                    bot.paint()

def updateBot(card):
    global botList
    global myHand
    for bot in botList:
        bot.updateList(card)
    myHand.append(card)
    #print(myHand)
    #print(bot1.cardList)
    #print(bot2.cardList)
    #print(winnerOfTwo(bot1.cardList,bot2.cardList))


def revealCard():
    global boardList
    global botList

    for bot in botList:
        if bot.folded==False:
            bot.revealCard()
    #print(boardList)
    #print(bot1.card1)
    #print(bot1.card2)


def winnerOfTwo(list1, list2) -> int:
    x = allHandCheck(list1)
    y = allHandCheck(list2)
    #print(interpretHand(x))
    #print(interpretHand(y))
    return handResult(x, y)


def overAllWinner():
    global myCard1
    global myCard2
    global botList
    global myHand
    global textWinner
    global numOfOpponents
    global frame
    global playerChips
    global potVal

    if pFold ==False:
        tempWinner = myHand
        winnerName = "Player "
        contenders = (botList).copy()

        numOfOpponents=8
        tie = False
        winningBot=None
        tieList = ["Player"]
        for bot in contenders:

            if bot.folded:
                numOfOpponents-=1
                continue
            x = handResult(allHandCheck(tempWinner), allHandCheck(bot.cardList))

            if x == 2:

                tempWinner = bot.cardList
                winningBot=bot

                winnerName = bot.__repr__()#"Bot " + str(bot.botNumber)
                tieList = [bot]
                tie = False
            elif x == 0:
                tieList.append(bot)#"Bot " + str(bot.botNumber)]

                #print(type(tieList[0]))

                tie = True
        if tie:

            try:
                textWinner.destroy()
            except:
                pass
            size =len(tieList)
            prize = int(potVal/size)
            for bot in tieList:

                if bot=="Player":
                    playerChips+=prize
                else:
                    bot.chips+=prize
            if stage==5:
                textWinner = Label(frame,
                                   text=" Game tied with " + str(tieList) + " " + interpretHand(allHandCheck(tempWinner)))
                textWinner.grid(row=1, column=1, columnspan=2)

        else:

            #print(tempWinner)
            #print(winnerName + " wins")
            try:
                textWinner.destroy()
            except:
                pass
            if stage==4:
                if winningBot==None:
                    playerChips+=potVal
                else:
                    winningBot.chips+=potVal

            if stage==5:
                textWinner = Label(frame, text=winnerName + " wins with " + interpretHand(allHandCheck(tempWinner)))
                textWinner.grid(row=1, column=1, columnspan=3)
    else:

        contenders = (botList).copy()
        winnerName = contenders[0].__repr__()
        numOfOpponents=7
        tie = False
        winningBot=contenders[0]
        tempWinner=winningBot.cardList
        tieList = [contenders[0]]
        contenders.remove(contenders[0])
        for bot in contenders:

            if bot.folded:
                numOfOpponents-=1
                continue
            x = handResult(allHandCheck(tempWinner), allHandCheck(bot.cardList))

            if x == 2:

                tempWinner = bot.cardList
                winningBot=bot

                winnerName = bot.__repr__()#"Bot " + str(bot.botNumber)
                tieList = [bot]
                tie = False
            elif x == 0:
                tieList.append(bot)#"Bot " + str(bot.botNumber)]

                #print(type(tieList[0]))

                tie = True
        if tie:

            try:
                textWinner.destroy()
            except:
                pass
            size =len(tieList)
            prize = int(potVal/size)
            for bot in tieList:
                bot.chips+=prize
            if stage==5:
                textWinner = Label(frame,
                                   text=" Game tied with " + str(tieList) + " " + interpretHand(allHandCheck(tempWinner)))
                textWinner.grid(row=1, column=1, columnspan=2)

        else:

            #print(tempWinner)
            #print(winnerName + " wins")
            try:
                textWinner.destroy()
            except:
                pass
            if stage==4:
                winningBot.chips+=potVal

            if stage==5:
                textWinner = Label(frame, text=winnerName + " wins with " + interpretHand(allHandCheck(tempWinner)))
                textWinner.grid(row=1, column=1, columnspan=3)


def run():
    global frame
    global blindStage
    global playerChips
    playerChips=4000
    blindStage=0
    frame = LabelFrame(root, padx=80, pady=130)  #padx=100, pady=150)
    frame.configure(bg="#ECDEC9")
    frame.place(x=1550, y=970, anchor=SE)
    #blindUpdate(blindStage)
    num = 2
    playerButton = Button(frame, text="player", padx=10, pady=10, command=playerAction)  #destroyCard)
    showButton = Button(frame, text="reveal", padx=10, pady=10, command=revealCard)
    hideButton = Button(frame, text="hide", padx=10, pady=10, command=hideCard)
    progressButton = Button(frame, text="board", padx=10, pady=10, command=revealBoard)
    #tempButton2 = Button(frame, text="rerun", padx=0, pady=0, command=game)

    showButton.grid(row=0, column=0, padx=10)
    hideButton.grid(row=0, column=1, padx=10)
    playerButton.grid(row=0, column=2, padx=10)
    progressButton.grid(row=0, column=3)
    #tempButton2.grid(row=1, column=0)
    quitButton = Button(root, text="Quit Game", command=makeSureQuit)
    quitButton.place(relx=1, x=-1, y=1, anchor=NE)

    botInit()
    game()


    root.mainloop()


def sim(h: list,s:list ,Opps: int) -> str:  #list is the hand that is being trialed
    trialDeck = Deck()
    hand=h.copy()
    shared=s.copy()
    for card in hand:
        trialDeck.remove(card)
    #print(trialDeck)
    trialDeck.shuffle()

    #shared = trialDeck.draw(5)
    num = 5-len(shared)
    for i in range(num):
        shared.append(trialDeck.drawOne())
    for card in shared:
        hand.append(card)
    #print(hand)

    state = "W"
    for i in range(Opps):

        #print(trialDeck)
        tempHand = trialDeck.draw(2)
        for card in shared:
            tempHand.append(card)
        x = winnerOfTwo(hand, tempHand)
        if x == 2:
            state = "L"
            break
        elif x == 0:
            state = "T"
    #del hand
    return state


def mcTrial(hand: list,shared:list, Opps: int,volume:int):
    WL = {"W": 0, "L": 0, "T": 0}  #win loss tie
    for j in range(volume):

        #print("----------")
        WL[sim(hand, shared,Opps)] += 1

    return WL

def blindUpdate(smallBlindPos:int):
    global blindLabel
    global blindImage
    global blindImage2
    global blindStage
    global blindLabel
    global blindLabel2
    try:
        blindLabel.destroy()
        blindLabel2.destroy()
    except:
        pass
    coordList=[[950,750],[550,750],[310,780],[310,480],[310,180],[550,300],[950,300],[1200,180],[1200,480]]
    bigCoord=None
    smallCoord=None
    if smallBlindPos==8:
        bigCoord=coordList[0]
        smallCoord=coordList[8]
        blindStage=0
    else:
        smallCoord=coordList[smallBlindPos]
        bigCoord=coordList[smallBlindPos+1]
        blindStage+=1
    smallURL = "images/smallBlind.png"
    image = Image.open(smallURL)
    image = image.resize((80, 80))
    blindImage = ImageTk.PhotoImage(image)
    blindLabel = (Label(root, image=blindImage))

    blindLabel.place(x=smallCoord[0],y=smallCoord[1],anchor=S)
    bigURL = "images/bigBlind.png"
    image2 = Image.open(bigURL)
    image2 = image2.resize((80, 80))
    blindImage2 = ImageTk.PhotoImage(image2)
    blindLabel2 = (Label(root, image=blindImage2))
    blindLabel2.place(x=bigCoord[0],y=bigCoord[1],anchor=S)
    #blindLabel.place(x=950, y=750, anchor=S) #player
    #blindLabel.place(x=550, y=750, anchor=S) #bot 1
    #blindLabel.place(x=310, y=780, anchor=S) #bot 2
    #blindLabel.place(x=310, y=480, anchor=S) #bot 3
    #blindLabel.place(x=310, y=180, anchor=S) #bot 4
    #blindLabel.place(x=550, y=300, anchor=S) #bot 5
    #blindLabel.place(x=950, y=300, anchor=S) #bot 6
    #blindLabel.place(x=1200, y=180, anchor=S)#bot 7
    #blindLabel.place(x=1200, y=480, anchor=S) #bot 8
#test deck, dont delete
"""
deck1 =[Card('T', "Hearts"),
        Card("8", "Clubs"),
        Card('9', "Clubs"),
        Card('T', "Spades"),
        Card("J", "Clubs"),
        Card('Q', "Clubs"),
        Card('2', "Hearts")]
"""
#s=[]#[Card("9", "Diamonds"), Card("Q", "Hearts"), Card("J", "Clubs")]
#print(mcTrial([Card("K", "Clubs"), Card("A", "Hearts")], s,5))
"""
numOfOpponents=1
testBot=Bot(1,Card("K", "Diamonds"), Card("A", "Hearts"))
testBot.cardList.append(Card('T', "Spades"))
testBot.cardList.append(Card('5', "Spades"))
testBot.cardList.append(Card('7', "Hearts"))
print(botDecision(testBot))
"""
run()