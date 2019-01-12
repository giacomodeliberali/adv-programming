#!/usr/bin/env python3

import logging

def green_log(str):
    """
        Log an info in the terminal with a green color
    """
    logging.info(f'\033[92m{str}\n\033[0m')
    
def red_log(str):
    """
        Log an info in the terminal with a red color
    """
    logging.info(f'\033[91m{str}\n\033[0m')

def set_log_color_blue():
    """
        Set the color of the logging.info to blue
    """
    logging.info(f'\033[94m')

def rm_log_color():
    """
        Rmeove the color of the logging.info
    """
    logging.info(f'\033[0m')