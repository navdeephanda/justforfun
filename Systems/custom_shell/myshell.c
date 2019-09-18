//Navdeep Handa (nkh15)
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>

/// @param  a a string to be compared
/// @param  b another string to be compared
/// @return 1 if the two strings are equal, and 0 otherwise
int streq(const char* a, const char* b)
{
    return strcmp(a, b) == 0;
}

/// @brief      takes an input string and breaks it up into an array of 
///             strings based on there being either a space, tab, or newline
///             between them.
/// @param      stringToParse the string to be broken up
/// @param[out] tokens an uninitialized array of tokens to be filled from the string to tokenize
/// @return     the length of the array of tokens
int string_tokenizer(char stringToParse[], char * tokens[])
{
    const char* delimiter = " \t\n";
    char* token = strtok(stringToParse, delimiter);
    int i;
	for(i = 0; token != NULL; i++)
	{
		tokens[i] = token;
		token = strtok(NULL, delimiter);
	}
    tokens[i] = NULL;
    return i;
}

/// @brief      parses an array of tokens to check for input and output redirection operators (> or <)
/// @param[out] inputIndex pointer to an integer containing the index of the tokens array that holds
///             the file to redirect standard input to
/// @param[out] outputIndex pointer to an integer containing the index of the tokens array that holds
///             the file to redirect standard output to
/// @param      tokens array of tokens to parse
/// @param      numTokens size of the tokens array
/// @return     whether the command is a valid use of redirection
int redirection(int * inputIndex, int * outputIndex, char * tokens[], int numTokens)
{
    *inputIndex = -1;
    *outputIndex = -1;
    
    int inputAlreadyRedir = 0;
    int outputAlreadyRedir = 0;

    for(int i = numTokens - 1; i >= 0; i--)
    {
        if(streq(tokens[i], "<"))
        {
            if(!inputAlreadyRedir)
            {
                inputAlreadyRedir = 1;
                tokens[i] = NULL;
                if(tokens[i+1] != NULL && !streq(tokens[i+1],">"))
                {
                    *inputIndex = i+1;
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                return 0;
            }
        }
        else if(streq(tokens[i], ">"))
        {
            if(!outputAlreadyRedir)
            {
                outputAlreadyRedir = 1;
                tokens[i] = NULL;
                if(tokens[i+1] != NULL && !streq(tokens[i+1],"<"))
                {
                    *outputIndex = i+1;
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                return 0;
            }
        }
    }
    return 1;
}

/// @brief exits the shell with either 0 or a user specified exit code
/// @param tokens an array of tokens which contains the exit command
///        and the exit code if the user typed one
void exit_shell(char * tokens[])
{
    if(tokens[1] == NULL)
    {
        exit(0);
    }
    else
    {
        int exit_status = atoi(tokens[1]);
        exit(exit_status);
    }
}

/// @brief changes the current working directory in the shell
/// @param tokens an array of tokens which contains the cd command and
///        the parameter to it if there is one
void change_directory(char * tokens[])
{
    if(tokens[1] == NULL)
    {
        printf("Please enter an argument for the cd command.\n");
    }
    else
    {
        int status = chdir(tokens[1]);
        if(status < 0)
            perror("Error");                            
    }
}
/// @brief runs any other command that isn't cd or exit
/// @param tokens an array of tokens which contains the command to be
///        run and its arguments if they exist
/// @param numTokens length of token array
void run_other_command(char * tokens[], int numTokens)
{
    int inputIndex;
    int outputIndex;

    int validProcess = redirection(&inputIndex, &outputIndex, tokens, numTokens);

    if(validProcess)
    {
        if(fork() == 0) //Child process
        {
            signal(SIGINT, SIG_DFL);

            if(inputIndex != -1)
            {
                FILE * inputSuccess = freopen(tokens[inputIndex], "r", stdin);
                if(inputSuccess == NULL)
                {
                    perror("Error");
                    exit(1);
                }
            }
            if(outputIndex != -1)
            {
                FILE * outputSuccess = freopen(tokens[outputIndex], "w", stdout);
                if(outputSuccess == NULL)
                {
                    perror("Error");
                    exit(1);
                }
            }

            execvp(tokens[0], tokens);
            perror("Error");
            exit(1);
        }
        else //parent process
        {
            int status = 0;
            int childpid = waitpid(-1, &status, 0);

            // These WIFEXITED, WEXITSTATUS etc. macros are detailed in `man waitpid`.
            if(childpid == -1)
            {
                perror("Something went horribly wrong\n");
            }
            else
            {
                if(!WIFEXITED(status))
                {
                    if(WIFSIGNALED(status))
                        printf("Program exited due to signal '%s'.\n", strsignal(WTERMSIG(status)));
                    else
                        printf("Program exited for some other reason.\n");                                
                }
            }
        }
    }
    else
    {
        printf("Error parsing redirection command.\n");
    }
}

int main(int argc, char * argv[])
{
    signal(SIGINT, SIG_IGN);
    while(1)
    {
        // allocate a buffer to collect user input from the command line
        char userInput[300];
        printf("myshell> ");

        // collect said user input
        fgets(userInput, sizeof(userInput), stdin);

        // allocate an array big enough to hold all the user input as
        // separate strings
        char* tokens[strlen(userInput)/2 + 1];

        // tokenize dem ;)
        int numTokens = string_tokenizer(userInput, tokens);

        if(tokens[0] != NULL)
        {
            if(streq(tokens[0],"exit"))
            {
                exit_shell(tokens);
            }
            else if(streq(tokens[0],"cd"))
            {
                change_directory(tokens);
            }
            else
            {
                run_other_command(tokens, numTokens);
            }
        }
    }
}