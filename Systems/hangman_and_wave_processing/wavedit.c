//Navdeep Handa 
//(nkh15)
//DO THE WAV

 #include <stdio.h>
 #include <stdint.h>
 #include <stdlib.h>
 #include <string.h>

/*STRUCTS AND ENUMS*/

typedef struct WAVHeader {
	char        riff_id[4];
	uint32_t    file_size;
	char        wave_id[4];
    char        fmt_id[4];
    uint32_t    fmt_size;
    uint16_t    data_format;
    uint16_t    number_of_channels;
    uint32_t    samples_per_second;
    uint32_t    bytes_per_second;
    uint16_t    block_alignment;
    uint16_t    bits_per_sample;
    char        data_id[4];
    uint32_t    data_size;
} WAVHeader;

typedef enum CmdOption 
{
    INVALID_ARG = -1, 
    NO_ARGS = 0, 
    FNAME_ONLY = 1, 
    RATE = 2, 
    REVERSE = 3
} CmdOption;

/*HELPERS*/
FILE * open_file(char * filename, char * mode)
{
    //file open, quits program if file doesn't exist
    FILE* input_file = fopen(filename, mode);
    if(input_file == NULL)
    {
        printf("Where's your file bro? Bc it ain't here\n");
        exit(1);
    }
    return input_file;
}

int streq(const char* a, const char* b) 
{
	return strcmp(a, b) == 0;
}

int streqn(const char* a, const char* b, size_t n) 
{
	return strncmp(a, b, n) == 0;
}

void fail_on_invalid_wav(void)
{
    printf("NOT a wav file!!!! :(\n");
    exit(1);
}

void reverse_int8_arr(int8_t arr[], int arr_len)
{
    for(int i = 0; i < arr_len/2; i++)
	{
		int temp = arr[i];
		arr[i] = arr[(arr_len - 1) - i];
		arr[(arr_len - 1) - i] = temp;
	}
}

void reverse_int16_arr(int16_t arr[], int arr_len)
{
    for(int i = 0; i < arr_len/2; i++)
	{
		int temp = arr[i];
		arr[i] = arr[(arr_len - 1) - i];
		arr[(arr_len - 1) - i] = temp;
	}
}

void reverse_int32_arr(int32_t arr[], int arr_len)
{
    for(int i = 0; i < arr_len/2; i++)
	{
		int temp = arr[i];
		arr[i] = arr[(arr_len - 1) - i];
		arr[(arr_len - 1) - i] = temp;
	}
}

/*DOERS*/

CmdOption parse_args(int argc, char ** argv)
{
    if(argc == 1)
        return NO_ARGS;
    if(argc == 2)
        return FNAME_ONLY;
    if(argc == 3 && streq(argv[2], "-reverse"))
        return REVERSE;
    if(argc == 4 && streq(argv[2],"-rate"))
        return RATE;
    return INVALID_ARG;
} 

void display_help_menu(void)
{
    printf("Usage: ./wavedit [FILE] [OPTION]...\n\n");
    printf("\t-rate [NUMBER]\tset the sample rate of the file to a value between 0 and 192,000 Hz; lower values sound slower and higher values sound faster\n");
    printf("\t-reverse      \treverse the sound in the file\n\n");
    printf("With no FILE, display this help menu.\n\n");
    printf("Examples:\n");
    printf("\t./wavedit hi_im_a_sound.wav           \toutputs some basic information about the audio file\n");
    printf("\t./wavedit do_the.wav -rate 44000      \tchanges the sample rate of the file to 44,000 Hz\n");
    printf("\t./wavedit wav_our_oceans.wav -reverse\treverses order of the samples in the file to reverse the sound\n\n");
    printf("If you have any issues with this code please don't tell me about it as I don't care\n");
    printf("Jk hmu nkh15@pitt.edu plz don't take off points\n");
}

void populate_wav_header(WAVHeader * file_header, FILE * wav_file)
{
    fread(file_header, sizeof(*file_header), 1, wav_file);
}

void check_if_wav(WAVHeader file_header)
{    
    if(!streqn(file_header.riff_id,"RIFF", sizeof(file_header.riff_id)))
        fail_on_invalid_wav();
    if(!streqn(file_header.wave_id,"WAVE", sizeof(file_header.wave_id)))
        fail_on_invalid_wav();
    if(!streqn(file_header.fmt_id,"fmt ", sizeof(file_header.fmt_id)))
        fail_on_invalid_wav();
    if(!streqn(file_header.data_id,"data", sizeof(file_header.data_id)))
        fail_on_invalid_wav();
    if(file_header.fmt_size != 16)
        fail_on_invalid_wav();
    if(file_header.data_format != 1)
        fail_on_invalid_wav();
    if(!(file_header.number_of_channels == 1 || file_header.number_of_channels == 2))
        fail_on_invalid_wav();
    if(file_header.samples_per_second <= 0 || file_header.samples_per_second > 192000)
        fail_on_invalid_wav();
    if(!(file_header.bits_per_sample == 8 || file_header.bits_per_sample == 16))
        fail_on_invalid_wav();
    if( file_header.bytes_per_second != ( file_header.samples_per_second * (file_header.bits_per_sample/8) * file_header.number_of_channels ) )
        fail_on_invalid_wav();
    if( file_header.block_alignment != ( (file_header.bits_per_sample/8) * file_header.number_of_channels ) )
        fail_on_invalid_wav();
}

