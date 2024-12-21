/**_**_*****_*_*********************
 *   Student ID: lun8              *
 *   Student name: Luis Oliveira   *
 ***********************************/

/*-------------------------------------------------------------------
 *
 *  Malloc Project Starter code:
 *        A (bad) malloc - always fails!
 *
 * Project stage 1:
 *        A malloc - modify the code to only request more memory
 *            if we don't have free blocks that can fit the request.
 *            We need to free! And keep track of the memory blocks.
 *            Implement a data structure on each block that allows you
 *            to traverse the list. Search free blocks. And be more
 *            efficient. Don't split or coalesce blocks!
 *
 * Project stage 2:
 *        A more efficient malloc - modify the code to split blocks
 *            when overallocation is imminent. Don't forget to write
 *            new metadata, and to update existing metadata to match
 *            the new state of the linked list. When freeing, coalesce
 *            blocks. If freeing creates a sequence of 2 or 3 free
 *            blocks, merge them into a single one; again, modify all the
 *            relevant metadata to make the linked list consistent
 *
 * Project stage 3:
 *        A faster malloc - modify the code to keep track of a second
 *            linked-list composed of free blocks. To do that, we take
 *            some of the block space reserved for program allocations
 *            but ONLY when the block is free! This is a more traditional
 *            linked list where nodes can be in any order, and thus
 *            requires an explicit node.
 *
 *-------------------------------------------------------------------- */

// C libraries for C things
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>

// This file provides the memory management functions we need for our implementation
#include "memlib.h"
// This file just contains some function declarations
#include "mm.h"

/**
 * This is a hard-coded heap, use it to debug the initial set of functions before starting the malloc implementation
 * It contains 3 blocks:
 *   - 64B free block
 *   - 32B allocated block
 *   - 32B free block
 */
/*
static long fake_heap[22] = {
    -64, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    32, (unsigned long)(&fake_heap), 1, 1, 1, 1,
    -32, (unsigned long)(&fake_heap[10]), 2, 2, 2, 2
};
*/

/* Macros for unscaled pointer arithmetic to keep other code cleaner.
     Casting to a char* has the effect that pointer arithmetic happens at
     the byte granularity (i.e. POINTER_ADD(0x1, 1) would be 0x2).    (By
     default, incrementing a pointer in C has the effect of incrementing
     it by the size of the type to which it points (e.g. Block).)
     We cast the result to void* to force you to cast back to the
     appropriate type and ensure you don't accidentally use the resulting
     pointer as a char* implicitly. Also avoids compiler complaints.
*/
#define UNSCALED_POINTER_ADD(p, x) ((void *)((char *)(p) + (x)))
#define UNSCALED_POINTER_SUB(p, x) ((void *)((char *)(p) - (x)))

/**********************************************************************
 *
 * For a more detailed explanation of these structs, check file mman.h
 * and the project handout
 *
 ***********************************************************************/
typedef struct _BlockInfo
{
    long int size;          // Size of block
    struct _Block *prev;    // Explicit pointer to previous Block
} BlockInfo_t;

typedef struct _FreeBlockInfo
{
    struct _Block *nextFree;    // Explicit pointer to next free Block (stage 3)
    struct _Block *prevFree;    // Explicit pointer to previous free Block (stage 3)
} FreeBlockInfo_t;

typedef struct _Block
{
    BlockInfo_t info;           // Composing both infos into a single struct
    FreeBlockInfo_t freeNode;   //  Think: What does this mean in terms of memory?
} Block_t;

typedef struct
{
    Block_t *malloc_list_tail;  // Use keep track of the tail node
    Block_t *free_list_head;    // Pointer to the first FreeBlockInfo_t in the free list, use in stage 3.
} malloc_info_t;

/* Variable to keep malloc information tidy */
static malloc_info_t malloc_info = {
    .free_list_head = NULL,
    .malloc_list_tail = NULL,
};

/* Alignment of blocks returned by mm_malloc.
 * (We need each allocation to at least be big enough for the free space
 * metadata... so let's just align by that.)    */
