# !/usr/local/bin/python3
import os


def raj2jar(root):
    """
    Takes a string root as parameter, which is the absolute path of a directory in the local file system. 

    This function rename all files with extension .raj in the subtree of directories rooted at root
    by changing the extension to .jar.
    """
    if not os.path.isdir(root):
        print("Root is not a valid directory")
        return

    for root, dirs, files in os.walk(root):
        for name in files:
            if name.endswith(".raj"):
                print("Renaming " + os.path.join(root, name) + " to jar... ", end='')
                try:
                    filename_without_extension = name[0:-4]
                    os.rename(os.path.join(root, name), os.path.join(root, filename_without_extension + '.jar'))
                    print("done.")
                except Exception as e:
                    print("error. ", end="")
                    print(e)
                    print("\n")