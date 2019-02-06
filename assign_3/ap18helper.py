#!/usr/bin/env python3

from exercise1 import raj2jar
from exercise2 import collect_sources
from exercise3 import rebuild_packages
from exercise4 import download_tests


def _loop():
    print(f'Choose one:')
    print(f'0: exit')
    print(f'1: raj2jar')
    print(f'2: collect_sources')
    print(f'3: rebuild_packages')
    print(f'4: download_tests')

    return input("choice: ")


def _invoke_raj2jar():
    root = input("Type root folder: ")
    raj2jar(root)


def _invoke_collect_sources():
    root = input("Type root folder: ")
    sources = input("Type sources file: ")
    collect_sources(root, sources)


def _invoke_rebuild_packages():
    root = input("Type root folder: ")
    rebuild_packages(root)


def _invoke_download_tests():
    root = input("Type root folder: ")
    url = input("Type url (empty for default): ")

    if url:
        download_tests(root, url)
    else:
        download_tests(root)


if __name__ == "__main__":
    choice = _loop()
    while(choice != "0"):
        if choice == "1":
            _invoke_raj2jar()
        elif choice == "2":
            _invoke_collect_sources()
        elif choice == "3":
            _invoke_rebuild_packages()
        elif choice == "4":
            _invoke_download_tests()
        else:
            print("Wrong choice")

        print("\n")
        choice = _loop()
