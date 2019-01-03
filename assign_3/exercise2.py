#!/usr/bin/env python3

import os
import logging
logging.basicConfig(level=logging.INFO)

def collect_sources(root, sources):
    """
    Takes as parameter two strings: root, which is an absolute path, and sources, which is the name of a text file.
    
    This function collects in the file sources placed in directory root the relative paths (using Linux separators)
    starting from root of all files with extension .java, .hs or .py contained in the subtree of directories rooted at root
    """

    if not os.path.isdir(root):
        logging.info("Root is not a valid directory")
        return

    sources_file_path = os.path.join(root, sources)
    try:
        sources_file = open(sources_file_path, "w")
    except Exception as e:
        logging.info(f'Cannot open sources file. Exception: {e}')
        return

    for root, dirs, files in os.walk(root):
        for name in files:
            if name.endswith(".java") or name.endswith(".hs") or name.endswith(".py"):
                filename = os.path.join(root, name)
                logging.info(f'Add {filename} to sources...')
                try:
                    sources_file.write(filename + "\n")
                    logging.info(f'\t - done.')
                except Exception as e:
                    logging.info(f'\t - error: {e}')

    sources_file.close()