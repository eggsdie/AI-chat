207 Final Group 16

Creating a chat messenger!

GROUP MEMBERS:

Eddie - eggsdie
Dareenat - J.Dareenat
Julian - JulianFisla
Alisha - phamali
Tiffany - Tfeng25


AI Chat

A chat messaging application that allows users to connect with friends, send messages, and utilize AI-generated responses. Users can create an account, login, and manage their friends and conversations. The application offers features for messaging, saving chat history, and translating messages, providing a seamless experience for connecting across language barriers.

Table of Contents

Domain
Software Specification
User Stories
Entities for the Domain
Proposed APIs
Domain

AI Chat is a messaging application that enables users to connect with friends via one-on-one chats, save message history, generate responses using AI, and support language translation for international communication.

Software Specification

The AI Chat program enables the following key functionalities:

Account Management:
Users can sign up by providing a username, email, and password, or log in with existing credentials.
A "Forgot Password" feature allows users to reset their password by entering their registered email.
Friend Management & Chat Creation:
Users can add friends. A new conversation is automatically created with each added friend, allowing for one-on-one messaging.
Messaging:
Users can send messages and images. Conversations are saved with timestamps to preserve chat history in chronological order.
An AI response generator can analyze conversation history and suggest responses.
Chat List:
Displays all past conversations in chronological order, showing each friend’s name, a preview of the last message, and markers for new messages or conversations.
Settings:
Allows users to manage account settings (e.g., profile picture, password, etc.).
User Stories

Eddie:
As a user, I want to create an account with a username, email, and password, and then log in to connect with friends.
Dareenat:
As a user, I want to start a conversation with a friend and have our messages saved with timestamps to easily continue conversations.
Tiffany:
As a user, I want to see my past conversations with all my friends, with a marker to indicate new messages or conversations.
Alisha:
As a user, I want the app to analyze my conversation and suggest responses for me to send.
Julian:
As a user, I want to communicate easily with international friends, minimizing language barriers.
Entities for the Domain

1. Message Class
Variables:

String textContent
String attachment (URL)
User sender
String timeStamp
Methods:

Constructor
Get Content, Get Attachment, Get Sender, Get Time Stamp
Send() - Triggers UI to display message
AddToChatList() - Adds message to the chat history
2. Chat Log Class
Represents a single conversation.

Variables:

Tuple<User> participants
List<Message> messages
Methods:

Constructor
GetParticipants()
GetMessages()
AddMessage() - Orders messages by time
3. User Class
Variables:

String email
String username
String password
String profilePicture (URL)
Dictionary<User, ChatHistory> chatList - Maps users to their respective chat histories
Methods:

GetChatID() - Retrieves conversation with a specific user
GetEmail(), GetUsername(), GetPassword() - For login/signup
GetProfilePicture() - For UI display
GetContacts() - Creates a contact list
GetConversationUsers() - Creates a chat list
ChangePassword()
ChangeProfilePicture()
