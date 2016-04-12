from gurobi_model import *
from gurobipy import *
import sys
import os
import operator

def check(cwd, days, tolerance, weekList):
    splittedLines = {}
    i = 0

    # Read result
    with open('{}/{}/result-{}days.sol'.format(cwd, 'outputs', days)) as f:
        lines = f.readlines()

    for line in lines:
        tempSplit = line.split(' ')

        varName = tempSplit[0]
        value = tempSplit[1].rstrip()

        if varName.startswith('Week'):
            splittedLines[i] = (varName, value)
            i = i+1

    # init result array
    weeklyResult = {}
    for week in weekList:
        weeklyResult[week] = {}
        weeklyResult[week]['BE1'] = 0
        weeklyResult[week]['BE2'] = 0
        weeklyResult[week]['CSE'] = 0
        weeklyResult[week]['REG'] = 0

    # training count array
    trainingCount = {}
    usedTrainingsCount = 0
    for t in TRAINING_WEIGHTS:
        trainingCount[t] = 0

    print "======== Schedule ========"

    # calc
    for week in weekList:
        print
        print "== {}:".format(week)
        for i in splittedLines:
            if splittedLines[i][0].startswith(week+'.') and splittedLines[i][0].endswith('.scale'):
                # Get Day
                d = splittedLines[i][0].split('.')[1]
                # Get Training name
                t = splittedLines[i][0].split('.')[2]

                # Get y value
                value = float(splittedLines[i][1])

                if value != 0:
                    # Calculate training scale
                    weeklyResult[week]['BE1'] += value * TRAINING_WEIGHTS[t][0]
                    weeklyResult[week]['BE2'] += value * TRAINING_WEIGHTS[t][1]
                    weeklyResult[week]['CSE'] += value * TRAINING_WEIGHTS[t][2]
                    weeklyResult[week]['REG'] += value * TRAINING_WEIGHTS[t][3]

                    # Count trainings
                    trainingCount[t] += 1
                    usedTrainingsCount += 1

                    print "{}: {} -> {}".format(d, t, value)


    print
    print "======== Distribution Results ========"
    print

    # Sort
    sorted_trainingCount = sorted(trainingCount.items(), key=operator.itemgetter(1), reverse = True)

    for t, v in sorted_trainingCount:
        print "{}: {} times in schedule".format(t, v)

    print
    print "{} trainings have been distributed (of {} needed)".format(usedTrainingsCount, days * 12)

    print
    print "======== Final Results ========"
    print
    print "Format: Basic 1 | Basic 2 | Competition Specific | Regeneration"
    print

    # Now percentage
    for week in weekList:
        # Print result
        weekAim = WEEKLY_AIMS.select(week, '*', '*', '*', '*', '*')
        aimBE1 = float(weekAim[0][1])
        aimBE2 = float(weekAim[0][2])
        aimCSE = float(weekAim[0][3])
        aimREG = float(weekAim[0][4])

        print "{}: {} | {} | {} | {}".format(week, weeklyResult[week]['BE1'], weeklyResult[week]['BE2'], weeklyResult[week]['CSE'], weeklyResult[week]['REG'])
        print "-> Aim: {} | {} | {} | {}".format(aimBE1, aimBE2, aimCSE, aimREG)
        print "-> Tolerance +/-: {}".format(tolerance)
        print



if __name__ == "__main__":
    weekList = getListOfWeeks(12)

    if len(sys.argv) <= 1:
        # Default test: Days: 4, Weeks: 12, Tolerance: 3
        cwd = os.getcwd().split('bin')[0]
        check(cwd, 4, 3, weekList)
    else:
        cwd = sys.argv[1]
        days = sys.argv[2]
        tol = sys.argv[3]

        check(cwd, days, tol, weekList)