void init_and_verify_wav(WAVHeader * file_header, FILE * wav_file)
{
    populate_wav_header(file_header, wav_file);
    check_if_wav(*file_header);
}

void display_wav_info(WAVHeader wav_header)
{
    char * channel_type;
    if(wav_header.number_of_channels == 1)
        channel_type = "mono";
    else
        channel_type = "stereo";
    
    printf("Now playing: a %u-bit %lu Hz %s track.\n",wav_header.bits_per_sample,(long unsigned int)wav_header.samples_per_second,channel_type);
    
    int len_in_samples = wav_header.data_size/wav_header.block_alignment;
    float len_in_seconds = len_in_samples/(float)wav_header.samples_per_second;

    printf("Track length: %.3f seconds (%i samples).\n", len_in_seconds, len_in_samples);
}

void set_sample_rate(WAVHeader * wav_header, int sample_rate, FILE * wav_file)
{
    wav_header->samples_per_second = (uint32_t)sample_rate;
    wav_header->bytes_per_second = wav_header->samples_per_second * (wav_header->bits_per_sample/8) * wav_header->number_of_channels;

    fseek(wav_file, 0, SEEK_SET);
    fwrite(wav_header, 1, sizeof(*wav_header), wav_file);
}

void reverse_sound(WAVHeader * wav_header, FILE * wav_file)
{
    int len_in_samples = wav_header->data_size/wav_header->block_alignment;

    if( wav_header->bits_per_sample == 8 && wav_header->number_of_channels == 1 )
    {
        int8_t samples[len_in_samples];
        fread(samples, sizeof(samples), 1, wav_file);
        reverse_int8_arr(samples, len_in_samples);
        fseek(wav_file, sizeof(WAVHeader), SEEK_SET);
        fwrite(samples, 1, sizeof(samples), wav_file);
    }
    if( (wav_header->bits_per_sample == 16 && wav_header->number_of_channels == 1) || 
    (wav_header->bits_per_sample == 8 && wav_header->number_of_channels == 2) )
    {
        int16_t samples[len_in_samples];
        fread(samples, sizeof(samples), 1, wav_file);
        reverse_int16_arr(samples, len_in_samples);
        fseek(wav_file, sizeof(WAVHeader), SEEK_SET);
        fwrite(samples, 1, sizeof(samples), wav_file);

    }
    if( wav_header->bits_per_sample == 16 && wav_header->number_of_channels == 2 )
    {
        int32_t samples[len_in_samples];
        fread(samples, sizeof(samples), 1, wav_file);
        reverse_int32_arr(samples, len_in_samples);
        fseek(wav_file, sizeof(WAVHeader), SEEK_SET);
        fwrite(samples, 1, sizeof(samples), wav_file);

    }
    
}

int main(int argc, char ** argv) 
{
    CmdOption option = parse_args(argc, argv);
    
    if(option == NO_ARGS)
        display_help_menu();
    if(option == FNAME_ONLY)
    {
        char * filename = argv[1];
        FILE * wav_file = open_file(filename, "rb");
        WAVHeader file_header;
        init_and_verify_wav(&file_header, wav_file);
        display_wav_info(file_header);
        fclose(wav_file);

    }
    if(option == RATE)
    {
        char * filename = argv[1];
        int new_rate = atoi(argv[3]);
        if(new_rate <= 0 || new_rate > 192000)
        {
            printf("Rate must be greater than 0 and less than or equal to 192000 Hz. Exiting...\n");
            exit(1);
        }

        FILE * wav_file = open_file(filename, "rb+");
        WAVHeader file_header;
        init_and_verify_wav(&file_header, wav_file);
        set_sample_rate(&file_header, new_rate, wav_file);
        fclose(wav_file);
    }
    if(option == REVERSE)
    {
        char * filename = argv[1];

        FILE * wav_file = open_file(filename, "rb+");
        WAVHeader file_header;
        init_and_verify_wav(&file_header, wav_file);
        reverse_sound(&file_header, wav_file);
        fclose(wav_file);
    }
    if(option == INVALID_ARG)
    {
        printf("Invalid command line option.\nRun ./wavedit to school yourself on how you should be calling this program!\n");
    }

    // //reverse_sound(&file_header,wav_file);
    // fclose(wav_file);

    //display_help_menu();

    return 0;
}