#define ALIGNMENT (sizeof(FreeBlockInfo_t))


/************************************************************************
 * Suggested memory management/navigation functions for the project.
 *   Start by implementing these, they will make your life easier and
 *   force you to thing about how the memory navigation/management works.
 ************************************************************************/

// Declarations
Block_t *first_block();
Block_t *next_block(Block_t *block);
void *request_more_space(size_t request_size);
size_t heap_size();

/**
 * This function should get the first block or returns NULL if there is not one.
 * You can use this to start your through search for a block.
 */
Block_t *first_block()
{   
    if (mem_heapsize()==0)
    {
        return NULL;
    }
    //Block_t *first = (Block_t *) 
    /* This code is here to help you debug next_block(), delete this once you do
    #warning This is a reminder, stop using the fake heap once you implement malloc!
    {
        first = (Block_t *)fake_heap;
    }
    // How can we do this using the functions in "memlib.h/c"?*/
    return mem_heap_lo();
}

/**
 * This function will get the adjacent block or returns NULL if there is not one.
 * You can use this to move along your malloc list one block at a time.
 */
Block_t *next_block(Block_t *block)
{

    Block_t *next = NULL;
    Block_t* end = (char*)mem_heap_lo() + mem_heapsize();
    long int x = block->info.size;// x is offset
    if (x < 0) {// free block size is negative
        x*=-1;
    }
    x+= sizeof(BlockInfo_t);// meta data size
    next = (Block_t *)UNSCALED_POINTER_ADD(block,x);// add total increase size 
    if((void*)next >=end)
    {
        return NULL;
    }
    // Are you sure it exists?
    // bound check
    
    return next;
}

/* This function will have the OS allocate more space for our heap.
 *
 * It returns a pointer to that new space. That pointer will always be
 * larger than the last request and be continuous in memory.
 */
void *request_more_space(size_t request_size)
{
    
    // Look into the functions in "memlib.h/c" and request an increase of the heap size
    void *ret = mem_sbrk(request_size+sizeof(BlockInfo_t)); // Should point at the new heap space! So we can initialize it
    
    if (ret == NULL || ret == (void*) -1) // OR whatever type of error you can detect! (hint: it's not null :)
    {
        // Let the program crash if you run out of memory!
        printf("ERROR: failed to request_more_space\n");
        exit(0);
    }
    malloc_info.malloc_list_tail=(Block_t*)ret;
    
    return ret;
}

/* Returns the size of the heap */
size_t heap_size()
{
    size_t size=mem_heapsize();
    // Delete this warning once you don't need the fake heap anymore
    /*#warning This is a reminder, stop using the fake heap once you implement malloc!
    {
        size = sizeof(fake_heap);
    }
    // How can we obtain this using the functions in "memlib.h/c"?*/
    return size;
}

/************************************************************************
 * Suggested heap management/navigation functions for the project.
 *   Start by implementing these, they will make your life easier and
 *   force you to thing about how the memory navigation/management works.
 ************************************************************************/

/******************************* Stage 1 ********************************/
/* Find a free block of at least the requested size in the heap.
    Returns NULL if no free block is large enough. */
Block_t *search_list(size_t request_size)
{
    // ptr_free_block will point to the beginning of the memory heap! stupid name!!!! changed
    Block_t *ptr_curr_block = first_block();
    Block_t* end = (char*)mem_heap_lo() + mem_heapsize();
    long int check_size = -request_size;// transalte request size into free block terms
    while (ptr_curr_block &&ptr_curr_block)// basicly a do while loop

    {
        long int x = ptr_curr_block->info.size; 

        if (x <=check_size) {
          //return block
            return ptr_curr_block;  
        }

        // Move to the next block
        //lastValid = ptr_curr_block;
        ptr_curr_block = next_block(ptr_curr_block);
    }
    // return null;

    // cant find anything
    //
    // You want to go through every block until you hit the end.
    // It should come in handy!
    // YOUR CODE HERE!
    //

    // Return NULL when you cannot find any available node big enough.
    return NULL;
}

