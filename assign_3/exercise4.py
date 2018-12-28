#!/usr/bin/env python3

import os
import subprocess
import requests
import logging
import csv
import urllib
from exercise3 import get_package_as_list

logging.basicConfig(level=logging.INFO)

def _downloadCSV(url):
    """
        Download the given CSV file and return it as a list of rows without the header
    """
    # open a session
    with requests.Session() as s:
        # download the file
        download = s.get(url)
        # parse as text
        decoded_content = download.content.decode('utf-8')
        # parse a csv
        cr = csv.reader(decoded_content.splitlines(), delimiter=',')
        # filter out header and empty trailing rows
        return filter((lambda x: x), list(cr)[1:])

def _exists_in_folder(folder, file):
    """
        Return a tuple (bool,string) where this the bool
        indicates if the given file is present in the specified directory.

        The second tuple component is the absolute file path of the file, if found. None otherwise
    """
    for root, dirs, files in os.walk(folder):
        for filename in files:
            if filename == file:
                # return also the full path of the file
                file_path = os.path.join(root, filename)
                return True, file_path
    return False, None

def download_tests(root, url="http://pages.di.unipi.it/corradini/Didattica/AP-18/PROG-ASS/03/Test"):

    csv_url = f'{url}/AP_TestRegistry.csv'
    logging.info(f'Downloading CSV from {csv_url}')
    entries = _downloadCSV(csv_url)

    for row in entries:
        filename = row[0].strip()
        # strip and filter empty items
        test_files = list(filter((lambda x: x),map((lambda x: x.strip()),row[1].split(":"))))
        command = row[2].strip()

        logging.info(f'\t - check {filename}...')

        exists_in_folder, path = _exists_in_folder(root,filename)

        if exists_in_folder:
            logging.info(f'\t\t - found in: {path}')
            if len(test_files) == 0:
                logging.info(f'\t\t - no test files to download')
                logging.info(f'')
                continue

            # extract the path from the full file name
            # the default test path is the same as file
            test_base_path = path[:-(len(filename)+1)]

            if filename.endswith('.java'):
                # get the java file package as list
                package = get_package_as_list(path)
                if len(package) > 0:
                    package_name = '.'.join(package)
                    # if there is a package, place tests in parent folder
                    test_base_path = os.path.abspath(os.path.join(test_base_path, os.pardir))
                    logging.info(f'\t\t - package name is {package_name}')
                    logging.info(f'\t\t - test_base_path (file\'s parent): {test_base_path}')
                else:
                    # else place them in the same folder as file
                    logging.info(f'\t\t - no package found')
                    logging.info(f'\t\t - test_base_path (same as file): {test_base_path}')
            else:
                # haskell and python test file are in the same folder as file
                logging.info(f'\t\t - test_base_path (same as file): {test_base_path}')

            logging.info(f'\t\t - begin download test files:')
            for tf in test_files:
                # create the target dir for the current test file
                tf_path = os.path.join(test_base_path, tf)
                logging.info(f'\t\t\t - downloading {tf} into {tf_path}')

                # download the file and put into the target dir
                urllib.request.urlretrieve(f'{url}/{tf}', tf_path)
            logging.info(f'\t\t\t => done')

            # now files are ready to be tested

            
            command = command[1:-1]
            logging.info(f'\t\t - running command: "{command}"')
            #process = subprocess.Popen(command.split(), cwd=test_base_path)
            #output, error = process.communicate()
            os.system(f'cd {test_base_path} && {command}')
            logging.info(f'\t\t - end command\n\n')

        else:
            logging.info(f'\t\t - not found')
        logging.info(f'')