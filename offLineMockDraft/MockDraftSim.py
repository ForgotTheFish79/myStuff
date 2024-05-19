import pandas as pd
import random
pd.set_option('display.max_columns', None)
pd.set_option('display.max_rows',None)
draft_file_path = "Draft2023.xlsx"

df=pd.read_excel(draft_file_path, engine='openpyxl')
df.drop(["PositionRank","Rank"], axis=1, inplace=True)
class Team:
    def __init__(self):
        # Instance Variables
        self.team={"QB":[],"RB":[],"WR":[],"TE":[],"FLEX":[],"DST":[],"K":[],"BN":[]}
    def __repr__(self):
        return "Team Roster:"+str(self.team)

    def add(self,name,pos)->bool:
        if pos=="QB"  or pos=="K" or pos=="DST":
            if(len(self.team[pos])<1):
                self.team[pos].append(name)
                return True
            elif(len(self.team["BN"])<7):
                self.team["BN"].append(name)
                return True
            else:
                print("REJECTED")
                return False
        elif(pos=="RB" or pos =="WR"):
            if(len(self.team[pos])<2):
                self.team[pos].append(name)
                return True
            elif(len(self.team["FLEX"])<1):
                self.team["FLEX"].append(name)
                return True
            elif(len(self.team["BN"])<7):
                self.team["BN"].append(name)
                return True
            else:
                print("REJECTED")
                return False
        elif(pos=="TE"):
            if(len(self.team[pos])<1):
                self.team[pos].append(name)
                return True
            elif(len(self.team["FLEX"])<1):
                self.team["FLEX"].append(name)
                return True
            elif(len(self.team["BN"])<7):
                self.team["BN"].append(name)
                return True
            else:
                print("REJECTED")
                return False
        else:
            print("UNKNOWN POSITION")
            return False
class rbHeavyBot:
    def  __init__(self,n,possible:pd.DataFrame):
        self.roster = Team()
        self.name = n
        self.RBdone = False
        self.availablePlayers = possible.copy()
        self.globolPool = possible
    def printTeam(self):
        print(self.roster)
    def modifyPool(self):
        if(len(self.roster.team["BN"])>=7):# no more bench space:
            if(len(self.roster.team["K"]>=1)):
                self.availablePlayers.drop(self.availablePlayers[self.availablePlayers["Position"] == "K"].index ,inplace= True)
            elif(len(self.roster.team["DST"]>=1)):
                self.availablePlayers.drop(self.availablePlayers[self.availablePlayers["Position"] == "DST"].index ,inplace= True)
            elif(len(self.roster.team["QB"]>=1)):
                self.availablePlayers.drop(self.availablePlayers[self.availablePlayers["Position"] == "QB"].index ,inplace= True)
            elif(len(self.roster.team["FLEX"])>=1):
                if(len(self.roster.team["TE"]>=1)):
                    self.availablePlayers.drop(self.availablePlayers[self.availablePlayers["Position"] == "TE"].index ,inplace= True)
                elif(len(self.roster.team["WR"]>=2)):
                    self.availablePlayers.drop(self.availablePlayers[self.availablePlayers["Position"] == "WR"].index ,inplace= True)
                elif(len(self.roster.team["RB"]>=1)):
                    self.availablePlayers.drop(self.availablePlayers[self.availablePlayers["Position"] == "RB"].index ,inplace= True)
    def checkCondition(self):
        if(len(self.roster.team["RB"])>=2):
            self.RBdone=True
    def pick(self,):
        #print(possible)
        self.checkCondition()
        if(self.RBdone):# don't need RB
          print()

        else:#need RB.
            print("need to draft RB")




    def __repr__(self):
        return str(self.name)+" is a RB Heavy Drafter, his roster is currently: "+str(self.roster)



myTeam=Team()

print(myTeam.team)


def otherDraft(df:pd.DataFrame):
    #remove semi-random players based on ranking
    randNum=random.randint(0, 100)
    #print(randNum)
    remove = -1
    if(randNum<25):
        remove=0
    elif(randNum<40):
        remove=1
    elif(randNum<55):
        remove=2
    elif(randNum<63):
        remove =3
    elif(randNum<70):
        remove=4
    elif(randNum<76):
        remove =5
    elif(randNum<81):
        remove=6
    elif(randNum<85):
        remove=7
    elif(randNum<89):
        remove=8
    elif(randNum<93):
        remove=9
    elif(randNum<96):
        remove=10
    elif(randNum<98):
        remove=11
    else:
        remove=12
    if(remove !=-1):
        df.drop(remove,inplace=True)
        df.reset_index(level=None, drop=True, inplace=True)

def draft(x:Team):
    randNum=random.randint(0, 11)
    print("Your draft position is : ",randNum)
    verify=False
    for i in range(16):
        for i in range(randNum):
            otherDraft(df)
        print(df.head(20))
        while(not verify):
            print(x.team)
            num=input("Pick a player by inputting the number before their name")
            num=int(num)
            player = df.iloc[num]
            verify=x.add( player.Name,player.Position)
            df.drop(num,inplace=True)
            df.reset_index(level=None, drop=True, inplace=True)
        for i in range(11-randNum):
            otherDraft(df)

        df.reset_index(level=None, drop=True, inplace=True)
        print(x.team)
        verify=False
    print(x.team)
draft(myTeam)