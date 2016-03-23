#!/bin/bash
# Script name: count_files.sh
#
# Description: Counts the number of files of each filetype (i.e file extension)
# 		in the current directory and its subdirectories and produces a
#		a summary report.
#
# Input: None
#
# Output: number of different file types
#
# Pseudocode: A high-level description of how 
# the shell script works goes here...
#

#to count files of all types recursively we can just use:

find . -type f -name "*.*" | sed 's/.*\.//' | sort | uniq -c  #this finds all files with some sort of extension



filesWithExt=`find . -type f -name "*.*" | sed 's/.*\.//' | sort | wc -l` #i get the count of all files with some kind of extension
totalFiles=`find . -type f | sed 's/.*\.//' | sort | wc -l` #i find the total number of files available
numFiles=`expr $totalFiles - $filesWithExt` #and get the number of files with no extension

printf "      $numFiles noext" #and I print it out with other stats

exit 0;