/******************************* Stage 2 ********************************/
/* Shrink block to size, and create a new block with remaining space. */
void split(Block_t *block, size_t size) {// empty block block, we want to slice size bytes from it
    remove_free_node(block);
    int totalSize = -(block->info.size);// space able to be sliced. not including header since there will be a block header
    int newSize = totalSize-size-sizeof(BlockInfo_t);//subtract block 1 size, block 2 header size from reserve
    if(newSize>0)
    {
        block->info.size=size;// block is shrunk to size;
        Block_t* newBlock =(Block_t*)UNSCALED_POINTER_ADD(block, (size+sizeof(BlockInfo_t)));// new block plopped after block 1  
        newBlock->info.size = -(newSize);// oldblock's prev stays the samehowever, set block 2 to its correct size
        newBlock->info.prev = block;//   give block 2 a prev
        //insert_free_node(newBlock);
        if (next_block(newBlock)==NULL) {// if there is no next block
            malloc_info.malloc_list_tail = newBlock;// change the tail
        }
        else
        {
            next_block(newBlock)->info.prev=newBlock;// change the next block's prev if there is a next
        }
        //get rid of block
        
        insert_free_node(newBlock);
       

    }
    else
    {// no space for split definitely the problem rn
        block->info.size*=-1;// no space for second node, just use, fragmentation be damned.;
       
        return;
    }
    /*potential problems:
    1.tail not connecting right 
    2.size nonsense with header
    split when freeblocksize >=meta+size;
    pseudocode
    */

}

/* Merge together consecutive free blocks. */
void coalesce(Block_t *block) {


    
    while(block->info.prev&&block->info.prev->info.size<=0)
    {

        block=block->info.prev;
    }// move backwards to the earliest connected free Node
    Block_t* next = next_block(block);
    while(next&&next->info.size<=0)
    {
        remove_free_node(next);
        int reduce = (next->info.size)-sizeof(BlockInfo_t);//always negative size;
        
        //swalloed the next block whole
        block->info.size+=reduce;
        next = next_block(block);
    }
    // met the end or a allocated block
    if(next)
    {
        next->info.prev=block;
    }
    else
    {
        malloc_info.malloc_list_tail=block;
    }
    //insert_free_node(block);

   
    
    //merge backwards until meeting null or allocaed node.

    

}

/******************************* Stage 3 ********************************/
/* Insert free block into the free_list.
 */
void insert_free_node(Block_t *block)
{// there is at least one node in the free list. since other wise this is not called
    /*pesudo oced
    no situation where NULL-insert-NULL
        scenarios
     
        NULL-Insert-Head-A-B-...-NULL->insert at the begining.

        Head-A-B-Insert-C-D-NULL->insert at the middle
        Head-B....-NULL-Insert  ->insert at the end
    
    */
   if(malloc_info.free_list_head==NULL)
   {
    malloc_info.free_list_head=block;
    block->freeNode.nextFree=NULL;
    block->freeNode.prevFree=NULL;
    return;
   }

   if(block<malloc_info.free_list_head)
   {//block ahead of free list head, situation head
    block->freeNode.nextFree=malloc_info.free_list_head;
    block->freeNode.prevFree=NULL;
    malloc_info.free_list_head->freeNode.prevFree=block;//doublely link
    malloc_info.free_list_head=block;
    return;
   }
   Block_t * prev = block->info.prev;
   while(prev->info.size>=0)
   {
        prev = prev->info.prev;// go backward until you find a free node, there will be one cause otherwise itscase 1
   }
   //prev is now a free node
   Block_t* nextFree = prev ->freeNode.nextFree;
    if (nextFree==NULL)//insert at the end
    {//
        prev->freeNode.nextFree=block;
        block->freeNode.prevFree=prev;
        block->freeNode.nextFree=NULL;
        return; 
    }
    prev->freeNode.nextFree=block;
    block->freeNode.prevFree=prev;
    nextFree->freeNode.prevFree=block;
    block->freeNode.nextFree=nextFree;
    return;
   
    
}

/* Remove free block from the free_list.
 */
