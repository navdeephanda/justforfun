#include <stdio.h>
#include <unistd.h>

#include "mymalloc.h"

int main() {
	// You can use sbrk(0) to get the current position of the break.
	// This is nice for testing cause you can see if the heap is the same size
	// before and after your tests, like here.
	void* heap_at_start = sbrk(0);
	//printheap();
	void* block = my_malloc(100);
	//printheap();
	void* yeet = my_malloc(40);
	//printheap();
	void* yote = my_malloc(16);
	//printheap();

	my_free(block);
	printheap();
	my_free(yote);
	printheap();
	void * skrrt = my_malloc(70);
	printheap();
	my_free(yeet);
	printheap();

	my_free(skrrt);
	printheap();

	block = NULL;

	void* heap_at_end = sbrk(0);
	unsigned int heap_size_diff = (unsigned int)(heap_at_end - heap_at_start);

	if(heap_size_diff)
		printf("Hmm, the heap got bigger by %u (0x%X) bytes...\n", heap_size_diff, heap_size_diff);

	// ADD MORE TESTS HERE.

	return 0;
}
