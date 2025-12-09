Scrum Simulation Tool - User Guide

Login Credentials
| Role          | Username | Password |
|---------------|----------|----------|
| Product Owner | po       | po123    |
| Scrum Master  | sm       | sm123    |
| Developer     | dev      | dev123   |

More Login Credentials -
User Name - viraj_rathor     
password - viraj123 

User Name - gunjan_purohit   
Password - gunjan123

User Name - sairaj_dalvi 
Password - sairaj123

User Name - pranav_irlapale 
password - pranav123

User Name - shreyas_revankar
password - shreyas123

Start with Product Owner (po/po123) to see all features

 Getting Started 

1. Run: `mvn exec:java` or run `MainApp.java`
2. Login as Product Owner (po/po123)
3. Create Team → Join Team
4. Click "Scrum Simulation" button
5. Click "Backlog" → "Add Story" to create user stories


Major Features - 

1. Role-Based Access Control
- Product Owner, Scrum Master, and Developer roles
- Different buttons appear based on logged-in role
- Permission validation prevents unauthorized actions

2. Product Backlog Management
- Create user stories with title, description, points, priority
- Adjust priorities with up/down controls
- Automated priority validation testing
- Move stories between backlog and sprint

3. Sprint Progress Tracking
- Real-time calculation of completed vs total story points
- Goal: 30 story points per sprint
- Automatic updates when stories marked DONE

4. Story Assignment System
- Scrum Masters assign stories to developers
- Support for multiple developers on one story
- Validation that assignees exist on team

5. Story Status Workflow
- Three-state workflow: TO_DO → IN_PROGRESS → DONE
- Only assigned developers can update status
- Guards prevent unauthorized status changes

6. Stakeholder Communication
- "Give Input" feature for stakeholder feedback
- Timestamped communication tracking
- Product Owner can review all communications
- Organized by story for easy reference

7. Multi-Select Bulk Operations
- Toggle multi-select mode
- Select multiple stories with checkboxes
- Bulk move stories to backlog
- Efficient sprint replanning

8. Team Management
- Create multiple teams
- Join/leave teams
- Manage team member roles (Scrum Master)
- Team member status indicators (online/offline)

9. Business Value Assessment
- Product Owner reviews business value of stories
- Used for prioritization decisions
- Separate dialog for focused review

10. Developer Work Dashboard
- "My Work" view shows only assigned stories
- Filtered view for developer focus
- Quick access to update story status


Key Features -

1. Create User Stories
- Click Backlog → Add Story
- Fill in: Title, Story Points, Priority, Description
- Click "Add"

2. Prioritize Backlog
- In Backlog dialog, use ↑ ↓ arrows to adjust priorities
- Click Run Tests to validate priority ordering

3. Assign Work
- Login as Scrum Master (sm/sm123)
- Click Assign Story
- Select story and developers
- Click "Assign"

4. Update Story Status
- Login as Developer (any of the 5 team members)
- Click My Work
- Edit story, change Status: TO_DO → IN_PROGRESS → DONE
- Watch sprint progress auto-update

5. Track Sprint Progress
- Top of dashboard shows: "Sprint Progress: X/30 points"
- Updates automatically when stories marked DONE

 6. Stakeholder Feedback
- Click Give Input
- Select story, enter feedback, submit
- Login as PO to click View Communication to see all feedback

 7. Bulk Operations
- Click Multi-Select button
- Check multiple stories
- Click Move to Backlog

Story Status Workflow

TO_DO → IN_PROGRESS → DONE

Only DONE stories count toward sprint progress


What Each Role Can Do

Product Owner - 
Manage backlog  
Set priorities 
Review business value 
View communications

Scrum Master - 
Everything PO can do 
Create teams 
Assign stories 
Manage roles

Developer -
View My Work 
Update status 
Give feedback 
View dashboard

P.S - All the 5 team members can login as Scrum Master, Product Owner and Developer