void remove_free_node(Block_t *block) {
    //TODO : A <-> B <-> C 
    Block_t *prev = block->freeNode.prevFree;
    Block_t *next = block->freeNode.nextFree;
    block->freeNode.nextFree=NULL;//disconnect
    block->freeNode.prevFree=NULL;
    if(next&&prev)
    {
        prev->freeNode.nextFree=next;
        next->freeNode.prevFree=prev;
        return;
        //delinked middle node in the case that its in the middle
    }
    else if(next)//only prev is NULL removing first node
    {
        malloc_info.free_list_head=next;
        next->freeNode.prevFree=NULL;
        return;
    }
    else if (prev)//no next so its the end 
    {
        prev->freeNode.nextFree=NULL;
      
        return;
    }
    else{
        //only free node
        malloc_info.free_list_head=NULL;
        return;
    }

}

/* Find a free block of at least the requested size in the free list.
    Returns NULL if no free block is large enough. */
Block_t *search_free_list(size_t request_size)
{
    Block_t *ptr_free_block = malloc_info.free_list_head;
    long int check_size = -request_size;
    while(ptr_free_block!=NULL)
    {
        if(ptr_free_block->info.size<=check_size)
        {
            return ptr_free_block;
        }
        ptr_free_block=ptr_free_block->freeNode.nextFree;
    }
    // When you are ready, you can implement the free list.
    // YOUR CODE HERE!
    //

    return NULL;
}

// TOP-LEVEL ALLOCATOR INTERFACE ------------------------------------

/* Initialize the allocator. */
int mm_init()
{
    // Modify this function only if you add variables that need to be initialized.
    // This will be called ONCE at the beginning of execution
    malloc_info.free_list_head = NULL;
    malloc_info.malloc_list_tail = NULL;

    // This is here to help you implement next_block(). Delete this once you do!
    //#warning This is a reminder, delete this block once you start implementing malloc!
    //{
        // This function prints the heap
        //examine_heap();
       // return -1;
   // }

    return 0;
}

/* Allocate a block of size size and return a pointer to it. If size is zero,
 * returns null.
 */
void *mm_malloc(size_t size)
{
    Block_t *ptr_free_block = NULL;
    long int request_size;

    // Zero-size requests get NULL.
    if (size == 0) return NULL;

    // Determine the amount of memory we want to allocate
    request_size = size;
    // Round up for correct alignment
    request_size = ALIGNMENT * ((request_size + ALIGNMENT - 1) / ALIGNMENT);
    Block_t *tail =  malloc_info.malloc_list_tail;
    // When you are ready to implement a free list, remove the search_list call
    // and uncomment the search_free_list call below it.
    ptr_free_block = search_free_list(request_size);
    
    if (ptr_free_block==NULL)// can't find space
    {
        ptr_free_block = request_more_space(request_size);
        if (ptr_free_block == NULL) {
            // Out of memory
            return NULL;
        }
        // Initialize the new block metadata
        ptr_free_block->info.size = request_size;
        ptr_free_block->info.prev =tail;
        
        // Update the tail pointer in malloc_info
        malloc_info.malloc_list_tail = ptr_free_block;
    }
    else{
        //if(ptr_free_block->info.size>=(request_size+sizeof(BlockInfo_t)))
        split(ptr_free_block,request_size);
          }
        //else
        //ptr_free_block->info.size = -ptr_free_block->info.size;// if anything goes wrong make this negative again!!!
    
    //#warning This is a reminder, don't use these function after you are done debugging!
    //{
        // This function prints the heap
        //examine_heap();
        // This function checks some common issues with the heap
        //check_heap();
    //}
    return UNSCALED_POINTER_ADD(ptr_free_block, sizeof(BlockInfo_t));
    // ptr_free_block = search_free_list(request_size);


    //
    // YOUR CODE HERE!
    //   IGNORE size and
    //   USE request_size
    //
    // You can change or remove the declarations above.
    // They are included as minor hints.

    // Implement mm_malloc.
    //    You can change or remove any of the above code.
    //    It is included as a suggestion of where to start.
    //
    // Remember to maintain your malloc_info
    //

    // This line is incorrect, make sure you only request more space if needed
  
    //#warning This is a reminder, don't use this after you verified request more space is working
    //{
       // ptr_free_block = request_more_space(0x10);
   // }
    // This funciton will cost you performance, remove it if you are not debugging.
  
}

