#!/usr/bin/env python3

import os
import logging
logging.basicConfig(level=logging.INFO)

def raj2jar(root):
    """
    Takes a string root as parameter, which is the absolute path of a directory in the local file system. 

    This function rename all files with extension .raj in the subtree of directories rooted at root
    by changing the extension to .jar.
    """
    if not os.path.isdir(root):
        logging.info("Root is not a valid directory")
        return

    file_count = 0
    for root, dirs, files in os.walk(root):
        for name in files:
            if name.endswith(".raj"):
                file_count += 1
                logging.info("Renaming " + os.path.join(root, name) + " to jar... ")
                try:
                    filename_without_extension = name[0:-4]
                    os.rename(os.path.join(root, name), os.path.join(root, filename_without_extension + '.jar'))
                    logging.info(f'\t - done.')
                except Exception as e:
                    logging.info(f'\t - error.')
                    logging.info(e)
                    logging.info("\n")
    logging.info(f'Renamed {file_count} raj files')