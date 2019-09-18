#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "mymalloc.h"

// USE THIS GODDAMN MACRO OKAY
#define PTR_ADD_BYTES(ptr, byte_offs) ((void*)(((char*)(ptr)) + (byte_offs)))

// Don't change or remove these constants.
#define MINIMUM_ALLOCATION  16
#define SIZE_MULTIPLE       8

// issa struct
// this is to store the header of each block in the allocator
typedef struct BlockHeader
{
	int dataSize;
	int isBlockFree;
	struct BlockHeader * nextBlock;
	struct BlockHeader * previousBlock;
} BlockHeader;

BlockHeader * headOfHeap = NULL;
BlockHeader * tailOfHeap = NULL;

unsigned int round_up_size(unsigned int data_size) {
	if(data_size == 0)
		return 0;
	else if(data_size < MINIMUM_ALLOCATION)
		return MINIMUM_ALLOCATION;
	else
		return (data_size + (SIZE_MULTIPLE - 1)) & ~(SIZE_MULTIPLE - 1);
}

void printheap()
{
    BlockHeader * currNode = headOfHeap;
	if(currNode == NULL)
	{
		printf("List is empty!\n");
		return;
	}
    while(currNode->nextBlock!=NULL)
    {
        printf("%d,%s -> ",currNode->dataSize, currNode->isBlockFree == 1 ? "free" : "full");
        currNode = currNode->nextBlock;
    }
    printf("%d,%s\n",currNode->dataSize,currNode->isBlockFree == 1 ? "free" : "full");
}

BlockHeader * first_fit(unsigned int dataSize)
{
	BlockHeader * current;
	for(current = headOfHeap; current != NULL; current = current->nextBlock)
	{
		if(current->isBlockFree == 1 && current->dataSize >= dataSize)
		{
			return current;
		}
	}
	return current;
}

void* my_malloc(unsigned int size) {
	if(size == 0)
		return NULL;

	// Round up the size of the requested allocation to a certain size
	size = round_up_size(size);

	// ------- Don't remove anything above this line. -------
	// Here's where you'll put your code!

	unsigned int sizeWithHeader = size + sizeof(BlockHeader);
	BlockHeader * retPtr;

	if(headOfHeap == NULL)
	{
		headOfHeap = sbrk(sizeWithHeader);
		tailOfHeap = headOfHeap;
		retPtr = headOfHeap;
	}
	else
	{
		// Try to find a block to reuse, using first-fit allocation
		retPtr = first_fit(size);
		
		if(retPtr == NULL)
		{
			tailOfHeap->nextBlock = sbrk(sizeWithHeader);
			BlockHeader * newTailOfHeap = tailOfHeap->nextBlock;
			newTailOfHeap->previousBlock = tailOfHeap;
			tailOfHeap = newTailOfHeap;
			retPtr = tailOfHeap;
		}
	}

	retPtr->dataSize = size;
	retPtr->isBlockFree = 0;

	BlockHeader * userPtr = PTR_ADD_BYTES(retPtr, sizeof(BlockHeader));
	return userPtr;
}

void my_free(void* ptr) {
	if(ptr == NULL)
		return;

	BlockHeader * headerPtr = PTR_ADD_BYTES(ptr, -sizeof(BlockHeader));

	// and here's where you free stuff.

	if(headerPtr == tailOfHeap)
	{
		tailOfHeap->isBlockFree = 1;
		
		while(tailOfHeap != headOfHeap && tailOfHeap->isBlockFree == 1)
		{
			BlockHeader * newPrev = tailOfHeap->previousBlock->previousBlock;
			
			tailOfHeap = tailOfHeap->previousBlock;
			tailOfHeap->nextBlock = NULL;
			tailOfHeap->previousBlock = newPrev;
		}
		BlockHeader * endOfPtrData = PTR_ADD_BYTES(tailOfHeap,sizeof(BlockHeader)+tailOfHeap->dataSize);
		brk(endOfPtrData);

		if(tailOfHeap == headOfHeap)
		{
			if(headOfHeap->isBlockFree == 1)
			{
				brk(tailOfHeap);
				headOfHeap = NULL;
				tailOfHeap = NULL;
			}
		}
	}
	else
	{
		headerPtr->isBlockFree = 1;
	}
	
	// -tail's prev is head (then tail = head) (BUT ALSO IF THE HEAD IS FREE, JUST CLEAR THE HEAP)
	// -tail is head (then tail = head = null)
	// else, 
    // tail prev is free (then tail = tail's prev's prev)
    // tail prev is not free (then tail = tail's prev)

}
