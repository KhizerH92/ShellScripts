#!/bin/bash
# Script name: spy.sh
#
# Description: Monitors when specified users log in and 
# out of a machine. 
#
# Upon receiving signal 10, it writes a report
# summarizing their activity while spy.sh was running.
# This report includes their usage 
# patterns including how many times they logged 
# in and out and the duration. The script also 
# computes which user logged in the most often, 
# and for the longest and shortest periods of time.
#
# Command line options:  This is where you explain
# any "-t" like options you use.
#
# Input: List of users to monitor in terms of 
#         their full names such as “Charles Palmer”
#
# Output:
# it runs until it receives signal 10, whereupon it creates its spy.log report.
#
# Special considerations and assumptions:  If any, they would go 
# here. Things like other files the shell script 
# depends on, interpretations of vague customer requirements, etc.
# For example, what do you do if a user has more than one userid ?
#
# Pseudocode: A high-level description of how 
# the shell script works goes here..

echo "" > logfile.txt 


declare -A loggedIn
declare -A shortestDur
declare -A longestDur
declare -A TotalUserTime

#the following function defines the exiting procedure

function quitting {

touch logfile.txt
echo "Spy.sh Report" >> logfile.txt
now=`date`
echo "Started at $start_time" >> logfile.txt
echo "Stopped at $now" >> logfile.txt
echo " " >> logfile.txt

for arg in ${BASH_ARGV[*]};
	do
	uname=`grep -w $arg /etc/passwd | cut -d ":" -f1`
	
	if [ -f $uname.txt ]; then
		logNum=`wc -l $uname.txt | cut -d " " -f1`
		echo "$uname logged on $logNum times for a total of ${TotalUserTime[$uname]} seconds. Here are the details" >> logfile.txt
		echo " "
		cat "$uname.txt" >> "logfile.txt"
		echo " " >> "logfile.txt"

		#and then remove that file
		rm $uname.txt
	fi

	
done

shortestTime=100000
shortestUser=""
for shortUser in "${!shortestDur[@]}"
	do
	if [[ ${shortestDur[$shortUser]} -le $shortestTime ]];
	then
		shortestTime=${shortestDur[$shortUser]}
		shortestUser=$shortUser
	fi
done


longestTime=0
longestUser=""
for longUser in ${!longestDur[@]}
	do
	if [[ ${longestDur[$longUser]} -ge $longestTime ]];
	then
		longestTime=${longestDur[$longUser]}
		longestUser=$longUser
	fi

done

longestTimeSpent=0
longestUserSpending=""

for longUserSpending in ${!TotalUserTime[@]}
	do
	if [[ ${TotalUserTime[$longUserSpending]} -ge $longestTimeSpent ]];
	then
		longestTimeSpent=${TotalUserTime[$longUserSpending]}
		longestUserSpending=$longUserSpending
	fi

done


echo " " >> logfile.txt
echo "$longestUserSpending spent the most time on Wildcat today:" >> logfile.txt
echo "$longestTimeSpent seconds in total for all his/her sessions" >> logfile.txt

echo " " >> logfile.txt
echo "$shortestUser spent the shortest time on Wildcat today:" >> logfile.txt
echo "$shortestTime seconds in total" >> logfile.txt

echo " " >> logfile.txt
echo "$longestUser logged on for the longest session on Wildcat today:" >> logfile.txt
echo "$longestTime seconds in total" >> logfile.txt

exit

}

#make sure that there is at least one person to spy on

if ! [ "$#" -ge 1 ]
then
	echo "Who should we spy on?! Give us a name!"
	exit 0
fi

#check that all the names entered are in the system, that is in the
# /etc/passwd. If they are not, exit the script and ask the user to
# take the person out:

for arg
do
	uname=`grep -w "$arg" /etc/passwd | cut -d ":" -f1`
	if [ -z $uname ]
	then
		echo "$arg is not in the system. Take him out"
		exit 0
	fi
done


start_time=`date`

IFS='
'

