#!/bin/bash
#
# File: birthday_match.sh
#
# Description: This file takes two birth dates as inputs and tells the user
#		if the two dates happened to be on the same day
#
# Input:       Two birthdates in the format MM/DD/YYYY.
#
# Output:      Match or not a match.
#
	

	#check if there is two dates to match days:
	
	if [ "$#" -ne 2 ];
	then 
		echo "Need to have exactly two dates"
		exit 0
	fi

	date1=$1
	date2=$2	
	
	#first check that the date format is correct!

	if ! [[ $date1 =~ [0-1][0-9]/[0-3][0-9]/[0-9]{4} ]];
	then
		echo "put date in format MM/DD/YYYY"
		exit 0
	fi	
	
	if ! [[ $date2 =~ [0-1][0-9]/[0-3][0-9]/[0-9]{4} ]];
	then
		echo "put date in format MM/DD/YYYY"
		exit 0
	fi
	

	#Then check if the date is even valid!
	
	date +"%m/%d/%Y" -d $date1  >/dev/null 2>&1 && x=1 || x=2 #checking ifthe date is valid
	
	date -d $date2 +"%m/%d/%Y" >/dev/null 2>&1 && y=1 || y=2 #checking if the second date is valid
		

	if [[ ($x -eq 2) || ($y -eq 2) ]]  #only if the date is valid should you test days and matches
	then
		echo "Enter a valid date in format: MM/DD/YYYY"
		exit 0
	else
		#echo $date1
		firstDay="$(date -d "$date1" +"%A")"   #getting the day from date
		secondDay="$(date -d "$date2" +"%A")"
	fi

	if [[ "$firstDay" == "$secondDay" ]]  #check if both days are the same
	then
		echo "First person was born on: $firstDay"
		echo "Second person was born on: $secondDay"
		echo "It's a match! Woohoo!"

	else
		echo "First person was born on: $firstDay"
		echo "Second person was born on: $secondDay"
		echo "It's not a match! Hmmm sorry?"
	fi

exit 0;