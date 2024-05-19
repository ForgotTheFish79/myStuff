from tkinter import messagebox

import pandas as pd
import numpy as np
from tkinter import *
from PIL import ImageTk, Image
import random
global df
draft_file_path = "Draft2023.xlsx"
df = pd.read_excel(draft_file_path, engine='openpyxl')
df.drop(["PositionRank", "Rank"], axis=1, inplace=True)


class Team:
    def __init__(self):
        # Instance Variables
        self.team = {"QB": [], "RB": [], "WR": [], "TE": [], "FLEX": [], "DST": [], "K": [], "BN": []}
        self.teamSize=0
        self.full=False

    def __repr__(self):
        return "Team Roster:" + str(self.team)
    def checkFull(self)->bool:
        if self.teamSize==16:
            self.full=True
        elif self.teamSize<16:
            self.full=False
        else:
            raise OverflowError
        return self.full
    def add(self, name, pos) -> bool:
        var=False
        if pos == "QB" or pos == "K" or pos == "DST":
            if (len(self.team[pos]) < 1):
                self.team[pos].append(name)
                self.teamSize=self.teamSize+1
                var= True
            elif (len(self.team["BN"]) < 7):
                self.team["BN"].append(name)
                self.teamSize=self.teamSize+1
                var= True
            else:
                print("REJECTED")
                var= False
        elif (pos == "RB" or pos == "WR"):
            if (len(self.team[pos]) < 2):
                self.team[pos].append(name)
                self.teamSize=self.teamSize+1
                var= True
            elif (len(self.team["FLEX"]) < 1):
                self.team["FLEX"].append(name)
                self.teamSize=self.teamSize+1
                var= True
            elif (len(self.team["BN"]) < 7):
                self.team["BN"].append(name)
                self.teamSize=self.teamSize+1
                var= True
            else:
                print("REJECTED")
                var= False
        elif (pos == "TE"):
            if (len(self.team[pos]) < 1):
                self.team[pos].append(name)
                self.teamSize=self.teamSize+1
                var= True
            elif (len(self.team["FLEX"]) < 1):
                self.team["FLEX"].append(name)
                self.teamSize=self.teamSize+1
                var= True
            elif (len(self.team["BN"]) < 7):
                self.team["BN"].append(name)
                self.teamSize=self.teamSize+1
                var= True
            else:
                print("REJECTED")
                var= False
        else:
            print("UNKNOWN POSITION")
            var= False
        self.checkFull()
        return var
global myTeam
myTeam = Team()

root = Tk()
root.title("Fantasy Football Offline Draft")
root.iconbitmap('football.ico')
root.geometry("1200x900")
global frame
global draftedFrame
def showTeam():
    top = Toplevel()
    top.iconbitmap('football.ico')

    for position in myTeam.team:
        lbl = Label(top, text=position, font=("Helvetica", 15)).pack()

        lbl = Label(top, text=myTeam.team[position]).pack(side="top")

    Button(top, text="Close", command=top.destroy).pack()


def labelDF():
    global frame
    global df

    Label(frame, text="Index",background="#90EE90").grid(row=0, column=0, sticky="w")
    Label(frame, text="Name",background="#90EE90").grid(row=0, column=1, sticky="w")
    Label(frame, text="Team",background="#90EE90").grid(row=0, column=2, sticky="w")
    Label(frame, text="Age",background="#90EE90").grid(row=0, column=3, sticky="w")
    Label(frame, text="Position",background="#90EE90").grid(row=0, column=4, sticky="w")
    Label(frame, text="ADP",background="#90EE90").grid(row=0, column=5, sticky="w")
    for i in range(35):
        player = df.iloc[i]
        #string=str(player.Name)+":   "+str(player.Team)+", Age:  "+str(int(player.Age))+", Pos:  "+str(player.Position)+", ADP:  "+str(player.ADP)
        #Label(root,text=string).grid(row=i,column=0,sticky="w")
        Label(frame, text=str(i),background="#90EE90").grid(row=i + 1, column=0, sticky="w")
        Label(frame, text=str(player.Name),background="#90EE90").grid(row=i + 1, column=1, sticky="w")
        Label(frame, text=str(player.Team),background="#90EE90").grid(row=i + 1, column=2, sticky="w")
        try:
            Label(frame, text=str(int(player.Age)),background="#90EE90").grid(row=i + 1, column=3, sticky="w")
        except ValueError:
            Label(frame, text="N/A",background="#90EE90").grid(row=i + 1, column=3, sticky="w")
        Label(frame, text=str(player.Position),background="#90EE90").grid(row=i + 1, column=4, sticky="w")
        Label(frame, text=str(player.ADP),background="#90EE90").grid(row=i + 1, column=5, sticky="w")