while true
do
	for arg #user in "$@"; loop to record login times
	do
		uname=`grep -w "$arg" /etc/passwd | cut -d ":" -f1` #get the username from the full name
		#printf "$uname"
		ptsVal=`who -H | grep $uname | awk '{print $2}' | cut -d "/" -f2`	#get the pts numbers
		#printf "$ptsVal\n"

		
		for x in $ptsVal;
			do 
			userString=$uname/$x  #string is of the form username/(pts number)
			#printf "$userString\n"
			userTime=`date` #and you get the date when you saw the person online
			#printf "$userTime\n"
						

			if [ ${loggedIn[$userString]+_} ] #now if such an array entry exists
				then
				: #don't do anything
				
			else
				
				#printf "$userTime\n"
				#printf "$userString\n"
				
				loggedIn[$userString]=$userTime #then add the string and their login time
				
				#echo "${!loggedIn[@]}"
				#printf "/\n"
				#echo "testing insertion ${loggedIn[$userString]}\n"
			
			fi #if thing ends here
		
		done
	done 			#So the above whole thing is the entry loop
	
	#echo "${!loggedIn[@]}\n" #checking all entries		
	
	for username in "${!loggedIn[@]}"; #you take each username/ptsvalue entry
	do
		#echo "Second for loop"

		#printf "$username is username\n"
		name=`echo $username | cut -d "/" -f1` #get the username
		#printf "$name\n"
		ptsVal2=`echo $username | cut -d "/" -f2` #get the pts
		#printf "$ptsVal2\n"
		whoIsOn=`who -H | grep $name | grep -w "pts/$ptsVal2"` #and grep using the name and the pts
		#printf "$whoIsOn\n"

		#echo "all good"
			
		if [ -z $whoIsOn ]; #if the person is not on

		then
			#echo "who is on is : $whoIsOn" 
			logoutTime=`date` #you get the logout time
			#echo "username is $username"
			#echo "keys: ${!loggedIn[@]}"
			
			logInTime=${loggedIn[$username]} #and the login time
			
			#echo "$logInTime is loginTime"
			
		#here we are updating longest and shortest times for users
			#echo "where is it \n"
			#echo $logoutTime
			#echo $logInTime
			#echo ${loggedIn[$username]}
			
			usersNewTime=`expr $(date -d $logoutTime "+%s") - $(date -d $logInTime "+%s")` #difference in seconds
			#echo "found it"
			#echo "users new time is $usersNewTime"
			#echo ${TotalUserTime[$name]}
			#echo ${!TotalUserTime[@]}
			TotalUserTime[$name]=$((${TotalUserTime[$name]} + $usersNewTime))
			

			if ! [ ${shortestDur[$name]+_} ];
			then
				shortestDur[$name]=$usersNewTime
			
			elif [[ $usersNewTime -le ${shortestDur[$name]} ]]; 
				then 
					shortestDur[$name]=$usersNewTime
			fi
	

			if ! [ ${longestDur[$name]+_} ];
			then
				longestDur[$name]=$usersNewTime
				#echo "saving first time ${longestDur[$name]}"
			elif [[ ${longestDur[$name]} -le $usersNewTime ]]; 
				then 
					longestDur[$name]=$usersNewTime
					#echo "longest time was ${longestDur[$name]}"
			
			#else
				#echo "$usersNewTime time shorter than ${longestDur[$name]}"
				
			fi



			if [ ! -f $name.txt  ]; then #create a file if one doesn't already exist
				touch $name.txt 
				echo -e "$name pts/$ptsVal2 $logInTime $logoutTime" >> $name.txt #add entry
			else
				echo -e "$name pts/$ptsVal2 $logInTime $logoutTime" >> $name.txt
			fi
		
			#else #if the person is on see from how many different terminals				
			#Update loggedIn table:
				
			unset loggedIn[$username]
		
		fi


		
		#echo "claered"
	done

sleep 5

trap 'quitting' SIGUSR1


done