/* Free the block referenced by ptr. */
void mm_free(void *ptr)
{
    Block_t *block = (Block_t *)UNSCALED_POINTER_SUB(ptr, sizeof(BlockInfo_t));
  
    if(block->info.size>=0)// change the size to free node
    {
        block->info.size=-block->info.size;
        block->freeNode.nextFree=NULL;
        block->freeNode.prevFree=NULL;
        insert_free_node(block);
        coalesce(block);
        
    }

    //put it into the free lsit somewhere
 
    //examine_heap();
        // This function checks some common issues with mthe heap
    //check_heap();
    
    //
    // YOUR CODE HERE!
    //

    // You can change or remove the declarations above.
    // They are included as minor hints.

    // When you are ready... you will want to implement coalescing:
    // coalesce(block);
}

/**********************************************************************
 * PROVIDED FUNCTIONS
 *
 * You do not need to modify these, but they might be helpful to read
 * over.
 *
 * Note: They will need you to correctly implement functions
 *     first_block and heap_size
 **********************************************************************/

/* Print the heap by iterating through it as an implicit free list. */
void examine_heap()
{
    /* print to stderr so output isn't buffered and not output if we crash */
    Block_t *curr = (Block_t *)first_block();
    Block_t *end = (Block_t *)UNSCALED_POINTER_ADD(first_block(), heap_size());
    fprintf(stderr, "====================================================\n");
    fprintf(stderr, "heap size:\t0x%lx\n", heap_size());
    fprintf(stderr, "heap start:\t%p\n", curr);
    fprintf(stderr, "heap end:\t%p\n", end);

    fprintf(stderr, "free_list_head: %p\n", (void *)malloc_info.free_list_head);

    fprintf(stderr, "malloc_list_tail: %p\n", (void *)malloc_info.malloc_list_tail);

    while (curr && curr < end)
    {
        /* print out common block attributes */
        fprintf(stderr, "%p: %ld\t", (void *)curr, curr->info.size);

        /* and allocated/free specific data */
        if (curr->info.size > 0)
        {
            fprintf(stderr, "ALLOCATED\tprev: %p\n", (void *)curr->info.prev);
        }
        else
        {
            fprintf(stderr, "FREE\tnextFree: %p, prevFree: %p, prev: %p\n", (void *)curr->freeNode.nextFree, (void *)curr->freeNode.prevFree, (void *)curr->info.prev);
        }

        curr = next_block(curr);
    }
    fprintf(stderr, "END OF HEAP\n\n");

    curr = malloc_info.free_list_head;
    fprintf(stderr, "Head ");
    while (curr)
    {
        fprintf(stderr, "-> %p ", curr);
        curr = curr->freeNode.nextFree;
    }
    fprintf(stderr, "\n");
}

/* Checks the heap data structure for consistency. */
int check_heap()
{
    Block_t *curr = (Block_t *)first_block();
    Block_t *end = (Block_t *)UNSCALED_POINTER_ADD(first_block(), heap_size());
    Block_t *last = NULL;
    long int free_count = 0;

    while (curr && curr < end)
    {
        if (curr->info.prev != last)
        {
            fprintf(stderr, "check_heap: Error: previous link not correct.\n");
            examine_heap();
        }

        if (curr->info.size <= 0)
        {
            // Free
            free_count++;
        }

        last = curr;
        curr = next_block(curr);
    }

    curr = malloc_info.free_list_head;
    last = NULL;
    while (curr)
    {
        if (curr == last)
        {
            fprintf(stderr, "check_heap: Error: free list is circular.\n");
            examine_heap();
        }
        last = curr;
        curr = curr->freeNode.nextFree;
        if (free_count == 0)
        {
            fprintf(stderr, "check_heap: Error: free list has more items than expected.\n");
            examine_heap();
        }
        free_count--;
    }

    return 0;
}
