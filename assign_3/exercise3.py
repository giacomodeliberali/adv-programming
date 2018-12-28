#!/usr/bin/env python3

import os
import re
from functools import reduce
import logging
logging.basicConfig(level=logging.INFO)

def get_package_as_list(file_path):
    # open the file in read mode
    file = open(file_path, 'r')

    # find the package directive with a regex
    # https://regex101.com/r/lT0oAE/2
    packages = re.findall(r"package\s[a-z.A-Z.0-9]*;", file.read())

    # close the java file
    file.close()

    # if it has some package directive, analyse it
    if packages:
        # take package name. Eg from "package uno.due.tre;" take "uno.due.tre"
        # assume the first taken by regex is the right one
        package_name = packages[0][8:-1]

        # split on the dot. Eg "uno.due.tre" = ["uno","due","tre"]
        return package_name.split(".") 
    else:
        return []


def rebuild_packages(root):
    """
    Takes as parameter a string, root, which is an absolute path.

    For each Java file in the subtree rooted at root, this function ensure that if the file
    includes a package statement, then it is in a directory with the same name of the package
    """

    if not os.path.isdir(root):
        logging.info(f'"{root}" is not a valid directory')
        return

    logging.info(f'Analysing "{root}" for java files...\n')

    # the total number of java files analysed  
    file_count = 0;        

    for root, dirs, files in os.walk(root):
        for filename in files:
            # take all java files
            if filename.endswith(".java"):

                # increment file count
                file_count += 1

                # take the absolute path
                file_path = os.path.join(root, filename)

                logging.info(f'\t - cheking {filename}:')

                # the package splitted on dots. Eg "package uno.due.tre;" = ["uno","due","tre"]
                package = get_package_as_list(file_path)

                # if it has some package directive, analyse it
                if len(package) > 0:

                    package_name = '.'.join(package)
                    logging.info(f'\t\t - package name: {package_name}')

                    # if filename ends with /uno/due/tre/filename then continue
                    package_subfolders = os.path.join(*package, filename)

                    if file_path.endswith(package_subfolders):
                        logging.info(f'\t\t => already in correct folder\n')
                        continue
                    else:
                        logging.info(f'\t\t\t - must be moved in correct folder')

                    # lambda to use inside the reduce. Create nested folder from the split of the package
                    # prev_folder is the accumulator of the parent folder, current_folder is the current one that
                    # have to be created inside the prev_folder
                    def make_dir(prev_folders, current_folder):

                        # check if the prev parent folder exists
                        folder = os.path.join(root, prev_folders)
                        if not os.path.exists(folder):
                            # and eventually create it
                            logging.info(f'\t\t\t - creating missing folder {folder}')
                            os.mkdir(folder)

                        # check that the current joined with the prev exists
                        folder = os.path.join(prev_folders, current_folder) # relative folder
                        if not os.path.exists(os.path.join(root, folder)): # absolute folder
                            # if not, create the current nested folder
                            logging.info(f'\t\t\t - creating missing folder {os.path.join(root, folder)}')
                            os.mkdir(os.path.join(root, folder))

                        # return the prev + current relative folder for the next iteration
                        return folder

                    # Check is some package directory is missing and eventually create it
                    current_package_folder = reduce(make_dir, package, "")

                    # the target directory where to move the current file
                    target = os.path.join(root, current_package_folder)  

                    logging.info(f'\t\t\t - moving to right folder')

                    # move the file renaming it
                    target_file = os.path.join(target, filename)
                    os.rename(file_path, target_file)

                    logging.info(f'\t\t => moved in: {target}')

                else:
                    # missing package directive, do nothing
                    logging.info(f'\t\t => no package info found\n')

    if file_count == 0:
        logging.info(f'No java source files were found in folder')
    else:
        logging.info(f'Analysed {file_count} java files.\n')