#dfLabel = Label(root,text=df[:40]).grid(row=1,column=1,columnspan=2,sticky="w")
def otherDraft(df: pd.DataFrame):
    global draftedFrame
    #remove semi-random players based on ranking
    randNum = random.randint(0, 100)
    #print(randNum)
    remove = -1
    if (randNum < 25):
        remove = 0
    elif (randNum < 45):
        remove = 1
    elif (randNum < 60):
        remove = 2
    elif (randNum < 68):
        remove = 3
    elif (randNum < 76):
        remove = 4
    elif (randNum < 81):
        remove = 5
    elif (randNum < 85):
        remove = 6
    elif (randNum < 89):
        remove = 7
    elif (randNum < 93):
        remove = 8
    elif (randNum < 95):
        remove = 9
    elif (randNum < 96):
        remove = 10
    elif (randNum < 99):
        remove = 11
    else:
        remove = 12
    if (remove != -1):
        #print(df.iloc[remove].Name)
        player=df.iloc[remove]

        draftedFrame.grid(row=0, column=2, padx=10, pady=10, sticky="N")
        Label(draftedFrame,text=player.Name).pack()
        df.drop(remove, inplace=True)
        df.reset_index(level=None, drop=True, inplace=True)


def pick():
    global df
    global draftedFrame
    global frame
    try:
        i = int(entryBox.get())
        size = df.shape[0]
        bar=39
        if(size<39):
            bar=size

        if (i > bar):
            raise IndexError('Index out of bound')
        else:
            entryBox.delete(0, END)
            player = df.iloc[i]
            if(myTeam.add(player.Name, player.Position)):
                df.drop(i,inplace=True)
                df.reset_index(level=None, drop=True, inplace=True)
                var= myTeam.checkFull()
                #print(var)
                frame.destroy()
                frame=LabelFrame(root, text="Players",background="#90EE90")
                frame.grid(row=0, column=0, padx=10, pady=10,rowspan=2)
                #print("----------------------")
                #print(round+str(myTeam.teamSize)+str(myTeam.full))
                #df.reset_index(level=None,drop=True,inplace=True)
                draftedFrame.destroy()
                draftedFrame = LabelFrame(root, text="Drafted Players before your pick", pady=50)
                draftedFrame.grid(row=0, column=2, padx=10, pady=10, sticky="N")

                if myTeam.teamSize%2==0:
                    draftBefore()
                    draftBefore()
                else:
                    draftAfter()
                    draftAfter()
                labelDF()
                if(var==True):

                    pickButton["state"]='disabled'
            else:
                messagebox.showerror("Error", "No spot for players of this position")



    except ValueError:
        entryBox.delete(0, END)
        messagebox.showerror("Error", "Input a number, not letters, or symbols")
        #Label(root, text=response).grid(row=1, column=1)
    except IndexError:
        entryBox.delete(0, END)
        messagebox.showerror("Error", "Index out of bound for the current draft list")
        #Label(root, text=response).grid(row=1, column=1)
def getDraftPosition():
    global randNum
    randNum=random.randint(0, 11)
    #randNum=11
    messagebox.showinfo("Notification", "Your draft position is : "+str(randNum+1)+" out of 12")
    #Label(root, text=response).grid(row=1, column=1)

def draftBefore():
    before =randNum
    for i in range(before):
        otherDraft(df)
def draftAfter():
    after=11-randNum
    for i in range(after):
        otherDraft(df)



getDraftPosition()

def showPos():
    messagebox.showinfo("Notification", "Your draft position is : "+str(randNum+1)+" out of 12")
    #response.grid(row=1,column=1)
    #Label(root, text=response).grid(row=1, column=1)


buttonFrame = LabelFrame(root, text="Actions", pady=50)
buttonFrame.grid(row=0, column=1, padx=10, pady=10, sticky="N")
draftedFrame = LabelFrame(root, text="Drafted Players before your pick", pady=50)
draftedFrame.grid(row=0, column=2, padx=10, pady=10, sticky="N")

draftBefore()
frame = LabelFrame(root, text="Players",background="#90EE90")
frame.grid(row=0, column=0, padx=10, pady=10,rowspan=2)
labelDF()
#entrybox label
Label(buttonFrame, text="Enter the Index Number of desired player below").grid(row=0, column=0, columnspan=2)
entryBox = Entry(buttonFrame, width=35, borderwidth=4)
pickButton = Button(buttonFrame, text="Draft", command=  pick)
teamButton = Button(buttonFrame, text=" Show Team", command=showTeam, padx=10, pady=10)
closeButton = Button(buttonFrame, text=" Exit", command=root.quit, padx=31, pady=10)
showDraftPosition = Button(buttonFrame, text=" Show Draft Position Again", command=showPos, padx=10, pady=10)
entryBox.grid(row=1, column=0, columnspan=2, padx=10, pady=10)
showDraftPosition.grid(row=3,column=0,padx=40)
teamButton.grid(row=2, column=0,pady=40)
closeButton.grid(row=2, column=1)
pickButton.grid(row=1, column=2, padx=10)
root.mainloop()
