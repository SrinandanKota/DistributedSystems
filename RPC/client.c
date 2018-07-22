#include <stdio.h>
#include <string.h>
#include <time.h>
#include <sys/types.h>
#include <rpc/rpc.h>
#include "date.h"

#define MAX_LEN 100

CLIENT *rpc_setup(char *host);
long get_response(void);
void date(CLIENT *clnt, long *option);
void memory(CLIENT *clnt);
void process(CLIENT *clnt);
void cpu(CLIENT *clnt);

main(int argc, char **argv)
{
  CLIENT *clnt;  /* client handle to server */
  char *server;  /* server */
  long response;

  if(argc != 2)
  {
    fprintf(stderr, "usage:%s hostname\n", argv[0]);
    exit(1);
  }

  server = argv[1];

  if((clnt = rpc_setup(server)) == 0)
    exit(1);	/* cannot connect */ 

  response = get_response();
  
  while(response!=7)
  {
    switch(response)
    {
      case 1: case 2: case 3:
        date(clnt, &response);
      break;
      case 4:
        memory(clnt);
      break;
      case 5:
        process(clnt);
      break;
      case 6:
        cpu(clnt);
      break;
    }
    response = get_response();
  }

  clnt_destroy(clnt);
  exit(0);
}

CLIENT *rpc_setup(char *server)
{
  CLIENT *clnt = clnt_create(server,DATE_PROG,DATE_VERS,"udp");
  if(clnt == NULL)
  {
    clnt_pcreateerror(server);
    return(0);
  }
  return(clnt);
}

long get_response()
{
  long choice;
  printf("Menu: \n");
  printf("1. Date\n");
  printf("2. Time\n");
  printf("3. Both\n");
  printf("4. Memory\n");
  printf("5. Process\n");
  printf("6. CPU\n");
  printf("7. Exit\n");
  printf("Make a choice (1-7):");
  scanf("%ld", &choice);
  return(choice);
}

void date(CLIENT *clnt, long *option)
{
// please implement

// test
//   printf("Client side\n");
//   printf("Printing date,time or both in client side\t");

    printf("Printing date,time or both\t");

    printf("%s\n", *date_1(option,clnt)); //calling rpc server side code and printing the details of date,time or both
	

}


void memory(CLIENT *clnt)
{
// please implement

//hold values from rpc server code
double *array;

//calling rpc server side code
array=memory_1(NULL,clnt);


// test
//   printf("Client side\n");

//   printf("Printing page size in client side: %f\n",array[0]);
//   printf("Printing total number of pages in client side: %f\n",array[1]);
//   printf("Printing total memory in ram in client side: %f\n",array[2]);
//   printf("Printing available pages in client side: %f\n",array[3]);
//   printf("Printing total available memory in ram in client side: %f\n",array[4]);

}

void process(CLIENT *clnt)
{
// please implement

//hold values from rpc server code
double *array;

//calling rpc server side code
array=process_1(NULL,clnt);

// test
//   printf("Client side\n");
//   printf("Printing load values from load function in client side: %f\n", *process_1(NULL,clnt));

}

void cpu(CLIENT *clnt)
{
// please implement

//hold values from rpc server code
double *array;

//calling rpc server side code
array=cpu_1(NULL,clnt);

// test
//printf("Printing number of processes in client code : %f\n",array[0]);
//printf("Printing boot time in client code : %f\n",array[1]);

}
