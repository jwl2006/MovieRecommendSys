
 # Created by wanghao on 3/29/16.
 
 #!/usr/bin/python
import csv
import sys

def getRowItem(row, file):
    for i in range(1,len(row)):
    	if(row[i].isdigit()):
    	    ret = row[0] + ',' + str(i) + ',' + row[i] + '\n'
            file.write(ret)
          
f = open(sys.argv[1])
csv_f = csv.reader(f)
csv_list = list(csv_f)

file = open(sys.argv[2],"wb")
for w in csv_list[1:]:
    getRowItem(w,file)
file.close()

    