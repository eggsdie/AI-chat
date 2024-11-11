207 Final Group 16

Creating a chat messenger!

GROUP MEMBERS:

Eddie - eggsdie
Dareenat - J.Dareenat
Julian - JulianFisla
Alisha - phamali
Tiffany - Tfeng25


\documentclass{article}
\usepackage{hyperref}
\title{AI Chat}
\date{}
\begin{document}
\maketitle

\section*{Overview}
AI Chat is a chat messaging application that allows users to connect with friends, send messages, and utilize AI-generated responses. Users can create an account, login, and manage their friends and conversations. The application offers features for messaging, saving chat history, and translating messages, providing a seamless experience for connecting across language barriers.

\tableofcontents

\section{Domain}
\textbf{AI Chat} is a messaging application that enables users to connect with friends via one-on-one chats, save message history, generate responses using AI, and support language translation for international communication.

\section{Software Specification}
The AI Chat program enables the following key functionalities:
\begin{enumerate}
    \item \textbf{Account Management}: 
    \begin{itemize}
        \item Users can sign up by providing a username, email, and password, or log in with existing credentials.
        \item A ``Forgot Password" feature allows users to reset their password by entering their registered email.
    \end{itemize}

    \item \textbf{Friend Management \& Chat Creation}:
    \begin{itemize}
        \item Users can add friends. A new conversation is automatically created with each added friend, allowing for one-on-one messaging.
    \end{itemize}

    \item \textbf{Messaging}: 
    \begin{itemize}
        \item Users can send messages and images. Conversations are saved with timestamps to preserve chat history in chronological order.
        \item An AI response generator can analyze conversation history and suggest responses.
    \end{itemize}

    \item \textbf{Chat List}:
    \begin{itemize}
        \item Displays all past conversations in chronological order, showing each friend’s name, a preview of the last message, and markers for new messages or conversations.
    \end{itemize}

    \item \textbf{Settings}:
    \begin{itemize}
        \item Allows users to manage account settings (e.g., profile picture, password, etc.).
    \end{itemize}
\end{enumerate}

\section{User Stories}
\begin{itemize}
    \item \textbf{Eddie}: As a user, I want to create an account with a username, email, and password, and then log in to connect with friends.
    \item \textbf{Dareenat}: As a user, I want to start a conversation with a friend and have our messages saved with timestamps to easily continue conversations.
    \item \textbf{Tiffany}: As a user, I want to see my past conversations with all my friends, with a marker to indicate new messages or conversations.
    \item \textbf{Alisha}: As a user, I want the app to analyze my conversation and suggest responses for me to send.
    \item \textbf{Julian}: As a user, I want to communicate easily with international friends, minimizing language barriers.
\end{itemize}

\section{Entities for the Domain}

\subsection{1. Message Class}
\textbf{Variables}:
\begin{itemize}
    \item \texttt{String textContent}
    \item \texttt{String attachment (URL)}
    \item \texttt{User sender}
    \item \texttt{String timeStamp}
\end{itemize}

\textbf{Methods}:
\begin{itemize}
    \item Constructor
    \item Get Content, Get Attachment, Get Sender, Get Time Stamp
    \item \texttt{Send()} - Triggers UI to display message
    \item \texttt{AddToChatList()} - Adds message to the chat history
\end{itemize}

\subsection{2. Chat Log Class}
Represents a single conversation.

\textbf{Variables}:
\begin{itemize}
    \item \texttt{Tuple<User> participants}
    \item \texttt{List<Message> messages}
\end{itemize}

\textbf{Methods}:
\begin{itemize}
    \item Constructor
    \item \texttt{GetParticipants()}
    \item \texttt{GetMessages()}
    \item \texttt{AddMessage()} - Orders messages by time
\end{itemize}

\subsection{3. User Class}
\textbf{Variables}:
\begin{itemize}
    \item \texttt{String email}
    \item \texttt{String username}
    \item \texttt{String password}
    \item \texttt{String profilePicture (URL)}
    \item \texttt{Dictionary<User, ChatHistory> chatList} - Maps users to their respective chat histories
\end{itemize}

\textbf{Methods}:
\begin{itemize}
    \item \texttt{GetChatID()} - Retrieves conversation with a specific user
    \item \texttt{GetEmail()}, \texttt{GetUsername()}, \texttt{GetPassword()} - For login/signup
    \item \texttt{GetProfilePicture()} - For UI display
    \item \texttt{GetContacts()} - Creates a contact list
    \item \texttt{GetConversationUsers()} - Creates a chat list
    \item \texttt{ChangePassword()}
    \item \texttt{ChangeProfilePicture()}
\end{itemize}

\section{Proposed APIs}
\begin{table}[h!]
    \centering
    \begin{tabular}{|l|l|l|}
        \hline
        \textbf{API Link} & \textbf{Purpose} & \textbf{Integrated?} \\
        \hline
        \href{https://libretranslate.com/}{LibreTranslate} & Message translation & No \\
        \hline
        \href{https://firebaseopensource.com/projects/firebase/firebase-admin-java/}{Firebase Admin Java} & Database management & No \\
        \hline
        \href{https://platform.openai.com/docs/api-reference/introduction}{OpenAI API} & AI-generated responses & No \\
        \hline
        \href{https://cohere.com/}{Cohere} & AI Chat Generation & No \\
        \hline
    \end{tabular}
\end{table}

\end{document}
