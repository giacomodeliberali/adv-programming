# !/usr/local/bin/python3
import os
import re
from functools import reduce


def dircontains(root, filename):
    for file in os.listdir(root):
        if file == filename:
            return True
    return False


def rebuild_packages(root):
    """
    Takes as parameter two strings: root, which is an absolute path.

    For each Java file in the subtree rooted at root, this function ensure that if the file
    includes a package statement, then it is in a directory with the same name of the package
    """

    if not os.path.isdir(root):
        print("Root is not a valid directory")
        return

    for root, dirs, files in os.walk(root):
        for name in files:
            # take all java files
            if name.endswith(".java"):

                # take the absolute path
                filename = os.path.join(root, name)

                # open the file 
                file = open(filename, 'r')

                print("Cheking " + filename + "...")

                # find the package directive with a regex
                # https://regex101.com/r/lT0oAE/2
                packages = re.findall(r"package\s[a-z.A-Z]*;", file.read())

                # close the java file
                file.close()

                # if it has some package directive, analyse it
                if packages:
                    # take package name. Eg from "package uno.due.tre;" take "uno.due.tre"
                    package_name = packages[0][8:-1]

                    # split on the dot. Eg "uno.due.tre" = ["uno","due","tre"]
                    folders = package_name.split(".")

                    # lambda to use inside the reduce. Create nested folder from the split of the package
                    # prev_folder is the accumulator of the parent folder, current_folder is the current one that
                    # have to be created inside the prev_folder
                    def mkdir(prev_folders, current_folder):
                        if not os.path.exists(os.path.join(root, prev_folders)):
                            os.mkdir(os.path.join(root, prev_folders))

                        folder = os.path.join(prev_folders, current_folder)
                        if not os.path.exists(folder):
                            os.mkdir(os.path.join(root, folder))

                        return folder

                    # Check is some package directory is missing and eventually create it
                    target = os.path.join(root, reduce(mkdir, folders))                    

                    if not dircontains(target, name):
                        # if the file is not in the correct folder, move it
                        print("\t Moving to right package folder")
                        target_file = os.path.join(target, name)
                        os.rename(filename, target_file)
                    else:
                        # else do nothing
                        print("\t Already ok")

                else:
                    # missing package directive, do nothing
                    print("\t No package info")


rebuild_packages("/Users/giacomodeliberali/code/unipi/adv-programming/assign_3")
