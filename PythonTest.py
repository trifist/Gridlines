import os
import pdb
import socket
import time

def changeName():
    path = "C:\\Users\\admin\\Desktop\\ecarx\\17B演示版本\\17B演示版本\\讯飞版语音助手1031_1080P\\assets\\1080P"
    for root, dirs, fs in os.walk(path):
        for name in fs:
            if("@1080P" in name):
                newName = name[:-10]
                os.rename(path + "\\" + name, path + "\\" + newName + ".png")
            else:
                print(name)
    return 0

def removeDup():
    oldList = []
    cmdSet = set()
    f = open("./Other.bnf", "r", encoding="utf8")
    line = f.readline()
    while(line):
        cmds = line.split("|")
        for cmd in cmds:
            cmdSet.add(cmd)
        line = f.readline()
    f.close()

    cmdSet.remove("\n")

    f2 = open("./result.txt", "w", encoding="utf8")
    count = 0
    for cmd in cmdSet:
        count=count+1
        f2.write(cmd)
        f2.write("|")
        if(count%10==0):
            f2.write("\n\n")
    f2.close()

    return 0

def adb():
    pdb.set_trace()
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(("127.0.0.1", 5432))
    s.sendall("get_time")
    print(s.recv(1024))

def main():
    adb()
    input("Succeess! Press Enter to exit")
    return 0


if(__name__ == "__main__"):
    main()
