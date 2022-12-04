# README 
## Mobile App 2 : Group Project 
## Students : Melanie BLOCK (25976) and Julie HEIM (26282)

## Requirements Checklist
- 1. Authenticate using Firebase1, upload data to Firestore
   - [ ] Sign up using StudentID (as username) and a password
   - [ ] After sign up, collect and update details such as name, course, year in Firestore > StudentID, allow editing these details later
   - [ ] Collect and store locally 1000 accelerometer data points
   - [ ] Once 1000 data points are collected, upload to Firestore > StudentID > accelerometer_data
   - [ ] Repeat 3 and 4 as long as the app is open and is in the foreground (Don't record when minimised)
- 2. Display Leaderboard
   - [ ] Retrieve accelerometer_data of all users and calculate movement score2 for each user
   - [ ] If accelerometer_data is unavailable or not in correct format or has more than 1000 data points, show score "N/A".
   - [ ] Show recycler view to display leaderboard with columns rank, name and score (use score for ranking)
   - [ ] Refresh every minute, show information: last refreshed (in time ago format3), and refreshing in x seconds
   - [ ] Show details of user when clicked in full screen, allow going back to leaderboard

## Challenges: 
working with git !!!!!!
connecting to Firebase on a second workspace
