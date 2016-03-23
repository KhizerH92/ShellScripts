#!/bin/bash
# Script name: url_search.sh
#
# Description: look at all the urls in a given file and find the count of words
# 		for each of the words input as parameters
#
# 
#
# Input: a file with urls and then words to get the count of.
#
# Output: words and then urls with the count of that word.
#
#File run example: url_search file.txt word1 word2 word3
#

if [ "$#" -le 1 ]; then
	echo "enter filename with urls and at least one word to search as arguments"
	exit 0

else
	file=$(<$1)
	

	for var in "${@:2}"; do
		for line in $file; do
			if ! [ -z $line ];
			then		
				webContent=`curl -s -L "$line" | grep $var | wc -l`
		
				echo $var
				echo $line $webContent
		
			fi
		done
		echo " "
	done
fi
		
		
