#!/usr/bin/env python3
# -*- coding: UTF-8 -*-

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

    # the list of files that have to be checked
    files_to_check = []

    for root, dirs, files in os.walk(root):
        for filename in files:
            # take all java files
            if filename.endswith(".java"):

                # create a dict for the current file with all the info needed to process it
                current_file = {}

                # increment file count
                file_count += 1

                # take the absolute path
                file_path = os.path.join(root, filename)

                current_file["name"] = filename
                current_file["path"] = file_path

                # the package splitted on dots. Eg "package uno.due.tre;" = ["uno","due","tre"]
                package = get_package_as_list(file_path)

                # if it has some package directive, analyse it
                if len(package) > 0:

                    package_name = '.'.join(package)

                    current_file["package"] = package_name

                    # if filename ends with /uno/due/tre/filename then continue
                    package_subfolders = os.path.join(*package, filename)

                    if file_path.endswith(package_subfolders):
                        current_file["is_in_right_folder"] = True
                    else:
                        current_file["is_in_right_folder"] = False

                        # store all the folders that need to be created before moving this file
                        current_file["folders"] = []

                        # lambda to use inside the reduce. Create nested folder from the split of the package
                        # prev_folder is the accumulator of the parent folder, current_folder is the current one that
                        # have to be created inside the prev_folder
                        def make_dir(prev_folders, current_folder):

                            # check if the prev parent folder exists
                            folder = os.path.join(root, prev_folders)
                            if not os.path.exists(folder):
                                # and eventually create it
                                current_file["folders"].append(folder)

                            # check that the current joined with the prev exists
                            folder = os.path.join(prev_folders, current_folder) # relative folder
                            if not os.path.exists(os.path.join(root, folder)): # absolute folder
                                # if not, create the current nested folder
                                current_file["folders"].append(os.path.join(root, folder))

                            # return the prev + current relative folder for the next iteration
                            return folder

                        # Check is some package directory is missing and eventually create it
                        current_package_folder = reduce(make_dir, package, "")

                        # the target directory where to move the current file
                        target = os.path.join(root, current_package_folder)  

                        # create the target dir for the current file
                        target_file = os.path.join(target, filename)
                        current_file["target_dir"] = target_file
                else:
                    # missing package directive, do nothing
                    current_file["package"] = None
        
                # Sometimes happens that the file is picked up twice,
                # for example when a file is moved while this script is running
                # To avoid error, ensure that each file is checked only once
                found = False
                for file in files_to_check:
                    if file["path"] == current_file["path"]:
                        found = True
                if not found:
                    files_to_check.append(current_file)

    # Now we are outside the iterator, start manipulating the filesystem

    # create folder outside of os.walk(), otherwise
    # they will be looped again in the iterator
    for file in files_to_check:
        logging.info(f'\t - cheking {file["name"]}:')
        logging.info(f'\t\t - location: {file["path"]}')

        if file["package"]:
            # a package exsist
            logging.info(f'\t\t - package name: {file["package"]}')

            if file["is_in_right_folder"]:
                # do nothing  
                _green_log("\t\t ✓ already in correct folder\n")
            else:

                # create missing folder and move
                logging.info(f'\t\t\t - must be moved in correct folder')

                # create folders
                for folder in file["folders"]:
                    if not os.path.exists(folder):
                        logging.info(f'\t\t\t - creating missing folder {os.path.join(root, folder)}')
                        try:
                            os.mkdir(folder)
                            logging.info(f'\t\t\t\t - ok')
                        except Exception as e:
                            _red_log(f'\t\t\t\t - error: {e}')
                
                # try moving the file in the target dir
                try:
                    logging.info(f'\t\t - moving in: {file["target_dir"]}')
                    os.rename(file["path"], file["target_dir"])
                    _green_log(f'\t\t\t ✓ moved in: {file["target_dir"]}')
                except Exception as e:
                    _red_log(f'\t\t\t - error: {e}\n')
        else:
            # do nothing
            _green_log(f'\t\t ✓ no package found\n')

    # print some stats
    if file_count == 0:
        logging.info(f'No java source files were found in folder')
    else:
        logging.info(f'Analysed {file_count} java files.\n')

def _green_log(str):
    """
        Log an info in the terminal with a green color
    """
    logging.info(f'\033[92m{str}\n\033[0m')
    
def _red_log(str):
    """
        Log an info in the terminal with a red color
    """
    logging.info(f'\033[91m{str}\n\033[0m')