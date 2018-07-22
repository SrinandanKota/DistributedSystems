#include <rpc/rpc.h>
#include <time.h>
#include <sys/types.h>
#include <linux/kernel.h>
#include <sys/sysinfo.h>
#include <linux/unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include "date.h"

#define MAX_LEN 100

int sysinfo(struct sysinfo *info);

char **date_1(long *option)
{
  struct tm *timeptr;
  time_t clock;
  static char *ptr;
  static char err[] = "Invalid Response \0";
  static char s[MAX_LEN];

  clock = time(0);
  timeptr = localtime(&clock);
  switch(*option)
  {
	case 1:strftime(s,MAX_LEN,"%A, %B %d, %Y",timeptr);
	  ptr=s;
	  break;

	case 2:strftime(s,MAX_LEN,"%T",timeptr);
	  ptr=s;
	  break;
	
	case 3:strftime(s,MAX_LEN,"%A, %B %d, %Y - %T",timeptr);
	  ptr=s;
	  break;

	default: ptr=err;
	  break;
  }

  return(&ptr);
}

double *memory_1(void)
{
//please implement

//variables to hold memory details
double *mptr;
double pag[5];

//calling sysinfo
struct sysinfo info;
sysinfo(&info);

printf("\n");
//printf("In server\n");
printf("Details obtained from sysinfo\n");
printf("Printing Total Ram size/usable memory size: %f\n",(double)info.totalram);
printf("Printing free Ram size/available memory size: %f\n",(double)info.freeram);
printf("Printing Shared Ram size/shared memory size: %f\n",(double)info.sharedram);
printf("Printing Total buffer size/memory in buffer: %f\n",(double)info.bufferram);
printf("\n");


printf("Printing Total Swap details\n");
printf("Printing Total swap size: %f\n",(double)info.totalswap);
printf("Printing Total swap size still available: %f\n",(double)info.freeswap);
printf("\n");



printf("Details for paging system\n");
printf("The page size of the process is : %f\n",(double)getpagesize());
printf("The total number of pages in the system is : %f\n",(double)get_phys_pages());
printf("The physical memory occuied by all the pages: %f\n",(double)(getpagesize()*get_phys_pages()));
printf("The total number of available pages in the system is: %f\n",(double)get_avphys_pages());
printf("The physical memory not occuied by all the pages: %f\n",(double)(getpagesize()*get_avphys_pages()));
printf("\n");


//storing information of page memory to client side
pag[0]=(double)getpagesize();
pag[1]=(double)get_phys_pages();
pag[2]=(double)getpagesize()*get_phys_pages();
pag[3]=(double)get_avphys_pages();
pag[4]=(double)getpagesize()*get_avphys_pages();

mptr=pag;

return mptr;

}

double *process_1(void)
{
//please implement

//containers to store values
double *ptr;
double pr[3];
double load_avg;

//calling sysinfo
struct sysinfo info;
sysinfo(&info);

printf("\n");
//printf("In server\n");
printf("Details obtained from sysinfo\n");
printf("Printing one minute load average  in server code : %f\n",(double)info.loads[0]);
printf("Printing five minute load average  in server code : %f\n",(double)info.loads[1]);
printf("Printing fifteen minute load average  in server code : %f\n",(double)info.loads[2]);
printf("\n");

pr[0]=(double)info.loads[0];
pr[1]=(double)info.loads[1];
pr[2]=(double)info.loads[2];

printf("Details obtained from load averages : %f\n",(double) getloadavg(pr,3));
printf("\n");

load_avg=(double)getloadavg(pr,3);

ptr=&load_avg;

return ptr;

}

double *cpu_1(void)
{

//containers to store values
double *cptr;
double cp[2];

long double a[4];
long double b[4];


//accessing file
FILE *f;
f=fopen("/proc/stat","r");
fscanf(f,"%*s %Lf %Lf %Lf %Lf ",&a[0],&a[1],&a[2],&a[3]);
fclose(f);
sleep(5);

f=fopen("/proc/stat","r");
fscanf(f,"%*s %Lf %Lf %Lf %Lf ",&b[0],&b[1],&b[2],&b[3]);
fclose(f);
sleep(5);


long double loadavg=(double)((b[0]+b[1]+b[2])-(a[0]+a[1]+a[2]))/((b[0]+b[1]+b[2]+b[3])-(a[0]+a[1]+a[2]+a[3]));
printf("CPU utilastion: %lf", loadavg);


//calling sysinfo
struct sysinfo info;
sysinfo(&info);

printf("\n");
//printf("In server\n");
printf("Number of processes : %f\n",(double)info.procs);
printf("Boot time is : %f\n",(double)info.uptime);
printf("\n");


//converting to double to send to client side
cp[0]=(double)info.procs;
cp[1]=(double)info.uptime;

cptr=cp;

return cptr;

//please implement

}

