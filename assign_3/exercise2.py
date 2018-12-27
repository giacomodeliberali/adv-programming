# !/usr/local/bin/python3
import os


def collect_sources(root, sources):
    """
    Takes as parameter two strings: root, which is an absolute path, and sources, which is the name of a text file.
    
    This function collects in the file sources placed in directory root the relative paths (using Linux separators)
    starting from root of all files with extension .java, .hs or .py contained in the subtree of directories rooted at root
    """

    if not os.path.isdir(root):
        print("Root is not a valid directory")
        return

    sources_file_path = os.path.join(root, sources)
    try:
        sources_file = open(sources_file_path, "w")
    except Exception as e:
        print("Cannot open sources file. " + e)
        return

    for root, dirs, files in os.walk(root):
        for name in files:
            if name.endswith(".java") or name.endswith(".hs") or name.endswith(".py"):
                filename = os.path.join(root, name)
                print("Add " + filename + " to sources", end='')
                try:
                    sources_file.write(filename + "\n")
                    print("done.")
                except Exception as e:
                    print("error. " + e)

    sources_file.close()

collect_sources("/Users/giacomodeliberali/code/unipi/adv-programming", "sf.txt")
