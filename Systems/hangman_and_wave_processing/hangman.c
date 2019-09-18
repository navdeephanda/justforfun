// navdeep handa
// nkh15

// time 2 hang with the man (or the WO man #its2019)

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h> 
#include <time.h>

/*GLOBAL*/

#define MAX_STR_LEN 20

/*HELPERS*/

void get_line(char* buffer, int size, FILE* stream) 
{
	fgets(buffer, size, stream);
	int len = strlen(buffer);
	// this is a little more robust than what we saw in class.
	if(len != 0 && buffer[len - 1] == '\n')
		buffer[len - 1] = '\0';
}

// returns 1 if the two strings are equal, and 0 otherwise.
int streq(const char* a, const char* b)
{
    return strcmp(a, b) == 0;
}

// returns 1 if the two strings are equal ignoring case, and 0 otherwise.
// so "earth" and "Earth" and "EARTH" will all be equal.
int streq_nocase(const char* a, const char* b)
{
    // hohoho aren't I clever
	for(; *a && *b; a++, b++) if(tolower(*a) != tolower(*b)) return 0;
	return *a == 0 && *b == 0;
}

int gen_rand_num(int low_value, int high_value)
{
    return (rand() % (high_value - low_value) + low_value);
}

int chareq_nocase(char a, char b)
{
    return (tolower(a) == tolower(b));
}

/*DOERS*/

FILE * open_file(void)
{
    //file open, quits program if file doesn't exist
    FILE* input_dict = fopen("dictionary.txt", "r");
    if(input_dict == NULL)
    {
        printf("Where's your dictionary file bro? Bc it ain't here");
        exit(1);
    }
    return input_dict;
}

void populate_string_arr(FILE * input_file, char out_array[][MAX_STR_LEN])
{
    int i = 0;
    while(!feof(input_file))
    {
        get_line(out_array[i], sizeof(out_array[i]), input_file);
        i++;
    }
}

char * determine_word_to_guess(int argc, char** argv, int num_of_words, char all_words[][MAX_STR_LEN])
{
    if(argc > 1)
    {
        return argv[1];
    }
    else
    {
        int rand_index = gen_rand_num(0, num_of_words);
        return all_words[rand_index];
    }
}

void prompt_user_guess(char curr_guess_state[], int word_to_guess_len)
{
    //show current state of user's word
    for(int i = 0; i < word_to_guess_len; i++)
    {
        printf("%c ", curr_guess_state[i]);
    }

    printf("Guess a letter or type the whole word:");
}

int find_char_in_word(char char_to_find, char * string_to_search, char output_string[MAX_STR_LEN])
{
    int found = 0;
    
    int string_to_search_len = strlen(string_to_search);
    for(int i = 0; i < string_to_search_len; i++)
    {
        if(chareq_nocase(char_to_find, *string_to_search++))
        {
            found = 1;
            output_string[i] = tolower(char_to_find);
        }

    }
    return found;
}

void update_strikes(int * strike_count)
{
    (*strike_count)++;
    printf("WRONG! Strike %d, ya square\n", *strike_count);
}

int main(int argc, char** argv)
{
    FILE * dict_file = open_file();

    //get the number of words in the dictionary (it is the first line of the file)
    char input[20];
    get_line(input, sizeof(input), dict_file);
	int num_of_words;
	sscanf(input, "%d", &num_of_words);

    char words[num_of_words][20];
    populate_string_arr(dict_file, words);

    fclose(dict_file);

    // seed random number generator
    srand((unsigned int)time(NULL));

    char* word_to_guess = determine_word_to_guess(argc, argv, num_of_words, words);

    //greet user
    int word_to_guess_len = strlen(word_to_guess);
    printf("Howdy pardner time 2 play some hangman ;) Your word has %d letters\n", word_to_guess_len);

    //allocate and initialize current guess state and strikes counter
    char curr_guess_state[word_to_guess_len];
    for(int i = 0; i < word_to_guess_len; i++)
    {
        curr_guess_state[i] = '_';
    }
    curr_guess_state[word_to_guess_len] = '\0';
    int strikes = 0;

    while(1)
    {
        prompt_user_guess(curr_guess_state, word_to_guess_len);

        char user_guess[MAX_STR_LEN];
        get_line(user_guess, sizeof(user_guess), stdin);
		
        if(streq(user_guess, ""))
            continue;

        if(strlen(user_guess) == 1)
        {
            int found = find_char_in_word(user_guess[0], word_to_guess, curr_guess_state);
            if(found == 1)
            {
                if(streq_nocase(curr_guess_state, word_to_guess))
                {
                    printf("YOU WINNNNNNNNNNNNNNNNNNNNNNNN\n");
                    return 0;
                }
            }
            else
            {
                update_strikes(&strikes);
            }
        }
        else
        {
            if(streq_nocase(user_guess, word_to_guess))
            {
                printf("YOU WINNNNNNNNNNNNNNNNNNNNNNNN\n");
                return 0;
            }
            else
            {
                update_strikes(&strikes);
            }
        
        }
        if(strikes > 4)
        {
            printf("You're a goddamn loser and will never amount to anything. Correct word is: %s\n",word_to_guess);
            return 0;
        }
    }

    return 0;
}