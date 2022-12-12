# README 
## Mobile App 2 : Group Project 
## Students : Melanie BLOCK (25976) and Julie HEIM (26282)

## Requirements Checklist:
- 1. Authenticate using Firebase1, upload data to Firestore
   - [x] Sign up using StudentID (as username) and a password
   - [x] After sign up, collect and update details such as name, course, year in Firestore > StudentID, allow editing these details later
   - [x] Collect and store locally 1000 accelerometer data points
   - [x] Once 1000 data points are collected, upload to Firestore > StudentID > accelerometer_data
   - [x] Repeat 3 and 4 as long as the app is open and is in the foreground (Don't record when minimised)
- 2. Display Leaderboard
   - [x] Retrieve accelerometer_data of all users and calculate movement score2 for each user
   - [x] If accelerometer_data is unavailable or not in correct format or has more than 1000 data points, show score "N/A".
   - [x] Show recycler view to display leaderboard with columns rank, name and score (use score for ranking)
   - [x] Refresh every minute, show information: last refreshed (in time ago format3), and refreshing in x seconds
   - [x] Show details of user when clicked in full screen, allow going back to leaderboard

## Report: 

The first challenges we faced were to work in a team. We are not very familiar with Git so we had to adjust to be able to work together in an "efficent" way, and without loosing our work. On the same topic, we had to implement the same Firebase account. Doing those two things were necessary to begin the "real work" but it took us some time. <br />
We dispatched the tasks pretty much easily. <br />
The signin/signup part took us a little while because of thought we had to use the Student ID only. It took a bit of a time understand that the authentication had nothing to do with the database. But once we understood that we were able to login with the student mail address in Firebase Authentication system and retrieve the Student ID to create the Firestore documents.<br />
The show/edit features challenges were mainly to find which syntax to use to be able to access the data stored in Firestore and how to use them.<br />


## References:
From the following references code was used in part or fully:
- https://stackoverflow.com/questions/35253368/how-can-i-create-an-array-in-kotlin-like-in-java-by-just-providing-a-size
- https://github.com/okwrtdsh/AccelerometerTest/blob/master/app/src/main/kotlin/com/github/okwrtdsh/accelerometertest/MainActivity.kt
- https://www.programiz.com/kotlin-programming/examples/concatenate-two-arrays
- https://stackoverflow.com/questions/35858608/how-to-convert-time-to-time-ago-in-android
